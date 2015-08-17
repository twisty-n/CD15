package scanner.fsm.states;

import io.ReturnCharacter;
import scanner.fsm.StateMachine;
import scanner.fsm.StateManager;
import scanner.tokenizer.SignificantCharacter;
import scanner.tokenizer.TokenClass;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    8/11/2015
 * File Name:       ZeroIntLitState
 * Project Name:    CD15 Compiler
 * Description:     Semantically, we are looking for a single zero.
 *                  A zero with characters following it, or anything other then a terminator or whitespace
 *                  will enter error consumption
 *
 *                  If we see a '.', we will enter the floating point state
 */
public class ZeroIntLitState extends State{

    public ZeroIntLitState(StateMachine executionContext) {
        super(executionContext);
    }

    @Override
    public void execute() {

        // So. On first entering this state, we have seen a zero, its in the lexeme, and the char being considered
        // is the character after the zero
        this.getExecutionContext().readNextCharacter();
        ReturnCharacter charObj = this.getExecutionContext().getCharacterForConsideration();
        char charCh = charObj.getCharacter();

        if ( Character.isWhitespace( charCh ) ) {

            // We've seen whitespace, we have a valid integer literal to consider
            this.getExecutionContext().exposeLexeme().setIsComplete(
                    true,
                    charObj.getIndexOnLine() - 1,
                    true,
                    TokenClass.TILIT
            );
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.START_STATE));

        } else if ( charCh == SignificantCharacter.DOT_OP.asChar() ) {

            // We may have a 0 level floating point literal
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.FLT_LIT_STATE));

        } else if ( SignificantCharacter.isOperatorOrDelimiter(charCh)  ) {

            // We know its not a dotOperator so we have some other op or delim
            this.getExecutionContext().exposeLexeme().setIsComplete(true, charObj.getIndexOnLine() - 1, true);
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.START_STATE));

        } else {

            // Character is an illegal or erronous character. Enter the error tracking state
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.ERR_CHEW_STATE));

        }

    }

    @Override
    public StateManager.StateClass getStateClass() {
        return StateManager.StateClass.ZERO_INT_LIT_STATE;
    }
}
