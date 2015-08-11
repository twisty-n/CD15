package scanner.fsm.states;

import io.ReturnCharacter;
import scanner.fsm.StateMachine;
import scanner.fsm.StateManager;
import scanner.tokenizer.Lexeme;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    8/11/2015
 * File Name:       ErrChewState
 * Project Name:    CD15 Compiler
 * Description:     Generalized error chewing state. Designed to chew errors from int and flot literals
 *                  Can perhaps be extended to chose other errors as well
 */
public class ErrChewState extends State  {

    public ErrChewState(StateMachine executionContext) {
        super(executionContext);
    }

    /*
    This function servers perfectly cooked steaks, so that you have to eat and come back
     */
    private void eatAndComeback() {
        this.getExecutionContext().readNextCharacter();
        this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.ERR_CHEW_STATE));
    }

    @Override
    public void execute() {

        ReturnCharacter charObj = this.getExecutionContext().getCharacterForConsideration();
        char charCh = charObj.getCharacter();

        if ( Character.isAlphabetic( charCh ) ) {

            // Its a-z / A-Z, so we keep chewing
            this.eatAndComeback();
            return;

        } else if ( Character.isDigit( charCh ) ) {

            // Same as above, keep chewing
            this.eatAndComeback();
            return;

        } else if ( charCh == Lexeme.SignificantCharacters.DOT_OP.asChar() ) {

            // This is also invalid, we consume this as part of the error
            this.eatAndComeback();
            return;

        } else {

            // If need be, we can add in extra character handling rules here
            // We've found something else set to start state and set this lexeme as done
            // We could have found whitespace, or an operator
            this.getExecutionContext().exposeLexeme().setIsComplete(true, charObj.getIndexOnLine() - 1);
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.START_STATE));
            return;

        }

    }

    @Override
    public StateManager.StateClass getStateClass() {
        return StateManager.StateClass.ERR_CHEW_STATE;
    }
}
