/*
 * Copyright (C) 2018 Beduino Master Projekt - University of Bremen
 *               2021 HAW Hamburg
 *
 * This file is subject to the terms and conditions of the GNU Lesser
 * General Public License v2.1. See the file LICENSE in the top level
 * directory for more details.
 */ 
 /**
  * @{
  * @brief Pet object for the Teamagotchi project
  * 
  * @author Moritz Holzer <moritz.holzer@haw-hamburg.de>
  * @}
  */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "liblwm2m.h"
#include "lwm2m_client.h"
#include "pet_model.h"

#define ENABLE_DEBUG    1
#include "debug.h"

#define _USED_INSTANCES(obj) (obj->object.instanceList)
#define _FREE_INSTANCES(obj) (obj->free_instances)

/**
 * @brief 'Execute' callback for the Pet object.
 *
 * @param[in] instance_id       Instance ID. Should be 0 as a single instance exists.
 * @param[in] resource_id       ID of the resource to execute.
 * @param[in] buffer            Information needed for the execution.
 * @param[in] length            Length of @p buffer.
 * @param[in] object            Device object pointer
 *
 * @return COAP_204_CHANGED             on success
 * @return COAP_404_NOT_FOUND           when wrong instance specified
 * @return COAP_400_BAD_REQUEST         when wrong information has been sent
 * @return COAP_405_METHOD_NOT_ALLOWED  when trying to execute a resource that is not supported
 */
static uint8_t _exec_cb(uint16_t instance_id, uint16_t resource_id, uint8_t *buffer, int length,
                           lwm2m_object_t *object);

/**
 * @brief Get a free instance from the list.
 *
 * @param[in] object        IPSO sensor base object
 *
 * @return Instance if available, NULL otherwise
 */
static lwm2m_obj_pet_inst_t *_get_instance_from_free_list(
    lwm2m_obj_pet_t *object);

/**
 * @brief Add an instance to the free instance list.
 *
 * @param[out] object   Pet object
 * @param[in] instance  Instance to add to the free list
 */
static void _put_instance_in_free_list(lwm2m_obj_pet_t *object,
                                       lwm2m_obj_pet_inst_t *instance);

static void _put_instance_in_free_list(lwm2m_obj_pet_t *object,
                                       lwm2m_obj_pet_inst_t *instance)
{
    assert(object);
    assert(instance);

    instance->list.id = UINT16_MAX;
    instance->list.next = NULL;

    _FREE_INSTANCES(object) = (lwm2m_obj_pet_inst_t *)LWM2M_LIST_ADD(
        _FREE_INSTANCES(object), instance
        );
}

static lwm2m_obj_pet_inst_t *_get_instance_from_free_list(lwm2m_obj_pet_t *object)
{
    assert(object);
    lwm2m_obj_pet_inst_t *instance = NULL;

    /* try to allocate an instance, by popping a free node from the list */
    _FREE_INSTANCES(object) = (lwm2m_obj_pet_inst_t *)lwm2m_list_remove(
        (lwm2m_list_t *)_FREE_INSTANCES(object), UINT16_MAX, (lwm2m_list_t **)&instance
        );

    return instance;
}

int lwm2m_object_pet_init_derived(lwm2m_client_data_t *client_data,
                                  lwm2m_obj_pet_t *object,
                                  uint16_t object_id,
                                  lwm2m_obj_pet_inst_t *instances,
                                  size_t instance_count)
{
    assert(object);
    assert(instances);
    memset(object, 0, sizeof(lwm2m_obj_pet_t));

    /* initialize the wakaama LwM2M object */
    object->object.objID = object_id;
    object->object.readFunc = NULL;
    object->object.executeFunc = _exec_cb;
    object->object.userData = client_data;

    /* initialize the instances and add them to the free instance list */
    for (unsigned i = 0; i < instance_count; i++) {
        _put_instance_in_free_list(object, &instances[i]);
    }
    return 0;
}

static uint8_t _exec_cb(uint16_t instance_id, uint16_t resource_id, uint8_t *buffer, int length,
                        lwm2m_object_t *object)
{
    (void)buffer;
    (void)length;

    lwm2m_obj_pet_inst_t *instance;
    uint8_t result;

    /* try to get the requested instance from the object list */
    instance = (lwm2m_obj_pet_inst_t *)lwm2m_list_find(object->instanceList,
                                                                    instance_id);
    if (!instance) {
        DEBUG("[lwm2m Pet:exec]: can't find instance %d\n", instance_id);
        result = COAP_404_NOT_FOUND;
        goto out;
    }

    switch (resource_id) {
    case LWM2M_PET_HUNGRY_ID:
        instance->hungry = true;
        result = COAP_204_CHANGED;
        break;
    case LwM2M_PET_ILL_ID:
        instance->ill = true;
        result = COAP_204_CHANGED;
        break;
    case LwM2M_PET_BORED_ID:
        instance->bored = true;
        result = COAP_204_CHANGED;
        break;
    case LwM2M_PET_DIRTY_ID:
        instance->dirty = true;
        result = COAP_204_CHANGED;
        break;
    default:
        result = COAP_405_METHOD_NOT_ALLOWED;
        break;
    }

out:
    return result;
}

int32_t lwm2m_object_pet_instance_create_derived(lwm2m_obj_pet_t *object,
                                                 const lwm2m_obj_pet_args_t *args)
{
    assert(object);
    assert(args);

    int32_t result = -ENOMEM;
    lwm2m_obj_pet_inst_t *instance = NULL;
    uint16_t _instance_id;
    if (object->free_instances == NULL) {
        DEBUG("[lwm2m:Pet]: object not initialized\n");
        result = -EINVAL;
        goto out;
    }

    mutex_lock(&object->mutex);

    /* determine ID for new instance */
    if (args->instance_id < 0) {
        _instance_id = lwm2m_list_newId((lwm2m_list_t *)_USED_INSTANCES(object));
    }
    else {
        /* sanity check */
        if (args->instance_id >= (UINT16_MAX - 1)) {
            DEBUG("[lwm2m:Pet]: instance ID %" PRIi32 " is too big\n",
                  args->instance_id);
            result = -EINVAL;
            goto free_out;
        }

        _instance_id = (uint16_t)args->instance_id;

        /* check that the ID is free to use */
        if (LWM2M_LIST_FIND(_USED_INSTANCES(object), _instance_id ) != NULL) {
            DEBUG("[lwm2m:Pet]: instance ID %" PRIi32 " already in use\n",
                  args->instance_id);
            goto free_out;
        }
    }

    /* get a free instance */
    instance = _get_instance_from_free_list(object);
    if (!instance) {
        DEBUG("[lwm2m:Pet]: can't allocate new instance\n");
        goto free_out;
    }

    memset(instance, 0, sizeof(lwm2m_obj_pet_inst_t));

    instance->list.id = _instance_id;
    instance->hungry = false;
    instance->ill = false;
    instance->bored = false;
    instance->dirty = false;
    instance->fed = false;
    instance->medicated = false;
    instance->played = false;
    instance->cleaned = false;
    //instance->read_cb = args->read_cb;
    //instance->read_cb_arg = args->read_cb_arg;

    /* copy name locally */
    //memset(instance->name, 'c', CONFIG_LWM2M_PET_NAME_MAX_SIZE);
    //strcpy(instance->name, "default");
    DEBUG("[lwm2m:Pet]: new instance with ID %d\n", _instance_id);

    /* add the new instance to the list */
    object->object.instanceList = LWM2M_LIST_ADD(object->object.instanceList, instance);
    result = instance->list.id;

free_out:
    mutex_unlock(&object->mutex);
out:
    return result;
}

int32_t lwm2m_pet_is_hungry(uint16_t instance_id,
                        lwm2m_object_t *object)
{
    lwm2m_obj_pet_inst_t *instance;
    int32_t result;

    /* try to get the requested instance from the object list */
    instance = (lwm2m_obj_pet_inst_t *)lwm2m_list_find(object->instanceList,
                                                                    instance_id);
    if (!instance) {
        DEBUG("[lwm2m Pet:is_hungry]: can't find instance %d\n", instance_id);
        result = -1;
        goto out;
    }
    result = instance->hungry;
out:
    return result;
}
