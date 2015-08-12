package scanner.fsm.states;

import io.ReturnCharacter;
import scanner.fsm.StateMachine;
import scanner.fsm.StateManager;
import scanner.tokenizer.SignificantCharacters;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    8/9/2015
 * File Name:       IntLitState
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class IntLitState extends State {

    public IntLitState(StateMachine executionContext) {
        super(executionContext);
    }

    @Override
    public void execute() {

        // Read in the next character and see what we can do
        this.getExecutionContext().readNextCharacter();

        ReturnCharacter charObj = this.getExecutionContext().getCharacterForConsideration();
        char charCh = charObj.getCharacter();

        // Now we determine where to go.
        // At this point, we've seen a single digit and its been added to the lexeme
        if ( Character.isDigit( charCh ) ) {

            // We've seen from 0-9 so we will re enter this state
            this.getExecutionContext().setNextState( StateManager.getState(StateManager.StateClass.INT_LIT_STATE) );
            return;

        } else if ( charCh == SignificantCharacters.DOT_OP.asChar() ) {

            // We've seen a dot operator, so now we will try to get a
            // floating point literal
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.FLT_LIT_STATE));
            return;

        } else if ( Character.isAlphabetic(charCh) ) {

            // This is invalid
            // We will need to consume and add to the lexeme until we find the
            // Next valid lexeme
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.ERR_CHEW_STATE));
            return;

        } else if ( Character.isWhitespace( charCh ) ) {

            // We've hit white space, this acts as a delimiter so
            // set the lexeme as complete
            this.getExecutionContext().exposeLexeme().setIsComplete(true, charObj.getIndexOnLine() - 1, true);
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.START_STATE));
            return;

        } else {

            // We can also merge this state with the above, but will leave it seperately
            // for semantic reasons!
            this.getExecutionContext().exposeLexeme().setIsComplete(true, charObj.getIndexOnLine() - 1, true);
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.START_STATE));
            return;

        }

    }

    @Override
    public StateManager.StateClass getStateClass() {
        return StateManager.StateClass.INT_LIT_STATE;
    }
}
