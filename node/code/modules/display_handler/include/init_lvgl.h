#pragma once

#ifdef __cplusplus
extern "C" {
#endif

#include "lvgl/lvgl.h"


#ifndef ILI9341_PARAM_CS
// PIN 10 on the feather
#define ILI9341_PARAM_CS GPIO_PIN(0, 27)
#endif
#ifndef ILI9341_PARAM_DCX
// PIN 11 on the feather
#define ILI9341_PARAM_DCX GPIO_PIN(0, 6)
#endif

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
