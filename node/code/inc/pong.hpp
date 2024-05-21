/**
 * @file pong.hpp
 * @brief pong is a simple class to showcase the DispatchHandler.
 * @author AnnsAnn <git@annsann.eu>
*/
#pragma once

#include <iostream>

#include "EVENTS.hpp"

#include "mbox.h"
#include "dispatch_handler.hpp"
#include "init_lvgl.h"


/**
 * @brief Ping is a simple class to showcase the DispatchHandler.
 * @details This class will send a ping event every second.
 */
class Pong : public DispatchHandler {
    void handleEvent(msg_t *event) {
        cout << "⬅️ Pong - Sending Ping @ Event: " << event->type << endl;

        riot::this_thread::sleep_for(chrono::seconds(1));
        //button_aus();

        msg_t message;
        message.type = EVENTS::PING;

        this->sendEvent(&message);
    }
};
