package context.error.handlers;

import parser.Parser;

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

    private Parser context;
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
    public abstract void recover();
}
