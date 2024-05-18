/*
 * Copyright (C) 2018 Beduino Master Projekt - University of Bremen
 *               2019 HAW Hamburg
 *
 * This file is subject to the terms and conditions of the GNU Lesser
 * General Public License v2.1. See the file LICENSE in the top level
 * directory for more details.
 */

/**
 * @ingroup     examples
 * @{
 *
 * @file
 * @brief       Example application for Eclipse Wakaama LwM2M Client
 *
 * @author      Christian Manal <manal@uni-bremen.de>
 * @author      Leandro Lanzieri <leandro.lanzieri@haw-hamburg.de>
 * @}
 */

#include <cstdio>
#include <vector>

#include <inttypes.h>
#include <stdio.h>
#include <stdlib.h>

#include "board.h"
#include "xtimer.h"
#include "thread.h"

#include "board.h"
#include "lwm2m_client.h"
#include "lwm2m_client_objects.h"
#include "lwm2m_platform.h"
#include "objects/common.h"
#include "lwm2m_handler.hpp"
#include "xtimer.h"
#include "dispatcher.hpp"
#include "dispatch_handler.hpp"

#include "msg.h"
#include "shell.h"
#define ENABLE_DEBUG
#define SHELL_QUEUE_SIZE (8)
static msg_t _shell_queue[SHELL_QUEUE_SIZE];

#include "architecture.h"
using namespace std;

kernel_pid_t DISPATCHER_PID;

extern int lwm2m_handler_cli(int argc, char **argv);
static const shell_command_t shell_commands[] = {
    //{"lwm2m","Lwm2m Handler", lwm2m_handler_cli},
    { NULL, NULL, NULL }
};

 //char handler_thread_stack[THREAD_STACKSIZE_MAIN];

int main(void)
{
    xtimer_sleep(5);
    // Create the dispatcher
    Dispatcher *dispatcher = new Dispatcher();
    dispatcher->startInternalThread();

    DISPATCHER_PID = dispatcher->getPID();
    //DISPATCHER_THREAD_ID = DISPATCHER_PID;

    Lwm2mHandler *lwm2mHandler = new Lwm2mHandler();
    lwm2mHandler->lwm2m_handler_init();
    lwm2mHandler->lwm2m_handler_start();
    lwm2mHandler->startInternalThread();
    dispatcher->subscribe({EVENTS::PET_HUNGRY}, lwm2mHandler->getPID());

    msg_init_queue(_shell_queue, SHELL_QUEUE_SIZE);
    char line_buf[SHELL_DEFAULT_BUFSIZE];
    shell_run(shell_commands, line_buf, SHELL_DEFAULT_BUFSIZE);
    while(0){

    }
    return 0;
}
