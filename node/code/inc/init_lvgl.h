#pragma once

#ifdef __cplusplus
extern "C" {
#endif
int init_lvgl(void);
void lv_puts(char *c);
void change_button(void);
void button_ein(void);
void button_aus(void);
#ifdef __cplusplus
}
#endif
