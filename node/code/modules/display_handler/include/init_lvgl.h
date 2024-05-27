#pragma once

#ifdef __cplusplus
extern "C" {
#endif

#include "lvgl/lvgl.h"

int init_lvgl(void);
void lv_puts(char *c);
void change_button(void);
void image_switch(void);
void down_click(void);
#ifdef __cplusplus
}
#endif
