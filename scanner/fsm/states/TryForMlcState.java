package scanner.fsm.states;

import context.error.CompilationError;
import io.ReturnCharacter;
import scanner.fsm.StateMachine;
import scanner.fsm.StateManager;
import scanner.tokenizer.SignificantCharacter;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    14/08/15
 * File Name:       TryForMlc
 * Project Name:    CD15
 * Description:
 */
public class TryForMlcState extends State{

    public TryForMlcState(StateMachine executionContext) {
        super(executionContext);
    }

    @Override
    public void execute() {

        // Read in next character
        this.getExecutionContext().readNextCharacter();
        ReturnCharacter charObj = this.getExecutionContext().getCharacterForConsideration();
        char charCh = charObj.getCharacter();

        // Specified behaviour: only valid transition is through seeing another plus
        // if we detect anything else, terminate lexeme, report as invalid and report
        // error
        if ( charCh != SignificantCharacter.PLUS_OP.asChar() ) {

            // WRONG WRONG WRONG WRONG, WRONG WRONG WRONG WRRRRRROOOONGGGG
            this.getExecutionContext().exposeLexeme().setIsComplete(
                    true,
                    charObj.getIndexOnLine() - 1,
                    false
            );
            CompilationError.record(this.getExecutionContext().exposeLexeme(), CompilationError.Type.MALFORMED_MLC_OPENER);
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.START_STATE));

        } else {

            // We have complete a multiline comment opener, shake hands with your
            // cohort, because our work has just begun
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.LOCK_MLC_STATE));

        }

    }

    @Override
    public StateManager.StateClass getStateClass() {
        return StateManager.StateClass.TRY_FOR_MLC_STATE;
    }
}
