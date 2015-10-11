package context.error.handlers;

import scanner.tokenizer.TokenClass;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    10/11/2015
 * File Name:       SyncOnCommaCascadeOnStmntException
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class SyncOnCommaCascadeOnStmntException extends ErrorHandlerException {

    /**
     * Attempt to find and sync on a comma
     * if we find statement keyword, throw statement keyword handler
     * @throws ErrorHandlerException
     */
    @Override
    public void recover() throws ErrorHandlerException {
        statementKeywords.add(TokenClass.TCOMA);
        while(!statementKeywords.contains(context.currentTokenClass()) ) {
            checkEOF();
        }
        statementKeywords.remove(TokenClass.TCOMA);
        if (!context.currentTokenClass().equals(TokenClass.TCOMA)) {
            // Then we hit a statement, escalate to statement level
            throw new SyncAtStatementKeywordException();
        }
        // Else we have a comma, and can recover within the list
    }
}
