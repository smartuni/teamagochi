/*
 * Copyright (C) 
 *
 * This file is subject to the terms and conditions of the GNU Lesser
 * General Public License v2.1. See the file LICENSE in the top level
 * directory for more details.
 */

/**
 *
 * @{
 * @file
 * @brief       
 *
 * @author      
 */

#ifndef EVENTS_H_T
#define EVENTS_H_T

#ifdef __cplusplus
extern "C" {
#endif

#include "event.h"
#include "thread.h"

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
    PET_FEED,
    PET_PLAY,
    PET_CLEAN,
    PET_MEDICATE,
    PET_HUNGRY,
    PET_ILL,
    PET_BORED,
    PET_DIRTY,
    VIBRATE,
}EVENT_T;

typedef struct {
    event_t super_event;
    EVENT_T event;
} team_event_t;


typedef enum
{
  EVENT_HANDLED,      //!< Event handled successfully.
  EVENT_UN_HANDLED,    //!< Event could not be handled.
  //!< Handler handled the Event successfully and posted new event to itself.
  TRIGGERED_TO_SELF,
}handler_result_t;

void trigger_event(EVENT_T event);
void events_start(void (*fsm_callback)(EVENT_T event));
void set_t_events_pid(kernel_pid_t pid);

#ifdef __cplusplus
}
#endif

#endif /* EVENTS_H_T */
