#pragma once

#include <cstdint>

extern "C" enum EVENTS: uint16_t {
    TERMINATE,
    WILDCARD, // Subscribe to all events [Special case]
    PING,
    PONG,
    BUTTON_OK_PRESSED,
    BUTTON_OK_RELEASED,
    BUTTON_UP_PRESSED,
    BUTTON_UP_RELEASED,
    BUTTON_DOWN_PRESSED,
    BUTTON_DOWN_RELEASED,
    PIC_SWITCH,
    DOWN_CLICK,
};
