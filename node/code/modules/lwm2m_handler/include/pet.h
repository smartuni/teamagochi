
#ifndef OBJECTS_TEAMAGOTCHI_PET_DERIVED_H
#define OBJECTS_TEAMAGOTCHI_PET_DERIVED_H

#ifdef __cplusplus
extern "C" {
#endif

#include "pet_model.h"
/**
 * @defgroup lwm2m_objects_pet_config LwM2M Pet object compile configurations
 * @ingroup lwm2m_client_config
 * @{
 */
/**
 * @brief Maximum number of instances of the object
 */
#ifndef CONFIG_LWM2M_PET_INSTANCES_MAX
#define CONFIG_LWM2M_PET_INSTANCES_MAX (1U)
#endif
/** @} */

/**
 * @brief LwM2M Pet object ID
 */
#define LWM2M_PET_OBJECT_ID 32769

/**
 * @brief   Initialize the pet object handle
 *
 * @param[in] client_data   Pointer to the LwM2M client data.
 *
 * @return Pointer to the global handle of the pet object.
 */
lwm2m_object_t *lwm2m_object_pet_init(lwm2m_client_data_t *client_data);

/**
 * @brief   Create a new Pet instance.
 *
 * @param[in]      args             Initialize structure with the parameter for the instance. Must
 *                                  not be NULL.
 *
 * @return > 0 value representing the instance ID if the instance was created successfully.
 * @return <0 otherwise
 */
int32_t lwm2m_object_pet_instance_create(const lwm2m_obj_pet_args_t *args);

/**
 * @brief Update the value of the illuminance sensor and trigger a notification
 *        to the observing servers, if any.
 *
 * @param[in] client_data   Pointer to the LwM2M client.
 * @param[in] instance_id   ID of the instance to update.
 * @param[in] value         New value for the sensor.
 */
void lwm2m_object_pet_update_value(const lwm2m_client_data_t *client_data,
                                           uint16_t instance_id, int16_t value);

#ifdef __cplusplus
}
#endif

#endif /* OBJECTS_TEAMAGOTCHI_PET_DERIVED_H */
/** @} */
