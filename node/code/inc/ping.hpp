#pragma once

/**
 * @file ping.hpp
 * @brief Ping is a simple class to showcase the DispatchHandler.
 * @author AnnsAnn <git@annsann.eu>
*/
#include <iostream>

#include "mbox.h"
#include "dispatch_handler.hpp"

/**
 * @brief Ping is a simple class to showcase the DispatchHandler.
 * @details This class will send a ping event every second.
 */
class Ping : public DispatchHandler {
    void handleEvent(msg_t *event) {
        cout << "Pong @ Event: " << event->type << endl;
    }
};
