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

#ifndef LWM2M_SERVER_ID
#define LWM2M_SERVER_ID 0
#endif

# define OBJ_COUNT (4)

#include "dispatch_handler.hpp"
#include "liblwm2m.h"
#include "lwm2m_client.h"

class Lwm2mHandler : public DispatchHandler{

    private:
        lwm2m_object_t *obj_list[OBJ_COUNT];
        lwm2m_client_data_t client_data;

    public:
        Lwm2mHandler();

        void lwm2m_handler_init(void);

        void lwm2m_handler_start(void);

        int lwm2m_handler_cli(int argc, char **argv);

        void handleEvent(msg_t *event);

        
};

#endif /* LWM2M_TEAMAGOTCHI_HANDLER_T */
