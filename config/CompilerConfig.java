package config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    8/8/2015
 * File Name:       config.CompilerConfig
 * Project Name:    CD15 Compiler
 * Description:     Stores configuration variables to be used throughout the program where needed
 */
public class CompilerConfig {

    public static PrintStream outputFile = null;
    public static boolean IS_ASSIGNMENT1 = true;

    private static final String DEBUG_FILE_LOCATION = "debug.txt";

    public static PrintStream getDebugOutputLocation(OUT_LOCATION location) {

        if (location == OUT_LOCATION.STD_OUT) {
            // Is it currently using std out?
            return System.out;
        } else {
            if ( CompilerConfig.outputFile == null ) {
                // Create new file if not exist
                try {
                    CompilerConfig.outputFile = new PrintStream(
                            new FileOutputStream(
                                    new File( CompilerConfig.DEBUG_FILE_LOCATION )
                            )
                    );
                } catch (FileNotFoundException fne) {
                    System.out.println("Fatal error constructing Debug File. Using stdio. Error report: " + fne.toString());
                } finally {
                    if (CompilerConfig.outputFile == null) {
                        CompilerConfig.outputFile = System.out;
                    }
                }
            }
            return CompilerConfig.outputFile;
        }
    }

    public static enum OUT_LOCATION {
        STD_OUT,
        FILE_OUT
    }


}
