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


#include "display_handler.hpp"
#include "init_lvgl.h"

#define ENABLE_DEBUG  1
#include "debug.h"

void DisplayHandler::handleEvent(msg_t *event){
    DEBUG("[DisplayHandler:handleEvent]\n");

     switch(event->type){
        case EVENTS::BUTTON_OK_PRESS:
           change_button(); 
        break;
     }

}
