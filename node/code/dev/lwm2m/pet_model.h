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

#include "liblwm2m.h"
#include "net/credman.h"
#include "lwm2m_client.h"
#include "lwm2m_client_config.h"

#ifdef __cplusplus
}
#endif

/**
 * @brief Resources of the LwM2M device object instance
 *
 * @see 32769.xml
 */
enum lwm2m_pet_resources {
    LWM2M_RES_PET_ID,             /**< Id of the pet on the device */
    LWM2M_RES_PET_HUNGRY,         /**< Tell the device that the pet is hungry */
    LWM2M_RES_PET_ILL,            /**< Tell the device that the pet is ill */
    LWM2M_RES_PET_BORED,          /**< Tell the device that the pet is bored */
    LWM2M_RES_PET_DIRTY,          /**< Tell the device that the pet is dirty */
    LWM2M_RES_PET_FED,            /**< pet has been fed */
    LWM2M_RES_PET_MEDICATED,      /**< pet has been medicated */
    LWM2M_RES_PET_PLAYED,         /**< the pet has played */
    LWM2M_RES_PET_CLEANED,        /**< the pet has been cleaned */
};

/**
 * @brief Initialize the Pet object.
 *
 * @param[in] client_data  LwM2M client data.
 *
 * @return Pointer to the Pet object on success
 */
lwm2m_object_t *lwm2m_object_pet_init(lwm2m_client_data_t *client_data);

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
// static uint8_t _execute_cb(uint16_t instance_id, uint16_t resource_id, uint8_t *buffer, int length,
//                            lwm2m_object_t *object);


// /**
//  * @brief 'Read' callback for the Pet object.
//  *
//  * @param[in] instance_id       Instance ID. Should be 0 as a single instance exists.
//  * @param[in, out] num_data     Number of resources requested. 0 means all.
//  * @param[in, out] data_array   Initialized data array to output the values,
//  *                              when @p num_data != 0. Uninitialized otherwise.
//  * @param[in] object            Device object pointer
//  *
//  * @return COAP_205_CONTENT                 on success
//  * @return COAP_404_NOT_FOUND               when resource can't be found
//  * @return COAP_500_INTERNAL_SERVER_ERROR   otherwise
//  */
// static uint8_t _read_cb(uint16_t instance_id, int *num_data, lwm2m_data_t **data_array,
//                         lwm2m_object_t *object);

// /**
//  * @brief 'Discover' callback for the Pet object.
//  *
//  * @param[in] instance_id       Instance ID. Should be 0 as a single instance exists.
//  * @param[in, out] num_data     Number of resources requested. 0 means all.
//  * @param[in, out] data_array   Initialized data array to determine if the resource exists,
//  *                              when @p num_data != 0. Uninitialized otherwise.
//  * @param[in] object            Device object pointer
//  *
//  * @return COAP_205_CONTENT                 on success
//  * @return COAP_404_NOT_FOUND               when a resource is not supported
//  * @return COAP_500_INTERNAL_SERVER_ERROR   otherwise
//  */
// static uint8_t _discover_cb(uint16_t instance_id, int *num_data, lwm2m_data_t **data_array,
//                             lwm2m_object_t *object);

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

                        

#endif /* OBJECTS_TEAMAGOTCHI_PET_H */
/** @} */
