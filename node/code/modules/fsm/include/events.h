/*
 * Copyright (C) 2024 HAW-Hamburg
 *
 * This file is subject to the terms and conditions of the GNU Lesser
 * General Public License v2.1. See the file LICENSE in the top level
 * directory for more details.
 */

/**
 *
 * @{
 * @file
 * @brief   Events for the Teamagotchi
 *
 * @author  Moritz Holzer <moritz.holzer@haw-hamburg.de>
 * @}  
 */

#ifndef EVENTS_H_T
#define EVENTS_H_T

#ifdef __cplusplus
extern "C" {
#endif

#include "event.h"
#include "thread.h"

/* Enum of all Events */
typedef enum {
    UNDEFINED,
    TERMINATE,
    WILDCARD, // Subscribe to all events [Special case]
    PING,
    PONG,
    BUTTON_OK_PRESSED,
    BUTTON_OK_RELEASED,
    BUTTON_UP_PRESSED,
    BUTTON_UP_RELEASED,
    BUTTON_LEFT_PRESSED,
    BUTTON_LEFT_RELEASED,
    BUTTON_DOWN_PRESSED,
    BUTTON_DOWN_RELEASED,
    BUTTON_RIGHT_PRESSED,
    BUTTON_RIGHT_RELEASED,
    BUTTON_OK_LONG,
    INFO_PRESSED,
    PET_FEED,
    PET_PLAY,
    PET_CLEAN,
    PET_MEDICATE,
    PET_HUNGRY,
    PET_ILL,
    PET_BORED,
    PET_DIRTY,
    VIBRATE,
    INIT,
    REGISTER,
    REGISTERED,
    READY,
    REGISTER_CODE,
    NAME,
    COLOR,
    HAPPINESS,
    WELLBEING,
    HEALTH,
    XP,
    HUNGER,
    CLEANLINESS,
    FUN
}EVENT_T;

typedef struct{
    int32_t id;                                  /**< id of pet */                
    char name[10];                                      /**< name of pet */
    int32_t color;                               /**< color of the pet */
    int32_t happiness;                           /**< happiness of the pet*/
    int32_t wellbeing;                           /**< wellbeing of the pet*/
    int32_t health;                              /**< health of the pet*/
    int32_t xp;                                  /**< XP of the pet*/
    int32_t hunger;                              /**< Hunger of the pet*/
    int32_t cleanliness;                         /**< Cleanliness of the pet*/
    int32_t fun;                                 /**< Fun of the pet*/
    int32_t feed;                                   /**< pet fed */
    int32_t medicate;                               /**< pet medicated */
    int32_t play;                                   /**< pet played */
    int32_t clean;                                  /**< pet cleaned */
    mutex_t mutex;
}pet_stats_t;

typedef struct{
    char code[8];
    mutex_t mutex;
}device_register_code;

/* Enum of the fsm handler return values*/
typedef enum
{
  HANDLED,      //!< Event handled successfully.
  UNHANDLED,    //!< Event could not be handled.
  //!< Handler handled the Event successfully and posted new event to itself.
  TRIGGERED_TO_SELF,
} handler_result_t;


/**
 * @brief Writes the actual pet stats into the parameter pointer
 * 
 */
void get_pet_stats(char *buf);

/**
 * @brief Writes the register code into the parameter pointer
 * 
 * 
 * @param[in] stats The pointer to an pet_stats_t object in which the that gets copied.
 */
char* get_register_code(void);

/**
 * @brief Triggers an Event for the FSM
 *
 * @param[in] event Event which should be send
 */
void trigger_event(EVENT_T event);

void trigger_event_int(EVENT_T event, int32_t value);
void trigger_event_string(EVENT_T event, char* value);
void trigger_event_bool(EVENT_T event, bool value);

/**
 * @brief Sets the Callback for the FSM and starts the event loop
 *
 * @param[in] fsm_callback Function Pointer to the FSM Callback
 */
void events_start(void (*fsm_callback)(EVENT_T event));

/**
 * @brief Sets the PID of the Thread tho which the Events should be send
 *
 * @param[in] pid PID of the Thread
 */
void set_t_events_pid(kernel_pid_t pid);

#ifdef __cplusplus
}
#endif

#endif /* EVENTS_H_T */
