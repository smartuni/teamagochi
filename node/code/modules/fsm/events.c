#include "events.h"

#define ENABLE_DEBUG  1
#include "debug.h"

#define RCV_QUEUE_SIZE  (8)

kernel_pid_t t_events_pid;
static msg_t rcv_queue[RCV_QUEUE_SIZE];

void set_t_events_pid(kernel_pid_t pid){
    t_events_pid = pid;
}

void trigger_event(EVENT_T _event){
    DEBUG("event input \n");
    msg_t msg;
    msg.type = _event;
    if (msg_try_send(&msg, t_events_pid) == 0) {
            printf("Receiver queue full.\n");
        }
}


void events_start(void (*fsm_callback)(EVENT_T event)){  
    msg_t msg;
    msg_init_queue(rcv_queue, RCV_QUEUE_SIZE);
    while (1) {
        msg_receive(&msg);
        fsm_callback(msg.type);
    }
}
