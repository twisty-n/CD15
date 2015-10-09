package context.error.handlers;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    10/7/2015
 * File Name:       SimpleMissingKeywordException
 * Project Name:    CD15 Compiler
 * Description:     Handles the case where a simple token is missing and par
 * sing should be able to be resumed after consuming the next token
 */
public class SimpleMissingTokenException extends ErrorHandlerException {
    /**
     * If we miss a simple keyword, just consume the next token and proceed as normal
     */
    @Override
    public void recover() {
        // Recovery is to discard the current token and load the next one
        this.context.nextToken();
    }
}
