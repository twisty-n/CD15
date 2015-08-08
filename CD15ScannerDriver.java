import io.InputController;
import scanner.Scanner;
import utils.DebugWriter;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    8/8/2015
 * File Name:       CD15ScannerDriver
 * Project Name:    CD15 Compiler
 * Description:     A basic driver file for the CD15 Scanner
 */
public class CD15ScannerDriver {

    public static void main(String[] args) {

        CD15ScannerDriver sd = new CD15ScannerDriver();
        sd.run(args);

    }

    public void run(String[] args) {

        // We will accept the input file via args
        // Can expand that is later to accept multiple input files and stuff
        String considerationFile = null;
        if (args.length > 0) {
            considerationFile = args[0];
        } else {
            DebugWriter.writeEverywhere("No input file. Terminating scanner");
            System.exit(0);
        }


        DebugWriter.writeToConsole("This is a test of the debug printing");
        DebugWriter.writeToFile("This is a test of the debug printing");

        Scanner scanner = new Scanner();
        scanner.configure( new InputController( considerationFile, scanner ) );

        while ( ! (scanner.getInputStatus() == InputController.InputStatus.END_OF_FILE)) {

            // Grab some lexemes

            // Make some tokens

            // Print that shiz

            // Have some dinner

        }
    }

}
