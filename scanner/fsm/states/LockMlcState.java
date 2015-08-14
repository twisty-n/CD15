package scanner.fsm.states;

import io.ReturnCharacter;
import scanner.fsm.StateMachine;
import scanner.fsm.StateManager;
import scanner.tokenizer.SignificantCharacters;
import utils.DebugWriter;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    14/08/15
 * File Name:       LockMlcState
 * Project Name:    CD15
 * Description:
 */
public class LockMlcState extends State {

    public LockMlcState(StateMachine executionContext) {
        super(executionContext);
    }

    @Override
    public void enterState() {

        // Note that the lexeme generated for a comment will be largely useless
        // So it doesn't matter if its tracked decently. As the program listing will be
        // Provided by the compilation unit, not anything to do with the lexer

        DebugWriter.writeToFile("Entering " + getStateClass().name() + " char under consideration: " + this.getExecutionContext().getCharacterForConsideration().getCharacter());
        ReturnCharacter preLex = this.getExecutionContext().getCharacterForConsideration();
        // We want to add whitespace to the lexeme for comments
        this.getExecutionContext().exposeLexeme().addCharToLexeme(preLex);

    }

    @Override
    public void execute() {

        // Get character
        this.getExecutionContext().readNextCharacter();
        ReturnCharacter charObj = this.getExecutionContext().getCharacterForConsideration();
        char charCh = charObj.getCharacter();

        // Loop around this state until we find a new line
        if ( charCh != SignificantCharacters.NEW_LINE.asChar() ) {

            // chew chew chew chew!
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.LOCK_MLC_STATE));

        } else {

            // We may have found the end of the multiline comment so handle
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.PREP_FOR_MLC_CLOSE_STATE));

        }

    }

    @Override
    public StateManager.StateClass getStateClass() {
        return StateManager.StateClass.LOCK_MLC_STATE;
    }
}
