package scanner.fsm;

import scanner.fsm.states.*;

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

            case START_STATE        :       return new DefaultState(executionContext);
            case IDT_KEY_STATE      :       return new IdtKeyState(executionContext);
            case INT_LIT_STATE      :       return new IntLitState(executionContext);
            case FLT_LIT_STATE      :       return new FltLitState(executionContext);
            case ZERO_INT_LIT_STATE :       return new ZeroIntLitState(executionContext);
            case ERR_CHEW_STATE     :       return new ErrChewState(executionContext);
            case GEN_ERR_CHEW_STATE :       return new GeneralErrChewState(executionContext);

            // If there is no corresponding state. This should happpen. This should never happen
            default: return new FatalErrorState(executionContext);

        }

    }

    public enum StateClass {

        START_STATE,    // The starting state of the machine
        FATAL_ERROR_STATE,
        IDT_KEY_STATE,
        INT_LIT_STATE,
        FLT_LIT_STATE,
        ZERO_INT_LIT_STATE,
        ERR_CHEW_STATE,
        GEN_ERR_CHEW_STATE,
    }

}
