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
#include "pet.h"

#define ENABLE_DEBUG 1
#include "debug.h"

/**
 * @brief Pet object implementation descriptor.
 */
static lwm2m_obj_pet_t _pet_object;

/**
 * @brief Pool of object instances.
 */
static lwm2m_obj_pet_inst_t _instances[CONFIG_LWM2M_PET_INSTANCES_MAX];

lwm2m_object_t *lwm2m_object_pet_init(lwm2m_client_data_t *client_data)
{
    assert(client_data);
    int res = lwm2m_object_pet_init_derived(client_data,
                                            &_pet_object,
                                            LWM2M_PET_OBJECT_ID,
                                            _instances,
                                            CONFIG_LWM2M_PET_INSTANCES_MAX);

    if (res) {
        DEBUG("[lwm2m:pet_derived]: failed to create object\n");
        return NULL;
    }

    return &_pet_object.object;
}

int32_t lwm2m_object_pet_instance_create(const lwm2m_obj_pet_args_t *args)
{
    int32_t result = lwm2m_object_pet_instance_create_derived(&_pet_object, args);

    if (result) {
        DEBUG("[lwm2m:pet_derived]: failed to create instance\n");
    }

    return result;
}

