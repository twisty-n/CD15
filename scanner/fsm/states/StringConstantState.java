package scanner.fsm.states;

import io.ReturnCharacter;
import scanner.fsm.StateMachine;
import scanner.fsm.StateManager;
import scanner.tokenizer.SignificantCharacter;
import scanner.tokenizer.TokenClass;
import utils.DebugWriter;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    14/08/15
 * File Name:       StringConstantState
 * Project Name:    CD15
 * Description:
 */
public class StringConstantState extends State {

    public StringConstantState(StateMachine executionContext) {
        super(executionContext);
    }

    @Override
    public void enterState() {

        DebugWriter.writeToFile("Entering " + getStateClass().name() + " char under consideration: " + this.getExecutionContext().getCharacterForConsideration().getCharacter());

        // For our string literals, we dont want the " stored as part of it, so ignore on char add
        ReturnCharacter preLex = this.getExecutionContext().getCharacterForConsideration();
        if (preLex.getCharacter() != '\n'
                && preLex.getCharacter() != SignificantCharacter.QUOTE.asChar()) {
            this.getExecutionContext().exposeLexeme().addCharToLexeme(preLex);
        }

    }

    @Override
    public void execute() {

        // We have seen a '"' so everything until the next '"' is part of the constant
        // If ew see a '\n' terminate processing, return to start state and flag lexeme
        // as invalid

        // Read in next character
        this.getExecutionContext().readNextCharacter();
        ReturnCharacter charObj = this.getExecutionContext().getCharacterForConsideration();
        char charCh = charObj.getCharacter();

        if ( charCh == SignificantCharacter.QUOTE.asChar() ) {

            // Terminate string constant, making sure not to include the "
            this.getExecutionContext().exposeLexeme().setIsComplete(
                    true,
                    charObj.getIndexOnLine() - 1,
                    true,
                    TokenClass.TSTRG
            );
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.START_STATE));
            this.getExecutionContext().readNextCharacter();

        } else if ( charCh == SignificantCharacter.NEW_LINE.asChar() ) {

            // This is an invalid terminator mark it and then set up the next state to go
            this.getExecutionContext().exposeLexeme().setIsComplete(
                    true,
                    charObj.getIndexOnLine(),
                    false
            );
            this.getExecutionContext().readNextCharacter();
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.START_STATE));

        } else {

            // its considered part of the string literal
            this.getExecutionContext().setNextState(StateManager.getState(StateManager.StateClass.STRING_CONSTANT_STATE));

        }

    }

    @Override
    public StateManager.StateClass getStateClass() {
        return StateManager.StateClass.STRING_CONSTANT_STATE;
    }
}
