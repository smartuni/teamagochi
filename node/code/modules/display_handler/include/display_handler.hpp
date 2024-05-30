/*
 * Copyright (C) 2024 HAW Hamburg
 *
 * This file is subject to the terms and conditions of the GNU Lesser
 * General Public License v2.1. See the file LICENSE in the top level
 * directory for more details.
 */

/**
 * @{
 *
 * @file
 * @brief       Wakaama LwM2M Handler
 *
 * @author      
 * @}
 */
#ifndef DISPLAY_HANDLER_T
#define DISPLAY_HANDLER_T

#include "dispatch_handler.hpp"

class DisplayHandler : public DispatchHandler{
    public:
        DisplayHandler();
        void handleEvent(msg_t *event);
        void startDisplayThread();
        void display_init();
        
    private:
        void display_run();
        kernel_pid_t display_thread_pid;
        char display_thread_stack [DISPLAY_STACKSIZE];
        static void *displayInitThread(void *This){
            puts("Display thread");
            ((DisplayHandler *)This)->display_run();
            return NULL;
        }  
};


#endif /* DISPLAY_HANDLER_T */
