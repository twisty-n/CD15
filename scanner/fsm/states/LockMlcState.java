package scanner.fsm.states;

import io.ReturnCharacter;
import scanner.fsm.StateMachine;
import scanner.fsm.StateManager;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    14/08/15
 * File Name:       LockMlcState
 * Project Name:    CD15
 * Description:
 */
public class LockMlcState extends State {

    public LockMlcState(StateMachine executionContext) {
        super(executionContext);
    }

    @Override
    public void execute() {

        // Get character
        this.getExecutionContext().readNextCharacter();
        ReturnCharacter charObj = this.getExecutionContext().getCharacterForConsideration();
        char charCh = charObj.getCharacter();

    }

    @Override
    public StateManager.StateClass getStateClass() {
        return null;
    }
}
