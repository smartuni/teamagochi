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


#include "thread.h"
#include "display_handler.hpp"
#include "init_lvgl.h"
#include "lvgl_riot.h"
#include "lvgl/lvgl.h"

#define ENABLE_DEBUG  1
#include "debug.h"

DisplayHandler::DisplayHandler(){
}

void DisplayHandler::handleEvent(msg_t *event){
    DEBUG("[DisplayHandler:handleEvent]\n");
     switch(event->type){
        case EVENTS::BUTTON_OK_PRESSED:
            enter_pressed();
        break;
        case EVENTS::BUTTON_OK_RELEASED:
            enter_released();
        break;
        case EVENTS::BUTTON_UP_PRESSED:
            up_pressed();
        break;
        case EVENTS::BUTTON_UP_RELEASED:
            up_released();
        break;
        case EVENTS::BUTTON_DOWN_PRESSED:
            down_pressed();
        break;
        case EVENTS::BUTTON_DOWN_RELEASED:
            down_released();
        break;
        case EVENTS::PIC_SWITCH:
           image_switch();
        break;
        case EVENTS::DOWN_CLICK:
            down_click();
        break;
     }
     lvgl_wakeup();

}

void DisplayHandler::display_run(){
    DEBUG("[DisplayHandler:init]: initialising\n");
    lvgl_run();
}

void DisplayHandler::startDisplayThread() {
    cout << "Starting display thread" << endl;
    this->display_thread_pid = thread_create(display_thread_stack, sizeof(display_thread_stack),
                  THREAD_PRIORITY_MAIN - 1, THREAD_CREATE_WOUT_YIELD, DisplayHandler::displayInitThread, this, "DisplayHandler for Class");             
}

void DisplayHandler::display_init(){
    init_lvgl();
}
