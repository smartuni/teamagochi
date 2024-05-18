/*
 * Copyright (C) 2024 HAW Hamburg
 *
 * This file is subject to the terms and conditions of the GNU Lesser
 * General Public License v2.1. See the file LICENSE in the top level
 * directory for more details.
 */

/**
 * @{
 *
 * @file
 * @brief       Wakaama LwM2M Handler
 *
 * @author      Moritz Holzer <moritz.holzer@haw-hamburg.de>
 * @}
 */

#include "lwm2m_handler.hpp"
#include "pet.h"
#include "pet_model.h"
//#include "board.h"
//#include "xtimer.h"
#include "lwm2m_client.h"
#include "lwm2m_client_objects.h"
//#include "lwm2m_platform.h"
//#include "objects/common.h"
#include "objects/device.h"

#define ENABLE_DEBUG  1
#include "debug.h"

static void lwm2m_write_callback(uint16_t event_id);

Lwm2mHandler::Lwm2mHandler(){
}

void Lwm2mHandler::lwm2m_handler_init(void)
{
    /* this call is needed before creating any objects */
    lwm2m_client_init(&this->client_data);
   
    /* add objects that will be registered */
    this->obj_list[0] = lwm2m_client_get_security_object(&this->client_data);
    this->obj_list[1] = lwm2m_client_get_device_object(&this->client_data);
    this->obj_list[2] = lwm2m_client_get_server_object(&this->client_data);
    this->obj_list[3] = lwm2m_object_pet_init(&this->client_data);
    lwm2m_obj_pet_args_t pet_args = {
        .instance_id = 0,
        .write_cb = &lwm2m_write_callback
    };
    int res = lwm2m_object_pet_instance_create(&pet_args);
    if (res<0) {
        puts("Error instantiating pet");
    }

    if (!this->obj_list[0] || !this->obj_list[1] || !this->obj_list[2] || !this->obj_list[3]) {
        puts("Could not create mandatory objects");
    }
}

void Lwm2mHandler::lwm2m_handler_start(void){
    lwm2m_client_run(&this->client_data, this->obj_list, OBJ_COUNT);
}

int Lwm2mHandler::lwm2m_handler_cli(int argc, char **argv){
    if (argc == 1) {
        goto help_error;
    }

    if (IS_ACTIVE(DEVELHELP) && !strcmp(argv[1], "feed")) {
        lwm2m_object_pet_fed(&this->client_data,0,this->obj_list[3]);
        return 0;
    }

    if (IS_ACTIVE(DEVELHELP) && !strcmp(argv[1], "medicate")) {
        lwm2m_object_pet_medicated(&this->client_data,0,this->obj_list[3]);
        return 0;
    }

    if (IS_ACTIVE(DEVELHELP) && !strcmp(argv[1], "play")) {
        lwm2m_object_pet_played(&this->client_data,0,this->obj_list[3]);
        return 0;
    }

    if (IS_ACTIVE(DEVELHELP) && !strcmp(argv[1], "clean")) {
        lwm2m_object_pet_cleaned(&this->client_data,0,this->obj_list[3]);
        return 0;
    }

    help_error:
    if (IS_ACTIVE(DEVELHELP)) {
        printf("usage: %s <feed|mediacte|play|clean>\n", argv[0]);
    }
    return 1;
}

void Lwm2mHandler::handleEvent(msg_t *event){
    DEBUG("[Lwm2mHandler:handleEvent]\n");
    switch(event->type){
        case EVENTS::PET_FEED:
            DEBUG("[Lwm2mHandler:handleEvent]: feed Pet");
            lwm2m_object_pet_fed(&this->client_data,0,this->obj_list[3]);
            break;
        case EVENTS::PET_MEDICATE:
            DEBUG("[Lwm2mHandler:handleEvent]: feed Pet");
            lwm2m_object_pet_medicated(&this->client_data,0,this->obj_list[3]);
            break;
        case EVENTS::PET_PLAY:
            DEBUG("[Lwm2mHandler:handleEvent]: feed Pet");
            lwm2m_object_pet_played(&this->client_data,0,this->obj_list[3]);
            break;
        case EVENTS::PET_CLEAN:
            DEBUG("[Lwm2mHandler:handleEvent]: feed Pet");
            lwm2m_object_pet_cleaned(&this->client_data,0,this->obj_list[3]);
            break;
    }
}

static void lwm2m_write_callback(uint16_t event_id){
    msg_t message;
    switch (event_id) {
        case LWM2M_PET_HUNGRY_ID:
            message.type = EVENTS::PET_HUNGRY;
            break;
        case LwM2M_PET_ILL_ID:
            break;
        case LwM2M_PET_BORED_ID:
            break;
        case LwM2M_PET_DIRTY_ID:
            break;
        default:
            DEBUG("[Lwm2mHandler:write_callback]: Id not found");
            return;
    }
    DispatchHandler::sendEvent(&message);
}
