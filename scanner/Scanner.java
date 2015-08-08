package scanner;

import io.InputController;
import scanner.fsm.StateMachine;
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
        this.input = input;
        this.lastReportedInputStatus = InputController.InputStatus.PUMP_SUCCESS;
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

}
