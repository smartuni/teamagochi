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
 * @brief    IO Handler: Vibration module and buttons init   
 *
 * @author   Eduard Lomtadze <eduard.lomtadze@haw-hamburg.de>   
 * @}
 */

#define ENABLE_DEBUG  1
#include "debug.h"

#include "init_buttons.h"

void IoHandler::init(){
    init_buttons();
}

void IoHandler::handleEvent(msg_t *event){
    DEBUG("[IoHandler:handleEvent]\n");
    switch(event->type){
        case EVENTS::VIBRATE :
            DEBUG("[IoHandler:handleEvent]: vibrate\n");
            vibrate();
            break;
    }
}
