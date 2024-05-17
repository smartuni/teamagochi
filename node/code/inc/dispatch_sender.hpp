#pragma once

#include "shell_commands.hpp"
#include "msg.h"

/**
 * @brief Send an event to the dispatcher (with C support)
 * @param event The event to send.
 */
extern "C" void sendEvent(msg_t *event) {
  if (DISPATCHER_THREAD_ID == -1) {
    cout << "Dispatcher PID not set yet!" << endl;
    return;
  }

  msg_try_send(event, DISPATCHER_PID);
}
