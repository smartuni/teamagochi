#pragma once

#include "shell.h"
#include <stdio.h>
#include "events.h"
#include <stdlib.h>

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

static int send_event(int argc, char **argv) {
    if (argc != 2) {
        puts("usage: send_event <event_id>");
        return 1;
    }

    int event_id = atoi(argv[1]);
    if (event_id < 0) {
        puts("usage: send_event <event_id>");
        return 1;
    }

    trigger_event(event_id);

    return 0;

}

static int registerFake_command(int argc, char **argv) {
    (void)argv;
    if (argc != 1) {
        puts("usage: registerFake");
        return 1;
    }
    trigger_event(REGISTERED);
    trigger_event(READY);
    return 0;
}

const shell_command_t SHELL_COMMANDS[] = {
    { "echo", "Prints the message to the console", echo_command },
    { "send_event", "Sends an event to the dispatcher. send_event 1", send_event },
    { "registerFake", "Fake´s the registration process", registerFake_command },
    { NULL, NULL, NULL }
};

void shell_loop(void) {
        /* buffer to read commands */
    char line_buf[SHELL_DEFAULT_BUFSIZE];

    /* run the shell, this will block the thread waiting for incoming commands */
    shell_run(SHELL_COMMANDS, line_buf, SHELL_DEFAULT_BUFSIZE);
}
