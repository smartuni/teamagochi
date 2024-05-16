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

#include "lwm2m_handler.h"
#include "pet.h"
#include "board.h"
#include "xtimer.h"
#include "lwm2m_client.h"
#include "lwm2m_client_objects.h"
#include "lwm2m_platform.h"
#include "objects/common.h"
#include "objects/device.h"

uint8_t connected = 0;
lwm2m_object_t *obj_list[OBJ_COUNT];
lwm2m_client_data_t client_data;

void lwm2m_handler_init(void)
{
    /* this call is needed before creating any objects */
    lwm2m_client_init(&client_data);

    /* add objects that will be registered */
    obj_list[0] = lwm2m_client_get_security_object(&client_data);
    obj_list[1] = lwm2m_client_get_device_object(&client_data);
    obj_list[2] = lwm2m_client_get_server_object(&client_data);
    obj_list[3] = lwm2m_object_pet_init(&client_data);

    lwm2m_obj_pet_args_t pet_args = {
        .instance_id = 0,
        .read_cb = NULL,
        .read_cb_arg = NULL
    };
    puts("hier\n");
    printf("%ld\n",pet_args.instance_id);
    int res = lwm2m_object_pet_instance_create(&pet_args);
    if (res<0) {
        puts("Error instantiating pet");
    }

    if (!obj_list[0] || !obj_list[1] || !obj_list[2] || !obj_list[3]) {
        puts("Could not create mandatory objects");
    }
}

void lwm2m_handler_start(void){
    lwm2m_client_run(&client_data, obj_list, OBJ_COUNT);
}

int lwm2m_handler_cli(int argc, char **argv){
    if (argc == 1) {
        goto help_error;
    }

    if (IS_ACTIVE(DEVELHELP) && !strcmp(argv[1], "get_reboot")) {
        printf("%d\n",lwm2m_device_reboot_requested());
        return 0;
    }

    help_error:
    if (IS_ACTIVE(DEVELHELP)) {
        printf("usage: %s <get_reboot>\n", argv[0]);
    }
    return 1;
}

void *handle_thread(void *arg)
{
    (void) arg;
    while(1){
        xtimer_sleep(5);
    }
    
    return NULL;
}
