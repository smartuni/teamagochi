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

#include "dispatch_handler.hpp"

class DisplayHandler : public DispatchHandler{
    public:
        DisplayHandler();
        void handleEvent(msg_t *event);
}


#endif /* DISPLAY_HANDLER_T */
