package scanner.fsm;

import scanner.fsm.states.FatalErrorState;
import scanner.fsm.states.DefaultState;
import scanner.fsm.states.IdtKeyState;
import scanner.fsm.states.State;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    8/8/2015
 * File Name:       StateManager
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class StateManager {

    private static StateMachine executionContext;
    private static StateManager instance;

    static {
        instance = new StateManager();
    }

    /**
     * This method must be called before using the state manager
     * @param ctx
     */
    public static void registerContext(StateMachine ctx) {
        StateManager.executionContext = ctx;
    }

    /**
     * Returns the instance of the state manager
     * @return
     */
    public static StateManager getInstance() {
        return StateManager.instance;
    }

    public static State getState(StateClass stateClass) {

        switch (stateClass) {

            case START_STATE: return new DefaultState(executionContext);
            case IDT_KEY_STATE: return new IdtKeyState(executionContext);


            // If there is no corresponding state. This should happpen. This should never happen
            default: return new FatalErrorState(executionContext);

        }

    }

    public enum StateClass {

        START_STATE,    // The starting state of the machine
        FATAL_ERROR_STATE,
        IDT_KEY_STATE,

    }

}
