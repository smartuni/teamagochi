#include "fsm.h"

#include <stdio.h>

#include "events.h"
#include "lwm2m_handler.h"
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
  const state_t *const Child;    //!< Child states of the current state.
  uint32_t Level;               //!< Hierarchy level from the top state.
};

static const state_t Top_Level[3];
static const state_t On_Level[3];
static const state_t Pet_Level[3];

static const state_t Top_Level[] = {
    {//first start
        firstStart_handler,                   // state handler
        firstStart_entry_handler,             // Entry action handler
        NULL,                               // Exit action handler
        NULL,                               // Parent state
        NULL,                       // Child state
        1                                   // Hierarchical state level
    },
    {//on
        on_handler,                   // state handler
        on_entry_handler,             // Entry action handler
        NULL,                               // Exit action handler
        NULL,                               // Parent state
        &On_Level[0],                       // Child state
        1                                   // Hierarchical state level
    },
    {//off
        off_handler,              // state handler
        NULL,                               // Entry action handler
        NULL,                               // Exit action handler
        NULL,                               // Parent state
        NULL,                               // Child state
        1                                   // Hierarchical state level
    }
};

static const state_t On_Level[] = {
    {//unregistered
        default_handler,              // state handler
        NULL,                     // Entry action handler
        NULL,                     // Exit action handler
        &Top_Level[1],            // Parent state
        NULL,                     // Child state
        2                         // Hierarchical state level
    },
    {//user_linked
        default_handler,              // state handler
        NULL,                     // Entry action handler
        NULL,                     // Exit action handler
        &Top_Level[1],            // Parent state
        NULL,                     // Child state
        2                         // Hierarchical state level
    },
    {//pet
        default_handler,              // state handler
        NULL,                     // Entry action handler
        NULL,                     // Exit action handler
        &Top_Level[1],            // Parent state
        &Pet_Level[0],            // Child state
        2                         // Hierarchical state level
    }
};

static const state_t Pet_Level[] = {
    {//Main_View
        default_handler,              // state handler
        NULL,                     // Entry action handler
        NULL,                     // Exit action handler
        &On_Level[2],             // Parent state
        NULL,                     // Child state
        3                         // Hierarchical state level
    },
    {//Game_View
        default_handler,              // state handler
        NULL,                     // Entry action handler
        NULL,                     // Exit action handler
        &On_Level[2],             // Parent state
        NULL,                     // Child state
        3                         // Hierarchical state level
    },
    {//Stat_View
        default_handler,              // state handler
        NULL,                     // Entry action handler
        NULL,                     // Exit action handler
        &On_Level[2],             // Parent state
        NULL,                     // Child state
        3                         // Hierarchical state level
    }
};

static const state_t *currentState = &Top_Level[0];

void fsm_start_thread(void){
    set_t_events_pid(thread_create(fsm_thread_stack, sizeof(fsm_thread_stack),
                  THREAD_PRIORITY_MAIN - 1, THREAD_CREATE_STACKTEST,
                  fsm_thread, NULL, "fsm_thread"));    
}

void *fsm_thread(void *arg) {
    (void)arg;
    DEBUG("[FSM:thread]: start\n");
    currentState->Entry();
    events_start(fsm_handle);
    return NULL;
}

void fsm_handle(EVENT_T event) {
    DEBUG("[FSM:fsm_handle]: handle\n");
    if (!currentState->Handler) puts("no handler \n");
    handler_result_t result = currentState->Handler(event);
    if (result != HANDLED) {
        DEBUG("[FSM:fsm_handle]: UNHANDLED\n");
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
    }
    else {
        DEBUG("[FSM:fsm_handle]: Event handled\n");
    }
}

void traverse_state(const state_t *target_state) {
    while (target_state->Level > currentState->Level) {
        if (currentState->Exit) currentState->Exit();
        currentState = currentState->Parent;
        if (currentState->Entry) currentState->Entry();
    } 
    while (target_state->Level < currentState->Level) {
        currentState = currentState->Child;
        if (currentState->Entry) currentState->Entry();
    }
    if (currentState->Level == target_state->Level) {
        if (currentState->Exit) currentState->Exit();
    }
    currentState = target_state;
    if (currentState->Entry) currentState->Entry();
}

handler_result_t default_handler(EVENT_T event) {
    DEBUG("[FSM:default_handler]: called\n");
    lwm2m_handleEvent(event);
    ioHandler_handleEvent(event);
    displayHandler_handleEvent(event);
    return HANDLED;
}

handler_result_t firstStart_handler(EVENT_T event) {
    switch (event) {
        case BUTTON_OK_PRESSED: //TODO: change in LONG_PRESSED
            DEBUG("[FSM:firstStart_handler]: OK_BUTTON_PRESSED\n");
            displayHandler_handleEvent(event);
            traverse_state(&Top_Level[1]); //transition to on
            return HANDLED;
            break;
        default:
            DEBUG("[FSM:firstStart_handler]: UNHANDLED\n");
            return HANDLED; //TODO: change in UNHANDLED
            break;
    }
}

void firstStart_entry_handler(void) {
    DEBUG("[FSM:firstStart_entry_handler]: called\n");
    lwm2m_handler_init();
    lwm2m_handler_start();
    io_init();
    display_init();
    startDisplayThread();
}

handler_result_t on_handler(EVENT_T event) {
    switch (event) {
        case BUTTON_OK_PRESSED: //TODO: change in LONG_PRESSED
            DEBUG("[FSM:on_handler]: OK_BUTTON_PRESSED\n");
            displayHandler_handleEvent(event);
            traverse_state(&Top_Level[2]); //transition to off
            return HANDLED;
            break;
        case BUTTON_UP_PRESSED:
        case BUTTON_DOWN_PRESSED:
        case BUTTON_LEFT_PRESSED:
        case BUTTON_RIGHT_PRESSED:
            DEBUG("[FSM:on_handler]: BUTTON_PRESSED\n");
            displayHandler_handleEvent(event);
            return HANDLED;
            break;
        default:
            DEBUG("[FSM:on_handler]: UNHANDLED\n");
            return HANDLED; //TODO: change in UNHANDLED 
            break;
    }
}

void on_entry_handler(void) {
    DEBUG("[FSM:on_entry_handler]: called\n");
    ioHandler_handleEvent(VIBRATE);
}

handler_result_t off_handler(EVENT_T event) {
    switch (event) {
        case BUTTON_OK_PRESSED: //TODO: change in LONG_PRESSED
            DEBUG("[FSM:off_handler]: OK_BUTTON_PRESSED\n");
            displayHandler_handleEvent(event);
            traverse_state(&Top_Level[1]); //transition to on
            return HANDLED;
            break;
        default:
            DEBUG("[FSM:off_handler]: UNHANDLED\n");
            return HANDLED; //TODO: change in UNHANDLED
            break;
    }
}

handler_result_t unregistered_state_handler(EVENT_T event) {
    DEBUG("[FSM:unregistered_state_handler]: called\n");
    switch (event) {
        case BUTTON_OK_PRESSED: //TODO: change in LINKED
            DEBUG("[FSM:unregistered_state_handler]: OK_BUTTON_PRESSED\n");
            displayHandler_handleEvent(event);
            traverse_state(&On_Level[1]);
            return HANDLED;
            break;
        case BUTTON_UP_PRESSED:
        case BUTTON_DOWN_PRESSED:
        case BUTTON_LEFT_PRESSED:
        case BUTTON_RIGHT_PRESSED:
            DEBUG("[FSM:unregistered_state_handler]: BUTTON_PRESSED\n");
            displayHandler_handleEvent(event);
            return HANDLED;
            break;

        default:
            DEBUG("[FSM:unregistered_state_handler]: UNHANDLED\n");
            return HANDLED; //TODO: change in UNHANDLED
            break;
    }
}

handler_result_t userLinked_handler(EVENT_T event) {
    DEBUG("[FSM:userLinked_handler]: called\n");
    switch (event) {
        case BUTTON_OK_PRESSED: //TODO: change in AUTHORIZED
            DEBUG("[FSM:userLinked_handler]: OK_BUTTON_PRESSED\n");
            displayHandler_handleEvent(event);
            traverse_state(&On_Level[2]);
            return HANDLED;
            break;
        default:
            DEBUG("[FSM:userLinked_handler]: UNHANDLED\n");
            return HANDLED; //TODO: change in UNHANDLED
            break;
    }
}

handler_result_t pet_handler(EVENT_T event) {
    DEBUG("[FSM:pet_handler]: called\n");
    switch (event) {
        case BUTTON_OK_PRESSED: //TODO: change in LONG_PRESSED
            DEBUG("[FSM:pet_handler]: OK_BUTTON_PRESSED\n");
            displayHandler_handleEvent(event);
            traverse_state(&On_Level[0]);
            return HANDLED;
            break;
        default:
            DEBUG("[FSM:pet_handler]: UNHANDLED\n");
            return HANDLED; //TODO: change in UNHANDLED
            break;
    }
}

handler_result_t mainView_handler(EVENT_T event) {
    DEBUG("[FSM:mainView_handler]: called\n");
    switch (event) {
        case BUTTON_OK_PRESSED: //TODO: change in LONG_PRESSED
            DEBUG("[FSM:mainView_handler]: OK_BUTTON_PRESSED\n");
            displayHandler_handleEvent(event);
            traverse_state(&On_Level[0]);
            return HANDLED;
            break;
        default:
            DEBUG("[FSM:mainView_handler]: UNHANDLED\n");
            return HANDLED; //TODO: change in UNHANDLED
            break;
    }
}

handler_result_t gameView_handler(EVENT_T event) {
    DEBUG("[FSM:gameView_handler]: called\n");
    switch (event) {
        case BUTTON_OK_PRESSED: //TODO: change in LONG_PRESSED
            DEBUG("[FSM:gameView_handler]: OK_BUTTON_PRESSED\n");
            displayHandler_handleEvent(event);
            traverse_state(&On_Level[0]);
            return HANDLED;
            break;
        default:
            DEBUG("[FSM:gameView_handler]: UNHANDLED\n");
            return HANDLED; //TODO: change in UNHANDLED
            break;
    }
}

handler_result_t StatView_handler(EVENT_T event) {
    DEBUG("[FSM:StatView_handler]: called\n");
    switch (event) {
        case BUTTON_OK_PRESSED: //TODO: change in LONG_PRESSED
            DEBUG("[FSM:StatView_handler]: OK_BUTTON_PRESSED\n");
            displayHandler_handleEvent(event);
            traverse_state(&On_Level[0]);
            return HANDLED;
            break;
        default:
            DEBUG("[FSM:StatView_handler]: UNHANDLED\n");
            return HANDLED; //TODO: change in UNHANDLED
            break;
    }
}
//EOF
