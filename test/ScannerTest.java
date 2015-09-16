package test;

import context.CompilationContext;
import io.InputController;
import scanner.Scanner;
import utils.DebugWriter;

import java.io.File;

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

        if (!(args.length > 0)) {
            DebugWriter.writeEverywhere("No input file. Terminating scanner");
            System.exit(0);
        }

        // Set up the scanner
        Scanner scanner = new Scanner();
        File[] files = new File(args[0]).listFiles();

        for (int i = 0; i < files.length; i++) {

            // Iterate over each source file
            String considerationFile = files[i].getPath();

            CompilationContext.configureCompilationContext(considerationFile);
            InputController ic = new InputController(considerationFile, scanner);
            scanner.configure(ic);
            while(scanner.canContinue()) { scanner.getNextValidToken(); }
            CompilationContext.Context.closeContext();
//
        }
    }

}
