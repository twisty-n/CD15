package scanner.fsm.states;

import io.ReturnCharacter;
import scanner.fsm.StateMachine;
import scanner.fsm.StateManager;
import utils.DebugWriter;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    8/8/2015
 * File Name:       State
 * Project Name:    CD15 Compiler
 * Description:     Encapsualtes a state that the FSM may be in
 *                  The state will define its own transitions
 */
public abstract class State {

    private StateMachine executionContext;          // The state machine that the state is operating in

    /**
     * Constructor setting up our execution context
     * @param executionContext
     */
    public State(StateMachine executionContext) {
        this.setContext(executionContext);
    }

    /**
     * This should be called before the state is entered and will register the current char
     * in the character buffer as belonging to the current lexeme as long as the character is not whitespace
     * Functionality as yet unthought of. But useful for debugging
     */
    public void enterState() {
        DebugWriter.writeToFile("Entering " + getStateClass().name()
                + " char under consideration: "
                + this.getExecutionContext().getCharacterForConsideration().getCharacter());

        ReturnCharacter preLex = this.executionContext.getCharacterForConsideration();
        if (preLex.getCharacter() != '\n' && preLex.getCharacter() != ' ' && preLex.getCharacter() != '\t') {
            this.executionContext.exposeLexeme().addCharToLexeme(preLex);
        }
    }

    /**
     * Executes when this state is transitioned into
     * Will be responsible for determining the transition rules
     */
    public abstract void execute();

    /**
     * Called when this state is exited
     * Will generally be responsible for outputing Lexemes to the Scanner context
     */
    public void exitState() {
        DebugWriter.writeToFile("Exiting " + getStateClass().name()
                + " current Lexeme: "
                + this.getExecutionContext().exposeLexeme().toString());
    }

    /**
     * Returns the type of class that this state is
     * @return
     */
    public abstract StateManager.StateClass getStateClass();

    /**
     * Set the execution context for this state
     * @param ctx
     */
    private void setContext(StateMachine ctx) {
        this.executionContext = ctx;
    }

    /**
     * Retrieve the execution context for this state
     * @return
     */
    public StateMachine getExecutionContext() {
        return this.executionContext;
    }

}
