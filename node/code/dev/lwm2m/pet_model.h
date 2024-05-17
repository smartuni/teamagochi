 /*
 * Copyright (C) 2021 HAW Hamburg
 *
 * This file is subject to the terms and conditions of the GNU Lesser
 * General Public License v2.1. See the file LICENSE in the top level
 * directory for more details.
 */
 
 
 
 
 /**
  * @{
  * @brief Pet object for the Teamagotchi project
  * 
  * For an XML description of the object see
  * 32769.xml
  *
  * ## Resources
  * 
|         Name            | ID | Mandatory |  Type   |  Range | Units |
| ----------------------- | -- | --------- | ------- | ------ | ----- | 
| Id                      |  0 |    Yes    | Integer |        |       |
|Name                     |  1 |    Yes    | String  |        |       |   
| Hungry                  |  2 |    Yes    |         |        |       |  
| Ill                     |  3 |    Yes    |         |        |       |   
| Bored                   |  4 |    Yes    |         |        |       |   
| Dirty                   |  5 |    Yes    |         |        |       |
| fed                     |  6 |    Yes    | Boolean |        |       |    
| medicated               |  7 |    Yes    | Boolean |        |       |
| played                  |  8 |    Yes    | Boolean |        |       |
| cleaned                 |  9 |    Yes    | Boolean |        |       |
  * 
  * @author Moritz Holzer <moritz.holzer@haw-hamburg.de>
  * @}
  */

#ifndef OBJECTS_TEAMAGOTCHI_PET_H
#define OBJECTS_TEAMAGOTCHI_PET_H


#ifdef __cplusplus
extern "C" {
#endif

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>

#include "mutex.h"
#include "liblwm2m.h"
#include "lwm2m_client.h"

#ifndef CONFIG_LWM2M_PET_NAME_MAX_SIZE
#define CONFIG_LWM2M_PET_NAME_MAX_SIZE 10
#endif

#define LWM2M_PET_ID 0
#define LWM2M_PET_HUNGRY_ID 1
#define LwM2M_PET_ILL_ID 2
#define LwM2M_PET_BORED_ID 3
#define LwM2M_PET_DIRTY_ID 4
#define LWM2M_PET_FED_ID 5
#define LWM2M_PET_MEDICATED_ID 6
#define LWM2M_PET_PLAYED_ID 7
#define LWM2M_PET_CLEANED_ID 8

// /**
//  * @brief Callback for reading the sensor value.
//  *
//  * @param[in]  read_cb_arg  Data passed for the read callback when the instance was created.
//  * @param[out] value       Pointer to the variable where the value will be stored.
//  *
//  * @return 0 on success
//  * @return <0 otherwise
//  */
// typedef int lwm2m_obj_pet_read_cb_t(void *read_cb_arg, int16_t *value);

/**
 * @brief Arguments for the creation of a pet object.
 */
typedef struct lwm2m_obj_pet_args {
    int32_t instance_id;                            /**< ID for the new instance. It must be between 0 and (UINT16_MAX - 1),
                                                         if -1 the next available ID will be used. */
    //void *read_cb_arg;                              /**< Data to pass to the read callback. May be NULL. need that when we want sensor values*/
    //lwm2m_obj_pet_read_cb_t *read_cb;               /**< Callback to read the pet values. May be NULL. need that when we want sensor values*/
} lwm2m_obj_pet_args_t;


/*Descriptor of a LwM2M pet object instance */
typedef struct lwm2m_obj_pet_inst {
    lwm2m_list_t list;                           /**< list handle */
    uint8_t id;                                  /**< id of pet */                
    char name[CONFIG_LWM2M_PET_NAME_MAX_SIZE];   /**< name of pet */
    bool hungry;                                 /**< pet hungry */
    bool ill;                                    /**< pet ill */
    bool bored;                                  /**< pet bored */
    bool dirty;                                  /**< pet dirty */
    bool fed;                                    /**< pet fed */
    bool medicated;                              /**< pet medicated */
    bool played;                                 /**< pet played */
    bool cleaned;                                /**< pet cleaned */
    //void *read_cb_arg;                           /**< Data to pass to the read callback. May be NULL. need that when we want sensor values*/ 
    //lwm2m_obj_pet_read_cb_t *read_cb;            /**< Callback to read the pet values. May be NULL. need that when we want sensor values*/
    mutex_t mutex;                               /**< Mutex for writing the values*/
} lwm2m_obj_pet_inst_t;

/**
 * @brief LwM2M Pet object
 */
typedef struct lwm2m_obj_pet {
    lwm2m_object_t object;                 /**< LwM2M object base */
    lwm2m_obj_pet_inst_t *free_instances;  /**< List of instances */
    mutex_t mutex;                         /**< Mutex for the object */
} lwm2m_obj_pet_t;

/**
 * @brief   Create a new object instance based on the Pet and add it to the
 *          @p object list.
 *
 * @param[in, out] object           Pointer to the LwM2M Pet object.
 * @param[in]      args             Initialize structure with the parameter for the instance. May
 *                                  not be NULL.
 *
 * @retval instance ID (>0) on success
 * @retval <0 otherwise
 */
int32_t lwm2m_object_pet_instance_create_derived(lwm2m_obj_pet_t *object,
                                                 const lwm2m_obj_pet_args_t *args);

/**
 * @brief   Initialize the a LwM2M Pet object.
 *
 * @param[in, out] client_data      Pointer to the LwM2M client.
 * @param[in, out] object           Pointer to the LwM2M Pet object.
 * @param[in]      instances        List of allocated instances.
 * @param[in]      instance_count   Number of allocated instances.
 *
 * @retval 0 on success
 * @retval <0 otherwise
 */
int lwm2m_object_pet_init_derived(lwm2m_client_data_t *client_data,
                                  lwm2m_obj_pet_t *object,
                                  uint16_t object_id,
                                  lwm2m_obj_pet_inst_t *instances,
                                  size_t instance_count);

/**
 * @brief Determines if the pet is hungry
 *
 * @return 1 pet is hungry
 * @return 0 pet isn't hungry
 * @return -1 instance not found
 */
int32_t lwm2m_pet_is_hungry(uint16_t instance_id,
                        lwm2m_object_t *object);

// /**
//  * @brief 'Write' callback for the Pet object.
//  *
//  * @param[in] instance_id       ID of the instance to write to
//  * @param[in] num_data          Number of resources to write
//  * @param[in] data_array        Array of data to write
//  * @param[in] object            Security object pointer
//  *
//  * @retval COAP_204_CHANGED                 on success
//  * @retval COAP_400_BAD_REQUEST             otherwise
//  */
// static uint8_t _write_cb(uint16_t instance_id, int num_data, lwm2m_data_t *data_array,
//                          lwm2m_object_t *object);

                        
#ifdef __cplusplus
}
#endif

#endif /* OBJECTS_TEAMAGOTCHI_PET_H */
/** @} */
