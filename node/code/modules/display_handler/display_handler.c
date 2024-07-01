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
#include "init_lvgl.h"
#include "lvgl_riot.h"
#include "lvgl/lvgl.h"
#include "display_handler.h"

#define ENABLE_DEBUG  1
#include "debug.h"

char display_thread_stack [DISPLAY_STACKSIZE];

char game_thread_stack [DISPLAY_STACKSIZE];

handler_result_t displayHandler_handleEvent(EVENT_T event){
    char buf[100];
    DEBUG("[DisplayHandler:handleEvent]\n");
     switch(event){
        case BUTTON_OK_PRESSED:
            enter_pressed();
        break;
        case BUTTON_OK_RELEASED:
            enter_released();
        break;
        case BUTTON_UP_PRESSED:
            up_pressed();
        break;
        case BUTTON_UP_RELEASED:
            up_released();
        break;
        case BUTTON_DOWN_PRESSED:
            down_pressed();
        break;
        case BUTTON_DOWN_RELEASED:
            down_released();
            //init_registered_pet();
        break;
        case BUTTON_LEFT_PRESSED:
            left_pressed();
        break;
        case BUTTON_LEFT_RELEASED:
            left_released();
        break;
        case BUTTON_RIGHT_PRESSED:
            right_pressed();
        break;
        case BUTTON_RIGHT_RELEASED:
            right_released();
        break;
        case REGISTER_CODE:
            init_not_registered_code(get_register_code());
            break;
        case REGISTERED:
            init_registered_no_pet();
            break;
        case READY:
            init_registered_pet();
            break;
        case INFO_PRESSED:
            get_pet_stats((char*)&buf);
            init_pet_stats((char*)&buf);
            break;
        case GAME_START:
            DEBUG("[DisplayHandler:handleEvent]: GAME_START\n");
            thread_create(game_thread_stack, sizeof(game_thread_stack),
                  THREAD_PRIORITY_MAIN - 1, THREAD_CREATE_WOUT_YIELD, game_loop, NULL, "Snake Game");
            break;
        default:
        break;
     }
    lvgl_wakeup();
    return HANDLED;
}

void *display_run(void * arg){
    (void) arg;
    DEBUG("[DisplayHandler:init]: initialising\n");
    lvgl_run();
    return NULL;
}

void startDisplayThread(void) {
    thread_create(display_thread_stack, sizeof(display_thread_stack),
                  THREAD_PRIORITY_MAIN - 1, THREAD_CREATE_WOUT_YIELD, display_run, NULL, "DisplayHandler for Class");             
}

void display_init(void){
    init_lvgl();
}
