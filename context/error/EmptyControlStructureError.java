package context.error;

import context.CompilationContext;
import scanner.tokenizer.Lexeme;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    10/7/2015
 * File Name:       EmptyControlStructureError
 * Project Name:    CD15 Compiler
 * Description:     Defines an error where there is an empty control structure
 */
public class EmptyControlStructureError extends CompilationError {

    private EmptyControlStructureError(CompilationContext.Phase phase, Lexeme offender, Type error) {
        super(phase, offender, error);
    }

    public EmptyControlStructureError(Lexeme lexemeAtPosition) {
        this(CompilationContext.Phase.SYNTACTIC_ANALYSIS, lexemeAtPosition, Type.EMPTY_CONTROL_STRUCTURE);
    }

    /**
     * Push a compilation error onto the buffer
     * @param lex
     */
    public static void record(Lexeme lex) {
        CompilationContext.getContext().bufferCompilationError(CompilationContext.Phase.SYNTACTIC_ANALYSIS,
                new EmptyControlStructureError(lex)
        );
    }

    public String toString() {
        return "Error Line: "
                + this.getOffendingLine()
                + this.getMessage();
    }

}
