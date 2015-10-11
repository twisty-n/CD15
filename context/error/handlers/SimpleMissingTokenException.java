package context.error.handlers;

import scanner.tokenizer.TokenClass;
import utils.Levenhstein;

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
        // We will assume that the token has been missed, and not replaced with the incorrect thing

        // Decide recovery action based on the L distance between what should have been and what is
        // If > 50% match, consume until next token, else, they may have simply skipped it, so
        // Leave it in there
        for (TokenClass tClass : TokenClass.keywords()) {
            if (new Levenhstein().compare(tClass.val(), context.currentTokenLexeme()) >= 0.5) {
                context.nextToken();
            }
        }


    }
}
