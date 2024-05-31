#include <stdio.h>
#include "shell_commands.h"

#include "events.h"
#include "fsm.h"

#define SHELL_QUEUE_SIZE (8)
static msg_t _shell_queue[SHELL_QUEUE_SIZE];
char fsm_thread_stack[THREAD_STACKSIZE_MAIN];

int main(void)
{
    thread_create(fsm_thread_stack, sizeof(fsm_thread_stack),
                  THREAD_PRIORITY_MAIN - 1, THREAD_CREATE_STACKTEST,
                  fsm_thread, NULL, "fsm_thread");
    msg_init_queue(_shell_queue, SHELL_QUEUE_SIZE);
    shell_loop();
}
