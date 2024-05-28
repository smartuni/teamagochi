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

//TODO Workaround
kernel_pid_t dispatcher_pid;
uint16_t BUTTON_OK_PRESSED = 4;
uint16_t BUTTON_OK_RELEASED = 5;
uint16_t BUTTON_UP_PRESSED = 6;
uint16_t BUTTON_UP_RELEASED = 7;
uint16_t BUTTON_LEFT_PRESSED = 8;
uint16_t BUTTON_LEFT_RELEASED = 9;
uint16_t BUTTON_DOWN_PRESSED = 10;
uint16_t BUTTON_DOWN_RELEASED = 11;
uint16_t BUTTON_RIGHT_PRESSED = 12;
uint16_t BUTTON_RIGHT_RELEASED = 13;



//TODO: change the actual pin layout according to PCB Design
gpio_t button_ok = GPIO_PIN(0, 5); //PIN A1
gpio_t button_right = GPIO_PIN(0, 30); //PIN sA2
gpio_t button_up = GPIO_PIN(0, 28); //PIN A3
gpio_t button_down = GPIO_PIN(0, 2); //PIN A4
gpio_t button_left = GPIO_PIN(0, 3); //PIN A5

gpio_t vibr_gpio = GPIO_PIN(1, 9);
gpio_mode_t vibr_gpio_mode = GPIO_OUT;

int init_buttons(void)
{
    puts("Buttons initialization...");

    gpio_init_int(button_up, GPIO_IN, GPIO_BOTH, button_up_callback, NULL);
    gpio_init_int(button_left, GPIO_IN, GPIO_BOTH, button_left_callback, NULL);
    gpio_init_int(button_down, GPIO_IN, GPIO_BOTH, button_down_callback, NULL);
    gpio_init_int(button_right, GPIO_IN, GPIO_BOTH, button_right_callback, NULL);
    gpio_init_int(button_ok, GPIO_IN, GPIO_BOTH, button_ok_callback, NULL);
    
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
        message.type = BUTTON_UP_PRESSED;
        msg_try_send(&message, dispatcher_pid);
    }
    else {
        printf("Button up released!\n");
        message.type = BUTTON_UP_RELEASED;
        msg_try_send(&message, dispatcher_pid);
    }
}

void button_left_callback (void *arg)
{
    (void) arg; /* the argument is not used */
    msg_t message;
    if (!gpio_read(button_left)) {
        printf("Button left pressed!\n");
        message.type = BUTTON_LEFT_PRESSED;
        msg_try_send(&message, dispatcher_pid);
    }
    else {
        printf("Button left released!\n");
        message.type = BUTTON_LEFT_RELEASED;
        msg_try_send(&message, dispatcher_pid);
    }
}

void button_down_callback (void *arg)
{
    (void) arg; /* the argument is not used */
    msg_t message;
    if (!gpio_read(button_down)) {
        printf("Button down pressed!\n");
        message.type = BUTTON_DOWN_PRESSED;
        msg_try_send(&message, dispatcher_pid);
    }
    else {
        printf("Button down released!\n");
        message.type = BUTTON_DOWN_RELEASED;
        msg_try_send(&message, dispatcher_pid);
    }
}

void button_right_callback (void *arg)
{
    (void) arg; /* the argument is not used */
    msg_t message;
    if (!gpio_read(button_right)) {
        printf("Button right pressed!\n");
        message.type = BUTTON_RIGHT_PRESSED;
        msg_try_send(&message, dispatcher_pid);
    }
    else {
        printf("Button right released!\n");
        message.type = BUTTON_RIGHT_RELEASED;
        msg_try_send(&message, dispatcher_pid);
    }
}

void button_ok_callback (void *arg)
{
    (void) arg; /* the argument is not used */
    msg_t message;
    if (!gpio_read(button_ok)) {
        printf("Button ok pressed!\n");
        message.type = BUTTON_OK_PRESSED;
        msg_try_send(&message, dispatcher_pid);
    }
    else {
        printf("Button ok released!\n");
        message.type = BUTTON_OK_RELEASED;
        msg_try_send(&message, dispatcher_pid);
    }
}


