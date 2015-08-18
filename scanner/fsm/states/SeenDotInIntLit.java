package scanner.fsm.states;

import context.error.CompilationError;
import io.ReturnCharacter;
import scanner.fsm.StateMachine;
import scanner.fsm.StateManager;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    17/08/15
 * File Name:       SeenDotInIntLit
 * Project Name:    CD15
 * Description:
 */
public class SeenDotInIntLit extends State {

    public SeenDotInIntLit(StateMachine executionContext) {
        super(executionContext);
    }

    @Override
    public void execute() {

        // once we have seen a dot in an integer literal, all we care about
        // is seeing another digist
        this.getExecutionContext().readNextCharacter();
        ReturnCharacter charObj = this.getExecutionContext().getCharacterForConsideration();
        char charCh = charObj.getCharacter();

        if ( ! Character.isDigit( charCh) ) {

            // This is instantly an error
            this.getExecutionContext().exposeLexeme().setIsComplete(
                    true,
                    charObj.getIndexOnLine(),
                    false
            );
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.START_STATE));
            CompilationError.record(this.getExecutionContext().exposeLexeme(), CompilationError.Type.MALFORMED_FLOAT_LITERAL);
        } else {

            // Otherwise we can proceed to the standard FlitState
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.FLT_LIT_STATE));

        }

    }

    @Override
    public StateManager.StateClass getStateClass() {
        return StateManager.StateClass.SEEN_DOT_IN_INT_LIT;
    }
}
