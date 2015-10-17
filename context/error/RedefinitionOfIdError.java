package context.error;

import context.CompilationContext;

/**
 * Created by Tristan Newmann on 10/17/2015.
 */
public class RedefinitionOfIdError extends CompilationError {

    private String id;
    private String scope;
    private Lexemable lex;

    private RedefinitionOfIdError(CompilationContext.Phase phase, Type error, Lexemable lex) {
        super(phase, lex, error);
    }

    public RedefinitionOfIdError(String id, String scope, Lexemable lex) {
        this(CompilationContext.Phase.SEMANTIC_ANALYSIS, Type.REDEFINITION_OF_ID, lex);
        this.lex = lex;
        this.scope = scope;
        this.id = id;
    }

    /**
     * Push a compilation error onto the buffer
     */
    public static void record(String id, String scope, Lexemable lex) {
        CompilationContext.getContext().bufferCompilationError(CompilationContext.Phase.SEMANTIC_ANALYSIS,
                new RedefinitionOfIdError(id, scope, lex)
        );
    }

    public String toString() {
        return
                this.getMessage()
                    + "\tIdentifier: " + this.id
                    + "\tScope:" + this.scope;
    }

}
