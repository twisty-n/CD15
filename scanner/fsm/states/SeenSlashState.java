package scanner.fsm.states;

import io.ReturnCharacter;
import scanner.fsm.StateMachine;
import scanner.fsm.StateManager;
import scanner.tokenizer.Lexeme;
import scanner.tokenizer.SignificantCharacter;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    13/08/15
 * File Name:       SeenSlashState
 * Project Name:    CD15
 * Description:
 */
public class SeenSlashState extends State {

    public SeenSlashState ( StateMachine executionContext ) {
        super ( executionContext );
    }

    @Override
    public void execute () {

        // Read char
        this.getExecutionContext().readNextCharacter();
        ReturnCharacter charObj = this.getExecutionContext().getCharacterForConsideration();
        char charCh = charObj.getCharacter();
        Lexeme lex = this.getExecutionContext().exposeLexeme();

        // If next char is '=' we have a /= character
        if ( charCh == SignificantCharacter.ASSIGN_OP.asChar() ) {
            // Complete lexeme and prepare charBuffer for default state
            lex.addCharToLexeme ( charObj );
            lex.setIsComplete(
                    true,
                    charObj.getIndexOnLine(),
                    true
            );
            this.getExecutionContext().readNextCharacter();
            this.getExecutionContext().setNextState( StateManager.getState ( StateManager.StateClass.START_STATE ) );

        } else if ( charCh == SignificantCharacter.SLASH_OP.asChar() ) {

            // if next char is // we have a line comment, consume until \n
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.LINE_COMMENT_STATE));

        } else {

            // else we just have a lone divide, close the lexeme
            // We wont touch the char buffer, but we will set the next state to be the start state
            lex.setIsComplete(
                    true,
                    charObj.getIndexOnLine(),
                    true
            );
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.START_STATE));

        }

    }

    @Override
    public StateManager.StateClass getStateClass () {
        return StateManager.StateClass.SEEN_SLASH_STATE;
    }
}
