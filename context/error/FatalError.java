package context.error;

import context.CompilationContext;
import context.error.handlers.ErrorHandlerException;
import scanner.tokenizer.Lexeme;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    10/9/2015
 * File Name:       FatalError
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class FatalError extends CompilationError {

    String errorInformation;

    private FatalError(CompilationContext.Phase phase, Lexeme offender, Type error) {
        super(phase, offender, error);
    }

    public FatalError(Lexeme lexemeAtPosition, String errorInformation) {
        this(CompilationContext.Phase.SYNTACTIC_ANALYSIS, lexemeAtPosition, Type.FATAL);
        this.errorInformation = errorInformation;
    }

    /**
     * Push a compilation error onto the buffer
     * @param lex
     */
    public static void record(Lexeme lex, String errorInformation, Handler errorHandler)
        throws ErrorHandlerException {
        CompilationContext.getContext().bufferCompilationError(CompilationContext.Phase.SYNTACTIC_ANALYSIS,
                new FatalError(lex, errorInformation)
        );
        errorHandler.handle(null);
    }

    public String toString() {
        return "Error Line: "
                + this.getOffendingLine()
                + "\nError information: " + this.errorInformation;
    }

}
