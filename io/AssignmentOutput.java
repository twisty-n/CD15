package io;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    8/8/2015
 * File Name:       AssignmentOutput
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public abstract class AssignmentOutput {

    /**
     * Write the specified output to the console
     * @param output
     */
    public abstract void writeConsoleOutput( String output );

    /**
     * Writes the specified output to predefined file
     * @param output
     */
    public abstract void writeFileOutput( String output );



}
