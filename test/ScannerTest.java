package test;

import io.InputController;
import scanner.Scanner;
import utils.DebugWriter;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    14/08/15
 * File Name:       ScannerTest
 * Project Name:    CD15
 * Description:
 */
public class ScannerTest {

    public static void main(String[] args) {

        new ScannerTest().execute(args);

    }

    public void execute(String[] args) {

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

        System.out.println("******************************************************************************************************************");
        System.out.println("******************************************************************************************************************");
        System.out.println("******************************************************************************************************************");
        System.out.println("******************************************************************************************************************");
        System.out.println("******************************************************************************************************************");
        System.out.println("RUNNING SCANNER TESTS!");
        while ( scanner.canContinue() ) {

            scanner.getNextToken();

            //DebugWriter.writeToConsole( "Report Token: " + scanner.getNextToken()  + "\t Scanner Status: " +scanner.getInputStatus().name() );
        }

    }

}
