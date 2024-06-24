#pragma once

#ifndef INIT_BUTTONS_T
#define INIT_BUTTONS_T


#ifdef __cplusplus
extern "C" {
#endif

#include <stdio.h>
#include "thread.h"
#include "board.h"
#include "periph/gpio.h"
#include "events.h"


//TODO Workaround
extern kernel_pid_t dispatcher_pid;

//Buttons initialization functions prototypes:
int init_buttons(void);

void button_up_callback (void *arg);
void button_left_callback (void *arg);
void button_down_callback (void *arg);
void button_right_callback (void *arg);
void button_ok_callback (void *arg);

//Screen functions:
void screen_off(void);
void screen_on(void);

//Vibration functions prototypes:
void vibrate(uint16_t msec);

#ifdef __cplusplus
}
#endif

#endif /* INIT_BUTTONS_T */
