package context.error;

import context.CompilationContext;

/**
 * Created by Tristan Newmann on 10/17/2015.
 */
public class InvalidProcCallError extends CompilationError {

    private String helpMessage;
    private String procName;
    private Lexemable lex;

    private InvalidProcCallError(CompilationContext.Phase phase, Type error, Lexemable lex) {
        super(phase, lex, error);
    }

    public InvalidProcCallError(String fullMessage, Lexemable lex) {
        this(CompilationContext.Phase.SEMANTIC_ANALYSIS, Type.INVALID_CALL, lex);
        this.helpMessage = fullMessage;
        this.lex = lex;
        this.procName = lex.getLexemeVal();
    }

    /**
     * Push a compilation error onto the buffer
     */
    public static void record(String helpMessage, Lexemable lex) {
        CompilationContext.getContext().bufferCompilationError(CompilationContext.Phase.SEMANTIC_ANALYSIS,
                new InvalidProcCallError(helpMessage, lex)
        );
    }

    public String toString() {
        return
                 this.getMessage() + procName + '\t'
                + this.helpMessage;
    }

}
