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
    private ReturnCharacter currentlyBeingConsidered;
    private Scanner context;
    private InputController input;

    public StateMachine(Scanner context, InputController input) {

        // Set up the state manager with the need details

        this.configure(context, input);
    }

    /**
     * Set up the FSM with all of its required vars and initial information
     */
    public void configure(Scanner context, InputController input) {

        this.context = context;
        this.input = input;

        StateManager.registerContext(this);
        this.currentState = StateManager.getState(StateManager.StateClass.START_STATE);

    }

    public Lexeme obtainLexeme() {

        // Spin around the states until we obtain a valid lexeme
        // We should always be in the starting state when this is called,
        // so we need some cleanup method

        // Emit
        return null;

    }

    public void readNextCharacter() {
        this.currentlyBeingConsidered = input.pumpChar();
        //if (!this.context.canContinue())
    }



}
