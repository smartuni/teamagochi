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
// #include "init_display.h"
#include "init_lvgl.h"
#include "ping.hpp"
#include "pong.hpp"
#include "riot/thread.hpp"
#include "lvgl/lvgl.h"
#include "shell_commands.hpp"

// Example Module Import
#include "external_module.h"

#include "test_folder/test_hello.h"

using namespace std;
using namespace riot;

kernel_pid_t DISPATCHER_PID;

/* main */
int main() {
  printf("\n************ We are in C++ 😎 ***********\n");
  printf("\n");

  puts("{\"result\": \"PASS\"}");

  // Show the example module function
  cout << "Example Module Init: " << external_module_initialized << endl;

  hello();

  cout << "Sleeping for 5 seconds...\n" << endl;
  riot::this_thread::sleep_for(chrono::seconds(5));
  cout << "Done sleeping.\n" << endl;

  // Create the dispatcher
  Dispatcher *dispatcher = new Dispatcher();
  dispatcher->startInternalThread();

  DISPATCHER_PID = dispatcher->getPID();
  DISPATCHER_THREAD_ID = DISPATCHER_PID;

  // Create the ping class
  Ping *ping = new Ping();
  ping->startInternalThread();

  // Create the pong class
  Pong *pong = new Pong();
  pong->startInternalThread();

  // Subscribe the ping and pong classes to each other
  dispatcher->subscribe({EVENTS::PING}, ping->getPID());
  dispatcher->subscribe({EVENTS::PONG}, pong->getPID());

  //   cout << "Sending initial ping event" << endl;
  //   msg_t message;
  //   message.type = EVENTS::PING;

  msg_try_send(&message, dispatcher->getPID());
printf("printf success\n");

printf("initing\n");
  init_lvgl();
  printf("init success\n");

  shell_loop();

  return 0;
}
