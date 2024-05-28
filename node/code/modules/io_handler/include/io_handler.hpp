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
 * @brief       
 *
 * @author      
 * @}
 */
#ifndef IO_HANDLER_T
#define IO_HANDLER_T

#include "dispatch_handler.hpp"

class IoHandler : public DispatchHandler{

    private:
    public:
        IoHandler();
        void init();
        void handleEvent(msg_t *event);

        
};

#endif /* IO_HANDLER_T */
