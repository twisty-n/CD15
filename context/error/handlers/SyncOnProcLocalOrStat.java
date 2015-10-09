package context.error.handlers;

import scanner.tokenizer.TokenClass;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    10/9/2015
 * File Name:       SyncOnProcLocalOrStat
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class SyncOnProcLocalOrStat extends ErrorHandlerException {

    /**
     * Attempt to sync on the local or a statement keyword inside a procedure
     */
    @Override
    public void recover() throws ErrorHandlerException {
        statementKeywords.add(TokenClass.TLOCL);
        statementKeywords.add(TokenClass.TPROC);
        while( !statementKeywords.contains(context.currentTokenClass())) {
            this.checkEOF();
            context.nextToken();
        }
        statementKeywords.remove(TokenClass.TLOCL);
        statementKeywords.remove(TokenClass.TPROC);
    }
}
