#pragma once

#ifndef INIT_LVGL_T
#define INIT_LVGL_T


#ifdef __cplusplus
extern "C" {
#endif

#include "lvgl/lvgl.h"
#include "thread.h"
//TODO Workaround
extern kernel_pid_t dispatcher_pid_lvgl;

void *lv_timer_thread(void *arg);
int init_lvgl(void);
void lv_puts(char *c);
void change_button(void);
void image_switch(void);
void down_click(void);
void enter_pressed(void);
void enter_released(void);
void up_pressed(void);
void up_released(void);
void down_pressed(void);
void down_released(void);
void left_pressed(void);
void left_released(void);
void right_pressed(void);
void right_released(void);

#ifdef __cplusplus
}
#endif

#endif /* INIT_LVGL_T */
