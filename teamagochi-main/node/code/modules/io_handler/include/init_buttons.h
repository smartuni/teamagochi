#pragma once

#ifndef INIT_BUTTONS_T
#define INIT_BUTTONS_T


#ifdef __cplusplus
extern "C" {
#endif

#include "thread.h"
//TODO Workaround
extern kernel_pid_t dispatcher_pid;

int init_buttons(void);

void button_up_callback (void *arg);
void button_left_callback (void *arg);
void button_down_callback (void *arg);
void button_right_callback (void *arg);
void button_ok_callback (void *arg);

#ifdef __cplusplus
}
#endif

#endif /* INIT_BUTTONS_T */