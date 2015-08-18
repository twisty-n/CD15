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
 * File Name:       IllegalCharacterState
 * Project Name:    CD15
 * Description:
 */
public class IllegalCharacterState extends State {

    public IllegalCharacterState(StateMachine executionContext) {
        super(executionContext);
    }

    @Override
    public void execute() {

        // While we dont see a character that constitutes a valid
        // CD15 character, loop and consume

        // Read
        this.getExecutionContext().readNextCharacter();
        ReturnCharacter charObj = this.getExecutionContext().getCharacterForConsideration();
        char charCh = charObj.getCharacter();

        if ( Character.isWhitespace( charCh )
                || Character.isAlphabetic( charCh )
                || Character.isDigit( charCh )
                || SignificantCharacter.isSignificantCharacter( charCh )
                )
        {

            // We have found something valid.
            // Terminate the lexeme here and mark is invalid
            this.getExecutionContext().exposeLexeme().setIsComplete(
                    true,
                    charObj.getIndexOnLine() - 1,
                    false
            );

            // Leave the charBuffer alone for the start state to handle
            CompilationError.record(this.getExecutionContext().exposeLexeme(), CompilationError.Type.UNKNOWN_CHARACTER);
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.START_STATE));
        } else {

            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.ILLEGAL_CHARACTER_STATE));

        }

    }

    @Override
    public StateManager.StateClass getStateClass() {
        return StateManager.StateClass.ILLEGAL_CHARACTER_STATE;
    }
}
