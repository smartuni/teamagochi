#include "events.h"
#include "event.h"

static event_queue_t queues[2];
static event_handler_t handler_fn;

event_queue_t * get_queue_standard(void){
    return &queues[1];
    // return &queues[1];
}

void trigger_event_input(EVENT_T event){
    team_event_t t_event = {.super.handler = handler_fn, .event = event};
    event_post(&queues[0],&t_event.super);
}


void trigger_event(EVENT_T event){
    team_event_t t_event = {.super.handler = handler_fn, .event = event};
    event_post(&queues[1],&t_event.super);
}


void events_handler_init(event_handler_t handlerfn){
    handler_fn = handlerfn;
    event_queues_init(queues,2);
}


void events_start(void){  
    event_loop_multi(queues,2);
}
