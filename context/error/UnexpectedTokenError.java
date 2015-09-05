package context.error;

import context.CompilationContext;
import scanner.tokenizer.Lexeme;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/5/2015
 * File Name:       UnexpectedTokenError
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class UnexpectedTokenError extends CompilationError {

    private String expected;

    private UnexpectedTokenError(CompilationContext.Phase phase, Lexeme offender, Type error) {
        super(phase, offender, error);
    }

    public UnexpectedTokenError(String expected, Lexeme offender) {
        this(CompilationContext.Phase.SYNTACTIC_ANALYSIS, offender, Type.UNEXPECTED_TOKEN);
        this.expected = expected;
    }

    /**
     * Push a compilation error onto the buffer
     * @param lex
     */
    public static void record(String expected, Lexeme lex) {
        CompilationContext.getContext().bufferCompilationError(CompilationContext.Phase.SYNTACTIC_ANALYSIS,
                new UnexpectedTokenError(expected, lex)
        );
    }

    public String toString() {
        return "Error Line: "
                + this.getOffendingLine()
                + "\tError Starting Column: "
                + this.getAproxStartColumn()
                + "\tError Ending Column: "
                + this.getAproxEndColumn()
                + '\t'
                + this.getMessage() + '\t'
                + "Found: \'" + this.offender.getLexemeVal() + "\'"
                + "Expected: \'" + this.expected + "\'";
    }

}
