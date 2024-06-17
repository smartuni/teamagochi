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
 * @brief    Vibration Module and Buttons Initialization   
 *
 * @author   Eduard Lomtadze <eduard.lomtadze@haw-hamburg.de>   
 * @}
 */

#include "init_buttons.h"
#include "ztimer.h"

#define ENABLE_DEBUG  1
#include "debug.h"

//Define the physical GPIO Pins used for buttons:
gpio_t button_ok = GPIO_PIN(0, 5); //PIN A1
gpio_t button_right = GPIO_PIN(0, 30); //PIN sA2
gpio_t button_up = GPIO_PIN(0, 28); //PIN A3
gpio_t button_down = GPIO_PIN(0, 2); //PIN A4
gpio_t button_left = GPIO_PIN(0, 3); //PIN A5

//Define the vibration module GPIO: 
gpio_t vibr_gpio = GPIO_PIN(1, 9);
gpio_mode_t vibr_gpio_mode = GPIO_OUT; //Define the vibr. GPIO as Output GPIO

bool long_pressed = false;

int init_buttons(void)
{
    puts("Buttons initialization...");

    //Initialize button interrupts and define callback funcs:
    gpio_init_int(button_up, GPIO_IN, GPIO_BOTH, button_up_callback, NULL);
    gpio_init_int(button_left, GPIO_IN, GPIO_BOTH, button_left_callback, NULL);
    gpio_init_int(button_down, GPIO_IN, GPIO_BOTH, button_down_callback, NULL);
    gpio_init_int(button_right, GPIO_IN, GPIO_BOTH, button_right_callback, NULL);
    gpio_init_int(button_ok, GPIO_IN, GPIO_BOTH, button_ok_callback, NULL);
    
    puts("Vibration Module initialization...");
    
    //Initialize vibration module
    gpio_init(vibr_gpio, vibr_gpio_mode);
    gpio_clear(vibr_gpio);
    return 0;
}

void timer_long_pressed_cb(void *arg) {
    (void) arg;
    long_pressed = true;
    DEBUG("Hallo vom Timer");
}

//Vibrate for msec milliseconds:
void vibrate(uint16_t msec) {
    
    gpio_set(vibr_gpio);
    ztimer_sleep(ZTIMER_MSEC, msec);
    gpio_clear(vibr_gpio);
}

void button_up_callback (void *arg)
{
    (void) arg; /* the argument is not used */
    if (!gpio_read(button_up)) {
        //DEBUG("Button up pressed!\n");
        trigger_event(BUTTON_UP_PRESSED);
    }
    else {
        //DEBUG("Button up released!\n");
        trigger_event(BUTTON_UP_RELEASED);
    }
}

void button_left_callback (void *arg)
{
    (void) arg; /* the argument is not used */
    if (!gpio_read(button_left)) {
        //DEBUG("Button left pressed!\n");
        trigger_event(BUTTON_LEFT_PRESSED);
    }
    else {
        //DEBUG("Button left released!\n");
        trigger_event(BUTTON_LEFT_RELEASED);
    }
}

void button_down_callback (void *arg)
{
    (void) arg; /* the argument is not used */
    if (!gpio_read(button_down)) {
        //DEBUG("Button down pressed!\n");
        trigger_event(BUTTON_DOWN_PRESSED);
    }
    else {
        //DEBUG("Button down released!\n");
        trigger_event(BUTTON_DOWN_RELEASED);
    }
}

void button_right_callback (void *arg)
{
    (void) arg; /* the argument is not used */
    if (!gpio_read(button_right)) {
        //DEBUG("Button right pressed!\n");
        trigger_event(BUTTON_RIGHT_PRESSED);
    }
    else {
        //DEBUG("Button right released!\n");
        trigger_event(BUTTON_RIGHT_RELEASED);
    }
}

void button_ok_callback (void *arg)
{
    (void) arg; /* the argument is not used */
    if (!gpio_read(button_ok)) {
        long_pressed = false;
        ztimer_t timeout = { .callback=timer_long_pressed_cb };
        ztimer_set(ZTIMER_SEC, &timeout, 2);
    }
    else {
        if (long_pressed) {
            long_pressed = false;
            DEBUG("Button ok long pressed!\n");
            trigger_event(BUTTON_OK_LONG);
        }
        else {
            DEBUG("Button ok pressed!\n");
            //ztimer_remove(&timeout);
            trigger_event(BUTTON_OK_PRESSED);
        }
        DEBUG("Button ok released!\n");
        trigger_event(BUTTON_OK_RELEASED);
    }
}
//EOF
