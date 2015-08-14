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
 * File Name:       PrepForMlcCloseState
 * Project Name:    CD15
 * Description:
 */
public class PrepForMlcCloseState extends State {

    public PrepForMlcCloseState(StateMachine executionContext) {
        super(executionContext);
    }


    @Override
    public void execute() {

        // We have seen a '\n' at this point
        // Our options are a continuation of the comment
        // or a plus, which might signal the end of a delimiter

        this.getExecutionContext().readNextCharacter();
        ReturnCharacter charObj = this.getExecutionContext().getCharacterForConsideration();
        char charCh = charObj.getCharacter();

        if ( charCh == SignificantCharacters.PLUS_OP.asChar() ) {

            // We have enough to try for a MLC Closer
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.TRY_FOR_MLC_CLOSE_STATE));


        } else {

            // It must just be a part of the comment
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.LOCK_MLC_STATE));

        }

    }

    @Override
    public StateManager.StateClass getStateClass() {
        return StateManager.StateClass.PREP_FOR_MLC_CLOSE_STATE;
    }
}
