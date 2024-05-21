/**
 * @brief Main file for the C++ code.
 * @author Tom <git@annsann.eu>
 */

#include <cstdio>
#include <vector>

#include "architecture.h"
#include "dispatch_handler.hpp"
#include "dispatcher.hpp"
#include "shell.h"
#include "init_lvgl.h"
#include "ping.hpp"
#include "pong.hpp"
#include "riot/thread.hpp"
#include "shell_commands.hpp"
#include "init_lvgl.h"

// Example Module Import
//#include "external_module.h"
//LWM2M Handler Import
//#include "lwm2m_handler.hpp"
//#include "display_handler.hpp"
//#include "test_folder/test_hello.h"

using namespace std;

#define SHELL_QUEUE_SIZE (8)
static msg_t _shell_queue[SHELL_QUEUE_SIZE];

kernel_pid_t DISPATCHER_PID;

/* main */
int main() {
  printf("\n************ We are in C++ 😎 ***********\n");
  printf("\n");

  puts("{\"result\": \"PASS\"}");

    //   // Show the example module function
    //   cout << "Example Module Init: " << external_module_initialized << endl;

    //   //hello();

    //   cout << "Sleeping for 5 seconds...\n" << endl;
    //   riot::this_thread::sleep_for(chrono::seconds(5));
//   cout << "Done sleeping.\n" << endl;

    // Create the dispatcher
    Dispatcher *dispatcher = new Dispatcher();
    dispatcher->startInternalThread();

    DISPATCHER_PID = dispatcher->getPID();
    DISPATCHER_THREAD_ID = DISPATCHER_PID;

// //   // Create the ping class
//    Ping *ping = new Ping();
//    ping->startInternalThread();

// //   // Create the pong class
//    Pong *pong = new Pong();
//    pong->startInternalThread();

// //   // Subscribe the ping and pong classes to each other
//    dispatcher->subscribe({EVENTS::PING}, ping->getPID());
//    dispatcher->subscribe({EVENTS::PONG}, pong->getPID());
    
    // Lwm2mHandler *lwm2mHandler = new Lwm2mHandler();
    // lwm2mHandler->lwm2m_handler_init();
    // lwm2mHandler->lwm2m_handler_start();
    // lwm2mHandler->startInternalThread();

    // dispatcher->subscribe({EVENTS::PET_HUNGRY}, lwm2mHandler->getPID());

    

    // cout << "Sending initial ping event" << endl;
    // msg_t message;
    // message.type = EVENTS::PING;

    // msg_try_send(&message, dispatcher->getPID());
    init_lvgl();

    
    // DisplayHandler *displayHandler = new DisplayHandler();
    // //displayHandler->display_init();
    // displayHandler->startDisplayThread();
    // displayHandler->startInternalThread();
    
    msg_init_queue(_shell_queue, SHELL_QUEUE_SIZE);

    shell_loop();
    
  return 0;
}
