package context.error.handlers;

import parser.Parser;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    10/7/2015
 * File Name:       Handleable
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public interface Handleable {

    /**
     * Perform the error recovery actions for this error type
     */
    public void handle(Parser parser) throws ErrorHandlerException;

    /**
     * Perform error recovery behaviour
     */
    public void recover();

}
