package scanner.fsm.states;

import io.ReturnCharacter;
import scanner.fsm.StateMachine;
import scanner.fsm.StateManager;
import scanner.tokenizer.Lexeme;
import scanner.tokenizer.SignificantCharacters;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    13/08/15
 * File Name:       BeginMlc
 * Project Name:    CD15
 * Description:
 */
public class BeginMlcState extends State {

    public BeginMlcState(StateMachine executionContext) {
        super(executionContext);
    }

    @Override
    public void execute() {

        // Read in next character
        this.getExecutionContext().readNextCharacter();
        ReturnCharacter charObj = this.getExecutionContext().getCharacterForConsideration();
        char charCh = charObj.getCharacter();
        Lexeme lex = this.getExecutionContext().exposeLexeme();

        // We have seen the '+' of our '+/+', now we need the '/'
        // Note that since we have seen a '+' , an '=' would form, '+=' so handle
        // that explicity
        // That leaves a '/' or literally anything else

        if ( charCh == SignificantCharacters.SLASH_OP.asChar() ) {

            // we have a '/' so try for MLC  final char
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.TRY_FOR_MLC_STATE));

        } else if ( charCh == SignificantCharacters.ASSIGN_OP.asChar() ) {

            // handle the += case explicity the same as SeenMultiOperatorComponent
            lex.addCharToLexeme(charObj);
            lex.setIsComplete(true, charObj.getIndexOnLine(), true);
            this.getExecutionContext().readNextCharacter();
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.START_STATE));

        } else {

            // its just the '+' operator, handle as per SeenMultiOperatorComponent
            lex.setIsComplete(
                    true,
                    charObj.getIndexOnLine() - 1,
                    true
            );
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.START_STATE));
        }

    }

    @Override
    public StateManager.StateClass getStateClass() {
        return StateManager.StateClass.BEGIN_MLC_STATE;
    }
}
