package scanner.fsm.states;

import io.ReturnCharacter;
import scanner.fsm.StateMachine;
import scanner.fsm.StateManager;
import scanner.tokenizer.SignificantCharacters;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    13/08/15
 * File Name:       PrepForMlc
 * Project Name:    CD15
 * Description:
 */
public class PrepForMlcState extends State {

    public PrepForMlcState(StateMachine executionContext) {
        super(executionContext);
    }

    @Override
    public void execute() {

        // We've seen a new line, so increment and check for a +
        this.getExecutionContext().readNextCharacter();
        ReturnCharacter charObj = this.getExecutionContext().getCharacterForConsideration();
        char charCh = charObj.getCharacter();

        // If we see a plus, set to beginMLC
        if ( charCh == SignificantCharacters.PLUS_OP.asChar() ) {

            // Leave + in buffer to be added to lexeme
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.BEGIN_MLC_STATE));

        } else {

            // Else leave in buffer and refer to start state
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.START_STATE));

        }


    }

    @Override
    public StateManager.StateClass getStateClass() {
        return null;
    }
}
