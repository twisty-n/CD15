package scanner;

import io.InputController;
import scanner.fsm.StateMachine;
import scanner.tokenizer.Token;
import utils.DebugWriter;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    8/8/2015
 * File Name:       Scanner
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class Scanner {

    private InputController.InputStatus lastReportedInputStatus;
    private InputController input;
    private StateMachine fsm;

    /**
     * default with no settings
     */
    public Scanner() {

        this.configure(null);
    }

    /**
     * Set up the scanner with out input controller
     * @param input
     */
    public Scanner(InputController input) {

        this.configure(input);
    }

    /**
     * Allows a scanner object to be reconfigured as it is going
     * @param input
     */
    public void configure(InputController input) {

        // Configure the input controller
        this.input = input;
        this.lastReportedInputStatus = InputController.InputStatus.PUMP_SUCCESS;

        // Configure the flying spaghetti machine
        this.fsm = new StateMachine(this, this.input);
    }

    /**
     * The input handler can call this to tell the scanner the status of the current character pump
     * @param status
     */
    public void reportInputStatus( InputController.InputStatus status) {
        this.lastReportedInputStatus = status;
        DebugWriter.writeToFile(status.name());
    }

    /**
     *
     */
    public InputController.InputStatus getInputStatus() {
        return this.lastReportedInputStatus;
    }

    public boolean canContinue() {
        return this.lastReportedInputStatus == InputController.InputStatus.PUMP_SUCCESS;
    }

    /**
     * Returns the next token identified
     * @return
     */
    public Token getToken() {

        // First use the FSL to obtain the lexeme

        //this.fsm.obtainLexeme();

        // Also need to get the start of the token information. We will track this in the FSM
        //Tokenizer.MakeToken(lexeme, this.)

        // Then pass to the tokenizer with the physical values to obtain a Token

        // Return the token
        return null;

    }

}
