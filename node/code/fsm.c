
#include "fsm.h"

#include <stdio.h>

#include "event.h"
#include "events.h"
#include "msg.h"
// #include "lwm2m_handler.h"
#include "display_handler.h"
#include "io_handler.h"

#define ENABLE_DEBUG  1
#include "debug.h"

char fsm_thread_stack[THREAD_STACKSIZE_DEFAULT + THREAD_EXTRA_STACKSIZE_PRINTF];

typedef struct hierarchical_state state_t;

struct hierarchical_state {
  fsm_handler Handler;  //!< State handler function
  void (*Entry)(void);  //!< Entry action for state
  void (*Exit)(void);   //!< Exit action for state.

  const state_t *const Parent;  //!< Parent state of the current state.
  const state_t *const Node;    //!< Child states of the current state.
  uint32_t Level;               //!< Hierarchy level from the top state.
};

handler_result_t testHandler(EVENT_T event);
void main_state_init_entry_handler(void);
handler_result_t main_state_init_handler(EVENT_T event);
void fsm_handle(EVENT_T event);
void *fsm_thread(void * arg);
// // This is an array of root (top most) states .
// static const state_t Top_Level[] = {{
//     top_level_awake_handler,        // state handler
//     top_level_awake_entry_handler,  // Entry action handler
//     top_level_awake_exit_handler,   // Exit action handler
//     NULL,                           // Parent state
//     RunningState,                   // Child state
//     0                               // Hierarchical state level
// }};

// // This is an array of root (top most) states .
// static const state_t RunningState[] = {{
//     running_state_init_handler,        // state handler
//     running_state_init_entry_handler,  // Entry action handler
//     running_state_init_exit_handler,   // Exit action handler
//     MainState,                         // Parent state
//     NULL,                              // Child state
//     1                                  // Hierarchical state level
// }};

// // This is an array of root (top most) states .
// static const state_t MainState[] = {
//     {
//         main_state_init_handler,        // state handler
//         main_state_init_entry_handler,  // Entry action handler
//         main_state_init_exit_handler,   // Exit action handler
//         RunningState,                   // Parent state
//         NULL,                           // Child state
//         2                               // Hierarchical state level
//     },
//     {
//         main_state_notregistered_handler,        // state handler
//         main_state_notregistered_entry_handler,  // Entry action handler
//         main_state_notregistered_exit_handler,   // Exit action handler
//         RunningState,                            // Parent state
//         NULL,                                    // Child state
//         2                                        // Hierarchical state level
//     },
//     {
//         main_state_linkednopet_handler,        // state handler
//         main_state_linkednopet_entry_handler,  // Entry action handler
//         main_state_linkednopet_exit_handler,   // Exit action handler
//         RunningState,                          // Parent state
//         NULL,                                  // Child state
//         2                                      // Hierarchical state level
//     },
//     {
//         main_state_pet_handler,        // state handler
//         main_state_pet_entry_handler,  // Entry action handler
//         main_state_pet_exit_handler,   // Exit action handler
//         RunningState,                  // Parent state
//         NULL,                          // Child state
//         2                              // Hierarchical state level
//     }};

static const state_t MainState[4];
static const state_t RunningState[1];

// This is an array of root (top most) states .
static const state_t MainState[] = {
    {
        main_state_init_handler,              // state handler
        main_state_init_entry_handler,  // Entry action handler
        NULL,                           // Exit action handler
        &RunningState[0],               // Parent state
        NULL,                           // Child state
        2                               // Hierarchical state level
    },
    {
        NULL,              // state handler
        NULL,              // Entry action handler
        NULL,              // Exit action handler
        &RunningState[0],  // Parent state
        NULL,              // Child state
        2                  // Hierarchical state level
    },
    {
        NULL,              // state handler
        NULL,              // Entry action handler
        NULL,              // Exit action handler
        &RunningState[0],  // Parent state
        NULL,              // Child state
        2                  // Hierarchical state level
    },
    {
        NULL,              // state handler
        NULL,              // Entry action handler
        NULL,              // Exit action handler
        &RunningState[0],  // Parent state
        NULL,              // Child state
        2                  // Hierarchical state level
    }};

// This is an array of root (top most) states .
static const state_t RunningState[] = {{
    NULL,           // state handler
    NULL,           // Entry action handler
    NULL,           // Exit action handler
    NULL,           // Parent state
    &MainState[0],  // Child state
    1               // Hierarchical state level
}};

// // This is an array of root (top most) states .
// static const state_t Top_Level[] = {{
//     NULL,        // state handler
//     NULL,  // Entry action handler
//     NULL,   // Exit action handler
//     NULL,                           // Parent state
//     &RunningState[0],                   // Child state
//     0                               // Hierarchical state level
// }};

static const state_t *currentState = &MainState[0];

void fsm_start_thread(void){
    set_t_events_pid(thread_create(fsm_thread_stack, sizeof(fsm_thread_stack),
                  THREAD_PRIORITY_MAIN - 1, THREAD_CREATE_STACKTEST,
                  fsm_thread, NULL, "fsm_thread"));
}

void *fsm_thread(void *arg) {
  (void)arg;
  DEBUG("[FSM:thread]: start\n");
  currentState->Entry();
//   events_handler_init(fsm_handle);
    events_start(fsm_handle);

  return NULL;
}

void fsm_handle(EVENT_T event) {
    DEBUG("[FSM:fsm_handle]: handle\n");
    // team_event_t *t_event = container_of(event, team_event_t, super_event);
    // DEBUG("[FSM:fsm_handle]: event defined: %d\n",t_event->event);
    handler_result_t result = currentState->Handler(event);
    if (result != EVENT_HANDLED) {
        const state_t *pState = currentState;
        do {
        // check if state has parent state.
        if (pState->Parent == NULL)  // Is Node reached top
        {
            DEBUG("[FSM:fsm_handle]: Fatal error, terminating.\n");
            // This is a fatal error. terminate state machine.
            return;
        }

        pState = pState->Parent;  // traverse to parent state
        } while (pState->Handler ==
                NULL);  // repeat again if parent state doesn't have handler
        pState->Handler(event);
    }else{
        DEBUG("[FSM:fsm_handle]: Event handled\n");
    }
}

void traverse_state(state_t target_state) {
  while (target_state.Level > currentState->Level) {
    if (currentState->Exit) currentState->Exit();
    currentState = currentState->Parent;
    if (currentState->Entry) currentState->Entry();
  }
  while (target_state.Level < currentState->Level) {
    currentState = currentState->Node;
    if (currentState->Entry) currentState->Entry();
  }
  if (currentState->Level == target_state.Level) {
    if (currentState->Exit) currentState->Exit();
  }
  currentState = &target_state;
  if (currentState->Entry) currentState->Entry();
}

handler_result_t testHandler(EVENT_T event) {
  switch (event) {
    case PET_FEED:
      puts("PET feed");
      break;
    default:
      break;
  }
  return EVENT_HANDLED;
}

void main_state_init_entry_handler(void) {
    DEBUG("[FSM:init_entry]\n");
    // lwm2m_handler_init();
    // lwm2m_handler_start();
    io_init();
    display_init();
    startDisplayThread();
}

handler_result_t main_state_init_handler(EVENT_T event) {
    DEBUG("[FSM:main_state_init_handler]: lwm2m handle\n");
    // lwm2m_handleEvent(event);
    ioHandler_handleEvent(event);
    displayHandler_handleEvent(event);
    
    return EVENT_HANDLED;
}
