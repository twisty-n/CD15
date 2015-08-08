package io;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    8/8/2015
 * File Name:       A1Output
 * Project Name:    CD15 Compiler
 * Description:     output class specifically designed to output the results of the scanner
 *                  based on what is required for the Part 1 requirements
 */
public class A1Output extends AssignmentOutput {

    @Override
    /**
     *
     */
    public void writeConsoleOutput(String output) {
        // implement this as per specifications
        // We will need to track the number of chars printed to the line and go onto a new line where needed
        // To do that, we will need an internal buffer in this class that flushes when needed
    }

    @Override
    /**
     *
     */
    public void writeFileOutput(String output) {
        // This will need not be implemented for this assignment, unless we want to use it to print the
        // program listing through the output listing controller
    }
}
