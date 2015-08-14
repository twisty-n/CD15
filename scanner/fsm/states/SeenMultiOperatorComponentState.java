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
 * File Name:       SeenMultiOperatorComponentState
 * Project Name:    CD15
 * Description:
 */
public class SeenMultiOperatorComponentState extends State {

    public SeenMultiOperatorComponentState ( StateMachine executionContext ) {
        super ( executionContext );
    }

    @Override
    public void execute () {

        // Read in next character
        this.getExecutionContext().readNextCharacter();
        ReturnCharacter charObj = this.getExecutionContext().getCharacterForConsideration();
        char charCh = charObj.getCharacter();

        // if its an '=' we have the second component for one of the multi
        // char operators.
        Lexeme lex = this.getExecutionContext().exposeLexeme();
        if ( charCh == SignificantCharacter.ASSIGN_OP.asChar() ) {
            // Manially deal with the input stream here
            lex.addCharToLexeme ( charObj );
            lex.setIsComplete (
                    true,
                    charObj.getIndexOnLine(),
                    true
            );
            this.getExecutionContext().readNextCharacter();
        } else {
            // If its anything else, leave in the buffer and terminate the lexeme
            lex.setIsComplete(
                    true,
                    charObj.getIndexOnLine() - 1,
                    true
            );

        }

        // We will always enter the start state here
        this.getExecutionContext().setNextState( StateManager.getState ( StateManager.StateClass.START_STATE ) );

    }

    @Override
    public StateManager.StateClass getStateClass () {
        return StateManager.StateClass.SEEN_MULTIOP_COMP_STATE;
    }
}
