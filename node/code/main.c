#include <stdio.h>
#include "shell_commands.h"

#include "events.h"
#include "fsm.h"
#include "thread.h"
#include "io_handler.h"
#include "init_buttons.h"


#define SHELL_QUEUE_SIZE (8)
static msg_t _shell_queue[SHELL_QUEUE_SIZE];


int main(void)
{   
    fsm_start_thread();
    msg_init_queue(_shell_queue, SHELL_QUEUE_SIZE);
    while (1) {
        vibrate(500);
        ztimer_sleep(ZTIMER_MSEC, 500);
    }
    shell_loop();
}
