/**
 * @brief Device name used to register at the LwM2M server
 */
#ifndef CONFIG_LWM2M_DEVICE_NAME
#define CONFIG_LWM2M_DEVICE_NAME "urn:t8i:dev:<SOMETHING>"
#endif

/**
 * @brief Device object manufacturer string
 */
#ifndef CONFIG_LWM2M_DEVICE_MANUFACTURER
#define CONFIG_LWM2M_DEVICE_MANUFACTURER "Sose 2024"
#endif

/**
 * @brief Device object model.
 *
 * @note Defaults to the board name
 */
#ifndef CONFIG_LWM2M_DEVICE_MODEL
#define CONFIG_LWM2M_DEVICE_MODEL RIOT_BOARD
#endif

/**
 * @brief Device object serial number
 */
#ifndef CONFIG_LWM2M_DEVICE_SERIAL
#define CONFIG_LWM2M_DEVICE_SERIAL "000000"
#endif

/**
 * @brief Device object firmware version
 *
 * @note Defaults to the running RIOT version
 */
#ifndef CONFIG_LWM2M_DEVICE_FW_VERSION
#define CONFIG_LWM2M_DEVICE_FW_VERSION RIOT_VERSION
#endif

/**
 * @brief Device object device type
 */
#ifndef CONFIG_LWM2M_DEVICE_TYPE
#define CONFIG_LWM2M_DEVICE_TYPE "Teamagotchi"
#endif

/**
 * @brief Device object hardware version
 *
 * @note Defaults to the board name
 */
#ifndef CONFIG_LWM2M_DEVICE_HW_VERSION
#define CONFIG_LWM2M_DEVICE_HW_VERSION RIOT_BOARD
#endif

/**
 * @brief Device object software version
 *
 * @note Defaults to the running RIOT version
 */
#ifndef CONFIG_LWM2M_DEVICE_SW_VERSION
#define CONFIG_LWM2M_DEVICE_SW_VERSION RIOT_VERSION
#endif
