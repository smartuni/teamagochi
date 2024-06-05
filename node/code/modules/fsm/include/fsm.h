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
 * @brief    FSM for the Teamagotchi Project   
 *
 * @author  Moritz Holzer <moritz.holzer@haw-hamburg.de>
 */

#ifndef FSM_H
#define FSM_Hs

#ifdef __cplusplus
extern "C" {
#endif

#include "events.h"

/**
 * @brief Starts the Thread of the FSM
 */
void fsm_start_thread(void);

/**
 * @brief   event handler type definition
 */
typedef handler_result_t (*fsm_handler)(EVENT_T);

#ifdef __cplusplus
}
#endif

#endif /* FSM_H */
