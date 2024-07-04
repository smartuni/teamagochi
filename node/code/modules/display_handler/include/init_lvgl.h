#ifndef INIT_LVGL_T
#define INIT_LVGL_T


#ifdef __cplusplus
extern "C" {
#endif

#include "lvgl/lvgl.h"
#include "thread.h"

int init_lvgl(void);
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
void init_not_registered_code(char* code);
void change_top_bar_text(char* top_bar_text);
void init_registered_no_pet(void);
void init_registered_pet(void);
void init_pet_stats(char* stats);
void showDeadScreen(void);

void * game_loop(void * arg);

#ifdef __cplusplus
}
#endif

#endif /* INIT_LVGL_T */
