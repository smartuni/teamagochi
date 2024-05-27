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

#include <stdio.h>

#include "board.h"
#include "periph/gpio.h"



char rcv_thread_stack[IO_HANDLER_STACKSIZE];


//TODO: change the actual pin layout according to PCB Design
gpio_t button_up = GPIO_PIN(0, 5); //PIN A1
gpio_t button_left = GPIO_PIN(0, 30); //PIN A2
gpio_t button_down = GPIO_PIN(0, 28); //PIN A3
gpio_t button_right = GPIO_PIN(0, 2); //PIN A4
gpio_t button_extra = GPIO_PIN(0, 3); //PIN A5

gpio_t vibr_gpio = GPIO_PIN(1, 9);
gpio_mode_t vibr_gpio_mode = GPIO_OUT;

int init_buttons(void)
{
    puts("Buttons initialization...");

    gpio_init_int(button_up, GPIO_IN, GPIO_BOTH, button_up_callback, NULL);
    gpio_init_int(button_left, GPIO_IN, GPIO_BOTH, button_left_callback, NULL);
    gpio_init_int(button_down, GPIO_IN, GPIO_BOTH, button_down_callback, NULL);
    gpio_init_int(button_right, GPIO_IN, GPIO_BOTH, button_right_callback, NULL);
    gpio_init_int(button_extra, GPIO_IN, GPIO_BOTH, button_extra_callback, NULL);
    
    puts("Vibration Module initialization...");
    
    gpio_init(vibr_gpio, vibr_gpio_mode);
    gpio_clear(vibr_gpio);
    return 0;
}

void button_up_callback (void *arg)
{
    (void) arg; /* the argument is not used */
    msg_t message;
    if (!gpio_read(button_up)) {
        printf("Button up pressed!\n");
        message.type = EVENTS::BUTTON_UP_PRESSED;
        msg_try_send(&message, dispatcher->getPID());
    }
    else {
        printf("Button up released!\n");
        message.type = EVENTS::BUTTON_UP_RELEASED;
        msg_try_send(&message, dispatcher->getPID());
    }
}

void button_left_callback (void *arg)
{
    (void) arg; /* the argument is not used */
    msg_t message;
    if (!gpio_read(button_left)) {
        printf("Button left pressed!\n");
        message.type = EVENTS::BUTTON_LEFT_PRESSED;
        msg_try_send(&message, dispatcher->getPID());
    }
    else {
        printf("Button left released!\n");
        message.type = EVENTS::BUTTON_LEFT_RELEASED;
        msg_try_send(&message, dispatcher->getPID());
    }
}

void button_down_callback (void *arg)
{
    (void) arg; /* the argument is not used */
    msg_t message;
    if (!gpio_read(button_down)) {
        printf("Button down pressed!\n");
        message.type = EVENTS::BUTTON_DOWN_PRESSED;
        msg_try_send(&message, dispatcher->getPID());
    }
    else {
        printf("Button down released!\n");
        message.type = EVENTS::BUTTON_DOWN_RELEASED;
        msg_try_send(&message, dispatcher->getPID());
    }
}

void button_right_callback (void *arg)
{
    (void) arg; /* the argument is not used */
    msg_t message;
    if (!gpio_read(button_right)) {
        printf("Button right pressed!\n");
        message.type = EVENTS::BUTTON_RIGHT_PRESSED;
        msg_try_send(&message, dispatcher->getPID());
    }
    else {
        printf("Button right released!\n");
        message.type = EVENTS::BUTTON_RIGHT_RELEASED;
        msg_try_send(&message, dispatcher->getPID());
    }
}

void button_extra_callback (void *arg)
{
    (void) arg; /* the argument is not used */
    msg_t message;
    if (!gpio_read(button_extra)) {
        printf("Button extra pressed!\n");
        message.type = EVENTS::BUTTON_EXTRA_PRESSED;
        msg_try_send(&message, dispatcher->getPID());
    }
    else {
        printf("Button extra released!\n");
        message.type = EVENTS::BUTTON_EXTRA_RELEASED;
        msg_try_send(&message, dispatcher->getPID());
    }
}


