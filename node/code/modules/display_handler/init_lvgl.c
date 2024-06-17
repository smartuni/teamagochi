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
#include "events.h"

#define CPU_LABEL_COLOR     "FF0000"
#define MEM_LABEL_COLOR     "0000FF"
#define CHART_POINT_NUM     100

lv_obj_t * roller1;


lv_obj_t * top_bar;
lv_obj_t * center;
lv_obj_t * bottom_bar;
lv_obj_t * tab;

lv_group_t* group1;
struct _lv_indev_drv_t drv;
lv_indev_t * indev;

typedef struct {
    uint8_t button;
    bool state;
} button_t;
button_t buttons[5];

/* Menu buttons */
LV_IMG_DECLARE(feed);
LV_IMG_DECLARE(play);
LV_IMG_DECLARE(wash);
LV_IMG_DECLARE(medicate);
LV_IMG_DECLARE(info);
lv_obj_t * feed_ico;
lv_obj_t * play_ico;
lv_obj_t * wash_ico;
lv_obj_t * medicate_ico;
lv_obj_t * info_ico;
typedef struct {
    EVENT_T event;
    lv_obj_t * img;
    int8_t index;
} img_index_pair_t;
img_index_pair_t img_index_pairs[5];

int8_t current_img_index = 0;
//init value end

//event here

/* Must be lower than LVGL_INACTIVITY_PERIOD_MS for autorefresh */
#define WAKEUP_TIME   100
static lv_timer_t *wakeup_task;

static uint32_t keypad_get_key(void);

void init_not_registered(void);
void init_registered_no_pet(void);
void init_not_registered_code(void);
void init_registered_pet(void);
void init_menu(void);

static void timer_deactivate(void);

//main here


static void menu_cb(lv_event_t * e){
    lv_event_code_t code = lv_event_get_code(e);
    if(code == LV_EVENT_KEY) {
        uint32_t key = lv_event_get_key(e);
        if(key == LV_KEY_LEFT || key == LV_KEY_RIGHT) {
            lv_obj_set_style_bg_opa(img_index_pairs[current_img_index].img,LV_OPA_TRANSP,LV_PART_MAIN);
            // Ändere die Indexposition entsprechend der gedrückten Taste
            if(key == LV_KEY_LEFT) {
                current_img_index--;
                if(current_img_index < 0) current_img_index = sizeof(img_index_pairs) / sizeof(img_index_pairs[0]) - 1;
            } else if(key == LV_KEY_RIGHT) {
                current_img_index++;
                if(current_img_index >= (int8_t)(sizeof(img_index_pairs) / sizeof(img_index_pairs[0]))) current_img_index = 0;
            }
            // Zeige das neue aktuelle Bild an
            lv_obj_set_style_bg_opa(img_index_pairs[current_img_index].img,LV_OPA_70,LV_PART_MAIN);
        }else if (key == LV_KEY_ENTER && (img_index_pairs[current_img_index].event == PET_FEED || 
                                          img_index_pairs[current_img_index].event == PET_PLAY ||
                                          img_index_pairs[current_img_index].event == PET_MEDICATE ||
                                          img_index_pairs[current_img_index].event == PET_CLEAN)){
            // msg_t message;
            // message.type = img_index_pairs[current_img_index].event ;
            // msg_try_send(&message, dispatcher_pid_lvgl);
        }
    }
}

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
            case LV_KEY_LEFT:
                return LV_KEY_LEFT;
                break;
            case LV_KEY_RIGHT:
                return LV_KEY_RIGHT;
                break;
            default:
                return 0;
                break;
            }
        }
    }
    return 0;
}

void init_default_screen(char* top_bar_text){
    lv_obj_clean(lv_scr_act());
    static lv_style_t style_base;
    lv_style_init(&style_base);
    lv_style_set_border_width(&style_base,0);
    lv_obj_t * screen = lv_obj_create(lv_scr_act());
    lv_obj_clear_flag(screen,LV_OBJ_FLAG_SCROLLABLE);
    lv_obj_set_size(screen, 320, 240);
    lv_obj_add_style(screen,&style_base, LV_PART_MAIN);

    /* Style for top bar */
    static lv_style_t style_top_bar;
    lv_style_init(&style_top_bar);
    lv_style_set_bg_color(&style_top_bar,lv_color_hex(0x6e1b50));
    lv_style_set_border_width(&style_top_bar,0);
    lv_style_set_radius(&style_top_bar,0);

    /*Create a container for the top row*/
    top_bar = lv_obj_create(screen);
    lv_obj_set_size(top_bar, 320, 30);
    lv_obj_set_x(top_bar,-13);
    lv_obj_set_y(top_bar,-13);
    lv_obj_set_flex_flow(top_bar, LV_FLEX_FLOW_ROW);
    lv_obj_add_style(top_bar,&style_top_bar,LV_PART_MAIN);
    lv_obj_clear_flag(top_bar,LV_OBJ_FLAG_SCROLLABLE);

    // // Add a label to the top bar
    lv_obj_t * pet_label = lv_label_create(top_bar);
    lv_label_set_text(pet_label, top_bar_text);
    lv_obj_set_style_text_color(pet_label, lv_color_hex(0xFFFFFF), LV_PART_MAIN);

    // /* Style for center */
    static lv_style_t style_center;
    lv_style_init(&style_center);
    lv_style_set_border_width(&style_center,0);
    lv_style_set_radius(&style_center,0);

    // /*Create a container for the center*/
    center = lv_obj_create(screen);
    lv_obj_set_size(center, 320, 160);
    lv_obj_set_x(center,-13);
    lv_obj_set_y(center,17);
    lv_obj_add_style(center,&style_center,LV_PART_MAIN);
    lv_obj_clear_flag(center,LV_OBJ_FLAG_SCROLLABLE);
    
    /* Background of the room*/
    LV_IMG_DECLARE(background);
    lv_obj_set_style_bg_img_src(center, &background,LV_PART_MAIN);
    
    /* Style of the bottom bar*/
    static lv_style_t style_bottom_bar;
    lv_style_init(&style_bottom_bar);
    lv_style_set_bg_color(&style_bottom_bar,lv_color_hex(0x6e1b50));
    lv_style_set_border_width(&style_bottom_bar,0);
    lv_style_set_radius(&style_bottom_bar,0);

     /*Create a container for the bottom row*/
    bottom_bar = lv_obj_create(screen);
    lv_obj_set_size(bottom_bar, 320, 50);
    lv_obj_set_x(bottom_bar,-13);
    lv_obj_set_y(bottom_bar,177);
    lv_obj_set_flex_flow(bottom_bar, LV_FLEX_FLOW_ROW);
    lv_obj_add_style(bottom_bar,&style_top_bar,LV_PART_MAIN);
    lv_obj_clear_flag(bottom_bar,LV_OBJ_FLAG_SCROLLABLE);

    
}

void enter_pressed(void){
    buttons[0].state = true;
}

void enter_released(void){
    buttons[0].state = false;
}

void up_pressed(void){
    init_default_screen("Kevin the Frog | Lvl 2 | 89/100 | Disconnected");
    init_menu();
    init_registered_pet();
    buttons[1].state = true;
}

void up_released(void){
    buttons[1].state = false;
}

void down_pressed(void){
    init_default_screen("Registering ...");
    init_menu();
    init_not_registered();
    buttons[2].state = true;
}

void down_released(void){
    buttons[2].state = false;    
}

void left_pressed(void){
    buttons[3].state = true;
}

void left_released(void){
    buttons[3].state = false;    
}

void right_pressed(void){
    buttons[4].state = true;
}

void right_released(void){
    buttons[4].state = false;    
}

static void timer_cb(lv_timer_t *param){
    (void) param;
    lvgl_wakeup();
}

static void timer_deactivate(void){
    lv_timer_del(wakeup_task);
}

void init_not_registered(void){

    // /* Style of the align bar*/
    static lv_style_t style_align;
    lv_style_init(&style_align);
    lv_style_set_border_width(&style_align,0);
    lv_style_set_radius(&style_align,0);
    lv_style_set_bg_opa(&style_align,LV_OPA_TRANSP);
    lv_style_set_width(&style_align,240);
    lv_style_set_height(&style_align,70);
    // //  /*Create a container for align*/
    lv_obj_t *align = lv_obj_create(center);
    lv_obj_center(align);
    lv_obj_clear_flag(align,LV_OBJ_FLAG_SCROLLABLE);
    lv_obj_add_style(align,&style_align,LV_PART_MAIN);
    lv_obj_align(align, LV_ALIGN_CENTER,0,0);

    // // // // /*Create a spinner*/
    lv_obj_t * spinner = lv_spinner_create(align,10000,100);
    lv_obj_set_size(spinner, 50, 50);
    lv_obj_align(spinner, LV_ALIGN_RIGHT_MID,0,0);
    // // /* Create registering label*/
    lv_obj_t * registering_label = lv_label_create(align);
    lv_label_set_text(registering_label,"registering");
    lv_obj_set_style_text_color(registering_label, lv_color_hex(0x000000), LV_PART_MAIN);
    lv_obj_set_style_text_font(registering_label,&lv_font_montserrat_24, LV_PART_MAIN);
    lv_obj_align(registering_label, LV_ALIGN_LEFT_MID,0,0);

    wakeup_task = lv_timer_create(timer_cb, WAKEUP_TIME, NULL); 
}



void init_not_registered_code(void){
    timer_deactivate();
    lv_obj_clean(center);

    // /* Style of the align bar*/
    static lv_style_t style_align;
    lv_style_init(&style_align);
    lv_style_set_border_width(&style_align,0);
    lv_style_set_radius(&style_align,0);
    lv_style_set_bg_opa(&style_align,LV_OPA_TRANSP);
    lv_style_set_width(&style_align,240);
    lv_style_set_height(&style_align,70);
    // //  /*Create a container for align*/
    lv_obj_t *align = lv_obj_create(center);
    lv_obj_center(align);
    lv_obj_clear_flag(align,LV_OBJ_FLAG_SCROLLABLE);
    lv_obj_add_style(align,&style_align,LV_PART_MAIN);
    lv_obj_align(align, LV_ALIGN_CENTER,0,0);

    // // /* Create registering label*/
    lv_obj_t * registering_label = lv_label_create(align);
    lv_label_set_text(registering_label,"code placeholder");
    lv_obj_set_style_text_color(registering_label, lv_color_hex(0x000000), LV_PART_MAIN);
    lv_obj_set_style_text_font(registering_label,&lv_font_montserrat_24, LV_PART_MAIN);
    lv_obj_align(registering_label, LV_ALIGN_LEFT_MID,0,0);

}

void init_registered_no_pet(void){
    lv_obj_clean(center);
}

void init_registered_pet(void){
    timer_deactivate();
    lv_obj_clean(center);

    // /* Style of the align */
    static lv_style_t style_align;
    lv_style_init(&style_align);
    lv_style_set_border_width(&style_align,0);
    lv_style_set_radius(&style_align,0);
    lv_style_set_bg_opa(&style_align,LV_OPA_TRANSP);
    lv_style_set_width(&style_align,240);
    lv_style_set_height(&style_align,160);
    // //  /*Create a container for align*/
    lv_obj_t *align = lv_obj_create(center);
    lv_obj_center(align);
    lv_obj_clear_flag(align,LV_OBJ_FLAG_SCROLLABLE);
    lv_obj_add_style(align,&style_align,LV_PART_MAIN);
    lv_obj_align(align, LV_ALIGN_CENTER,0,0);

    /* Pet*/
    LV_IMG_DECLARE(frog);
    lv_obj_t * pet = lv_img_create(align);
    lv_img_set_src(pet, &frog);
    lv_obj_align(pet, LV_ALIGN_CENTER, 0, 0);
}

void init_menu(void){
    static lv_style_t style;
    lv_style_init(&style);
    lv_style_set_bg_opa(&style,LV_OPA_TRANSP);
    lv_style_set_bg_color(&style,lv_color_hex(0xFFFFFF));

    feed_ico = lv_img_create(bottom_bar);
    lv_img_set_src(feed_ico, &feed);
    lv_obj_add_style(feed_ico,&style,LV_PART_MAIN);
    lv_obj_set_style_bg_opa(feed_ico,LV_OPA_70,LV_PART_MAIN);

    play_ico = lv_img_create(bottom_bar);
    lv_img_set_src(play_ico, &play);
    lv_obj_add_style(play_ico,&style,LV_PART_MAIN);

    wash_ico = lv_img_create(bottom_bar);
    lv_img_set_src(wash_ico, &wash);
    lv_obj_add_style(wash_ico,&style,LV_PART_MAIN);

    medicate_ico = lv_img_create(bottom_bar);
    lv_img_set_src(medicate_ico, &medicate);
    lv_obj_add_style(medicate_ico,&style,LV_PART_MAIN);

    info_ico = lv_img_create(bottom_bar);
    lv_img_set_src(info_ico, &info);
    lv_obj_add_style(info_ico,&style,LV_PART_MAIN);

    img_index_pairs[0].img = feed_ico;
    img_index_pairs[0].index = 0;
    img_index_pairs[0].event = PET_FEED;
    img_index_pairs[1].img = play_ico;
    img_index_pairs[1].index = 1;   
    img_index_pairs[1].event = PET_PLAY;
    img_index_pairs[2].img = wash_ico;
    img_index_pairs[2].index = 2;
    img_index_pairs[2].event = PET_CLEAN;   
    img_index_pairs[3].img = medicate_ico;
    img_index_pairs[3].index = 3; 
    img_index_pairs[3].event = PET_MEDICATE;  
    img_index_pairs[4].img = info_ico;
    img_index_pairs[4].index = 4;
    img_index_pairs[4].event = 0;

    lv_obj_add_event_cb(bottom_bar,menu_cb,LV_EVENT_ALL,NULL);
    lv_group_add_obj(group1,bottom_bar);
    // // /* Style of the align */
    // static lv_style_t style_align;
    // lv_style_init(&style_align);
    // lv_style_set_border_width(&style_align,0);
    // // lv_style_set_radius(&style_align,0);
    // lv_style_set_bg_opa(&style_align,LV_OPA_TRANSP);;
    // // //  /*Create a container for align*/
    // lv_obj_t *align = lv_obj_create(center);
    // lv_obj_center(align);
    // lv_obj_clear_flag(align,LV_OBJ_FLAG_SCROLLABLE);
    // lv_obj_add_style(align,&style_align,LV_PART_MAIN);
    // lv_obj_align(align, LV_ALIGN_LEFT_MID,-12,0);
    // lv_obj_set_size(align,80,140);

    // roller1 = lv_roller_create(align);
    // lv_roller_set_options(roller1,
    //                       "Exit\n"
    //                       "Feed\n"
    //                       "Medicate\n"
    //                       "Play\n"
    //                       "Pet\n"
    //                       "Wash",
    //                       LV_ROLLER_MODE_INFINITE);

    // lv_roller_set_visible_row_count(roller1, 4);
    // lv_obj_set_size(roller1,79,139);
    // lv_obj_align(roller1, LV_ALIGN_CENTER, 0, 0);
    // lv_obj_add_event_cb(roller1,menu_cb,LV_EVENT_ALL,NULL);
    // lv_group_add_obj(group1,roller1);
    // //lv_obj_add_event_cb(roller1, event_handler, LV_EVENT_KEY, NULL);

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
    init_default_screen("Initializing ...");
    init_menu();
    return 0;
}
