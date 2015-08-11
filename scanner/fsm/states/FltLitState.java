package scanner.fsm.states;

import io.ReturnCharacter;
import scanner.fsm.StateMachine;
import scanner.fsm.StateManager;
import scanner.tokenizer.Lexeme;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    8/9/2015
 * File Name:       FltLitState
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class FltLitState extends State {

    public FltLitState(StateMachine executionContext) {
        super(executionContext);
    }

    @Override
    public void execute() {

        ReturnCharacter charObj = this.getExecutionContext().getCharacterForConsideration();
        char charCh = charObj.getCharacter();

        if ( Character.isDigit( charCh ) ) {

            // We are all good to consume and come back to this state

        } else if ( Character.isAlphabetic( charCh ) ) {

            // This is an error point

        } else if ( charCh == Lexeme.SignificantCharacters.DOT_OP.asChar() ) {

            // Another error point

        }

    }

    @Override
    public StateManager.StateClass getStateClass() {
        return StateManager.StateClass.FLT_LIT_STATE;
    }
}
