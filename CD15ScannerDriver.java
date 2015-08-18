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

        String considerationFile = null;
        if (args.length > 0) {
            considerationFile = args[0];
        } else {
            DebugWriter.writeEverywhere("No input file. Terminating scanner");
            System.exit(0);
        }

        Scanner scanner = new Scanner();
        InputController ic = new InputController( considerationFile, scanner );
        scanner.configure(ic);

        while ( scanner.canContinue() ) {
            scanner.getNextValidToken();
        }
    }

}
