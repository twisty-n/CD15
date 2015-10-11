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

    // Args  [ mode (folder|file), {folder || files} ]
    public void run(String[] args) {

        if (!(args.length > 0)) {
            DebugWriter.writeEverywhere("No input file. Terminating scanner");
            System.exit(0);
        }

        // Determine mode
        String mode = args[0];
        if (mode.equals("help")) {
            this.help();
            return;
        }
        boolean folderMode = (mode.equals("folder") ? true : false);

        // Set up the scanner
        Scanner scanner = new Scanner();
        ArrayList<File> files = new ArrayList<>();

        if (folderMode) {
            File[] temp = new File(args[1]).listFiles();
            for (File file : temp) {
                if (file.isFile()) files.add(file);
            }
        } else {
            files.add(new File(args[1]));
        }


        for (int i = 0; i < files.size(); i++) {

            // Iterate over each source file
            String considerationFile = files.get(i).getPath();

            CompilationContext.configureCompilationContext(considerationFile);
            InputController ic = new InputController(considerationFile, scanner);
            scanner.configure(ic);
            Parser parser = new Parser(scanner);

            System.out.println("Compiling CD15 Source File: " + considerationFile);

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
        }
    }

    public void help() {
        System.out.println("****************************");
        System.out.println("Compiler instructions:");
        System.out.println("To compile a single file: 'java CD15 file my/file/path.cd15'");
        System.out.println("\nThis compiler supports batch compiling, to compile a directory of CD15");
        System.out.println("To compile a directory: 'java CD15 folder path/to/cd15/files'");
        System.out.println("\nEach .cd15 file will have a program listing produced in the standard output" +
                "\n as well as debug files in the .cd15 source directory.");
        System.out.println("\nTo view an AST, open the utils/index.html and use the choose file dialog to view a graphic AST");
        System.out.println("Note that AST's will only be produced for successful compilations");
    }

}
