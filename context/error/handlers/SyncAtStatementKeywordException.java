package context.error.handlers;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    10/9/2015
 * File Name:       SyncAtStatementException
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class SyncAtStatementKeywordException extends ErrorHandlerException {

    /**
     * Will consume the input tokens until it arrives as the next statement keyword
     */
    @Override
    public void recover() throws FatalException{
        while(!statementKeywords.contains(context.currentTokenClass())) {
            this.checkEOF();
            context.nextToken();
        }
    }
}
