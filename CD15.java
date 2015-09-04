import context.CompilationContext;
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
public class CD15 {

    public static void main(String[] args) {

        CD15 sd = new CD15();
        sd.run(args);

    }

    public void run(String[] args) {

        if (!(args.length > 0)) {
            DebugWriter.writeEverywhere("No input file. Terminating scanner");
            System.exit(0);
        }

        // Set up the scanner
        Scanner scanner = new Scanner();

        for (int i = 0; i < args.length; i++) {

            // Iterate over each source file
            String considerationFile = args[i];
            CompilationContext.configureCompilationContext(considerationFile);
            InputController ic = new InputController( considerationFile, scanner );
            scanner.configure(ic);

            //System.out.println("Tokenizing File: " + considerationFile);

            while ( scanner.canContinue() ) {
                scanner.getNextValidToken();
            }
            //scanner.reportEOF(); // Hax
            CompilationContext.getContext().closeContext();

            System.out.println();
            System.out.println();

        }

    }

}
