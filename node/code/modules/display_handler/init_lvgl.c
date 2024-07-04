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

#include <string.h>

#include "lvgl/lvgl.h"
#include "lvgl_riot.h"
#include "lvgl/src/core/lv_event.h"
#include "lvgl/src/core/lv_group.h"
#include "lvgl/src/core/lv_indev.h"
#include "lvgl/src/hal/lv_hal_indev.h"
#include "disp_dev.h"
#include "ztimer.h"
#include "init_lvgl.h"
#include "events.h"

#define ENABLE_DEBUG  1
#include "debug.h"

typedef enum {
    UP,
    DOWN,
    LEFT,
    RIGHT
} Direction;

Direction direction;

lv_obj_t * screen;

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
void init_menu(void);

//main here


static void menu_cb(lv_event_t * e) {
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
        }else if (key == LV_KEY_ENTER){
            DEBUG("Enter pressed\n");
            trigger_event(img_index_pairs[current_img_index].event);
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

void enter_pressed(void){
    buttons[0].state = true;
}

void enter_released(void){
    buttons[0].state = false;
}

void up_pressed(void){
    direction = UP;
    buttons[1].state = true;
}

void up_released(void){
    buttons[1].state = false;
}

void down_pressed(void){
    direction = DOWN;
    buttons[2].state = true;
}

void down_released(void){
    buttons[2].state = false;    
}

void left_pressed(void){
    direction = LEFT;
    buttons[3].state = true;
}

void left_released(void){
    buttons[3].state = false;    
}

void right_pressed(void){
    direction = RIGHT;
    buttons[4].state = true;
}

void right_released(void){
    buttons[4].state = false;    
}

static void timer_cb(lv_timer_t *param){
    (void) param;
    lvgl_wakeup();
}

void change_top_bar_text(char* top_bar_text){
    lv_obj_clean(top_bar);
    // Add a label to the top bar
    lv_obj_t * pet_label = lv_label_create(top_bar);
    lv_label_set_text(pet_label, top_bar_text);
    lv_obj_set_style_text_color(pet_label, lv_color_hex(0xFFFFFF), LV_PART_MAIN);

}  

void init_default_screen(char* top_bar_text){
    lv_obj_clean(lv_scr_act());
    static lv_style_t style_base;
    lv_style_init(&style_base);
    lv_style_set_border_width(&style_base,0);
    lv_obj_t * screen = lv_obj_create(lv_scr_act());
    lv_obj_clear_flag(screen,LV_OBJ_FLAG_SCROLLABLE);
    lv_obj_set_size(screen, 320, 240);
    lv_obj_add_style(screen, &style_base, LV_PART_MAIN);

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

void init_not_registered(void){
    lv_obj_clean(center);
    /* Style of the align bar*/
    static lv_style_t style_align;
    lv_style_init(&style_align);
    lv_style_set_border_width(&style_align,0);
    lv_style_set_radius(&style_align,0);
    lv_style_set_bg_opa(&style_align,LV_OPA_TRANSP);
    lv_style_set_width(&style_align,240);
    lv_style_set_height(&style_align,70);
    /*Create a container for align*/
    lv_obj_t *align = lv_obj_create(center);
    lv_obj_center(align);
    lv_obj_clear_flag(align,LV_OBJ_FLAG_SCROLLABLE);
    lv_obj_add_style(align,&style_align,LV_PART_MAIN);
    lv_obj_align(align, LV_ALIGN_CENTER,0,0);

    /*Create a spinner*/
    lv_obj_t * spinner = lv_spinner_create(align,10000,100);
    lv_obj_set_size(spinner, 50, 50);
    lv_obj_align(spinner, LV_ALIGN_RIGHT_MID,0,0);
    /* Create registering label*/
    lv_obj_t * registering_label = lv_label_create(align);
    lv_label_set_text(registering_label,"connecting");
    lv_obj_set_style_text_color(registering_label, lv_color_hex(0x000000), LV_PART_MAIN);
    lv_obj_set_style_text_font(registering_label,&lv_font_montserrat_24, LV_PART_MAIN);
    lv_obj_align(registering_label, LV_ALIGN_LEFT_MID,0,0);

    wakeup_task = lv_timer_create(timer_cb, WAKEUP_TIME, NULL); 
}

void init_not_registered_code(char *code){
    // timer_deactivate();
    lv_obj_clean(center);
    /* Style of the align bar*/
    static lv_style_t style_align;
    lv_style_init(&style_align);
    lv_style_set_border_width(&style_align,0);
    lv_style_set_radius(&style_align,0);
    lv_style_set_bg_opa(&style_align,LV_OPA_TRANSP);
    lv_style_set_width(&style_align,240);
    lv_style_set_height(&style_align,70);
    /*Create a container for align*/
    lv_obj_t *align = lv_obj_create(center);
    lv_obj_center(align);
    lv_obj_clear_flag(align,LV_OBJ_FLAG_SCROLLABLE);
    lv_obj_add_style(align,&style_align,LV_PART_MAIN);
    lv_obj_align(align, LV_ALIGN_CENTER,0,0);

    /* Create registering label*/
    lv_obj_t * registering_label = lv_label_create(align);
    lv_label_set_text(registering_label,code);
    lv_obj_set_style_text_color(registering_label, lv_color_hex(0x000000), LV_PART_MAIN);
    lv_obj_set_style_text_font(registering_label,&lv_font_montserrat_24, LV_PART_MAIN);
    lv_obj_align(registering_label, LV_ALIGN_LEFT_MID,0,0);
}

void init_registered_no_pet(void){
    init_default_screen("Pet Room");
    lv_obj_clean(center);
    init_menu();
}

void init_registered_pet(void){
    // timer_deactivate();
    lv_obj_clean(center);    

    /* Style of the align */
    static lv_style_t style_align;
    lv_style_init(&style_align);
    lv_style_set_border_width(&style_align,0);
    lv_style_set_radius(&style_align,0);
    lv_style_set_bg_opa(&style_align,LV_OPA_TRANSP);
    lv_style_set_width(&style_align,240);
    lv_style_set_height(&style_align,160);
    /*Create a container for align*/
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

void init_pet_stats(char* stats){
    // timer_deactivate();
    lv_obj_clean(center);

    /* Style of the align */
    static lv_style_t style_align;
    lv_style_init(&style_align);
    lv_style_set_border_width(&style_align,0);
    lv_style_set_radius(&style_align,0);
    lv_style_set_bg_opa(&style_align,LV_OPA_TRANSP);
    lv_style_set_width(&style_align,240);
    lv_style_set_height(&style_align,120);
    /*Create a container for align*/
    lv_obj_t *align = lv_obj_create(center);
    lv_obj_center(align);
    lv_obj_clear_flag(align,LV_OBJ_FLAG_SCROLLABLE);
    lv_obj_add_style(align,&style_align,LV_PART_MAIN);
    lv_obj_align(align, LV_ALIGN_CENTER,0,0);

    static lv_style_t style;
    lv_style_init(&style);
    lv_style_set_bg_opa(&style,LV_OPA_50);
    lv_style_set_bg_color(&style,lv_color_hex(0x0));
    lv_obj_add_style(align,&style,LV_PART_MAIN);
    /* Pet stats*/
    lv_obj_t * stats_label = lv_label_create(align);
    lv_label_set_text(stats_label,stats);
    
    lv_obj_set_style_text_color(stats_label, lv_color_hex(0xFFFFFF), LV_PART_MAIN);
    lv_obj_set_style_text_font(stats_label,&lv_font_montserrat_12, LV_PART_MAIN);
    lv_obj_align(stats_label, LV_ALIGN_LEFT_MID,0,8);
}

void init_menu(void){
    lv_obj_clean(bottom_bar);
    current_img_index = 0;
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
    img_index_pairs[4].event = INFO_PRESSED;

    lv_obj_add_event_cb(bottom_bar,menu_cb,LV_EVENT_ALL,NULL);
    lv_group_add_obj(group1,bottom_bar);
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
    init_not_registered();
    return 0;
}

void showDeadScreen(void){
    lv_obj_clean(lv_scr_act());
    lv_obj_t * screen = lv_obj_create(lv_scr_act());
    lv_obj_clear_flag(screen,LV_OBJ_FLAG_SCROLLABLE);
    lv_obj_set_size(screen, 320, 240);
    static lv_style_t style_base;
    lv_style_init(&style_base);
    lv_style_set_border_width(&style_base,0);
    lv_obj_add_style(screen, &style_base, LV_PART_MAIN);
    lv_obj_align(screen, LV_ALIGN_CENTER, 0, 0);

    lv_obj_t * dead_label = lv_label_create(screen);
    static lv_style_t style_label;
    lv_style_init(&style_label);
    lv_style_set_text_font(&style_label, &lv_font_montserrat_24);
    lv_obj_add_style(dead_label, &style_label, LV_PART_MAIN);

    lv_label_set_text(dead_label, "Your pet is dead!");
    lv_obj_align(dead_label, LV_ALIGN_CENTER, 0, 0);
}


/******

for snake game


*******/

#define SNAKE_MAX_LENGTH 10
#define SNAKE_START_LENGTH 3
#define GRID_SIZE 15
#define GRID_WIDTH (320 / GRID_SIZE)
#define GRID_HEIGHT (240 / GRID_SIZE)
#define SNAKE_SPEED 300 // Milliseconds

int snake_speed = SNAKE_SPEED;


typedef struct {
    int x;
    int y;
} Point;

static Point snake[SNAKE_MAX_LENGTH];
static int snake_length;
static Point food;

static lv_obj_t *snake_objs[SNAKE_MAX_LENGTH];
static lv_obj_t *food_obj;

void game_won(void) {
    lv_obj_t *label = lv_label_create(screen);
    snake_speed = SNAKE_SPEED;
    lv_label_set_text(label, "You Win!");
    lv_obj_align(label, LV_ALIGN_CENTER, 0, 0);
    ztimer_sleep(ZTIMER_SEC,3);
    init_default_screen("Initializing ...");
    init_registered_pet();
    init_menu();
    trigger_event(GAME_FINISHED);
}

void init_game(void) {
    snake_length = SNAKE_START_LENGTH;
    snake_speed = SNAKE_SPEED;
    lv_obj_clean(lv_scr_act());
    screen = lv_obj_create(lv_scr_act());
    static lv_style_t style_base;
    lv_style_init(&style_base);
    lv_style_set_border_width(&style_base,0);
    lv_style_set_pad_all(&style_base,0);
    lv_obj_clear_flag(screen,LV_OBJ_FLAG_SCROLLABLE);
    lv_obj_set_size(screen, 320, 240);
    lv_obj_add_style(screen, &style_base, LV_PART_MAIN);
    lv_obj_align(screen, LV_ALIGN_CENTER, 0, 0);
     
    static lv_style_t snake_style;
    lv_style_init(&snake_style);
    lv_style_set_bg_color(&snake_style, lv_color_hex(0x00FF00)); // Grün für die Schlange

    static lv_style_t food_style;
    lv_style_init(&food_style);
    lv_style_set_bg_color(&food_style, lv_color_hex(0xFF0000)); // Rot für das Essen


    for (int i = 0; i < snake_length; i++) {
        snake[i].x = GRID_WIDTH / 2 - i;
        snake[i].y = GRID_HEIGHT / 2;
        snake_objs[i] = lv_obj_create(screen);
        lv_obj_set_size(snake_objs[i], GRID_SIZE, GRID_SIZE);
        lv_obj_add_style(snake_objs[i], &snake_style, LV_PART_MAIN);
        lv_obj_set_pos(snake_objs[i], snake[i].x * GRID_SIZE, snake[i].y * GRID_SIZE);
    }
    direction = RIGHT;

    // Place food
    food.x = lv_rand(1, GRID_WIDTH-1);
    food.y = lv_rand(1, GRID_HEIGHT-1);
    food_obj = lv_obj_create(screen);
    lv_obj_set_size(food_obj, GRID_SIZE, GRID_SIZE);
    lv_obj_add_style(food_obj, &food_style, LV_PART_MAIN);
    lv_obj_set_pos(food_obj, food.x * GRID_SIZE, food.y * GRID_SIZE);
}

bool update_game(void) {
    // Move snake body
    for (int i = snake_length - 1; i > 0; i--) {
        snake[i] = snake[i - 1];
        lv_obj_set_pos(snake_objs[i], snake[i].x * GRID_SIZE, snake[i].y * GRID_SIZE);
    }

    // Move snake head
    switch (direction) {
        case UP: snake[0].y--; break;
        case DOWN: snake[0].y++; break;
        case LEFT: snake[0].x--; break;
        case RIGHT: snake[0].x++; break;
    }

    lv_obj_set_pos(snake_objs[0], snake[0].x * GRID_SIZE, snake[0].y * GRID_SIZE);
    // DEBUG("x: %d, y: %d\n",snake[0].x * GRID_SIZE,snake[0].y * GRID_SIZE);

    // Check food collision
    if (snake[0].x == food.x && snake[0].y == food.y) {
        if (snake_length < SNAKE_MAX_LENGTH) {
            snake_length++;
            lv_obj_t *new_body_part = lv_obj_create(screen);
            lv_obj_set_size(new_body_part, GRID_SIZE, GRID_SIZE);
            static lv_style_t style;
            lv_style_init(&style);
            lv_style_set_bg_opa(&style, LV_OPA_50);
            lv_style_set_bg_color(&style, lv_color_hex(0x00FF00)); // Grün für die Schlange
            lv_obj_add_style(new_body_part, &style, LV_PART_MAIN);

            snake_objs[snake_length - 1] = new_body_part;
        }

        food.x = lv_rand(0, GRID_WIDTH);
        food.y = lv_rand(0, GRID_HEIGHT);
        lv_obj_set_pos(food_obj, food.x * GRID_SIZE, food.y * GRID_SIZE);
        snake_speed -= 10;
    }

    // Check win condition
    if (snake_length >= 10) {
        game_won();
        return true; // Stop further updates if the game is won
    }

    // Check wall collision
    if (snake[0].x < 0 || snake[0].x >= GRID_WIDTH || snake[0].y < 0 || snake[0].y >= GRID_HEIGHT) {
        // Game Over
        init_game();
    }

    // Check self collision
    for (int i = 1; i < snake_length; i++) {
        if (snake[0].x == snake[i].x && snake[0].y == snake[i].y) {
            // Game Over
            init_game();
        }
    }
    return false;
}

void lv_tick_task(void *arg) {
    (void)arg;
    //lv_tick_inc(1);
}



void *game_loop(void * arg) {
    (void) arg;
    init_game();

    //Start game loop
    while (1) {
        if (update_game()) {
            break;
        }
        lv_task_handler();
        ztimer_sleep(ZTIMER_MSEC, snake_speed);
    }
    game_won();
    return NULL;
}

/******

for snake game


*******/
