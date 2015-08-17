package context;

import context.error.CompilationError;

import java.util.ArrayList;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    17/08/15
 * File Name:       CompilationContext
 * Project Name:    CD15
 * Description:
 */
public abstract class CompilationContext {

    private  CompilationContext currentContext;
    protected ArrayList<CompilationError> errorBuffer;

    /**
     * Sets the current phase that the compiler is in
     * @param compilationPhase
     */
    public final void setCurrentContext(Phase compilationPhase) {

        this.currentContext.close();
        switch (compilationPhase) {

            case LEXICAL_ANALYSIS: {
                //this.currentContext = new ScanningContext();
                break;
            }

            case SYNTACTIC_ANALYSIS: {
                //this.currentContext = new ParsingContext();
                break;
            }
        }

        // Open the new Context
        this.currentContext.start();

    }

    public abstract void close();
    public abstract void start();


    public enum Phase {
        LEXICAL_ANALYSIS,
        SYNTACTIC_ANALYSIS,
        SEMANTIC_ANALYSIS,
        CODE_GENERATION,
        CODE_OPTIMIZATION
    }

}
