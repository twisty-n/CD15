package io;

import context.CompilationContext;
import utils.DebugWriter;
import scanner.Scanner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    8/8/2015
 * File Name:       InputController
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class InputController {

    private FileInputStream fis;        // The file input stream to read from
    private int charIndexOnLine;        // The characters physical index on the line
    private int lineIndexInFile;        // The linex physical index in the file
    private boolean newLineOnNextRead;  // Should we treat the character we will next read as the start of a newline
    private String currentFile;         // The current file that we are scanning
    private Scanner context;            // We can report to the context that we have reached EOF

    /**
     * Expects the absolute path of the file that is to be opened
     * @param considersationFile    The file to be opened for scanning
     * @param context               The Scanning context to report to
     */
    public InputController( String considersationFile, Scanner context ) {

        this.charIndexOnLine = -1;
        this.lineIndexInFile = 1;
        this.newLineOnNextRead = false;
        this.currentFile = considersationFile;
        this.context = context;

        try {

            this.fis = new FileInputStream( considersationFile );

        } catch ( FileNotFoundException fne) {
            DebugWriter.writeEverywhere("FATAL ERROR: Could not open input file."
                        + considersationFile
                        +" \nError contents: "
                        + fne
                        + "\nStack trace"
                        + fne.getStackTrace()
            );
            this.context.reportInputStatus(InputStatus.FATAL_ERROR);
        }

    }

    /**
     * Pumps the input stream for a single character
     * This will report to the scanner context the status of the input stream after pumping
     * @return pumped character
     */
    public ReturnCharacter pumpChar() {
        char returnChar = '~';
        try {

            // Now we update our tracking of the physical location
            if (this.newLineOnNextRead) {
                // We just did the read so update the line tracking and reset the position
                this.lineIndexInFile++;
                this.charIndexOnLine = -1;
                this.newLineOnNextRead = false;
            }

            int val = fis.read();

            // If val is -1, we've hit the end of the file, report it and return default
            // We also close the stream
            if ( val == -1 ) {
                this.context.reportInputStatus(InputStatus.END_OF_FILE);
                this.closeStream();
                return new ReturnCharacter();
            }

            // Otherwise, cast to a char and return
            returnChar = ( char ) val;

            // Both set up the reader for the next call if its a new line, and handle windows -_-
            if ( this.isNewline(returnChar) ) {
                // Tell the input controller to reset the information next round
                this.newLineOnNextRead = true;
                returnChar = '\n';
            }

            this.charIndexOnLine++;

            // We've succeeded so report success
            this.context.reportInputStatus(InputStatus.PUMP_SUCCESS);

        } catch (IOException e) {
            DebugWriter.writeEverywhere("FATAL ERROR: Could not read input file: "
                    +this.currentFile
                    + " \nError contents: "
                    + e
                    + "\n Stack trace: "
                    + e.getStackTrace()
            );
            this.context.reportInputStatus(InputStatus.FATAL_ERROR);
        } catch (NullPointerException e) {
            DebugWriter.writeEverywhere("FATAL ERROR: Trying to read from null input file: "
                            +this.currentFile
                            + " \nError contents: "
                            + e
                            + "\n Stack trace: "
                            + e.getStackTrace()
            );
            this.context.reportInputStatus(InputStatus.FATAL_ERROR);
        } finally {
            ReturnCharacter retChar = new ReturnCharacter(returnChar, this.charIndexOnLine, this.lineIndexInFile, this.currentFile);
            CompilationContext.getContext().bufferSourceCharacter(retChar);
            return retChar;
        }
    }

    /**
     * Tests if a character is a new line character, automatically handling windows crap
     * @param character         The character to consider
     * @return
     * @throws IOException
     */
    private boolean isNewline( char character ) throws IOException {

        // Handle the shitiness that is deal with windows
        if ( character == '\r' ) {
            //pump again to consume the '\n'
            char newLine = ( char ) this.fis.read();
            if ( newLine != '\n' ) {
                DebugWriter.writeEverywhere("Corrupted input stream. Mark me down because I cant read files -_-");
                context.reportInputStatus(InputStatus.FATAL_ERROR);
            }
            return true;
        }

        return character == '\n';

    }

    /**
     * Close the current file
     */
    public void closeStream() {
        if (this.fis != null) try {
            this.fis.close();
        } catch (IOException ioe) {
            DebugWriter.writeEverywhere("FATAL ERROR: Could not close input file :"
                            + this.currentFile
                            + " \nError report: "
                            + ioe
                            + "\n Stack trace: "
                            + ioe.getStackTrace()
            );
            // We don't need to report as we have should have already set up the context with the character
            //this.context.reportInputStatus(InputStatus.FATAL_ERROR);
        }
    }

    /**
     * Enumerates the possible input states of the file after pumping a character
     */
    public enum InputStatus {
        END_OF_FILE,
        FATAL_ERROR,
        PUMP_SUCCESS
    }

}
