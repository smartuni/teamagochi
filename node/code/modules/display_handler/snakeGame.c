#include "lvgl/lvgl.h"
#include "lvgl_driver/lvgl_driver.h"

#define LV_COLOR_RED LV_COLOR_MAKE(0xFF, 0x00, 0x00)
#define LV_COLOR_GREEN LV_COLOR_MAKE(0x00, 0xFF, 0x00)
#define LV_COLOR_BLUE LV_COLOR_MAKE(0x00, 0x00, 0xFF)

/******

for snake game


*******/

#define SNAKE_MAX_LENGTH 50
#define SNAKE_START_LENGTH 3
#define GRID_SIZE 10
#define GRID_WIDTH (LV_HOR_RES_MAX / GRID_SIZE)
#define GRID_HEIGHT (LV_VER_RES_MAX / GRID_SIZE)
#define SNAKE_SPEED 200000 // Microseconds

typedef struct {
    int x;
    int y;
} Point;

static Point snake[SNAKE_MAX_LENGTH];
static int snake_length;
static Point food;

static lv_obj_t *snake_objs[SNAKE_MAX_LENGTH];
static lv_obj_t *food_obj;

void game_won() {
    // Display a "You Win" message
    lv_obj_t *label = lv_label_create(lv_scr_act(), NULL);
    lv_label_set_text(label, "You Win!");
    lv_obj_align(label, NULL, LV_ALIGN_CENTER, 0, 0);
    

    trigger_event(GAME_FINISHED);



    // Optionally, reset the game or provide options to the player
    // init_game();
}

void init_game() {
    snake_length = SNAKE_START_LENGTH;
    for (int i = 0; i < snake_length; i++) {
        snake[i].x = GRID_WIDTH / 2 - i;
        snake[i].y = GRID_HEIGHT / 2;
        snake_objs[i] = lv_obj_create(lv_scr_act(), NULL);
        lv_obj_set_size(snake_objs[i], GRID_SIZE, GRID_SIZE);
        lv_obj_set_style_local_bg_color(snake_objs[i], LV_OBJ_PART_MAIN, LV_STATE_DEFAULT, LV_COLOR_GREEN);
        lv_obj_set_pos(snake_objs[i], snake[i].x * GRID_SIZE, snake[i].y * GRID_SIZE);
    }
    direction = RIGHT;

    // Place food
    food.x = rand() % GRID_WIDTH;
    food.y = rand() % GRID_HEIGHT;
    food_obj = lv_obj_create(lv_scr_act(), NULL);
    lv_obj_set_size(food_obj, GRID_SIZE, GRID_SIZE);
    lv_obj_set_style_local_bg_color(food_obj, LV_OBJ_PART_MAIN, LV_STATE_DEFAULT, LV_COLOR_RED);
    lv_obj_set_pos(food_obj, food.x * GRID_SIZE, food.y * GRID_SIZE);
}

void update_game() {
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

    // Check food collision
    if (snake[0].x == food.x && snake[0].y == food.y) {
        if (snake_length < SNAKE_MAX_LENGTH) {
            snake_length++;
            lv_obj_t *new_body_part = lv_obj_create(lv_scr_act(), NULL);
            lv_obj_set_size(new_body_part, GRID_SIZE, GRID_SIZE);
            lv_obj_set_style_local_bg_color(new_body_part, LV_OBJ_PART_MAIN, LV_STATE_DEFAULT, LV_COLOR_GREEN);
            snake_objs[snake_length - 1] = new_body_part;
        }

        food.x = rand() % GRID_WIDTH;
        food.y = rand() % GRID_HEIGHT;
        lv_obj_set_pos(food_obj, food.x * GRID_SIZE, food.y * GRID_SIZE);
    }

    // Check win condition
    if (snake_length >= 20) {
        game_won();
        return; // Stop further updates if the game is won
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
}

void lv_tick_task(void *arg) {
    (void)arg;
    lv_tick_inc(1);
}



void game_loop(void) {
    init_game();

    // Create a periodic timer to update LVGL
    xtimer_t lv_timer;
    xtimer_set_cb(&lv_timer, lv_tick_task, NULL);
    xtimer_set(&lv_timer, 1000);

    // Start game loop
    while (1) {
        update_game();
        lv_task_handler();
        xtimer_usleep(SNAKE_SPEED);
    }
}

/******

for snake game


*******/