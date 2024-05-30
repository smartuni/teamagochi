#pragma once

#include "shell.h"
#include <stdio.h>

static kernel_pid_t DISPATCHER_THREAD_ID;

static int echo_command(int argc, char **argv)
{
    /* check that the command is called correctly */
    if (argc != 2) {
        puts("usage: echo <message>");
        puts("Note: to echo multiple words wrap the message in \"\"");
        return 1;
    }

    /* print the first argument */
    puts(argv[1]);

    return 0;
}

/**
 * @brief Sends an event to the dispatcher.
 * @example send_event 1
*/
static int send_event(int argc, char **argv) {
    if (argc != 2) {
        puts("usage: send_event <event>");
        return 1;
    }

    msg_t message;
    message.type = atoi(argv[1]);

    msg_try_send(&message, DISPATCHER_THREAD_ID);

    return 0;
}

const shell_command_t SHELL_COMMANDS[] = {
    { "echo", "Prints the message to the console", echo_command },
    { "send_event", "Sends an event to the dispatcher. send_event 1", send_event },
    { NULL, NULL, NULL }
};

void shell_loop(void) {
        /* buffer to read commands */
    char line_buf[SHELL_DEFAULT_BUFSIZE];

    cout << "Starting shell loop" << endl;

    /* run the shell, this will block the thread waiting for incoming commands */
    shell_run(SHELL_COMMANDS, line_buf, SHELL_DEFAULT_BUFSIZE);
}
