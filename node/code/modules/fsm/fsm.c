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
handler_result_t off_handler(EVENT_T event);
void off_entry(void);
handler_result_t unregistered_handler(EVENT_T event);
void unregistered_entry(void);
handler_result_t userLinked_handler(EVENT_T event);
void userLinked_entry(void);
handler_result_t pet_handler(EVENT_T event);
void pet_entry(void);
handler_result_t mainView_handler(EVENT_T event);
void mainView_entry(void);
handler_result_t gameView_handler(EVENT_T event);
void gameView_entry(void);

//Our definition of an hierachical state
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
static const state_t Pet_Level[2];

static const state_t Top_Level[] = {
    {//on
        on_handler,     //state handler
        on_entry,       //Entry action handler
        NULL,        //Exit action handler
        NULL,       //Parent state
        &On_Level[0],       //Child state
        1       //Hierarchical state level
    },
    {//off
        off_handler,              //state handler
        off_entry,                //Entry action handler
        NULL,                 //Exit action handler
        NULL,                     //Parent state
        NULL,                     //Child state
        1                         //Hierarchical state level
    }
};

static const state_t On_Level[] = {
    {//unregistered
        unregistered_handler,              // state handler
        unregistered_entry,                     // Entry action handler
        NULL,                     // Exit action handler
        &Top_Level[0],            // Parent state
        NULL,                     // Child state
        2                         // Hierarchical state level
    },
    {//user_linked
        userLinked_handler,              // state handler
        userLinked_entry,                     // Entry action handler
        NULL,                     // Exit action handler
        &Top_Level[0],            // Parent state
        NULL,                     // Child state
        2                         // Hierarchical state level
    },
    {//pet
        pet_handler,              // state handler
        pet_entry,                     // Entry action handler
        NULL,                     // Exit action handler
        &Top_Level[0],            // Parent state
        &Pet_Level[0],            // Child state
        2                         // Hierarchical state level
    }
};

static const state_t Pet_Level[] = {
    {//Main_View
        mainView_handler,              // state handler
        mainView_entry,                     // Entry action handler
        NULL,                     // Exit action handler
        &On_Level[2],             // Parent state
        NULL,                     // Child state
        3                         // Hierarchical state level
    },
    {//Game_View TODO: not used yet
        gameView_handler,              // state handler
        gameView_entry,                     // Entry action handler
        NULL,                     // Exit action handler
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
        const state_t *pState = currentState;
        do {
            //check if state has parent state.
            if (pState->Parent == NULL) { //Is Node reached top
                //This is a fatal error. terminate state machine.
                return;
            }
            pState = pState->Parent;  // traverse to parent state
            result = pState->Handler(event);
        }
        while (pState->Handler ==
                NULL || result == UNHANDLED);  // repeat again if parent state doesn't have handler or event is not handled
    }

}

void traverse_state(const state_t *target_state) {
    while (target_state->Level < currentState->Level) {
        if (currentState->Exit) currentState->Exit();
        currentState = currentState->Parent;
        //if (currentState->Entry) currentState->Entry(); //dont call entry action if you come from a child state
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
            return HANDLED; //TODO: Error detection
    }
}

void on_entry(void) {
    ioHandler_handleEvent(VIBRATE);
    ioHandler_handleEvent(SCREEN_ON);
    if (registered && userLinked) {
        traverse_state(&On_Level[2]); //transition to pet
    }
    else if (registered) {
        traverse_state(&On_Level[1]); //transition to user_linked
    }
    else {
        traverse_state(&On_Level[0]); //transition to unregistered
    }
}

handler_result_t off_handler(EVENT_T event) {
    switch (event) {
        case BUTTON_OK_LONG:
            DEBUG("[FSM:off_handler]: BUTTON_OK_LONG\n");
            traverse_state(&Top_Level[0]); //transition to on
            return HANDLED;
        default:
            DEBUG("[FSM:off_handler]: UNHANDLED\n");
            return HANDLED; //TODO: Error detection
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
    ioHandler_handleEvent(SCREEN_OFF);
    ioHandler_handleEvent(VIBRATE);
}

handler_result_t unregistered_handler(EVENT_T event) {
    switch (event) {
        case REGISTER_CODE:
            DEBUG("[FSM:unregistered_state_handler]: REGISTER_CODE\n");
            displayHandler_handleEvent(REGISTERED); //is that right?
            displayHandler_handleEvent(REGISTER_CODE);
            return HANDLED;
        case REGISTERED:
            DEBUG("[FSM:unregistered_state_handler]: REGISTERED\n");
            traverse_state(&On_Level[1]); //transition to user_linked
            return HANDLED;
        default:
            DEBUG("[FSM:unregistered_state_handler]: UNHANDLED\n");
            return UNHANDLED;
    }
}

void unregistered_entry(void) {
    displayHandler_handleEvent(REGISTER_CODE);
}

handler_result_t userLinked_handler(EVENT_T event) {
    switch (event) {
        case READY:
            DEBUG("[FSM:userLinked_handler]: READY\n");
            displayHandler_handleEvent(READY);
            traverse_state(&On_Level[2]); //transition to pet
            return HANDLED;
        default:
            DEBUG("[FSM:userLinked_handler]: UNHANDLED\n");
            return UNHANDLED;
    }
}

void userLinked_entry(void) {
    registered = true;
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
    traverse_state(&Pet_Level[0]); //transition to main_view
}

handler_result_t mainView_handler(EVENT_T event) {
    switch (event) {
        case BUTTON_OK_LONG:
            DEBUG("[FSM:mainView_handler]: BUTTON_OK_LONG\n");
            return UNHANDLED;
        case BUTTON_OK_PRESSED:
            DEBUG("[FSM:mainView_handler]: BUTTON_OK_PRESSED\n");
            displayHandler_handleEvent(event);
            return HANDLED;
        case BUTTON_OK_RELEASED:
            DEBUG("[FSM:mainView_handler]: BUTTON_OK_RELEASED\n");
            displayHandler_handleEvent(event);
            return HANDLED;
        case BUTTON_UP_PRESSED:
            DEBUG("[FSM:mainView_handler]: BUTTON_UP_PRESSED\n");
            displayHandler_handleEvent(event);
            return HANDLED;
        case BUTTON_UP_RELEASED:
            DEBUG("[FSM:mainView_handler]: BUTTON_UP_RELEASED\n");
            displayHandler_handleEvent(event);
            return HANDLED;
        case BUTTON_DOWN_PRESSED:
            DEBUG("[FSM:mainView_handler]: BUTTON_DOWN_PRESSED\n");
            displayHandler_handleEvent(event);
            return HANDLED;
        case BUTTON_DOWN_RELEASED:
            DEBUG("[FSM:mainView_handler]: BUTTON_DOWN_RELEASED\n");
            displayHandler_handleEvent(event);
            return HANDLED;
        case BUTTON_LEFT_PRESSED:
            DEBUG("[FSM:mainView_handler]: BUTTON_LEFT_PRESSED\n");
            displayHandler_handleEvent(event);
            return HANDLED;
        case BUTTON_LEFT_RELEASED:
            DEBUG("[FSM:mainView_handler]: BUTTON_LEFT_RELEASED\n");
            displayHandler_handleEvent(event);
            return HANDLED;
        case BUTTON_RIGHT_PRESSED:
            DEBUG("[FSM:mainView_handler]: BUTTON_RIGHT_PRESSED\n");
            displayHandler_handleEvent(event);
            return HANDLED;
        case BUTTON_RIGHT_RELEASED:
            DEBUG("[FSM:mainView_handler]: BUTTON_RIGHT_RELEASED\n");
            displayHandler_handleEvent(event);
            return HANDLED;
        case PET_FEED:
            DEBUG("[FSM:mainView_handler]: PET_FEED\n");
            lwm2m_handleEvent(PET_FEED);
            return HANDLED;
        case PET_PLAY:
            DEBUG("[FSM:mainView_handler]: PET_PLAY\n");
            traverse_state(&Pet_Level[1]); //transition to game_view
            return HANDLED;
        case PET_MEDICATE:
            DEBUG("[FSM:mainView_handler]: PET_MEDICATE\n");
            lwm2m_handleEvent(PET_MEDICATE);
            return HANDLED;
        case PET_CLEAN:
            DEBUG("[FSM:mainView_handler]: PET_CLEAN\n");
            lwm2m_handleEvent(PET_CLEAN);
            return HANDLED;
        case INFO_PRESSED:
        DEBUG("[FSM:mainView_handler]: INFO_PRESSED\n");
            displayHandler_handleEvent(INFO_PRESSED);
            return HANDLED;
        default:
            DEBUG("[FSM:mainView_handler]: UNHANDLED\n");
            return UNHANDLED;
    }
}

void mainView_entry(void) {
    displayHandler_handleEvent(READY); //to draw the pet
}

static bool raus = false;

handler_result_t gameView_handler(EVENT_T event) {
    switch (event) {
        case BUTTON_OK_PRESSED:
            if (raus) {
                traverse_state(&Pet_Level[0]); //transition to main_view
            }
            else {
                raus = true;
            }
            return HANDLED;
        case BUTTON_OK_RELEASED:
        case BUTTON_UP_PRESSED:
        case BUTTON_UP_RELEASED:
        case BUTTON_DOWN_PRESSED:
        case BUTTON_DOWN_RELEASED:
        case BUTTON_LEFT_PRESSED:
        case BUTTON_LEFT_RELEASED:
        case BUTTON_RIGHT_PRESSED:
        case BUTTON_RIGHT_RELEASED:
            displayHandler_handleEvent(event);
            return HANDLED;
        case GAME_FINISHED:
            DEBUG("[FSM:gameView_handler]: GAME_FINISHED\n");
            traverse_state(&Pet_Level[0]); //transition to main_view
            lwm2m_handleEvent(PET_PLAY);     
            return HANDLED;
        default:
            DEBUG("[FSM:gameView_handler]: UNHANDLED\n");
            return UNHANDLED;
    }
}

void gameView_entry(void) {
    raus = false;
    displayHandler_handleEvent(GAME_START);
}
//EOF
