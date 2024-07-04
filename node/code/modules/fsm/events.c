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

void get_pet_stats(char *buf){
    sprintf(buf,"Happiness: %ld%%\n Wellbeing: %ld%%\n Health: %ld%%\n XP: %ld%%\n Hunger: %ld%%\n Cleanliness: %ld%%\n Fun: %ld%%\n",
            pet_stats.happiness, pet_stats.wellbeing, pet_stats.health, pet_stats.xp,pet_stats.hunger, pet_stats.cleanliness, pet_stats.fun);
}

void get_pet_short_info(char *buf){
    sprintf(buf,"%s XP: %ld%%",pet_stats.name, pet_stats.xp);
}

char* get_register_code(void){
    printf("code in get: %s\n",register_code.code);
    return register_code.code;
}

void set_t_events_pid(kernel_pid_t pid){
    t_events_pid = pid;
}

void trigger_event(EVENT_T _event){
    //DEBUG("event input \n");
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
        strcpy(register_code.code,value);
        trigger_event(REGISTER_CODE);
    }else if(event == NAME){
        if (!value){
            DEBUG("[events:string] no value given");
        }
        mutex_lock(&pet_stats.mutex);
        strcpy(pet_stats.name,value);
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
