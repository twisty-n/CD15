package context.error;

import context.CompilationContext;
import context.error.handlers.ErrorHandlerException;
import parser.Parser;
import scanner.tokenizer.Lexeme;

/**
 * Created by Tristan Newmann on 10/17/2015.
 */
public class IdMismatchError extends CompilationError {

    private String expectedId;
    private String foundId;

    private IdMismatchError(CompilationContext.Phase phase, Type error, Lexemable lex) {
        super(phase, lex, error);
    }

    public IdMismatchError(String expected, String found, Lexemable lex) {
        this(CompilationContext.Phase.SEMANTIC_ANALYSIS, Type.ID_MISMATCH, lex);
        this.expectedId = expected;
        this.foundId = found;
    }

    /**
     * Push a compilation error onto the buffer
     */
    public static void record(String expected, String found, Lexemable lex) {
        CompilationContext.getContext().bufferCompilationError(CompilationContext.Phase.SEMANTIC_ANALYSIS,
                new IdMismatchError(expected, found, lex)
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
                + "Found: \'" +this.foundId + "\'"
                + "\tExpected: \'" + this.expectedId + "\'";
    }

}
