#pragma once

#include <cstdint>

extern "C" enum EVENTS: uint16_t {
    TERMINATE,
    WILDCARD, // Subscribe to all events [Special case]
    PING,
    PONG,
    PET_HUNGRY,
    PET_ILL,
    PET_BORED,
    PET_DIRTY,
    PET_FEED,
    PET_MEDICATE,
    PET_PLAY,
    PET_CLEAN,
};
