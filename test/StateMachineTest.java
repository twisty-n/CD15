package test;

import io.InputController;
import scanner.Scanner;
import utils.DebugWriter;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    8/8/2015
 * File Name:       StateMachineTest
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class StateMachineTest {

    public static void main(String[] args) {

        new StateMachineTest().execute(args);

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
        System.out.println("$$$$$$$$$$$$$$$$$$$$$ Running State Machine Tests $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

        while ( scanner.canContinue() ) {

            DebugWriter.writeToConsole( "Report Lexeme: " + scanner.getMachine().obtainLexeme() + "\t Scanner Status: " +scanner.getInputStatus().name() );
        }

    }

}
