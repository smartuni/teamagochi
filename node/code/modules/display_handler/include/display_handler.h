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
 * @brief       Wakaama LwM2M Handler
 *
 * @author      
 * @}
 */
#ifndef DISPLAY_HANDLER_T
#define DISPLAY_HANDLER_T

#include "events.h"

handler_result_t displayHandler_handleEvent(EVENT_T event);
void startDisplayThread(void);
void display_init(void);

#endif /* DISPLAY_HANDLER_T */
