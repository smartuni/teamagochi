#include "fsm.h"

#include <stdio.h>

#include "events.h"
#include "lwm2m_handler.h"
#include "display_handler.h"
#include "io_handler.h"

#define ENABLE_DEBUG  1
#include "debug.h"

char fsm_thread_stack[THREAD_STACKSIZE_DEFAULT + THREAD_EXTRA_STACKSIZE_PRINTF];


void *fsm_thread(void * arg);
void fsm_handle(EVENT_T event);

handler_result_t on_handler(EVENT_T event);
void on_entry(void);
void on_exit(void);
handler_result_t off_handler(EVENT_T event);
void off_entry(void);
void off_exit(void);
handler_result_t unregistered_handler(EVENT_T event);
void unregistered_entry(void);
void unregistered_exit(void);
handler_result_t userLinked_handler(EVENT_T event);
void userLinked_entry(void);
void userLinked_exit(void);
handler_result_t pet_handler(EVENT_T event);
void pet_entry(void);
void pet_exit(void);
handler_result_t mainView_handler(EVENT_T event);
void mainView_entry(void);
void mainView_exit(void);
handler_result_t gameView_handler(EVENT_T event);
void gameView_entry(void);
void gameView_exit(void);
handler_result_t StatView_handler(EVENT_T event);
void StatView_entry(void);
void StatView_exit(void);


typedef struct hierarchical_state state_t;
struct hierarchical_state {
  fsm_handler Handler;  //!< State handler function
  void (*Entry)(void);  //!< Entry action for state
  void (*Exit)(void);   //!< Exit action for state.

  const state_t *const Parent;  //!< Parent state of the current state.
  const state_t *const Child;    //!< Child states of the current state.
  uint32_t Level;               //!< Hierarchy level from the top state.
};

static bool firstStart = true;
static bool registered = false;
static bool userLinked = false;

static const state_t Top_Level[2];
static const state_t On_Level[3];
static const state_t Pet_Level[3];

static const state_t Top_Level[] = {
    {//on
        on_handler,                   // state handler
        on_entry,             // Entry action handler
        on_exit,                               // Exit action handler
        NULL,                               // Parent state
        &On_Level[0],                       // Child state
        1                                   // Hierarchical state level
    },
    {//off
        off_handler,              // state handler
        off_entry,                               // Entry action handler
        off_exit,                               // Exit action handler
        NULL,                               // Parent state
        NULL,                               // Child state
        1                                   // Hierarchical state level
    }
};

static const state_t On_Level[] = {
    {//unregistered
        unregistered_handler,              // state handler
        unregistered_entry,                     // Entry action handler
        unregistered_exit,                     // Exit action handler
        &Top_Level[0],            // Parent state
        NULL,                     // Child state
        2                         // Hierarchical state level
    },
    {//user_linked
        userLinked_handler,              // state handler
        userLinked_entry,                     // Entry action handler
        userLinked_exit,                     // Exit action handler
        &Top_Level[0],            // Parent state
        NULL,                     // Child state
        2                         // Hierarchical state level
    },
    {//pet
        pet_handler,              // state handler
        pet_entry,                     // Entry action handler
        pet_exit,                     // Exit action handler
        &Top_Level[0],            // Parent state
        &Pet_Level[0],            // Child state
        2                         // Hierarchical state level
    }
};

static const state_t Pet_Level[] = {
    {//Main_View
        mainView_handler,              // state handler
        mainView_entry,                     // Entry action handler
        mainView_exit,                     // Exit action handler
        &On_Level[2],             // Parent state
        NULL,                     // Child state
        3                         // Hierarchical state level
    },
    {//Game_View
        gameView_handler,              // state handler
        gameView_entry,                     // Entry action handler
        gameView_exit,                     // Exit action handler
        &On_Level[2],             // Parent state
        NULL,                     // Child state
        3                         // Hierarchical state level
    },
    {//Stat_View
        StatView_handler,              // state handler
        StatView_entry,                     // Entry action handler
        StatView_exit,                     // Exit action handler
        &On_Level[2],             // Parent state
        NULL,                     // Child state
        3                         // Hierarchical state level
    }
};

static const state_t *currentState = &Top_Level[1];

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
    if (!currentState->Handler) puts("no handler \n");
    handler_result_t result = currentState->Handler(event);
    if (result != HANDLED) {
        DEBUG("[FSM:fsm_handle]: UNHANDLED\n");
        const state_t *pState = currentState;
        do {
            //check if state has parent state.
            if (pState->Parent == NULL) { //Is Node reached top
                DEBUG("[FSM:fsm_handle]: Fatal error, terminating.\n");
                //This is a fatal error. terminate state machine.
                return;
            }
            DEBUG("[FSM:fsm_handle]: Traverse to parent state\n");
            pState = pState->Parent;  // traverse to parent state
            result = pState->Handler(event);
            DEBUG("[FSM:fsm_handle]: result: %d\n", result);
        }
        while (pState->Handler ==
                NULL || result == UNHANDLED);  // repeat again if parent state doesn't have handler
        DEBUG("[FSM:fsm_handle]: State machine traversed to parent state\n");
    }

}

void traverse_state(const state_t *target_state) {
    while (target_state->Level < currentState->Level) {
        if (currentState->Exit) currentState->Exit();
        currentState = currentState->Parent;
        //if (currentState->Entry) currentState->Entry();
    } 
    while (target_state->Level > currentState->Level) {
        currentState = currentState->Child;
        if (currentState->Entry) currentState->Entry();
    }
    if (currentState->Level == target_state->Level) {
        if (currentState->Exit) currentState->Exit();
    }
    currentState = target_state;
    if (currentState->Entry) currentState->Entry();
}

handler_result_t on_handler(EVENT_T event) {
    switch (event) {
        case BUTTON_OK_LONG:
            DEBUG("[FSM:on_handler]: BUTTON_OK_LONG\n");
            traverse_state(&Top_Level[1]); //transition to off
            DEBUG("[FSM:on_handler]: transition to off\n");
            return HANDLED;
        default:
            DEBUG("[FSM:on_handler]: UNHANDLED\n");
            return HANDLED; //TODO Error detection
    }
}

void on_entry(void) {
    DEBUG("[FSM:on_entry_handler]: called\n");
    ioHandler_handleEvent(VIBRATE);
    traverse_state(&On_Level[0]); //wie gehe ich in den Child State
}

void on_exit(void) {

}

handler_result_t off_handler(EVENT_T event) {
    switch (event) {
        case BUTTON_OK_LONG:
            DEBUG("[FSM:off_handler]: BUTTON_OK_LONG\n");
            traverse_state(&Top_Level[0]); //transition to on
            return HANDLED;
            break;
        default:
            DEBUG("[FSM:off_handler]: UNHANDLED\n");
            return HANDLED; //TODO Error detection
            break;
    }
}

void off_entry(void) {
    if (firstStart) {
        firstStart = false;
        lwm2m_handler_init();
        lwm2m_handler_start();
        io_init();
        display_init();
        startDisplayThread();
    }
}

void off_exit(void) {

}

handler_result_t unregistered_handler(EVENT_T event) {
    switch (event) {
        case REGISTERED:
            displayHandler_handleEvent(REGISTERED);
            traverse_state(&On_Level[1]);
            return HANDLED;
        default:
            DEBUG("[FSM:unregistered_state_handler]: UNHANDLED\n");
            return UNHANDLED;
            break;
    }
}

void unregistered_entry(void) {
    DEBUG("[FSM:unregistered_entry]: called\n");
    displayHandler_handleEvent(REGISTER_CODE);
}

void unregistered_exit(void) {

}

handler_result_t userLinked_handler(EVENT_T event) {
    switch (event) {
        case READY:
            displayHandler_handleEvent(READY);
            traverse_state(&On_Level[2]);
            return HANDLED;
        default:
            DEBUG("[FSM:userLinked_handler]: UNHANDLED\n");
            return UNHANDLED;
    }
}

void userLinked_entry(void) {
    registered = true;
}

void userLinked_exit(void) {

}

handler_result_t pet_handler(EVENT_T event) {
    switch (event) {
        default:
            DEBUG("[FSM:pet_handler]: UNHANDLED\n");
            return UNHANDLED;
    }
}

void pet_entry(void) {
    userLinked = true;
    traverse_state(&Pet_Level[0]);
}

void pet_exit(void) {

}

handler_result_t mainView_handler(EVENT_T event) {
    switch (event) {
        case BUTTON_OK_LONG:
            DEBUG("[FSM:mainView_handler]: langer Button nach oben\n");
            return UNHANDLED;
        case BUTTON_UP_PRESSED:
        case BUTTON_UP_RELEASED:
        case BUTTON_DOWN_PRESSED:
        case BUTTON_DOWN_RELEASED:
        case BUTTON_LEFT_PRESSED:
        case BUTTON_LEFT_RELEASED:
        case BUTTON_RIGHT_PRESSED:
        case BUTTON_RIGHT_RELEASED:
            DEBUG("[FSM:mainView_handler]: BUTTON_PRESSED or BUTTON_RELEASED\n");
            displayHandler_handleEvent(event);
            return HANDLED;
        case PET_FEED:
            DEBUG("[FSM:mainView_handler]: PET_FEED\n");
            lwm2m_handleEvent(PET_FEED);
            return HANDLED;
        case PET_PLAY:
            DEBUG("[FSM:mainView_handler]: PET_PLAY\n");
            lwm2m_handleEvent(PET_PLAY);
            return HANDLED;
        case PET_MEDICATE:
            DEBUG("[FSM:mainView_handler]: PET_MEDICATE\n");
            lwm2m_handleEvent(PET_MEDICATE);
            return HANDLED;
        case PET_CLEAN:
            DEBUG("[FSM:mainView_handler]: PET_CLEAN\n");
            lwm2m_handleEvent(PET_CLEAN);
            return HANDLED;
        default:
            DEBUG("[FSM:mainView_handler]: UNHANDLED\n");
            return UNHANDLED;
    }
}

void mainView_entry(void) {
    //displayHandler_handleEvent(MAIN_VIEW);
}

void mainView_exit(void) {

}

handler_result_t gameView_handler(EVENT_T event) {
    switch (event) {
        default:
            DEBUG("[FSM:gameView_handler]: UNHANDLED\n");
            return UNHANDLED;
            break;
    }
}

void gameView_entry(void) {
    //displayHandler_handleEvent(GAME_VIEW);
}

void gameView_exit(void) {

}

handler_result_t StatView_handler(EVENT_T event) {
    switch (event) {
        default:
            DEBUG("[FSM:StatView_handler]: UNHANDLED\n");
            return UNHANDLED;
            break;
    }
}

void StatView_entry(void) {
    //displayHandler_handleEvent(STAT_VIEW);
}

void StatView_exit(void) {

}
//EOF
