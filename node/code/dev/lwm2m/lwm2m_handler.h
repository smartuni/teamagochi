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

#include "board.h"
#include "xtimer.h"
#include "lwm2m_client.h"
#include "lwm2m_client_objects.h"
#include "lwm2m_platform.h"
#include "objects/common.h"
#include "objects/device.h"

# define OBJ_COUNT (2)

void lwm2m_handler_init(void);

void lwm2m_handler_start(void);

void *handle_thread(void *arg);
