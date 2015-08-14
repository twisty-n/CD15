package scanner.fsm.states;

import io.ReturnCharacter;
import scanner.fsm.StateMachine;
import scanner.fsm.StateManager;
import scanner.tokenizer.SignificantCharacters;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    14/08/15
 * File Name:       TryForMlcCloseState
 * Project Name:    CD15
 * Description:
 */
public class TryForMlcCloseState extends State {

    public TryForMlcCloseState(StateMachine executionContext) {
        super(executionContext);
    }

    @Override
    public void execute() {

        // We've seen an '+' now try for a '/'
        this.getExecutionContext().readNextCharacter();
        ReturnCharacter charObj = this.getExecutionContext().getCharacterForConsideration();
        char charCh = charObj.getCharacter();

        if ( charCh == SignificantCharacters.SLASH_OP.asChar() ) {

            // We have the second part of our closer
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.CLOSE_MLC_STATE));

        } else {

            // Must be part of the comment, go back to chewing
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.LOCK_MLC_STATE));

        }

    }

    @Override
    public StateManager.StateClass getStateClass() {
        return StateManager.StateClass.TRY_FOR_MLC_CLOSE_STATE;
    }
}
