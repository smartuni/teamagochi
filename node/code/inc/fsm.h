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

#ifndef FSM_H
#define FSM_Hs

#ifdef __cplusplus
extern "C" {
#endif

#include "event.h"
#include "events.h"

void *fsm_thread(void * arg);
void fsm_handle(event_t *event);
/**
 * @brief   event handler type definition
 */
typedef handler_result_t (*fsm_handler)(EVENT_T);

#ifdef __cplusplus
}
#endif

#endif /* FSM_H */
