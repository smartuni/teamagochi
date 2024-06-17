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
#include <inttypes.h>

#include "liblwm2m.h"
#include "lwm2m_client.h"
#include "device_model.h"

#define ENABLE_DEBUG    1
#include "debug.h"

#define _USED_INSTANCES(obj) (obj->object.instanceList)
#define _FREE_INSTANCES(obj) (obj->free_instances)

/**
 * @brief Gets the current value of a given @p instance.
 *
 * @param[in, out] data     Initialized data structure.
 * @param[in] instance      Pointer to the instance to get the value from.
 *
 * @return COAP_205_CONTENT on success
 * @return COAP_404_NOT_FOUND if the value is not found
 */
static uint8_t _get_value(lwm2m_data_t *data, lwm2m_obj_pet_inst_t *instance);

/**
 * @brief 'Read' callback for the pet device object.
 *
 * @param[in] instance_id       Instance ID. Should be 0 as a single instance exists.
 * @param[in, out] num_data     Number of resources requested. 0 means all.
 * @param[in, out] data_array   Initialized data array to output the values,
 *                              when @p num_data != 0. Uninitialized otherwise.
 * @param[in] object            Device object pointer
 *
 * @return COAP_205_CONTENT                 on success
 * @return COAP_404_NOT_FOUND               when resource can't be found
 * @return COAP_500_INTERNAL_SERVER_ERROR   otherwise
 */
static uint8_t _read_cb(uint16_t instance_id, int *num_data, lwm2m_data_t **data_array,
                        lwm2m_object_t *object);

/**
 * @brief Sets the current value of a given @p instance.
 *
 * @param[in, out] data     Initialized data structure.
 * @param[in] instance      Pointer to the instance to set the value.
 *
 * @return COAP_205_CONTENT on success
 * @return COAP_404_NOT_FOUND if the value is not found
 */
static uint8_t _set_value(lwm2m_data_t *data, lwm2m_obj_pet_inst_t *instance);

/**
 * @brief 'Write' callback for the LwM2M pet device implementation.
 *
 * @param[in] instance_id       ID of the instance to write resource to.
 * @param[in] num_data          Number of elements in @p data_array.
 * @param[in] data_array        IDs of resources to write and values.
 * @param[in] object            pet device object handle
 *
 * @return COAP_204_CHANGED on success
 * @return COAP_404_NOT_FOUND if the instance was not found
 * @return COAP_400_BAD_REQUEST if a value is not encoded correctly
 * @return COAP_500_INTERNAL_SERVER_ERROR otherwise
 */
static uint8_t _write_cb(uint16_t instance_id, int num_data, lwm2m_data_t * data_array,
                         lwm2m_object_t * object);

/**
 * @brief Get a free instance from the list.
 *
 * @param[in] object        pet device base object
 *
 * @return Instance if available, NULL otherwise
 */
static lwm2m_obj_pet_device_inst_t *_get_instance_from_free_list(
    lwm2m_obj_pet_t *object);

/**
 * @brief Add an instance to the free instance list.
 *
 * @param[out] object   Pet object
 * @param[in] instance  Instance to add to the free list
 */
static void _put_instance_in_free_list(lwm2m_obj_device_t *object,
                                       lwm2m_obj_device_inst_t *instance);

static void _put_instance_in_free_list(lwm2m_obj_device_t *object,
                                       lwm2m_obj_device_inst_t *instance)
{
    assert(object);
    assert(instance);

    instance->list.id = UINT16_MAX;
    instance->list.next = NULL;

    _FREE_INSTANCES(object) = (lwm2m_obj_device_inst_t *)LWM2M_LIST_ADD(
        _FREE_INSTANCES(object), instance
        );
}

static lwm2m_obj_device_inst_t *_get_instance_from_free_list(lwm2m_obj_device_t *object)
{
    assert(object);
    lwm2m_obj_device_inst_t *instance = NULL;

    /* try to allocate an instance, by popping a free node from the list */
    _FREE_INSTANCES(object) = (lwm2m_obj_device_inst_t *)lwm2m_list_remove(
        (lwm2m_list_t *)_FREE_INSTANCES(object), UINT16_MAX, (lwm2m_list_t **)&instance
        );

    return instance;
}

int lwm2m_object_device_init_derived(lwm2m_client_data_t *client_data,
                                  lwm2m_obj_device_t *object,
                                  uint16_t object_id,
                                  lwm2m_obj_device_inst_t *instances,
                                  size_t instance_count)
{
    assert(object);
    assert(instances);

    memset(object, 0, sizeof(lwm2m_obj_device_t));

    /* initialize the wakaama LwM2M object */
    object->object.objID = object_id;
    object->object.readFunc = _read_cb;
    object->object.writeFunc = _write_cb;
    object->object.userData = client_data;

    /* initialize the instances and add them to the free instance list */
    for (unsigned i = 0; i < instance_count; i++) {
        _put_instance_in_free_list(object, &instances[i]);
    }
    return 0;
}

static uint8_t _get_value(lwm2m_data_t *data, lwm2m_obj_device_inst_t *instance)
{
    assert(data);
    assert(instance);

    switch (data->id) {
    case LWM2M_DEVICE_STATUS_ID:
        lwm2m_data_encode_string(instance->id, data);
        break;
    case LWM2M_DEVICE_REGISTER_ID:
        lwm2m_data_encode_string(instance->id, data);
        break;
    case LW2M_DEVICE_TIME_ID:
        lwm2m_data_encode_string(instance->id, data);
        break;
    default:
        return COAP_404_NOT_FOUND;
    }
    return COAP_205_CONTENT;
}

static uint8_t _read_cb(uint16_t instance_id, int *num_data, lwm2m_data_t **data_array,
                        lwm2m_object_t *object)
{
    lwm2m_obj_device_inst_t *instance;
    uint8_t result;
    int i = 0;
    
    /* try to get the requested instance from the object list */
    instance = (lwm2m_obj_device_inst_t *)lwm2m_list_find(object->instanceList,
                                                                    instance_id);
    if (!instance) {
        DEBUG("[lwm2m pet device:read]: can't find instance %d\n", instance_id);
        result = COAP_404_NOT_FOUND;
        goto out;
    }

    /* if the number of resources is not specified, we need to read all resources */
    if (!*num_data) {
        DEBUG("[lwm2m pet device:read]: reading all resources\n");

        uint16_t res_list[] = {
            LWM2M_DEVICE_STATUS_ID,
            LWM2M_DEVICE_REGISTER_ID,
            LW2M_DEVICE_TIME_ID
        };

        /* allocate structures to return resources */
        int res_num = ARRAY_SIZE(res_list);
        *data_array = lwm2m_data_new(res_num);

        if (NULL == *data_array) {
            result = COAP_500_INTERNAL_SERVER_ERROR;
            goto out;
        }

        /* return the number of resources being read */
        *num_data = res_num;

        /* set the IDs of the resources in the data structures */
        for (i = 0; i < res_num; i++) {
            (*data_array)[i].id = res_list[i];
        }
    }

    /* now get the values */
    i = 0;
    do {
        DEBUG("[lwm2m pet device:read]: reading resource %d\n", (*data_array)[i].id);
        result = _get_value(&(*data_array)[i], instance);
        i++;
    } while (i < *num_data && COAP_205_CONTENT == result);

out:
    return result;
}

static uint8_t _set_value(lwm2m_data_t *data, lwm2m_obj_device_inst_t *instance){
    assert(data);
    assert(instance);
    char[CONFIG_LWM2M_STRING_MAX_SIZE] value;
    switch (data->id) {
        case LWM2M_DEVICE_STATUS_ID:
            value = data->value;
            break
        case LWM2M_DEVICE_STATUS_ID:
            value = data->value;
            break;
        case LWM2M_DEVICE_STATUS_ID:
            value = data->value;
            break;
    default:
        return COAP_404_NOT_FOUND;
    }
    instance->write_cb(value);
    return COAP_204_CHANGED;
}

static uint8_t _write_cb(uint16_t instance_id, int num_data, lwm2m_data_t * data_array,
                         lwm2m_object_t * object)
{
    lwm2m_obj_pet_inst_t *instance;
    uint8_t result;
    int i = 0;
    /* try to get the requested instance from the object list */
    instance = (lwm2m_obj_device_inst_t *)lwm2m_list_find(object->instanceList,
                                                       instance_id);
    
    if (!instance) {
        
        DEBUG("[lwm2m pet device:write]: can't find instance %d\n", instance_id);
        result = COAP_404_NOT_FOUND;
        goto out;
    }
    mutex_lock(&instance->mutex);

    i = 0;
    do {
        DEBUG("[lwm2m pet device:write]: write resource %d\n", (data_array)[i].id);
        result = _set_value(&(data_array)[i], instance);
        i++;
    } while (i < num_data && COAP_204_CHANGED == result);
    
    mutex_unlock(&instance->mutex);
out:
    return result;
}

 */
int32_t lwm2m_object_device_instance_create_derived(lwm2m_obj_device_t *object,
                                                    const lwm2m_obj_device_args_t *args)
{
    assert(object);
    assert(args);
    int32_t result = -ENOMEM;
    lwm2m_obj_device_inst_t *instance = NULL;
    uint16_t _instance_id;
    if (object->free_instances == NULL) {
        DEBUG("[lwm2m pet device:Pet]: object not initialized\n");
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
            DEBUG("[lwm2m pet device:Pet]: instance ID %" PRIi32 " is too big\n",
                  args->instance_id);
            result = -EINVAL;
            goto free_out;
        }

        _instance_id = (uint16_t)args->instance_id;

        /* check that the ID is free to use */
        if (LWM2M_LIST_FIND(_USED_INSTANCES(object), _instance_id ) != NULL) {
            DEBUG("[lwm2m pet device:Pet]: instance ID %" PRIi32 " already in use\n",
                  args->instance_id);
            goto free_out;
        }
    }

    /* get a free instance */
    instance = _get_instance_from_free_list(object);
    if (!instance) {
        DEBUG("[lwm2m pet device:Pet]: can't allocate new instance\n");
        goto free_out;
    }

    memset(instance, 0, sizeof(lwm2m_obj_pet_inst_t));

    instance->list.id = _instance_id;
  
    instance->write_cb = args->write_cb;

    /* copy name locally */
    memset(instance->status, 'c', CONFIG_LWM2M_STRING_MAX_SIZE);
    strcpy(instance->status, "default");
    memset(instance->register_code, 'c', CONFIG_LWM2M_STRING_MAX_SIZE);
    strcpy(instance->register_code, "default");
    memset(instance->time, 'c', CONFIG_LWM2M_STRING_MAX_SIZE);
    strcpy(instance->time, "default");
    DEBUG("[lwm2m pet device:Pet]: new instance with ID %d\n", _instance_id);

    /* add the new instance to the list */
    object->object.instanceList = LWM2M_LIST_ADD(object->object.instanceList, instance);
    result = instance->list.id;

free_out:
    mutex_unlock(&object->mutex);
out:
    return result;
}
