/**
 * @brief Main file for the C++ code.
 * @author Tom <git@annsann.eu>
 */

#include <cstdio>
#include <vector>

#include "architecture.h"
#include "init_display.h"
#include "ping.hpp"
#include "dispatch_handler.hpp"
#include "riot/thread.hpp"

using namespace std;
using namespace riot;

/* main */
int main() {
  printf("\n************ We are in C++ 😎 ***********\n");
  printf("\n");

  init_display();

  puts("{\"result\": \"PASS\"}");

  cout << "Sleeping for 5 seconds...\n" << endl;
  riot::this_thread::sleep_for(chrono::seconds(5));
  cout << "Done sleeping.\n" << endl;
  Ping *ping = new Ping();
  
  ping->startInternalThread();

  while (true) {
    cout << "Sending ping event" << endl;
    msg_t message;
    message.content.value = 69;

    msg_try_send(&message, ping->getPID());

    riot::this_thread::sleep_for(chrono::seconds(1));
  }

  return 0;
}
