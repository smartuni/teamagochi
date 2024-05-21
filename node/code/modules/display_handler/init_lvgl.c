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

#include <string.h>

#include "lvgl/lvgl.h"
#include "lvgl_riot.h"
#include "disp_dev.h"
#include "init_lvgl.h"
#include "ili9341.h"
#include "ili9341_params.h"
#include "lcd.h"

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

lv_obj_t * label ;
lv_obj_t * btn2;

void change_button(void)
{
    if(lv_obj_has_state(btn2, LV_EVENT_CLICKED)) {
        lv_obj_clear_state(btn2, LV_EVENT_CLICKED);
        // lv_obj_add_flag(btn2,LV_OBJ_FLAG_HIDDEN);
        printf("aus\n");
    }
    else{
        // lv_obj_clear_flag(btn2,LV_OBJ_FLAG_HIDDEN);
        lv_obj_add_state(btn2, LV_EVENT_CLICKED);
        printf("ein\n");
    }
    lvgl_wakeup();
}

void button_ein(void)
{
        lv_obj_add_state(btn2, LV_EVENT_PRESSED);
                printf("ein\n");
lvgl_wakeup();
}

void button_aus(void)
{
        lv_obj_clear_state(btn2, LV_EVENT_PRESSED);
                printf("aus\n");
lvgl_wakeup();
}

void lv_print_init(void)
{
    /*Change the active screen's background color*/
    lv_obj_set_style_bg_color(lv_scr_act(), lv_color_hex(0x003a57), LV_PART_MAIN);
    label = lv_label_create(lv_scr_act());
    // char a[100]="hallo world";
    // a[2]='w';
    // lv_label_set_text(label, a);
    // /*Create a white label, set its text and align it to the center*/
    // lv_obj_set_style_text_color(lv_scr_act(), lv_color_hex(0xffffff), LV_PART_MAIN);
    // lv_obj_align(label, LV_ALIGN_CENTER, 0, 0);
}

void lv_puts(char *c)
{ 
    lv_label_set_text(label,c);
    lv_obj_set_style_text_color(lv_scr_act(), lv_color_hex(0xffffff), LV_PART_MAIN);
    lv_obj_align(label, LV_ALIGN_CENTER, 0, 0);
}

static void event_handler(lv_event_t * e)
{
    lv_event_code_t code = lv_event_get_code(e);
    if(code == LV_EVENT_CLICKED) {
        LV_LOG_USER("Clicked");
    }
    else if(code == LV_EVENT_VALUE_CHANGED) {
        LV_LOG_USER("Toggled");
    }
}

void lv_btn_1(void)
{
    lv_obj_t * label1;

    btn2 = lv_btn_create(lv_scr_act());
    lv_obj_add_event_cb(btn2, event_handler, LV_EVENT_ALL, NULL);
    lv_obj_align(btn2, LV_ALIGN_CENTER, 0, 40);
    lv_obj_add_flag(btn2, LV_OBJ_FLAG_CHECKABLE);
    lv_obj_set_height(btn2, LV_SIZE_CONTENT);

    label1 = lv_label_create(btn2);
    lv_label_set_text(label1, "Toggle");
    lv_obj_center(label1);

    //to push it virtually
    //lv_obj_add_state(btn2, LV_EVENT_CLICKED);


}

int init_lvgl(void)
{
    lcd_t dev;
    dev.driver = &lcd_ili9341_driver;

    printf("%d",ILI9341_PARAM_CS);

    puts("lcd TFT display test application");

    /* initialize the sensor */
    printf("Initializing display...");

    /* Enable backlight if macro is defined */
#ifdef BACKLIGHT_ON
  BACKLIGHT_ON;
#endif

  if (lcd_init(&dev, &ili9341_params[0]) == 0) {
    puts("[OK]");
  } else {
    puts("[Failed]");
    return 1;
  }
    /* Enable backlight */
    //gpio_init(GPIO_PIN(0,30), GPIO_OUT);//backlight controller
    //disp_dev_backlight_on();
    /* Create the system monitor widget */
    //   char str[100]="close world";
    lv_print_init();
    lv_puts("close world");
    lv_btn_1();
    //scanf_check();
    //lvgl_run();

    return 0;
}
