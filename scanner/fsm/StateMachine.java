package scanner.fsm;

import io.InputController;
import io.ReturnCharacter;
import scanner.Scanner;
import scanner.fsm.states.State;
import scanner.tokenizer.Lexeme;


/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    8/8/2015
 * File Name:       StateMachine
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class StateMachine {

    private State currentState;
    private State nextState;
    private ReturnCharacter currentlyBeingConsidered;
    private Scanner context;
    private InputController input;

    private Lexeme lexemeBeingBuilt;

    public StateMachine(Scanner context, InputController input) {

        // Set up the state manager with the need details
        this.configure(context, input);
    }

    /**
     * Set up the FSM with all of its required vars and initial information
     */
    public void configure(Scanner context, InputController input) {

        // Assign vars
        this.context = context;
        this.input = input;
        this.currentlyBeingConsidered = input.pumpChar();

        // Set up state management
        StateManager.registerContext(this);
        this.currentState = StateManager.getState(StateManager.StateClass.START_STATE);
        this.nextState = null;

        // this.lexemeBeingBuilt = new Lexeme();
        this.constructNewLexeme();

    }

    private void constructNewLexeme() {
        this.lexemeBeingBuilt = new Lexeme();
        this.lexemeBeingBuilt.setLineIndexInFile( this.currentlyBeingConsidered.getLineIndexInFile());
    }

    public void setNextState(State state) {
        this.nextState = state;
    }

    /**
     * Returns a complete Lexeme to the caller
     * @return
     */
    public Lexeme obtainLexeme() {

        // Spin around the states until we obtain a valid lexeme

        // Oh shoot me
        while ( true ) {

            // Exexute over the states
            this.currentState.enterState();
            this.currentState.execute();
            this.currentState.exitState();

            // Change the state
            this.currentState = nextState;
            this.nextState = null;

            // I like explicit exits ;)
            if ( lexemeBeingBuilt.isComplete() ) {
                break;
            }

        }

        // Clean up after ourselves for next time
        // TODO: Add in the physical information here!!!!
        Lexeme completeLexeme = lexemeBeingBuilt;
        //lexemeBeingBuilt = new Lexeme();
        this.constructNewLexeme();

        // Emit
        return completeLexeme;

    }

    /**
     * Overwrites the character currently being considered with the character to be next considered
     */
    public void readNextCharacter() {
        this.currentlyBeingConsidered = input.pumpChar();
        //if (!this.context.canContinue())
    }

    /**
     * Returns the character that is currently being considered for lexing
     * @return
     */
    public ReturnCharacter getCharacterForConsideration() {
        return this.currentlyBeingConsidered;
    }

    /**
     * Exposes the current Lexeme that is being built
     * @return
     */
    public Lexeme exposeLexeme() {
        return this.lexemeBeingBuilt;
    }




}
