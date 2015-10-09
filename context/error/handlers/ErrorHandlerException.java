package context.error.handlers;

import context.error.EOFError;
import parser.Parser;
import scanner.tokenizer.TokenClass;

import java.util.HashSet;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    10/7/2015
 * File Name:       Recoverable
 * Project Name:    CD15 Compiler
 * Description:     Abstract error handling class from which to derive annonymous classes
 */
public abstract class ErrorHandlerException extends Exception implements Handleable {

    protected Parser context;
    protected static final HashSet<TokenClass> statementKeywords = new HashSet<TokenClass>(){{
        add(TokenClass.TLOOP);
        add(TokenClass.TEXIT);
        add(TokenClass.TIFKW);
        add(TokenClass.TPRIN);
        add(TokenClass.TPRLN);
        add(TokenClass.TINPT);
        add(TokenClass.TCALL);
    }};

    protected void checkEOF() throws ErrorHandlerException {
        if (context.currentTokenClass().equals(TokenClass.TEOF)) {
            EOFError.record();
        }
    }
    /**
     * Default behaviour is to throw an exception
     * @throws ErrorHandlerException
     */
    @Override
    public void handle(Parser context) throws ErrorHandlerException {
        this.context = context;
        throw this;
    }

    @Override
    public abstract void recover() throws ErrorHandlerException;
}
