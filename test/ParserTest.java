package test;

import context.CompilationContext;
import io.DotTreeCrawler;
import io.InputController;
import parser.Parser;
import parser.ast.TreeNode;
import scanner.Scanner;
import utils.DebugWriter;

import java.io.File;
import java.util.ArrayList;

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
        File[] temp = new File(args[0]).listFiles();
        ArrayList<File> files = new ArrayList<>();
        for (File file : temp) {
            if (file.isFile()) files.add(file);
        }

        for (int i = 0; i < files.size(); i++) {

            // Iterate over each source file
            String considerationFile = files.get(i).getPath();

            CompilationContext.configureCompilationContext(considerationFile);
            InputController ic = new InputController(considerationFile, scanner);
            scanner.configure(ic);
            Parser parser = new Parser(scanner);

            System.out.println("Tokenizing File: " + considerationFile);

            // Construct the parser and pass it the scanner
            // Then close the context
            try {
                TreeNode ast = parser.parse();
                StringBuffer astBuffer = DotTreeCrawler.crawl(ast);
                CompilationContext.Context.setAst(astBuffer);
            } catch(Exception e) {
                e.printStackTrace();
            } finally {
                scanner.spinUntilEmpty();
                CompilationContext.getContext().closeContext();
            }

            System.out.println();
            System.out.println();

        }
    }
}
