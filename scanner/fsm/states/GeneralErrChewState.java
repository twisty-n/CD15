package scanner.fsm.states;

import io.ReturnCharacter;
import scanner.fsm.StateMachine;
import scanner.fsm.StateManager;
import scanner.tokenizer.SignificantCharacters;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    8/12/2015
 * File Name:       GeneralErrChewState
 * Project Name:    CD15 Compiler
 * Description:     This will consume a line up to the next whitespace or any
 *                  delimiter or operator
 */
public class GeneralErrChewState extends State {

    public GeneralErrChewState(StateMachine executionContext) {
        super(executionContext);
    }

    /*
This function servers generally well cooked steaks, so that you have to eat and come back
 */
    private void eatAndComeback() {
        this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.ERR_CHEW_STATE));
    }


    @Override
    public void execute() {
        this.getExecutionContext().readNextCharacter();
        ReturnCharacter charObj = this.getExecutionContext().getCharacterForConsideration();
        char charCh = charObj.getCharacter();

        if ( Character.isWhitespace( charCh ) || SignificantCharacters.isOperatorOrDelimiter( charCh ) ) {

            // This will terminate the error
            this.getExecutionContext().exposeLexeme().setIsComplete(true, charObj.getIndexOnLine() - 1, false);
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.START_STATE));
            return;

        } else {
            this.eatAndComeback();
        }

        /*
        if ( Character.isAlphabetic( charCh ) ) {

            // Its a-z / A-Z, so we keep chewing
            this.eatAndComeback();
            return;

        } else if ( Character.isDigit( charCh ) ) {

            // Same as above, keep chewing
            this.eatAndComeback();
            return;

        } else if ( !SignificantCharacters.isOperatorOrDelimiter(charCh) ) {

            // This is some illegal character in the context of the program
            this.eatAndComeback();

        } else {

            // If need be, we can add in extra character handling rules here
            // We've found something else set to start state and set this lexeme as done
            // We could have found whitespace, or an operator
            this.getExecutionContext().exposeLexeme().setIsComplete(true, charObj.getIndexOnLine() - 1, false);
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.START_STATE));
            return;

        }
        */
    }

    @Override
    public StateManager.StateClass getStateClass() {
        return StateManager.StateClass.GEN_ERR_CHEW_STATE;
    }
}
