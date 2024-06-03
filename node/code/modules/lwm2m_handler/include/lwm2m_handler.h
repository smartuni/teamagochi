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
 * @author      Moritz Holzer <moritz.holzer@haw-hamburg.de>
 * @}
 */
#ifndef LWM2M_TEAMAGOTCHI_HANDLER_T
#define LWM2M_TEAMAGOTCHI_HANDLER_T

#include "events.h"
#include "event.h"

#ifndef LWM2M_SERVER_ID
#define LWM2M_SERVER_ID 0
#endif


void lwm2m_handler_init(void);

void lwm2m_handler_start(void);

int lwm2m_handler_cli(int argc, char **argv);

handler_result_t lwm2m_handleEvent(EVENT_T event);


#endif /* LWM2M_TEAMAGOTCHI_HANDLER_T */
