package scanner.fsm.states;

import scanner.fsm.StateMachine;

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
     * Executes before this state is transitioned into to
     * Functionality as yet unthought of. But useful for debugging
     */
    public abstract void enterState();

    /**
     * Executes when this state is transitioned into
     * Will be responsible for determining the transition rules
     */
    public abstract void execute();

    /**
     * Called when this state is exited
     * Will generally be responsible for outputing Lexemes to the Scanner context
     */
    public abstract void exitState();

    /**
     * Set the execution context for this state
     * @param ctx
     */
    public void setContext(StateMachine ctx) {
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
