package context.error.handlers;

import scanner.tokenizer.TokenClass;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    10/9/2015
 * File Name:       SyncOnEndKwException
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class SyncOnStatementOrEndException extends ErrorHandlerException {

    /**
     * Spin until we see the end keyword
     * @throws ErrorHandlerException
     */
    @Override
    public void recover() throws ErrorHandlerException {
        statementKeywords.add(TokenClass.TENDK);
        while(!statementKeywords.contains(context.currentTokenClass())) {
            this.checkEOF();
            context.nextToken();
        }
        statementKeywords.remove(TokenClass.TENDK);
    }
}
