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
#include "lwm2m_client.h"
#include "lwm2m_client_objects.h"
#include "objects/device.h"
#include "objects/security.h"
#include "credentials.h"


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
    this->obj_list[0] = lwm2m_object_security_init(&this->client_data);
    this->obj_list[1] = lwm2m_client_get_server_object(&this->client_data, CONFIG_LWM2M_SERVER_SHORT_ID);
    this->obj_list[2] = lwm2m_object_device_init(&this->client_data);
    this->obj_list[3] = lwm2m_object_pet_init(&this->client_data);
    // /* create security object instance */
    /* create security object instance */
    lwm2m_obj_security_args_t security_args = {
        .server_id = CONFIG_LWM2M_SERVER_SHORT_ID,
        .server_uri = CONFIG_LWM2M_SERVER_URI,
        .security_mode = LWM2M_SECURITY_MODE_NONE,
        .pub_key_or_id = psk_id,
        .pub_key_or_id_len = sizeof(psk_id) -1,
        .secret_key = psk_key,
        .secret_key_len = sizeof(psk_key) -1,
        .server_pub_key = server_rpk_pub,
        .server_pub_key_len = sizeof(server_rpk_pub),
        .is_bootstrap = false, /* set to true when using Bootstrap server */
        .client_hold_off_time = 5,
        .bootstrap_account_timeout = 0
    };

    int res = lwm2m_object_security_instance_create(&security_args, 1);
    if (res < 0) {
        puts("Could not instantiate the security object\n");
        return;
    }


    lwm2m_obj_pet_args_t pet_args = {
        .instance_id = 0,
        .write_cb = &lwm2m_write_callback
    };
    res = lwm2m_object_pet_instance_create(&pet_args);
    if (res<0) {
        puts("Error instantiating pet\n");
    }

    if (!this->obj_list[1] || !this->obj_list[2] || !this->obj_list[3]) {
        puts("Could not create mandatory objects\n");
    }
}

void Lwm2mHandler::lwm2m_handler_start(void){
    DEBUG("[Lwm2mHandler:start]: starting\n");
    lwm2m_client_run(&this->client_data, this->obj_list, OBJ_COUNT);
}

int Lwm2mHandler::lwm2m_handler_cli(int argc, char **argv){
    if (argc == 1) {
        goto help_error;
    }

    if (IS_ACTIVE(DEVELHELP) && !strcmp(argv[1], "feed")) {
        lwm2m_object_pet_feed(&this->client_data,0,this->obj_list[3]);
        return 0;
    }

    if (IS_ACTIVE(DEVELHELP) && !strcmp(argv[1], "medicate")) {
        lwm2m_object_pet_medicate(&this->client_data,0,this->obj_list[3]);
        return 0;
    }

    if (IS_ACTIVE(DEVELHELP) && !strcmp(argv[1], "play")) {
        lwm2m_object_pet_play(&this->client_data,0,this->obj_list[3]);
        return 0;
    }

    if (IS_ACTIVE(DEVELHELP) && !strcmp(argv[1], "clean")) {
        lwm2m_object_pet_clean(&this->client_data,0,this->obj_list[3]);
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
            DEBUG("[Lwm2mHandler:handleEvent]: feed Pet\n");
            lwm2m_object_pet_feed(&this->client_data,0,this->obj_list[3]);
            break;
        case EVENTS::PET_MEDICATE:
            DEBUG("[Lwm2mHandler:handleEvent]: medicate Pet\n");
            lwm2m_object_pet_medicate(&this->client_data,0,this->obj_list[3]);
            break;
        case EVENTS::PET_PLAY:
            DEBUG("[Lwm2mHandler:handleEvent]: play Pet\n");
            lwm2m_object_pet_play(&this->client_data,0,this->obj_list[3]);
            break;
        case EVENTS::PET_CLEAN:
            DEBUG("[Lwm2mHandler:handleEvent]: clean Pet\n");
            lwm2m_object_pet_clean(&this->client_data,0,this->obj_list[3]);
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
            message.type = EVENTS::PET_ILL;
            break;
        case LwM2M_PET_BORED_ID:
            message.type = EVENTS::PET_BORED;
            break;
        case LwM2M_PET_DIRTY_ID:
            message.type = EVENTS::PET_DIRTY;
            break;
        default:
            DEBUG("[Lwm2mHandler:write_callback]: Id not found\n");
            return;
    }
    DispatchHandler::sendEvent(&message);
}
