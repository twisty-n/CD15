package context.error;

import context.CompilationContext;
import context.error.handlers.ErrorHandlerException;
import parser.Parser;
import scanner.tokenizer.Lexeme;

/**
 * Created by Tristan Newmann on 10/17/2015.
 */
public class TypeMismatchError extends CompilationError {

    String expected;
    String found;

    private TypeMismatchError(CompilationContext.Phase phase, Lexemable offender, Type error) {
        super(phase, offender, error);
    }

    public TypeMismatchError(String expected, String found, Lexemable offender) {
        this(CompilationContext.Phase.SEMANTIC_ANALYSIS, offender, Type.TYPE_MISMATCH);
        this.expected = expected;
        this.found = found;
    }


    /**
     * Push a compilation error onto the buffer
     * @param lex
     */
    public static void record(String expected, String found, Lexemable lex) {
        CompilationContext.getContext().bufferCompilationError(CompilationContext.Phase.SYNTACTIC_ANALYSIS,
                new TypeMismatchError(expected, found, lex)
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
                + "\tExpected: \'" + this.expected + "\'";
    }

}
