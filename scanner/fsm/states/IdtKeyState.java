package scanner.fsm.states;

import scanner.fsm.StateMachine;
import scanner.fsm.StateManager;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    8/8/2015
 * File Name:       IDT_KEY_STATE
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class IdtKeyState extends State{

    /**
     * Constructor setting up our execution context
     *
     * @param executionContext
     */
    public IdtKeyState(StateMachine executionContext) {
        super(executionContext);
    }

    @Override
    public void execute() {

        char underConsideration = this.getExecutionContext().getCharacterForConsideration().getCharacter();

        // Loop while we continue to see characters or numbers
        if ( Character.isLetterOrDigit(underConsideration) ) {
            // We are able to loop so return early
            this.getExecutionContext().readNextCharacter();
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.IDT_KEY_STATE));
            return;
        }  else {
            // We've encountered something else
            this.getExecutionContext().exposeLexeme().setIsComplete(true, this.getExecutionContext().getCharacterForConsideration().getIndexOnLine() - 1);
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.START_STATE));
        }

    }

    @Override
    public StateManager.StateClass getStateClass() {
        return StateManager.StateClass.IDT_KEY_STATE;
    }
}
