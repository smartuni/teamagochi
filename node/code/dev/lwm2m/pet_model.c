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

#include "kernel_defines.h"
#include "liblwm2m.h"
#include "pet_model.h"
#include "lwm2m_client_config.h"

#define ENABLE_DEBUG    0
#include "debug.h"

// static bool hungry;
// static bool ill;
// static bool bored;
// static bool dirty;

/**
 * @brief 'Discover' callback for the Pet object.
 *
 * @param[in] instance_id       Instance ID. Should be 0 as a single instance exists.
 * @param[in, out] num_data     Number of resources requested. 0 means all.
 * @param[in, out] data_array   Initialized data array to determine if the resource exists,
 *                              when @p num_data != 0. Uninitialized otherwise.
 * @param[in] object            Device object pointer
 *
 * @return COAP_205_CONTENT                 on success
 * @return COAP_404_NOT_FOUND               when a resource is not supported
 * @return COAP_500_INTERNAL_SERVER_ERROR   otherwise
 */
static uint8_t _discover_cb(uint16_t instance_id, int *num_data, lwm2m_data_t **data_array,
                            lwm2m_object_t *object);



/*Descriptor of a LwM2M pet object instance */
typedef struct {
    uint8_t *id;                 /**< types of power sources (0-7) */
    uint16_t *power_voltage;     /**< voltage of power sources in mV */
    uint16_t *power_current;     /**< current of power sources in mA */
    uint8_t battery_status;      /**< battery status (0-6) */
    uint32_t mem_total;          /**< amount of memory on the device in kB */
    uint16_t(*ext_dev_info)[2];  /**< external devices information */
    uint8_t ext_dev_info_len;    /**< amount of external devices information */
    uint8_t error_code[7];       /**< error codes */
    uint8_t error_code_used;     /**< amount of error codes used */
} pet_data_t;

typedef struct {
    lwm2m_list_t list;              /**< Linked list handle */
} lwm2m_obj_pet_inst_t;

static const lwm2m_obj_pet_inst_t _instance;


/**
 * @brief Implementation of the object interface for the Device Object.
 */
static lwm2m_object_t _pet_object = {
    .next           = NULL,
    .objID          = 5,
    .instanceList   = (lwm2m_list_t *)&_instance,
    .readFunc       = NULL,
    .executeFunc    = NULL,
    .discoverFunc   = _discover_cb,
    .writeFunc      = NULL,
    .deleteFunc     = NULL,
    .createFunc     = NULL,
    .userData       = NULL
};

static uint8_t _discover_cb(uint16_t instance_id, int *num_data, lwm2m_data_t **data_array,
                            lwm2m_object_t *object)
{
    uint8_t result;
    int i;

    (void)object;

    if (instance_id != 0) {
        return COAP_404_NOT_FOUND;
    }

    result = COAP_205_CONTENT;

    if (*num_data == 0) {
        /* This list must contain all available resources */
        uint16_t res[] = {
            LWM2M_RES_PET_ID, LWM2M_RES_PET_HUNGRY, LWM2M_RES_PET_ILL,
            LWM2M_RES_PET_BORED, LWM2M_RES_PET_DIRTY, LWM2M_RES_PET_FED,
            LWM2M_RES_PET_MEDICATED,LWM2M_RES_PET_PLAYED,LWM2M_RES_PET_CLEANED,
        };
        int len = ARRAY_SIZE(res);

        *data_array = lwm2m_data_new(len);
        *num_data = len;

        if (*data_array == NULL) {
            DEBUG("[lwm2m:pet:discover] could not allocate data array\n");
            return COAP_500_INTERNAL_SERVER_ERROR;
        }

        for (i = 0; i < len; i++) {
            (*data_array)[i].id = res[i];
        }
    }
    else {
        /* Check if each given resource is present */
        for (i = 0; i < *num_data && result == COAP_205_CONTENT; i++) {
            switch ((*data_array)[i].id) {
                case LWM2M_RES_PET_ID:
                case LWM2M_RES_PET_HUNGRY:
                case LWM2M_RES_PET_ILL:
                case LWM2M_RES_PET_BORED:
                case LWM2M_RES_PET_DIRTY:
                case LWM2M_RES_PET_FED:
                case LWM2M_RES_PET_MEDICATED:
                case LWM2M_RES_PET_PLAYED:
                case LWM2M_RES_PET_CLEANED:
                    break;
                default:
                    result = COAP_404_NOT_FOUND;
            }
        }
    }

    return result;
}

lwm2m_object_t *lwm2m_get_pet_device(void)
{
    lwm2m_object_t *obj;

    obj = (lwm2m_object_t *)lwm2m_malloc(sizeof(lwm2m_object_t));

    if (obj == NULL) {
        goto err_out;
    }

    memset(obj, 0, sizeof(lwm2m_object_t));
    obj->instanceList = (lwm2m_list_t *)lwm2m_malloc(sizeof(lwm2m_list_t));

    if (obj->instanceList == NULL) {
        goto free_obj;
    }

    memset(obj->instanceList, 0, sizeof(lwm2m_list_t));

    obj->objID = LWM2M_DEVICE_OBJECT_ID;

    obj->readFunc = prv_device_read;
    obj->writeFunc = prv_device_write;
    obj->executeFunc = prv_device_execute;
    obj->discoverFunc = prv_device_discover;

    return obj;

free_obj:
    lwm2m_free(obj);

err_out:
    return NULL;
}

void lwm2m_free_object_device(lwm2m_object_t *obj)
{
    if (obj == NULL) {
        return;
    }
    if (obj->userData) {
        lwm2m_free(obj->userData);
    }
    if (obj->instanceList) {
        lwm2m_free(obj->instanceList);
    }
    lwm2m_free(obj);
}
