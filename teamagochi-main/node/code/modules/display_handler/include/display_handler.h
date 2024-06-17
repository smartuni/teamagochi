/*
 * Copyright (C) 2024 HAW Hamburg
 *
 * This file is subject to the terms and conditions of the GNU Lesser
 * General Public License v2.1. See the file LICENSE in the top level
 * directory for more details.
 */

/**
 * @{
 *
 * @file
 * @brief       Display Handler
 *
 * @author      Moritz Holzer <moritz.holzer@haw-hamburg.de>
 * @}
 */
#ifndef DISPLAY_HANDLER_T
#define DISPLAY_HANDLER_T

#include "events.h"

/**
 * @brief Handler for the FSM states
 *
 * @param[in] event Event which should be handled
 */
handler_result_t displayHandler_handleEvent(EVENT_T event);

/**
 * @brief Starts the Thread of the Display Handler
 */
void startDisplayThread(void);

/**
 * @brief Inits the GUI
 */
void display_init(void);

#endif /* DISPLAY_HANDLER_T */
