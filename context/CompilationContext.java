package context;

import config.CompilerConfig;
import context.error.CompilationError;
import io.ReturnCharacter;
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

    public CompilationContext(String sourceFile) {
        this.sourceFile = sourceFile;
        this.errorBuffers = new HashMap<>();
        this.outputBuffer = new StringBuffer();
        this.totalErrorCount = 0;
        this.lineCount = 0;
        errorBuffers.put(Phase.LEXICAL_ANALYSIS, new ArrayList<CompilationError>());
        errorBuffers.put(Phase.SYNTACTIC_ANALYSIS, new ArrayList<CompilationError>());
        errorBuffers.put(Phase.SEMANTIC_ANALYSIS, new ArrayList<CompilationError>());
        errorBuffers.put(Phase.CODE_GENERATION, new ArrayList<CompilationError>());
        errorBuffers.put(Phase.CODE_OPTIMIZATION, new ArrayList<CompilationError>());
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
        String fileName = this.sourceFile + "_compilation-listing.txt";

                                    /*      NASTY HAX ZONE      */
        // *************************************************************************************************************
        if (CompilerConfig.IS_ASSIGNMENT1) {                                                                     //    *
                                                                                                                 //    *
            // Write shit to STD OUT as well                                                                     //    *
            // HAX ALERT I just cut an paste                                                                     //    *
            try    {                                                                                             //    *
                Writer writer = new BufferedWriter(new OutputStreamWriter(System.out));                          //    *
                                                                                                                 //    *
                this.writeCompilationSummary(writer);                                                            //    *
                writer.write(this.outputBuffer.toString());                                                      //    *
                for (Phase phase : Phase.values()) {                                                             //    *
                    this.writeCompilationErrors(phase, writer);                                                  //    *
                }                                                                                                //    *
                writer.flush();                                                                                  //    *
            } catch (FileNotFoundException e) {                                                                  //    *
                DebugWriter.writeToFile("ERROR: cannot write to program listing file. \n" + e.getCause());       //    *
            }catch (IOException e) {                                                                             //    *
                DebugWriter.writeToFile("ERROR: cannot write to program listing file. \n" + e.getCause());       //    *
            }                                                                                                    //    *
                                                                                                                 //    *
        }                                                                                                        //    *
        // *************************************************************************************************************

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName), "utf-8"))) {
            this.writeCompilationSummary(writer);
            writer.write(this.outputBuffer.toString());
            for (Phase phase : Phase.values()) {
                this.writeCompilationErrors(phase, writer);
            }

        } catch (FileNotFoundException e) {
            DebugWriter.writeToFile("ERROR: cannot write to program listing file. \n" + e.getCause());
        }catch (IOException e) {
            DebugWriter.writeToFile("ERROR: cannot write to program listing file. \n" + e.getCause());
        }

        CompilationContext.currentContext = null;

    }

    private void writeCompilationSummary(Writer writer) throws IOException{

        writer.write("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        writer.write("Compilation Listing.\nSource File: " + this.sourceFile + '\n');
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

    public enum Phase {
        LEXICAL_ANALYSIS,
        SYNTACTIC_ANALYSIS,
        SEMANTIC_ANALYSIS,
        CODE_GENERATION,
        CODE_OPTIMIZATION
    }

}
