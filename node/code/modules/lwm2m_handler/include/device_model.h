 /**
  * @{
  * @brief Pet object for the Teamagotchi project
  * 
  * For an XML description of the object see
  * 420.xml
  *
  * ## Resources
  * 
    |         Name            | ID | Mandatory |  Type   |  Range | Units |
    | ----------------------- | -- | --------- | ------- | ------ | ----- | 
    | Status                  |  1 |    Yes    | String  |        |       | 
    | Register Code           |  2 |    No     | String  |        |       |     
    | Time                    |  3 |    No     | String  |        |       |     
  * 
  * Original code was by: Moritz Holzer <moritz.holzer@haw-hamburg.de>
  * See pet_modal.h
  * @}
  */

#pragma once

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>

#include "mutex.h"
#include "liblwm2m.h"
#include "lwm2m_client.h"

#ifndef CONFIG_LWM2M_STRING_MAX_SIZE
#define CONFIG_LWM2M_STRING_MAX_SIZE 10
#endif

#define LWM2M_DEVICE_STATUS_ID 0
#define LWM2M_DEVICE_REGISTER_ID 1
#define LW2M_DEVICE_TIME_ID 2


// /**
//  * @brief Callback for writing the status value.
//  *
//  * @param[in]  EVENT_ID  ID of LWM2M Item
//  *
//  * @return 0 on success
//  * @return <0 otherwise
//  */
typedef void lwm2m_obj_device_write_cb_t(uint16_t EVENT_ID);

/**
 * @brief Arguments for the creation of a pet device object.
 */
typedef struct lwm2m_obj_device_args {
    int32_t instance_id;                            /**< ID for the new instance. It must be between 0 and (UINT16_MAX - 1),
                                                         if -1 the next available ID will be used. */
    lwm2m_obj_device_write_cb_t *write_cb;               /**< Callback to write the pet values. May be NULL. need that for the lwm2m handler*/
} lwm2m_obj_device_args_t;


/*Descriptor of a LwM2M pet object instance */
typedef struct lwm2m_obj_device_inst {
    lwm2m_list_t list;                           /**< list handle */            
    char status[CONFIG_LWM2M_STRING_MAX_SIZE];
    char register_code[CONFIG_LWM2M_STRING_MAX_SIZE];
    char time[CONFIG_LWM2M_STRING_MAX_SIZE];
    
    lwm2m_obj_device_write_cb_t *write_cb;          /**< Callback to write the values. May be NULL. need that for the lwm2m handler*/
    mutex_t mutex;                               /**< Mutex for writing the values*/
} lwm2m_obj_device_inst_t;

/**
 * @brief LwM2M Pet object
 */
typedef struct lwm2m_obj_device {
    lwm2m_object_t object;                 /**< LwM2M object base */
    lwm2m_obj_device_inst_t *free_instances;  /**< List of instances */
    mutex_t mutex;                         /**< Mutex for the object */
} lwm2m_obj_device_t;

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
int32_t lwm2m_object_device_instance_create_derived(lwm2m_obj_device_t *object,
                                                 const lwm2m_obj_device_args_t *args);

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
int lwm2m_object_device_init_derived(lwm2m_client_data_t *client_data,
                                  lwm2m_obj_device_t *object,
                                  uint16_t object_id,
                                  lwm2m_obj_device_inst_t *instances,
                                  size_t instance_count);

/**
 * @brief Sets the status and trigger a notification
 *        to the observing servers, if any.
 *
 * @param[in] client_data   Pointer to the LwM2M client.
 * @param[in] object        Pointer to the LwM2M IPSO object.
 * @param[in] instance_id   ID of the instance to update.
 */
void lwm2m_object_device_status(const lwm2m_client_data_t *client_data,uint16_t instance_id,
                          const lwm2m_object_t *object);
