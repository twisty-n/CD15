package context.error;

import context.CompilationContext;
import context.error.handlers.ErrorHandlerException;
import context.error.handlers.FatalException;
import scanner.tokenizer.Lexeme;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    10/9/2015
 * File Name:       EOFError
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class EOFError extends CompilationError {

    private EOFError(CompilationContext.Phase phase, Lexeme offender, Type error) {
        super(phase, offender, error);
    }

    public EOFError() {
        this(CompilationContext.Phase.SYNTACTIC_ANALYSIS, null, Type.FATAL);
    }

    /**
     * Push a compilation error onto the buffer
     */
    public static void record()
            throws ErrorHandlerException {
        CompilationContext.getContext().bufferCompilationError(CompilationContext.Phase.SYNTACTIC_ANALYSIS,
                new EOFError()
        );
        throw new FatalException();
    }

    public String toString() {
        return "Reached end of file while parsing";
    }

}
