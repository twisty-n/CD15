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
 * Date Created:    14/08/15
 * File Name:       CloseMlcState
 * Project Name:    CD15
 * Description:
 */
public class CloseMlcState extends State {

    public CloseMlcState(StateMachine executionContext) {
        super(executionContext);
    }

    @Override
    public void execute() {

        this.getExecutionContext().readNextCharacter();
        ReturnCharacter charObj = this.getExecutionContext().getCharacterForConsideration();
        char charCh = charObj.getCharacter();

        // If we see a plus we have just found the terminator for our multiline comment
        if ( charCh == SignificantCharacters.PLUS_OP.asChar() ) {

            // Add the '+' to the lexeme
            // close the Lexeme and mark as a comment
            // Set the next state to be start state
            // Increment the char pointer
            Lexeme lex = this.getExecutionContext().exposeLexeme();
            lex.addCharToLexeme( charObj );
            lex.setIsComplete(
                    true,
                    charObj.getIndexOnLine(),
                    true,
                    true    // Marking as a comment
            );
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.START_STATE));
            this.getExecutionContext().readNextCharacter();

        } else {

            // We only have a partial closer, so it must have been part of the comment
            // Redump into the character chewer
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.LOCK_MLC_STATE));

        }

    }

    @Override
    public StateManager.StateClass getStateClass() {
        return StateManager.StateClass.CLOSE_MLC_STATE;
    }
}
