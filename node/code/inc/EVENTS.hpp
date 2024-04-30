#pragma once

#include <cstdint>

enum EVENTS: uint16_t {
    TERMINATE,
    WILDCARD, // Subscribe to all events [Special case]
    PING,
    PONG,
};
