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
 * @brief    IO Handler: Vibration module and buttons initialization   
 *
 * @author   Eduard Lomtadze <eduard.lomtadze@haw-hamburg.de>   
 * @}
 */

#define ENABLE_DEBUG  0
#include "debug.h"

#include "init_buttons.h"
#include "io_handler.hpp"

IoHandler::IoHandler(){

}

void IoHandler::init(){
    dispatcher_pid = DispatchHandler::getPID();
    init_buttons();
}

void IoHandler::handleEvent(msg_t *event){
    DEBUG("[IoHandler:handleEvent]\n");
    (void) event;
    // switch(event->type){
    //     case EVENTS::VIBRATE :
    //         DEBUG("[IoHandler:handleEvent]: vibrate\n");
    //         vibrate();
    //         break;
    // }s
}
