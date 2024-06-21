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

void get_register_code(char *code){
    mutex_lock(&register_code.mutex);
    if (!strcpy(code,register_code.code)){
        DEBUG("Error setting device_register_code memory");
    }
    printf("code in get: %s\n",code);
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

void trigger_event_int(EVENT_T event, int32_t value){
    mutex_lock(&pet_stats.mutex);
    if (event == COLOR){
        pet_stats.color = value;
        trigger_event(COLOR);
    }else if (event == HAPPINESS){
        pet_stats.happiness = value;
        trigger_event(HAPPINESS);
    }else if (event == WELLBEING){
        pet_stats.wellbeing = value;
        trigger_event(WELLBEING);
    }else if (event == HEALTH){
        pet_stats.health = value;
        trigger_event(HEALTH);
    }else if (event == XP){
        pet_stats.xp = value;
        trigger_event(XP);
    }else if (event == HUNGER){
        pet_stats.hunger = value;
        trigger_event(HUNGER);
    }else if (event == CLEANLINESS){
        pet_stats.cleanliness = value;
        trigger_event(CLEANLINESS);
    }else if (event == FUN){
        pet_stats.fun = value;
        trigger_event(FUN);
    }
    mutex_unlock(&pet_stats.mutex);
}


void trigger_event_string(EVENT_T event, char* value){
    if (event == REGISTER_CODE){
        if (!value){
            DEBUG("[events:string] no value given");
        }
        mutex_lock(&register_code.mutex);
        char code[8];
        register_code.code = (char *) &code;
        strcpy(register_code.code,value);
        mutex_unlock(&register_code.mutex);
        trigger_event(REGISTER_CODE);
    }else if(event == NAME){
        if (!value){
            DEBUG("[events:string] no value given");
        }
        mutex_lock(&pet_stats.mutex);
        memcpy(pet_stats.name,value,sizeof(*value));
        mutex_unlock(&pet_stats.mutex);
    }
}

void events_start(void (*fsm_callback)(EVENT_T event)){
    mutex_init(&pet_stats.mutex);
    mutex_init(&register_code.mutex);
    msg_t msg;
    msg_init_queue(rcv_queue, RCV_QUEUE_SIZE);
    while (1) {
        msg_receive(&msg);
        fsm_callback(msg.type);        
    }
}
