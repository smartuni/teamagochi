#include "events.h"
#include "msg.h"
#include "mutex.h"

#define ENABLE_DEBUG  1
#include "debug.h"

#define RCV_QUEUE_SIZE  (8)

kernel_pid_t t_events_pid;
static msg_t rcv_queue[RCV_QUEUE_SIZE];

pet_stats_t pet_stats;
device_register_code register_code;

void get_pet_stats(pet_stats_t* stats){
    mutex_lock(&pet_stats.mutex);
    if (!memcpy(stats,&pet_stats,sizeof(pet_stats))){
        DEBUG("Error setting pet_stats memory");
    }
    mutex_unlock(&pet_stats.mutex);
}

void get_register_code(char* code ){
    mutex_lock(&register_code.mutex);
    if (!memcpy(code,register_code.code,sizeof(register_code))){
        DEBUG("Error setting device_register_code memory");
    }
    mutex_unlock(&register_code.mutex);
}

void set_t_events_pid(kernel_pid_t pid){
    t_events_pid = pid;
}

void trigger_event(EVENT_T _event){
    msg_t msg;
    msg.type = _event;
    if (msg_try_send(&msg, t_events_pid) == 0) {
            DEBUG("Receiver queue full.\n");
    }
}

void trigger_event_device(EVENT_T event, char* value){
    if (event == REGISTER_CODE){
        if (!value){
            DEBUG("[events:device] no value given");
        }
        mutex_lock(&register_code.mutex);
        memcpy(register_code.code,value,sizeof(register_code));
        mutex_unlock(&register_code.mutex);
        trigger_event(REGISTER);
    }
}



void events_start(void (*fsm_callback)(EVENT_T event)){
    mutex_init(&pet_stats.mutex);
    mutex_init(&register_code.mutex);
    msg_t msg;
    msg_init_queue(rcv_queue, RCV_QUEUE_SIZE);
    while (1) {
        msg_receive(&msg);
        if(msg.type == INIT ||
           msg.type == REGISTER ||
           msg.type == REGISTERED ||
           msg.type == READY){
            puts("REGISTER");

        }else{
            fsm_callback(msg.type);
        }
        
    }
}
