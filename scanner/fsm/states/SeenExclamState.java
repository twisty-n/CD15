package scanner.fsm.states;

import context.error.CompilationError;
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
 * File Name:       SeenExclamState
 * Project Name:    CD15
 * Description:
 */
public class SeenExclamState extends State {

    public SeenExclamState ( StateMachine executionContext ) {
        super ( executionContext );
    }

    @Override
    public void execute () {

        // Same logic as for the multiopreator component except!
        // A '!' by itself is invalid, so we will close the lexeme
        // but also mark it as invalid
        // Read in next character
        this.getExecutionContext().readNextCharacter();
        ReturnCharacter charObj = this.getExecutionContext().getCharacterForConsideration();
        char charCh = charObj.getCharacter();

        Lexeme lex = this.getExecutionContext().exposeLexeme();

        if ( charCh == SignificantCharacter.ASSIGN_OP.asChar() ) {

            // We have a valid '!=' save it and prepare the fsm for
            // accepting input again as per SMOCS standard
            lex.addCharToLexeme( charObj );
            lex.setIsComplete(
                    true,
                    charObj.getIndexOnLine(),
                    true
            );
            this.getExecutionContext().readNextCharacter();
        } else {

            // DUN-DOWWWW < sad trombone sound >
            // TODO: make sure we output a nice error
            lex.setIsComplete(
                    true,
                    charObj.getIndexOnLine() - 1,
                    false
            );
            CompilationError.record(this.getExecutionContext().exposeLexeme(), CompilationError.Type.UNKNOWN_CHARACTER);
        }

        this.getExecutionContext().setNextState( StateManager.getState ( StateManager.StateClass.START_STATE ) );

    }

    @Override
    public StateManager.StateClass getStateClass () {
        return StateManager.StateClass.SEEN_EXCLAM_STATE;
    }
}
