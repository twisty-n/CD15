package test;

import context.CompilationContext;
import io.DotTreeCrawler;
import io.InputController;
import parser.Parser;
import parser.ast.TreeNode;
import scanner.Scanner;
import utils.DebugWriter;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/5/2015
 * File Name:       ParserTest
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class ParserTest {

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

        for (int i = 0; i < args.length; i++) {

            // Iterate over each source file
            String considerationFile = args[i];
            CompilationContext.configureCompilationContext(considerationFile);
            InputController ic = new InputController(considerationFile, scanner);
            scanner.configure(ic);
            Parser parser = new Parser(scanner);

            System.out.println("Tokenizing File: " + considerationFile);

            // Construct the parser and pass it the scanner
            // Then close the context

            TreeNode ast = parser.parse();
            StringBuffer astBuffer = DotTreeCrawler.crawl(ast);
            CompilationContext.Context.setAst(astBuffer);
            CompilationContext.getContext().closeContext();

            System.out.println();
            System.out.println();

        }
    }
}
