#include "dispatcher.hpp"
#include "GLOBALS.hpp"

Dispatcher::Dispatcher() {
    this->subscriptions = list<Subscription>();
    this->setRespectTerminate(false); // We need to send the other classes a terminate event
}

void Dispatcher::subscribe(list<EVENTS> events, kernel_pid_t pid) {
    Subscription sub;
    sub.event = events;
    sub.pid = pid;

    this->subscriptions.push_back(sub);
}

void Dispatcher::handleEvent(msg_t *event) {
    for (Subscription sub : this->subscriptions) {
        for (EVENTS e : sub.event) {
            if (e == event->type || e == EVENTS::WILDCARD || event->type == EVENTS::TERMINATE) {
                cout << "👀 PID: " << sub.pid << " was interested in event " << event->type << " notifying them" << endl;

                msg_t msg;
                msg.content.ptr = event->content.ptr;
                msg.content.value = event->content.value;
                msg.type = event->type;

                msg_try_send(&msg, sub.pid);
            }
        }
    }
}
