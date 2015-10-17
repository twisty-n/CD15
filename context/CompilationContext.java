package context;

import context.error.CompilationError;
import context.symbolism.SymbolTable;
import io.ReturnCharacter;
import scanner.tokenizer.Token;
import utils.Browser;
import utils.DebugWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    17/08/15
 * File Name:       CompilationContext
 * Project Name:    CD15
 * Description:
 *
 */
public class CompilationContext {

    private static CompilationContext currentContext;
    private HashMap<Phase, ArrayList<CompilationError>> errorBuffers;
    private String sourceFile;
    private StringBuffer outputBuffer;
    private int totalErrorCount;
    private int lineCount;
    public SymbolTable SymbolTable; // Have it named in this way for brevity
    public static CompilationContext Context;
    public StringBuffer ast;
    public boolean compilationSuccess;      // Initally set to true. If there is an error, set to false

    public CompilationContext(String sourceFile) {
        this.sourceFile = sourceFile;
        this.errorBuffers = new HashMap<>();
        this.outputBuffer = new StringBuffer();
        this.totalErrorCount = 0;
        this.lineCount = 0;
        ast = null;
        errorBuffers.put(Phase.LEXICAL_ANALYSIS, new ArrayList<CompilationError>());
        errorBuffers.put(Phase.SYNTACTIC_ANALYSIS, new ArrayList<CompilationError>());
        errorBuffers.put(Phase.SEMANTIC_ANALYSIS, new ArrayList<CompilationError>());
        errorBuffers.put(Phase.CODE_GENERATION, new ArrayList<CompilationError>());
        errorBuffers.put(Phase.CODE_OPTIMIZATION, new ArrayList<CompilationError>());
        this.SymbolTable = new SymbolTable(Token.generateKeywords(), true);
        CompilationContext.Context = this;
        this.compilationSuccess = true;
    }

    public void setAst(StringBuffer ast) {
        this.ast = ast;
    }

    /**
     *
     * @param sourceFile
     */
    public static void configureCompilationContext(String sourceFile) {
        if ( CompilationContext.currentContext != null ) {
            throw new RuntimeException("Trying to configure a compilation context but a context already existed");
        }
        CompilationContext.currentContext = new CompilationContext(sourceFile);
    }

    public static CompilationContext getContext() {
        if (CompilationContext.currentContext == null) {
            throw new RuntimeException("A compilation context was requested but has not been configured");
        }
        return CompilationContext.currentContext;
    }

    /**
     * Adds an error to the error buffer for the current phase of compilation
     * @param compilationPhase      The phase of compilation in which the error was recorded
     * @param error                 The generated compiler error
     */
    public void bufferCompilationError(Phase compilationPhase, CompilationError error) {
        this.compilationSuccess = false;
        this.totalErrorCount++;
        this.errorBuffers.get(compilationPhase).add(error);
        DebugWriter.writeToFile("Recorded compilation error: " + error.toString());

    }

    /**
     * Adds an error to the error buffer for the current phase of compilation
     * @param error                 The generated compiler error
     */
    public void bufferCompilationError(CompilationError error) {
        this.bufferCompilationError(error.getPhase(), error);
    }

    public void bufferSourceCharacter(ReturnCharacter sourceCharacter) {
        // KISS it for now, we can add in more information as we go
        int sourceCharLine = sourceCharacter.getLineIndexInFile();
        if (sourceCharLine > this.lineCount) {
            this.outputBuffer.append(sourceCharLine + "\t" + "|| ");
            this.lineCount = sourceCharLine;
        }

        this.outputBuffer.append(sourceCharacter.getCharacter());
    }

    /**
     * Closing the compilation context indicates that the compilation phase has been completed for some specific
     * code file.
     * This method must be called prior to opening a new compiation context for a different code file
     */
    public void closeContext() {

        // Flush out everything to file
        // Record our errors and stuff
        // Clean up anything that we need to
        String sourceFile = this.sourceFile;
        String fileName = sourceFile + "_compilation-listing.txt";

        this.writeCompilationListing(fileName, true);
        this.writeSymbolTable(sourceFile + "_sym-tab.txt");
        if (compilationSuccess) {
            this.writeAstListing(this.ast, sourceFile+"_ast.txt");
        }

        CompilationContext.currentContext = null;

    }

    private void writeCompilationListing(String fileName, boolean writeToConsole) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName), "utf-8"))) {
            this.writeCompilationSummary(writer);
            writer.write(this.outputBuffer.toString());
            for (Phase phase : Phase.values()) {
                this.writeCompilationErrors(phase, writer);
            }

            if (writeToConsole) {
                Writer console = new BufferedWriter(new OutputStreamWriter(System.out));
                this.writeCompilationSummary(console);
                console.write(this.outputBuffer.toString());
                for (Phase phase : Phase.values()) {
                    this.writeCompilationErrors(phase, console);
                }
                console.flush();
            }


        } catch (FileNotFoundException e) {
            DebugWriter.writeToFile("ERROR: cannot write to program listing file. \n" + e.getCause());
        }catch (IOException e) {
            DebugWriter.writeToFile("ERROR: cannot write to program listing file. \n" + e.getCause());
        }
    }

    private void writeSymbolTable(String fileName) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName), "utf-8"))) {

            // TODO: write a nice printing method here

        } catch (FileNotFoundException e) {
            DebugWriter.writeToFile("ERROR: cannot write to symbol file. \n" + e.getCause());
        }catch (IOException e) {
            DebugWriter.writeToFile("ERROR: cannot write to symbol file. \n" + e.getCause());
        }
    }

    private void writeCompilationSummary(Writer writer) throws IOException{

        writer.write("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        writer.write("Compilation Listing.\nSource File: " + this.sourceFile + '\n');
        writer.write("Compilation Status: " + (this.compilationSuccess ? "Successful" : "Failed") + '\n');
        writer.write("Total Compilation Errors: " + this.totalErrorCount+ '\n');
        writer.write("Lexical Errors: " + this.errorBuffers.get(Phase.LEXICAL_ANALYSIS).size()+ '\n');
        writer.write("Syntatic Errors: " + this.errorBuffers.get(Phase.SYNTACTIC_ANALYSIS).size()+ '\n');
        writer.write("Semantic Errors: " + this.errorBuffers.get(Phase.SEMANTIC_ANALYSIS).size()+ '\n');
        writer.write("CodeGen Errors: " + this.errorBuffers.get(Phase.CODE_GENERATION).size()+ '\n');
        writer.write("Optimization Errors: " + this.errorBuffers.get(Phase.CODE_OPTIMIZATION).size()+ '\n');
        writer.write("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+ "\n\n\n");

    }

    private void writeCompilationErrors(Phase phase, Writer writer) throws IOException {

        if ( this.errorBuffers.get(phase).size() == 0 ) {
            return;
        }

        writer.write("\nCompilation Errors for Phase: " + phase.name() + '\n');

        for (CompilationError error : this.errorBuffers.get(phase)) {
            writer.write(error.toString()+'\n');
        }

    }

    private void writeAstListing(StringBuffer buff, String fileName) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName), "utf-8"))) {
            if (buff != null) {
                System.out.println("Open AST at the follow location:\t" + fileName);
                writer.write(buff.toString());
            }
            // Browse to the following file
           // new Browser().browseTo("index.html");

        } catch (FileNotFoundException e) {
            DebugWriter.writeToFile("ERROR: cannot write to symbol file. \n" + e.getCause());
        }catch (IOException e) {
            DebugWriter.writeToFile("ERROR: cannot write to symbol file. \n" + e.getCause());
        }
    }

    public enum Phase {
        LEXICAL_ANALYSIS,
        SYNTACTIC_ANALYSIS,
        SEMANTIC_ANALYSIS,
        CODE_GENERATION,
        CODE_OPTIMIZATION
    }

}
