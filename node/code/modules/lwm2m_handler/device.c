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

#include "mutex.h"
#include "liblwm2m.h"
#include "lwm2m_client.h"
#include "device.h"

#define ENABLE_DEBUG 1
#include "debug.h"

/**
 * @brief Pet object implementation descriptor.
 */
static lwm2m_obj_pet_device_t _device_object;

/**
 * @brief Pool of object instances.
 */
static lwm2m_obj_pet_device_inst_t _instances[CONFIG_LWM2M_PET_DEVICE_INSTANCES_MAX];
lwm2m_object_t *lwm2m_object_pet_device_init(lwm2m_client_data_t *client_data)
{
    assert(client_data);
    int res = lwm2m_object_pet_device_init_derived(client_data,
                                            &_device_object,
                                            LWM2M_PET_DEVICE_OBJECT_ID,
                                            _instances,
                                            CONFIG_LWM2M_PET_DEVICE_INSTANCES_MAX);

    if (res) {
        DEBUG("[lwm2m:pet_device_derived]: failed to create object\n");
        return NULL;
    }

    return &_device_object.object;
}

int32_t lwm2m_object_pet_device_instance_create(const lwm2m_obj_pet_device_args_t *args)
{
    int32_t result = lwm2m_object_device_instance_create_derived(&_device_object, args);

    if (result) {
        DEBUG("[lwm2m:pet_device_derived]: failed to create instance\n");
    }

    return result;
}

