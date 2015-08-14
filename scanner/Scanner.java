package scanner;

import config.CompilerConfig;
import io.A1Output;
import io.InputController;
import scanner.fsm.StateMachine;
import scanner.tokenizer.Lexeme;
import scanner.tokenizer.Token;
import scanner.tokenizer.TokenClass;
import scanner.tokenizer.TokenFactory;
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
    private A1Output outputForA1;

    /**
     * default with no settings
     */
    public Scanner() {

        //this.configure(null);
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
        this.outputForA1 = new A1Output(CompilerConfig.getDebugOutputLocation(CompilerConfig.OUT_LOCATION.STD_OUT));
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
     * Obtains the next lexeme from the FSM and will transform and return into a token
     * Method will return null if the obtained lexeme was a comment, or was an invalid token
     * @return
     */
    private Token getToken() {

        //First use the FSL to obtain the lexeme
        Lexeme lex = this.fsm.obtainLexeme();
        Token token = null;

        // If the token is a comment or is undefined, return null
        if ( ! lex.isComment() && lex.isValid() ) {
            token =  TokenFactory.constructToken(lex);
            outputForA1.addTokenToBuffer(token);
            return token;
        }

        // FOR ASSIGNMENT 1 **********************
        if ( ! lex.isComment() ) {
            token = TokenFactory.constructToken(lex);
            this.outputForA1.addTokenToBuffer( token );
        }

        // Return the token
        return token;

    }

    /**
     * Will return the next VALID token to the caller
     * @return
     */
    public Token getNextToken() {

        // Strip out non-valid tokens from the stream returned to the caller
        Token token = null;
        do {
            token = this.getToken();
        } while( token == null );       // Spin while the obtained token is null. A null token is something invalid
        return token;

    }

    /**
     * For debugging only
     * @return
     */
    public StateMachine getMachine() {
        return this.fsm;
    }

}
