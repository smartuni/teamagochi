#pragma once

#include "shell.h"
#include <stdio.h>

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

const shell_command_t SHELL_COMMANDS[] = {
    { "echo", "Prints the message to the console", echo_command },
    { NULL, NULL, NULL }
};

void shell_loop(void) {
        /* buffer to read commands */
    char line_buf[SHELL_DEFAULT_BUFSIZE];

    cout << "Starting shell loop" << endl;

    /* run the shell, this will block the thread waiting for incoming commands */
    shell_run_forever(SHELL_COMMANDS, line_buf, SHELL_DEFAULT_BUFSIZE);
}
