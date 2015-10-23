package context.error;

import context.CompilationContext;
import scanner.tokenizer.Lexeme;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    17/08/15
 * File Name:       CompilationError
 * Project Name:    CD15
 * Description:
 */
public class CompilationError implements Comparable<CompilationError>{

    private CompilationContext.Phase compilationPhase;
    protected Lexemable offender;
    private String errorMessage;

    public enum Type {

        // FATAL
        FATAL("A fatal parsing error occured and could not be recovered", CompilationContext.Phase.SYNTACTIC_ANALYSIS),
        // LEXICAL ERRORS
        UNCLOSED_STRING_LITERAL("Unclosed string literal. Are you missing a \" at the end of your string?", CompilationContext.Phase.LEXICAL_ANALYSIS),
        MALFORMED_INTEGER_LITERAL("Malformed integer value. Integer values should be of the form '0' or '123'",CompilationContext.Phase.LEXICAL_ANALYSIS),
        MALFORMED_FLOAT_LITERAL("Malformed floating point value. Floating point values should be of the form 0.0 or 12.34", CompilationContext.Phase.LEXICAL_ANALYSIS),
        MALFORMED_MLC_OPENER("Malformed multi-line comment opening symbol. Did you mean '+/+'?", CompilationContext.Phase.LEXICAL_ANALYSIS),
        UNKNOWN_CHARACTER("The character or character string is illegal. Did you mean to use a defined operator or delimiter?", CompilationContext.Phase.LEXICAL_ANALYSIS),
        UNCLOSED_MLC("You have an unclosed multi-line comment. Did you forget to delimit your comment block with '+/+'", CompilationContext.Phase.LEXICAL_ANALYSIS),

        // SYNTACTIC ERRORS
        UNEXPECTED_TOKEN("Unexpected token found.", CompilationContext.Phase.SYNTACTIC_ANALYSIS),
        EMPTY_CONTROL_STRUCTURE("Empty loop or control structure. Expected statements", CompilationContext.Phase.SYNTACTIC_ANALYSIS),

        // Semantic errors
        ID_MISMATCH("Closing id doesn't match opening id", CompilationContext.Phase.SEMANTIC_ANALYSIS),
        UNDECLARED_IDENTIFIER("Undeclared Identifier", CompilationContext.Phase.SEMANTIC_ANALYSIS),
        TYPE_MISMATCH("Type mismatch. Unexpected type found.", CompilationContext.Phase.SEMANTIC_ANALYSIS),
        INVALID_CALL("Error calling procedure: ", CompilationContext.Phase.SEMANTIC_ANALYSIS),
        REDEFINITION_OF_ID("The identifer has already beed used in the current scope", CompilationContext.Phase.SEMANTIC_ANALYSIS),
        EXIT_USED_WO_ENCLOSING_LOOP("Exit statement used outside loop target", CompilationContext.Phase.SEMANTIC_ANALYSIS),
        NO_EXIT_STATEMENT("Loop contains no exit statement within root level scope", CompilationContext.Phase.SEMANTIC_ANALYSIS),
        RECURSIVE_CALL("Procedcures cannot make recursive calls", CompilationContext.Phase.SEMANTIC_ANALYSIS),
        ;

        private final String errorMessage;
        private final CompilationContext.Phase phase;
        private Type(String errorMessage, CompilationContext.Phase phase) {
            this.errorMessage = errorMessage;
            this.phase = phase;
        }
        public String getPrettyMessage() { return this.errorMessage; }
        public CompilationContext.Phase getPhase() { return this.phase; }

    }

    /**
     * Push a compilation error onto the buffer
     * @param lex
     */
    public static void record(Lexemable lex, Type type) {
        CompilationContext.getContext().bufferCompilationError(type.getPhase(),
            new CompilationError(type.getPhase(), lex, type)
        );
    }

    protected CompilationError(CompilationContext.Phase phase, Lexemable offender, Type error) {
        this.offender = offender;
        this.compilationPhase = phase;
        this.errorMessage = error.getPrettyMessage();
    }

    public CompilationContext.Phase getPhase() {
        return this.compilationPhase;
    }

    public String getMessage() {
        return this.errorMessage;
    }

    public int getOffendingLine() {
        return this.offender.getLineIndexInFile();
    }

    public int getAproxStartColumn() {
        return this.offender.getStartLineIndex();
    }

    public int getAproxEndColumn() {
        return this.offender.getEndLineIndex();
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
                    + "Error value: \'" + this.offender.getLexemeVal() + "\'";
    }

    /**
     * Orders compilation errors in terms of the most recent error found during compilation
     * Errors found in earlier compilation phases will come first
     * Errors found on earlier lines will come first, and the same as for columns
     * @param compilationError
     * @return
     */
    @Override
    public int compareTo(CompilationError compilationError) {
        // First compare phases
        // Then compare line index
        // then compare column start index
        // then compare column end index
        if ( (this.compilationPhase.compareTo(compilationError.compilationPhase) == 0) ) {
            if ( this.getOffendingLine() == compilationError.getOffendingLine()  ) {
                if (this.getAproxStartColumn() == compilationError.getAproxStartColumn()) {
                    if (this.getAproxEndColumn() == compilationError.getAproxEndColumn()) {
                        return 0;
                    } else {
                        return new Integer(this.getAproxStartColumn()).compareTo(new Integer(compilationError.getAproxStartColumn()));
                    }
                } else {
                    return new Integer(this.getAproxStartColumn()).compareTo(new Integer(compilationError.getAproxStartColumn()));
                }
            } else {
                return new Integer(this.getOffendingLine()).compareTo(new Integer(compilationError.getOffendingLine()));
            }
        } else {
            return this.compilationPhase.compareTo(compilationError.compilationPhase);
        }
    }
}
