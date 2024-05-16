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

# define OBJ_COUNT (4)

void lwm2m_handler_init(void);

void lwm2m_handler_start(void);

void *handle_thread(void *arg);

int lwm2m_handler_cli(int argc, char **argv);
