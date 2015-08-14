package utils;

import config.CompilerConfig;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    8/8/2015
 * File Name:       DebugWriter
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class DebugWriter {

    private final static boolean DEBUG = true;

    /**
     * Prints the information to the debug file
     * @param information
     */
   public static void writeToFile(String information) {
       CompilerConfig.getDebugOutputLocation(CompilerConfig.OUT_LOCATION.FILE_OUT).println(
            "DEBUG: " + information
       );
   }

    /**
     * Prints the information to the console
     * @param information
     */
    public static void writeToConsole(String information) {

        if (DEBUG) {
            DebugWriter.writeToFile(information);
            return;
        }

        CompilerConfig.getDebugOutputLocation(CompilerConfig.OUT_LOCATION.STD_OUT).println(
                "DEBUG: " + information
        );
    }

    /**
     * Prints the requested information to all output sources
     * @param information
     */
    public static void writeEverywhere(String information) {
        DebugWriter.writeToConsole(information);
        DebugWriter.writeToFile(information);
    }

}
