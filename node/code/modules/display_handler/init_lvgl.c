/*
 * Copyright (C) 2019 Inria
 *
 * This file is subject to the terms and conditions of the GNU Lesser
 * General Public License v2.1. See the file LICENSE in the top level
 * directory for more details.
 */

/**
 * @ingroup     tests
 * @{
 *
 * @file
 * @brief       LVGL example application
 *
 * @author      Alexandre Abadie <alexandre.abadie@inria.fr>
 *
 * @}
 */
#include "lvgl/src/widgets/lv_img.h"
#include "lvgl/src/widgets/lv_roller.h"
//#include "lvgl/src/widgets/lv_label.h"

#include <string.h>

#include "lvgl/lvgl.h"
#include "lvgl_riot.h"
#include "lvgl/src/core/lv_event.h"
#include "lvgl/src/core/lv_group.h"
#include "lvgl/src/core/lv_indev.h"
#include "lvgl/src/hal/lv_hal_indev.h"
#include "disp_dev.h"
#include "init_lvgl.h"
#include "ili9341.h"
#include "ili9341_params.h"
#include "lcd.h"
#include "ztimer.h"
#include "xtimer.h"
#include "march1.h"
#include "periph/gpio.h"
#include "thread.h"

#define CPU_LABEL_COLOR     "FF0000"
#define MEM_LABEL_COLOR     "0000FF"
#define CHART_POINT_NUM     100

/* Must be lower than LVGL_INACTIVITY_PERIOD_MS for autorefresh */
#define REFR_TIME           200

//static lv_timer_t *refr_task;

// static void sysmon_task(lv_timer_t *param)
// {
//     (void)param;

//     /* Force a wakeup of lvgl when each task is called: this ensures an activity
//        is triggered and wakes up lvgl during the next LVGL_INACTIVITY_PERIOD ms */
//     lvgl_wakeup();
// }

//static lv_obj_t *label;


//init value here


lv_obj_t * btn2;
lv_obj_t * label ;
lv_obj_t * img1 ;
lv_obj_t * roller1;
lv_obj_t * roller2;

lv_group_t* group1;
struct _lv_indev_drv_t drv;
lv_indev_t * indev;


typedef struct {
    uint32_t button;
    bool state;
}button_t;

button_t buttons[5];

//init value end

//event here



static uint32_t keypad_get_key(void);

static void event_handler(lv_event_t * e)
{
    
    lv_event_code_t code = lv_event_get_code(e);
    lv_obj_t * obj = lv_event_get_target(e);
    if(code == LV_EVENT_VALUE_CHANGED) {
        puts("value changed\n");
        char buf[32];
        lv_roller_get_selected_str(obj, buf, sizeof(buf));
        LV_LOG_USER("Selected month: %s\n", buf);
    }else if(code == LV_EVENT_CLICKED) {
        LV_LOG_USER("Clicked");
    }
    else if(code == LV_EVENT_VALUE_CHANGED) {
        LV_LOG_USER("Toggled");
    }
}

//event end

//init function here

void lv_print_init(void)
{
    lv_obj_set_style_bg_color(lv_scr_act(), lv_color_hex(0x003a57), LV_PART_MAIN);
    label = lv_label_create(lv_scr_act());
}

void lv_btn_2(void)
{
    lv_obj_t * label1;

    btn2 = lv_btn_create(lv_scr_act());
    lv_obj_align(btn2, LV_ALIGN_CENTER, 0, 60);
    lv_obj_add_flag(btn2, LV_OBJ_FLAG_CHECKABLE);
    lv_obj_set_height(btn2, LV_SIZE_CONTENT);
    lv_obj_add_event_cb(btn2, event_handler, LV_EVENT_ALL, NULL);
    label1 = lv_label_create(btn2);
    lv_label_set_text(label1, "Toggle");
    lv_obj_center(label1);

    //to push it virtually
    lv_obj_add_state(btn2, LV_EVENT_CLICKED);


}

void lv_example_roller_1(void)
{
    roller1 = lv_roller_create(lv_scr_act());
    lv_roller_set_options(roller1,
                          "Main\n"
                          "Feed\n"
                          "Medicate\n"
                          "Play\n"
                          "Pet\n"
                          "Wash",
                          LV_ROLLER_MODE_INFINITE);

    lv_roller_set_visible_row_count(roller1, 4);
    lv_obj_align(roller1, LV_ALIGN_CENTER, 80, -20);
    lv_obj_add_event_cb(roller1, event_handler, LV_EVENT_KEY, NULL);
}

void lv_example_roller_2(void)
{
    roller2 = lv_roller_create(lv_scr_act());
    lv_roller_set_options(roller2,
                          "M\n"
                          "F\n"
                          "M\n"
                          "P\n"
                          "P\n"
                          "W",
                          LV_ROLLER_MODE_INFINITE);

    lv_roller_set_visible_row_count(roller2, 4);
    lv_obj_align(roller2, LV_ALIGN_CENTER, -80, -20);
    lv_obj_add_event_cb(roller2, event_handler, LV_EVENT_ALL, NULL);
}

void lv_example_image_1(void)
{
    LV_IMG_DECLARE(march1);
    img1= lv_img_create(lv_scr_act());
    lv_img_set_src(img1, &march1);
    lv_obj_align(img1, LV_ALIGN_CENTER, 0, -20);
}

//init function end

//function here
void lv_puts(char *c)
{ 
    lv_label_set_text(label,c);
    lv_obj_set_style_text_color(lv_scr_act(), lv_color_hex(0xffffff), LV_PART_MAIN);
    lv_obj_align(label, LV_ALIGN_CENTER, 0, 0);
}

void image_switch(void)
{
    puts("[image_switch]");
        if(lv_obj_has_flag(img1, LV_OBJ_FLAG_HIDDEN)) {
        printf("aus");
        lv_obj_clear_flag(img1, LV_OBJ_FLAG_HIDDEN);
        printf(" geraust\n");
    }
    else{
        // lv_obj_clear_flag(btn2,LV_OBJ_FLAG_HIDDEN);
        lv_obj_add_flag(img1, LV_OBJ_FLAG_HIDDEN);
    }
    lvgl_wakeup();

}

void change_button(void)
{
    // lv_event_send(btn2,LV_EVENT_CLICKED,NULL);
    // lv_event_send(btn2, LV_EVENT_RELEASED, NULL);

    if(lv_obj_has_state(btn2, LV_EVENT_CLICKED)) {//LV_EVENT_CLICKED
        printf("aus\n");
        lv_obj_clear_state(btn2, 7);
        printf("aus\n");
    }
    else{
        printf("ein\n");
        lv_obj_add_state(btn2, 7);
        printf("ein\n");
    }
    lvgl_wakeup();
}

void down_click(void)
{
    lv_group_send_data(group1,LV_KEY_ENTER);
    //lv_event_send(roller1, LV_KEY_RIGHT, NULL);
    // int temp=lv_roller_get_selected(roller1);
    // lv_roller_set_selected(roller1, ++temp, LV_ANIM_ON);
    // lv_event_send(roller1, 18, NULL);//LV_KEY_DOWN
    lvgl_wakeup();
}
//function end

//main here




/*Will be called by the library to read the keyboard*/
void keypad_read(struct _lv_indev_drv_t *indev, lv_indev_data_t *data){
    (void) indev;
    static uint32_t last_key = 0;
    /*Get whether the a key is pressed and save the pressed key*/
    uint32_t act_key = keypad_get_key();
    if(act_key != 0) {
        data->state = LV_INDEV_STATE_PRESSED;
        last_key = act_key;
    }
    else {
        data->state = LV_INDEV_STATE_RELEASED;
    }

    data->key = last_key;
}

/*Get the currently being pressed key.  0 if no key is pressed*/
static uint32_t keypad_get_key(void)
{
    for (int i = 0; i <= 5; i++){
        if (buttons[i].state){
            switch (buttons[i].button)
            {
            case LV_KEY_ENTER:
                return LV_KEY_ENTER;
                break;
            case LV_KEY_UP:
                return LV_KEY_UP;
                break;
            case LV_KEY_DOWN:
                return LV_KEY_DOWN;
                break;
            default:
                return 0;
                break;
            }
        }
    }
    return 0;
}

void enter_pressed(void){
    buttons[0].state = true;
    puts("enter pressed\n");
}

void enter_released(void){
    buttons[0].state = false;
    puts("enter released\n");
}

void up_pressed(void){
    buttons[1].state = true;
    puts("up pressed\n");
}

void up_released(void){
    buttons[1].state = false;
    puts("up released\n");
}

void down_pressed(void){
    buttons[2].state = true;
    puts("down pressed\n");
}

void down_released(void){
    buttons[2].state = false;
    puts("down released\n");
    
}


int init_lvgl(void)
{ 
    buttons[0].button = LV_KEY_ENTER;
    buttons[0].state  = false;
    buttons[1].button = LV_KEY_UP;
    buttons[1].state  = false;
    buttons[2].button = LV_KEY_DOWN;
    buttons[2].state  = false;
    buttons[3].button = LV_KEY_LEFT;
    buttons[3].state  = false;
    buttons[4].button = LV_KEY_RIGHT;
    buttons[4].state  = false;

    group1 = lv_group_create();
    lv_indev_drv_init(&drv);
    drv.type = LV_INDEV_TYPE_KEYPAD;
    
    drv.read_cb = keypad_read;
    indev = lv_indev_drv_register(&drv);

    lv_indev_set_group(indev,group1);
    lv_print_init();
    lv_puts("close world");
    lv_example_roller_1();

    lv_group_add_obj(group1,roller1);
    // //lv_obj_add_event_cb(roller1, event_handler, LV_EVENT_ALL, NULL);
    // //lv_example_roller_2();
    // //lv_btn_2();
    // lv_example_image_1();
    // printf("anim running: %d\n",lv_anim_count_running());
    return 0;
}
