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

            case START_STATE        :           return new DefaultState(executionContext);
            case IDT_KEY_STATE      :           return new IdtKeyState(executionContext);
            case INT_LIT_STATE      :           return new IntLitState(executionContext);
            case SEEN_DOT_IN_INT_LIT:           return new SeenDotInIntLit(executionContext);
            case FLT_LIT_STATE      :           return new FltLitState(executionContext);
            case ZERO_INT_LIT_STATE :           return new ZeroIntLitState(executionContext);
            case ERR_CHEW_STATE     :           return new ErrChewState(executionContext);
            case GEN_ERR_CHEW_STATE :           return new GeneralErrChewState(executionContext);
            case SEEN_MULTIOP_COMP_STATE:       return new SeenMultiOperatorComponentState(executionContext);
            case SEEN_EXCLAM_STATE  :           return new SeenExclamState(executionContext);
            case SEEN_SLASH_STATE   :           return new SeenSlashState(executionContext);
            case LINE_COMMENT_STATE :           return new LineCommentState(executionContext);
            case SEEN_STANDALONE_OP_OR_DELIM:   return new SeenStandaloneOpOrDelim(executionContext);

            case PREP_FOR_MLC_STATE  :          return new PrepForMlcState(executionContext);
            case BEGIN_MLC_STATE     :          return new BeginMlcState(executionContext);
            case TRY_FOR_MLC_STATE   :          return new TryForMlcState(executionContext);
            case LOCK_MLC_STATE      :          return new LockMlcState(executionContext);
            case PREP_FOR_MLC_CLOSE_STATE:      return new PrepForMlcCloseState(executionContext);
            case TRY_FOR_MLC_CLOSE_STATE:       return new TryForMlcCloseState(executionContext);
            case CLOSE_MLC_STATE     :          return new CloseMlcState(executionContext);

            case STRING_CONSTANT_STATE:         return new StringConstantState(executionContext);
            case ILLEGAL_CHARACTER_STATE:       return new IllegalCharacterState(executionContext);

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
        SEEN_MULTIOP_COMP_STATE,
        SEEN_EXCLAM_STATE,
        SEEN_SLASH_STATE,
        LINE_COMMENT_STATE,
        SEEN_STANDALONE_OP_OR_DELIM,
        PREP_FOR_MLC_STATE,
        BEGIN_MLC_STATE,
        TRY_FOR_MLC_STATE,
        LOCK_MLC_STATE,
        PREP_FOR_MLC_CLOSE_STATE,
        TRY_FOR_MLC_CLOSE_STATE,
        CLOSE_MLC_STATE,
        STRING_CONSTANT_STATE,
        ILLEGAL_CHARACTER_STATE,
        SEEN_DOT_IN_INT_LIT,
    }

}
