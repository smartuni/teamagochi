#pragma once

#ifdef __cplusplus
extern "C" {
#endif

#include "lvgl/lvgl.h"


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
#ifdef __cplusplus
}
#endif
