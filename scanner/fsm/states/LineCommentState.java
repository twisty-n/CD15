package scanner.fsm.states;

import io.ReturnCharacter;
import scanner.fsm.StateMachine;
import scanner.fsm.StateManager;
import scanner.tokenizer.SignificantCharacter;
import utils.DebugWriter;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    13/08/15
 * File Name:       LineCommentState
 * Project Name:    CD15
 * Description:
 */
public class LineCommentState extends State {

    public LineCommentState ( StateMachine executionContext ) {
        super ( executionContext );
    }

    @Override
    public void enterState() {

        DebugWriter.writeToFile("Entering " + getStateClass().name() + " char under consideration: " + this.getExecutionContext().getCharacterForConsideration().getCharacter());

        ReturnCharacter preLex = this.getExecutionContext().getCharacterForConsideration();

        // We want to add whitespace to the lexeme for comments
        this.getExecutionContext().exposeLexeme().addCharToLexeme(preLex);

    }

    @Override
    public void execute () {

        // Read in the characer
        this.getExecutionContext().readNextCharacter();
        ReturnCharacter charObj = this.getExecutionContext().getCharacterForConsideration();
        char charCh = charObj.getCharacter();

        // Consume and add to the lexeme until we get to the \n operator
        if ( charCh != SignificantCharacter.NEW_LINE.asChar() ) {

            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.LINE_COMMENT_STATE));

        } else {

            // Leave the \n in the charBuffer for the DefaultState to deal with
            // Complete the lexeme and stamp as being a comment
            this.getExecutionContext().exposeLexeme().setIsComplete(
                    true,
                    charObj.getIndexOnLine(),
                    true,
                    true        // Marking as a comment
            );

            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.START_STATE));

        }

    }

    @Override
    public StateManager.StateClass getStateClass () {
        return StateManager.StateClass.LINE_COMMENT_STATE;
    }
}
