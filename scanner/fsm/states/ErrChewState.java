package scanner.fsm.states;

import io.ReturnCharacter;
import scanner.fsm.StateMachine;
import scanner.fsm.StateManager;
import scanner.tokenizer.SignificantCharacter;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    8/11/2015
 * File Name:       ErrChewState
 * Project Name:    CD15 Compiler
 * Description:    Specialized error chewing state. Designed to chew errors from int and float literals
 *                 Semantically, this will eat errors including any dot operators, but will stop at the normal
 *                 whitespace and other delimiters
 */
public class ErrChewState extends State  {

    public ErrChewState(StateMachine executionContext) {
        super(executionContext);
    }

    /*
    This function servers perfectly cooked steaks, so that you have to eat and come back
     */
    private void eatAndComeback() {
        this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.ERR_CHEW_STATE));
    }

    @Override
    public void execute() {


        this.getExecutionContext().readNextCharacter();
        ReturnCharacter charObj = this.getExecutionContext().getCharacterForConsideration();
        char charCh = charObj.getCharacter();

        if (
                Character.isWhitespace( charCh ) ||
                ( SignificantCharacter.isOperatorOrDelimiter(charCh)
                        && charCh != SignificantCharacter.DOT_OP.asChar() )
        ) {

            // This will terminate the error
            this.getExecutionContext().exposeLexeme().setIsComplete(true, charObj.getIndexOnLine() - 1, false);
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.START_STATE));
            return;

        } else {
            this.eatAndComeback();
        }

        /*
        ## Ser Derpsalots Adventures in having a bunch of stupid logic
        ## Behold the Holy Hand Grenade of Antioch!
        if ( Character.isAlphabetic( charCh ) ) {

            // Its a-z / A-Z, so we keep chewing
            this.eatAndComeback();
            return;

        } else if ( Character.isDigit( charCh ) ) {

            // Same as above, keep chewing
            this.eatAndComeback();
            return;

        } else if ( charCh == SignificantCharacters.DOT_OP.asChar() ) {

            // This is also invalid, we consume this as part of the error
            this.eatAndComeback();
            return;

        } else if ( ! SignificantCharacters.isOperatorOrDelimiter( charCh ) ) {

            // An illegal character!
            this.eatAndComeback();
            return;

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
        return StateManager.StateClass.ERR_CHEW_STATE;
    }
}
