#pragma once

#include <cstdint>

extern "C" enum EVENTS: uint16_t {
    TERMINATE,
    WILDCARD, // Subscribe to all events [Special case]
    PING,
    PONG,
    BUTTON_OK_PRESS,
    PIC_SWITCH,
    DOWN_CLICK,
};
