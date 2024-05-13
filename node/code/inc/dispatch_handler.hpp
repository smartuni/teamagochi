/**
 * @brief DispatchHandler is an abstract class that receives events.
 * @author Tom <git@annsann.eu>
 */
#pragma once

using namespace std;

#include <iostream>

#include "msg.h"
#include "GLOBALS.hpp"
#include "EVENTS.hpp"
#include "thread.h"

/**
 * DispatchHandler is an abstract class that receives events.
 * @see Dispatcher
 * If you want to receive events, you should inherit from this class and
 * implement the handleEvent function. handleData is optional, but you should
 * implement it if you want to receive data from the heightsensor.
 */
class DispatchHandler {
 private:
  /**
   * @brief The event queue.
   */
  static const int QUEUE_SIZE = 8;
  msg_t rcv_queue[QUEUE_SIZE];
  char internal_thread_stack [THREAD_STACKSIZE_MAIN];
  kernel_pid_t internal_thread_pid;
  bool respect_terminate = true;

 public:
  /**
   * @brief Constructor.
   * @details Initializes the event queue and starts the event loop.
   */
  DispatchHandler() { }

  void setRespectTerminate(bool respect_terminate) {
    this->respect_terminate = respect_terminate;
  }

  /**
   * @brief Send an event to the dispatcher.
   * @param event The event to send.
  */
  static void sendEvent(msg_t *event) {
    if (DISPATCHER_PID == -1) {
      cout << "Dispatcher PID not set yet!" << endl;
      return;
    }

    msg_try_send(event, DISPATCHER_PID);
  }

  kernel_pid_t getPID() { return this->internal_thread_pid; }

  /**
   * This quirky function does heinous things in order to make riot threads work with C++ classes.
   * @warning Warcrimes :3
  */
  void startInternalThread() {
    cout << "Starting internal thread" << endl;
    this->internal_thread_pid = thread_create(internal_thread_stack, sizeof(internal_thread_stack),
                  THREAD_PRIORITY_MAIN - 1, THREAD_CREATE_WOUT_YIELD, internalThreadEntryFunction, this, "DispatchHandler for Class");
  }

  /**
   * @brief Starts the event loop.
   * @details This function will run forever and call the handleEvent function
   * for each event.
   */
  void startHandler() {
    msg_init_queue(rcv_queue, QUEUE_SIZE);
    
    // Event loop - Blocks when no events are available
    while (true) {
      msg_t message;
      msg_receive(&message);

      if (message.type == EVENTS::TERMINATE) {
        cout << "💣 Received TERMINATE event, exiting..." << endl;
        if (!this->respect_terminate) {
            cout << "😎 Not respecting terminate, will handle event anyways" << endl;
            this->handleEvent(&message);
            cout << "🌃 Handled Terminate Event, now going offline fr ..." << endl;
        }
        return;
      }

      this->handleEvent(&message);
    }
  }

  /**
   * @brief Handle an event.
   * @param event The event to handle.
   * @warning Make sure to implement this function in your subclass.
   */
  virtual void handleEvent(msg_t *event) = 0;

 private:
  /**
   * @brief Internal thread entry function.
   * The C++11 thread compat layer doesn't appear to properly support C++ classes
   * so we have to do some (really) cursed pthread stuff to make it work.
  */
  static void *internalThreadEntryFunction(void *This) {
    ((DispatchHandler *)This)->startHandler();

    return NULL;
  }
};
