#pragma once

#include "dispatch_handler.hpp"
#include "EVENTS.hpp"

#include <list>

using namespace std;

struct Subscription {
    list<EVENTS> event;
    kernel_pid_t pid;
};

class Dispatcher : public DispatchHandler {
    private:
        list<Subscription> subscriptions;

    public:
    Dispatcher();

    /**
     * @brief Registers a PID to an event.
     * @details This function will register a PID to an event. When the event
     * is received, the PID will be sent the event.
     * @param events List of events to subscribe to.
     * @param pid The PID to send the event to.
    */
    void subscribe(list<EVENTS> events, kernel_pid_t pid);

    /**
     * Internally used to send an event to a PID.
    */
    void handleEvent(msg_t *event);
};
