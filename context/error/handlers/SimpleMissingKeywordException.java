package context.error.handlers;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    10/7/2015
 * File Name:       SimpleMissingKeywordException
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class SimpleMissingKeywordException extends ErrorHandlerException {

    /**
     * If we miss a simple keyword, just consume the next token and proceed as normal
     */
    @Override
    public void recover() {

    }
}
