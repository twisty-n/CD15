package parser;


import context.CompilationContext;
import context.error.*;
import context.error.handlers.*;
import context.symbolism.Property;
import context.symbolism.Query;
import context.symbolism.STRecord;
import context.symbolism.SymbolTable;
import parser.ast.TreeNode;
import parser.ast.nodes.*;
import scanner.Scanner;
import scanner.tokenizer.Token;
import scanner.tokenizer.TokenClass;
import utils.DebugWriter;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashMap;
import java.util.Map;


/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/4/2015
 * File Name:       Parser
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class Parser {

    private Token currentToken;
    private Scanner scanner;

    private Context context;

    private SymbolTable symbolTable;

    private class Context {

        public int procDeclParams;
        public int callArgumentParams;
        public int procVarParams;
        public int procValParams;

        public String currentProc;

        public String scope;
        public Parser owner;

        public final String global = "global";

        /**
         * Will create a default context
         * The default scope will be progam
         * @param owner
         */
        public Context(Parser owner) {
            this.procDeclParams = 0;
            this.procVarParams = 0;
            this.procValParams = 0;
            this.callArgumentParams = 0;
            this.resetCallContext();
            this.scope = "global";
            this.owner = owner;
        }

        /**
         * Destroys the current context and creates a new one
         */
        public void reset() {
            owner.context = new Context(owner);
        }

        public void resetCallContext() {
            this.callArgumentParams = 0;
        }
    }

    /**
     * We need the scanner in order to parse
     * The rest of the things we need are attached to
     * the compilation context
     * @param scanner
     */
    public Parser(Scanner scanner) {
        this.scanner = scanner;
        this.currentToken = scanner.getNextValidToken();
        this.context = new Context(this);
        symbolTable = CompilationContext.Context.SymbolTable;
    }

    public TreeNode parse() {
        return program();
    }

    public TokenClass currentTokenClass() { return this.currentToken.getTokenClass(); }
    public String currentTokenLexeme() {
        return this.currentToken.getProperLexeme().getLexemeVal().toLowerCase();
    }

    /**
     * Sets the current token to be the next token from the scanner
     */
    public void nextToken() {
        this.currentToken = scanner.getNextValidToken();
    }

    /**
     * Checks the current token to see if the classes match
     * if so, will obtain the next token and return true
     * else will leave current token alone and return false
     * @param tokenClass
     * @return
     */
    protected boolean isCurrentToken(TokenClass tokenClass, boolean reportError, Handler errorHandler)
            throws ErrorHandlerException{
        if (tokenClass.equals(currentToken.getTokenClass())) {
            nextToken();
            return true;
        }
        // No match. Whine. Loudly
        if (reportError) {
            // We will only handle simple missing keywords
            // in this block
            try {
                UnexpectedTokenError.record(tokenClass.val(), currentToken.getProperLexeme(), errorHandler, this);
            } catch(SimpleMissingTokenException sem) {
                sem.recover();
            }
        }
        return false;
    }

    /**
     * Checks the current token to see if the classes match
     * if so, will obtain the next token and return true
     * else will leave current token alone and return false
     *
     * This should be used to check tokens where its not an error if the token type is not found
     * @param tokenClass
     * @return
     */
    protected boolean isCurrentToken(TokenClass tokenClass) {
        if (tokenClass.equals(currentToken.getTokenClass())) {
            nextToken();
            return true;
        }
        return false;
    }

    /**
     * Checks the current token to see if the classes match
     * if so, will store an STRecord andobtain the next token and return true
     * else will leave current token alone and return false
     * @param tokenClass
     * @return
     */
    protected STRecord matchCurrentAndStoreRecord(TokenClass tokenClass, Handler errorHandler) throws ErrorHandlerException {
        if (tokenClass.equals(currentToken.getTokenClass())) {
            STRecord rec = currentToken.getSymbol();
            nextToken();
            return rec;
        }
        UnexpectedTokenError.record(tokenClass.val(), currentToken.getProperLexeme(), errorHandler, this);
        return null;
    }

    /**
     * Returns the completed AST for the Program
     * or null if parsing fatally failed
     * @return
     * program <id> <arrays> <procs> <main> end program <id>
     */
    protected TreeNode program() {
        try {

            TreeNode arrays = null;
            TreeNode procs = null;
            TreeNode main = null;

            isCurrentToken(TokenClass.TPROG, true, Handler.OPENING_PROG_KW);
            STRecord openeningId  =  matchCurrentAndStoreRecord(TokenClass.TIDNT, Handler.PROG_ID_DECL);

            try {
                arrays = arrays();
            } catch (SyncOnProcLocalOrStat ex) {ex.recover();}
            procs = procs();
            main = main();

            isCurrentToken(TokenClass.TENDK, true, Handler.END_PROG_KW);
            isCurrentToken(TokenClass.TPROG, true, Handler.END_PROG_END_KW);
            STRecord closingId = matchCurrentAndStoreRecord(TokenClass.TIDNT, Handler.END_PROG_ID);

            if(!closingId.equals(openeningId)) {
                IdMismatchError.record(openeningId.getLexemeString(), closingId.getLexemeString(), closingId);
            }

            return new Program(arrays, procs, main);
        } catch (ErrorHandlerException ehe) {
           // CompilationError.record(currentToken.getProperLexeme(), CompilationError.Type.FATAL);
            // There is nothing that we can do if we've already recorded the error
        }

        // In extreme cases, we will do full abort
        return null;
    }

    /**
     *
     *  <arrays> ::= arrays <arraydecl> <arrdltail>
     * @return
     */
    protected TreeNode arrays() throws ErrorHandlerException{
        // Match empty
        if (!isCurrentToken(TokenClass.TARRS)) {
            return null;
        }
        // Otherwise we have a set of valid arrays to work with

        // Get an array decl
        TreeNode dec = arrayDeclaration();
        TreeNode rest = arrayTail();

        // If we didn't find any other declarations
        if ( rest == null ) {
            return dec;
        }

        // Otherwise return the thing back up
        return new ArrayDecList(dec, rest);
    }


    // NARRYL <arrdltail> ::= , <arraydecl> <arrdltail>
    // Special <arrdltail> ::= ?
    protected TreeNode arrayTail() throws ErrorHandlerException{
        // If not a comma, we've finished the array declarations
        if(!isCurrentToken(TokenClass.TCOMA)) {
            return null;
        }
        // Else we've seen a comma
        //So match an array declaration, set to left,
        TreeNode dec = arrayDeclaration();
        // Match an arrayTail. Set to right
        TreeNode rest = arrayTail();

        // If this is the last one, just return the dec
        if (rest == null) {
            return dec;
        }

        return new ArrayDecList(dec, rest); // Set left and right
    }

    // NARRDEC <arraydecl> ::= <id> [ <intlit> ]
    protected TreeNode arrayDeclaration() throws ErrorHandlerException{
        // Match an ID
        TreeNode decl = new ArrayDeclaration();
        decl.setName(matchCurrentAndStoreRecord(TokenClass.TIDNT, Handler.ARRAY_DECL_ID));

        // Check for redeclaration
        if (decl.getName().existsInScope(context.global)) {
            RedefinitionOfIdError.record(decl.getName().getLexemeString(), context.global, decl.getName());
        }

        decl.getName().addProperty("type", "global", new Property("array"));
        // Match a left brace
        isCurrentToken(TokenClass.TLBRK, true, Handler.ARRAY_LBRK);
        // Match an IntLit
        decl.setType(matchCurrentAndStoreRecord(TokenClass.TILIT, Handler.ARRAY_LENGTH));
        // Match a Right brace
        isCurrentToken(TokenClass.TRBRK, true, Handler.ARRAY_RBRK);

        return decl;
    }

    //--NMAIN <main> ::= local <idlist> ; <stats>
    protected Main main() throws ErrorHandlerException{
        TreeNode localVars = null;
        try {
            isCurrentToken(TokenClass.TLOCL, true, Handler.PROGRAM_LOCAL_DECL);
            localVars = localVariables();
            isCurrentToken(TokenClass.TSEMI, true, Handler.LOCAL_DECL_SEMI);    // Handled in call
        } catch (SyncAtStatementKeywordException e) {
            // Increment along unil we find a stat keyword thats not an identifier
            // We will only recover to the nearest control structure, not assignment statement
            e.recover();
        }
        TreeNode statementList = statements();

        // Return the built main node
        Main main = new Main(localVars, statementList);
        return main;
    }

    // Returns a NDLIST OR a single local if there is only one
    //    NSIMDEC <idlist> ::= <id> <idltail>
    protected TreeNode localVariables() throws ErrorHandlerException {

        // Build an ID: Match the ID, store it
        LocalDeclaration local = new LocalDeclaration();
        STRecord var = matchCurrentAndStoreRecord(TokenClass.TIDNT, Handler.LOCAL_DECL_ID);

        if (var.existsInScope(context.global)) {
            RedefinitionOfIdError.record(var.getLexemeString(), context.global, var);
        }

        local.setName(var);
        var.addProperty("type", context.global, new Property("number"));
        var.addProperty("initialiazed", context.global, new Property(false));

        // Assign a type

        // Call localVariablesTail
        LocalDecList restOftheVars = new LocalDecList();
        TreeNode tail = localVarsTail();
        if (tail == null) {
            return local; // The actual declaration
        }
        // Dont forget to set the symbol table record
        restOftheVars.setLeft(local);
        restOftheVars.setRight(tail);
        return restOftheVars;
    }

    //    NIDLST <idltail> ::= , <idlist>
    protected TreeNode localVarsTail() throws ErrorHandlerException{
        // Check a comma, if it is, continue
        if (!isCurrentToken(TokenClass.TCOMA)) {
            return null;
        }
        return localVariables();
    }

    // NPROC <proc> ::= proc <id> <parameters> <locals> <stats> end proc <id>
    protected ProcedureDeclaration procedure() throws ErrorHandlerException{
        // Match proc
        context.reset();
        ProcedureDeclaration dec = new ProcedureDeclaration();
        if(!isCurrentToken(TokenClass.TPROC)) {
            // If we dont see a proc, assume that there are none
            return null;
        }

        TreeNode procedureParams = null;
        TreeNode procedureLocals = null;
        TreeNode procedureStatements = null;

        try {
            dec.setName(matchCurrentAndStoreRecord(TokenClass.TIDNT, Handler.PROC_DECL_ID));

            context.scope = dec.getName().getLexemeString();
            if (dec.getName().existsInScope(context.global)) {
                RedefinitionOfIdError.record(dec.getName().getLexemeString(), context.global, dec.getName());
            }

            dec.getName().addProperty("type", context.global, new Property("proc"));
            dec.getName().addProperty("declared", context.global, new Property(true));
            // Call proc_params
            procedureParams = procedureParams();
        } catch(SyncOnProcLocalOrStat se) {
            se.recover();
        }

        try {
            procedureLocals = procedureLocals();
        } catch (SyncAtStatementKeywordException se) {
            // Recover until we hit a statement keyword
            se.recover();
        }

        procedureStatements = statements();

        isCurrentToken(TokenClass.TENDK, true, Handler.PROC_DECL_END);
        isCurrentToken(TokenClass.TPROC, true, Handler.PROC_DECL_END_PROC);
        STRecord closingId = matchCurrentAndStoreRecord(TokenClass.TIDNT, Handler.PROC_DECL_ID_END);  // <<-- Check for sem anal

        if(!dec.getName().equals(closingId)) {
            IdMismatchError.record(dec.getName().getLexemeString(), closingId.getLexemeString(), closingId);
        }

        dec.setLeft(procedureParams);
        dec.setMiddle(procedureLocals);
        dec.setRight(procedureStatements);

        dec.getName().addProperty("numberOfParams", context.global, new Property(context.procDeclParams));
        dec.getName().addProperty("numVarParams", context.global, new Property(context.procVarParams));

        context.reset();
        return dec;
    }

    // NPARAMS <parameters> ::= var <plist> <paramstail>
    // NPARAMS <parameters> ::= val <pidlist>
    // Special <parameters> ::= ?
    protected TreeNode procedureParams() throws ErrorHandlerException{

        // Proc can have var | val | Epsilon to start with
        TokenClass tokenClass = currentToken.getTokenClass();
        if (!tokenClass.equals(TokenClass.TVALP) && !tokenClass.equals(TokenClass.TVARP)) {
            // If its not var or val, there are no params
            return null;
        }

        // It must be VAR or VAL
        if (isCurrentToken(TokenClass.TVARP)) {
            // Have a set of var params
            // Call plist
            TreeNode varParams = procVarParams();
            TreeNode valParams = paramsTail();
            return new ProcParameters(varParams, valParams);
            // Call paramstail
        } else {
            // Call PID list
            isCurrentToken(TokenClass.TVALP, true, Handler.PARAMS_VALP_ALONE);
            ProcParameters params = new ProcParameters();
            params.setMiddle(pIdList());
            return params;
        }
    }

    // Special <paramstail> ::= val <pidlist>
    // Special <paramstail> ::= ?
    protected TreeNode paramsTail() throws ErrorHandlerException {
        if(!isCurrentToken(TokenClass.TVALP)) {
            // There are no val params
            return null;
        }
        // Otherwise, match them up
        return pIdList();
    }

    // NSIMPAR <pidlist> ::= <id> <pidltail>
    protected TreeNode pIdList() throws ErrorHandlerException{
        // Match an id
        STRecord id = matchCurrentAndStoreRecord(TokenClass.TIDNT, Handler.PARAMS_ID);

        if (id.existsInScope(context.scope)) {
            RedefinitionOfIdError.record(id.getLexemeString(), context.scope, id);
        }
        TreeNode simPar = new SimpleParameter();
        simPar.setName(id);
        id.addProperty("type", context.scope, new Property("number"));
        context.procDeclParams++;
        context.procValParams++;
        // Call pIdLtail
        TreeNode restOfTheParams = pIdLTail();
        if (restOfTheParams == null) {
            return simPar;
        } else {
            TreeNode paramList = new ParameterList();
            paramList.setLeft(simPar);
            paramList.setRight(restOfTheParams);
            return paramList;
        }
    }


    // NPLIST <pidltail> ::= , <pidlist>
    // Special <pidltail> ::= ?
    protected TreeNode pIdLTail() throws ErrorHandlerException{
        if(!isCurrentToken(TokenClass.TCOMA)) {
            return null;
        }
        return pIdList();
    }

    // <plist> ::= <param> <plisttail>
    protected TreeNode procVarParams() throws ErrorHandlerException{
        TreeNode param = varParam();
        TreeNode restOfTheParams = procVarList();
        if (restOfTheParams == null) {
            return param;
        }
        TreeNode paramList = new ParameterList();
        paramList.setLeft(param);
        paramList.setRight(restOfTheParams);
        return paramList;
    }

    protected TreeNode procVarList() throws ErrorHandlerException{
        if(!isCurrentToken(TokenClass.TCOMA)) {
            return null;
        }
        return procVarParams();
    }

    // Special <param> ::= <id> <paramtail>
    // NSIMPAR <paramtail> ::= ?
    // NARRPAR <paramtail> ::= [ ]
    protected TreeNode varParam() throws ErrorHandlerException{
        STRecord id = matchCurrentAndStoreRecord(TokenClass.TIDNT, Handler.PARAMS_ID);
        if (id.existsInScope(context.scope)) {
            RedefinitionOfIdError.record(id.getLexemeString(), context.scope, id);
        }
        context.procDeclParams++;
        context.procVarParams++;
        id.addProperty("orderInParams", context.scope, new Property(context.procDeclParams)); // Record which param we are looking at
        if (isCurrentToken(TokenClass.TLBRK)) {
            isCurrentToken(TokenClass.TRBRK, true, Handler.PARAMS_ARR_RBRK);
            // We just matched an ArrayParam
            ArrayParameter arrPam = new ArrayParameter();
            arrPam.setName(id);
            id.addProperty("paramType", context.scope, new Property("array"));
            return arrPam;
        } else {
            // We matched a simple param
            SimpleParameter simParam = new SimpleParameter();
            simParam.setName(id);
            id.addProperty("paramType", context.scope, new Property("number"));
            return simParam;
        }
    }

    // Special <locals> ::= local <decllist> ; | ?
    protected TreeNode procedureLocals() throws ErrorHandlerException{
        // Match local
        if(!isCurrentToken(TokenClass.TLOCL)) {
            // There were no locals
            return null;
        }
        // Call and return decllist
        TreeNode locals = declList();
        isCurrentToken(TokenClass.TSEMI, true, Handler.PROC_LOCAL_END_SEMI);
        return locals;
    }


    // Special <decllist> ::= <decl> <dltail>
    protected TreeNode declList() throws ErrorHandlerException{
        TreeNode declaration = decl();
        TreeNode restOfTheDeclarations = dlTail();
        if (restOfTheDeclarations == null) {
            return declaration;
        }
        // Other wise, return up the built list
        ProcLocals locals = new ProcLocals();
        locals.setLeft(declaration);
        locals.setRight(restOfTheDeclarations);
        return locals;
    }

    // NDLIST <dltail> ::= , <decllist>
    // Special <dltail> ::= ?
    protected TreeNode dlTail() throws ErrorHandlerException{
        // Match a comma or nothing
        if(!isCurrentToken(TokenClass.TCOMA)) {
            return null;
        }
        // call declList
        return declList();
    }

    // Special <decl> ::= <id> <dtail>
    protected TreeNode decl() throws ErrorHandlerException {
        STRecord id = matchCurrentAndStoreRecord(TokenClass.TIDNT, Handler.PROC_LOCAL_ID);
        if (id.existsInScope(context.scope)) {
            RedefinitionOfIdError.record(id.getLexemeString(), context.scope, id);
        }
        // We store the Id in the thing that comes back to us from dtail
        TreeNode thing = dTail(id);
        thing.setName(id);
        return thing;
    }

    // NARRDEC <dtail> ::= [ <intlit> ]
    // NSIMDEC <dtail> ::= ?
    protected TreeNode dTail(STRecord id) throws ErrorHandlerException{
        if (isCurrentToken(TokenClass.TLBRK)) {
            STRecord arrayLength = matchCurrentAndStoreRecord(TokenClass.TILIT, Handler.PROC_LOCAL_ARR_LENGTH);
            if (isCurrentToken(TokenClass.TRBRK, true, Handler.PROC_LOCAL_ARR_RBRK)) {
                id.addProperty("type", context.scope, new Property("array"));
                return new ArrayDeclaration().setType(arrayLength);
            }
        }
        id.addProperty("type", context.scope, new Property("number"));
        return new LocalDeclaration();
    }

    // NPROCL <procs> ::= <proc> <procs>
    protected TreeNode procs() throws ErrorHandlerException{
        // Call procedure
        ProcedureDeclaration dec = procedure();
        if (dec == null) {
            return null;
        }
        TreeNode restOfTheProcedures = procs();
        if (restOfTheProcedures == null) {
            return dec;
        }
        // Other wise
        ProcDecList procList = new ProcDecList(dec, restOfTheProcedures);
        return procList;
    }



    // Special <stat> ::= <loopstat> | <exitstat> | <ifstat>
    // Special <stat> ::= <asgnstat> | <iostat> | <callstat>

    // TODO: handle catching at the statement level

    protected TreeNode statement() throws ErrorHandlerException{
        // Call and return some statement based on whatever we've seen
        switch (currentToken.getTokenClass()) {
            case TLOOP: return loopStatement();
            case TEXIT: return exitStatement();
            case TIFKW: return ifStatement();
            case TPRIN: // Cascade
            case TPRLN: // Cascade
            case TINPT: return ioStatement();
            case TCALL: return callStatement();
            case TIDNT: return assignmentStatement();
            default:
        }
        // Will happen if the program contains no statements
        FatalError.record(this.currentToken.getProperLexeme(), "Expected a statement but didn't find one", Handler.FATAL);
        return null;
    }

    // In this case, just recycle the method after checking that we still have statements
    // NSLIST <sltail> ::= <stat> <sltail>
    // Special <sltail> ::= ?
    protected TreeNode slTail() throws ErrorHandlerException{
        // if the current token thing doesn't match a statement, or id kill it
        if (!currentToken.getTokenClass().isStatementKeyword()
                && !currentToken.getTokenClass().isIdentifier()) {
            return null;
        }
        return statements();
    }

    // Special <stats> ::= <stat> <sltail>
    protected TreeNode statements() throws ErrorHandlerException {
        TreeNode statement = null;
        try {
            statement = statement();
        } catch (SyncAtStatementKeywordException e) {
            e.recover();
        }

        // Call sltail
        TreeNode statementList = slTail();
        if (statementList == null) {
            return statement;
        }
        return new StatementList(statement, statementList);
    }

    // NLOOP <loopstat> ::= loop <id> <stats> end loop <id>
    protected TreeNode loopStatement() throws ErrorHandlerException{
        isCurrentToken(TokenClass.TLOOP); // It really should be loop
        STRecord loopId = matchCurrentAndStoreRecord(TokenClass.TIDNT, Handler.LOOP_DECL_ID);
        loopId.addProperty("type", context.scope, new Property("loop"));
        loopId.addProperty("inLoop", context.scope, new Property(true));
        TreeNode loopStatements = statements();
        // If there are no statements we have an error
        if (loopStatements == null) {
            EmptyControlStructureError.record(currentToken.getProperLexeme());
        }
        isCurrentToken(TokenClass.TENDK, true, Handler.LOOP_END_KW);
        isCurrentToken(TokenClass.TLOOP, true, Handler.LOOP_END_LOOP_KW);
        STRecord closingId = matchCurrentAndStoreRecord(TokenClass.TIDNT, Handler.LOOP_END_ID); // Sem anal here

        if(!loopId.equals(closingId)) {
            IdMismatchError.record(loopId.getLexemeString(), closingId.getLexemeString(), closingId);
        }

        TreeNode loop = new Loop();
        loop.setMiddle(loopStatements);
        loop.setName(loopId);
        if (!new Boolean(true).equals(loopId.getPropertyValue("hasExitStatement", context.scope, Boolean.class))) {
            CompilationError.record(loopId, CompilationError.Type.NO_EXIT_STATEMENT);
        }
        loopId.addProperty("inLoop", context.scope, new Property(false));
        return loop;
    }

    // NEXIT <exitstat> ::= exit <id> when <bool> ;
    protected TreeNode exitStatement() throws ErrorHandlerException{
        isCurrentToken(TokenClass.TEXIT); // Matched by virtue of being here
        STRecord exitIdentifer = matchCurrentAndStoreRecord(TokenClass.TIDNT, Handler.EXIT_LOOP_ID);
        if (!exitIdentifer.existsInScope(context.scope)) {
           CompilationError.record(exitIdentifer, CompilationError.Type.UNDECLARED_IDENTIFIER);
        }
        if (!"loop".equals(exitIdentifer.getPropertyValue("type", context.scope, String.class))) {
            TypeMismatchError.record("loop", exitIdentifer.getPropertyValue("type",
                    context.scope, String.class), exitIdentifer);
        }
        if (! new Boolean(true).equals(exitIdentifer.getPropertyValue("inLoop", context.scope, Boolean.class))) {
            CompilationError.record(exitIdentifer, CompilationError.Type.EXIT_USED_WO_ENCLOSING_LOOP);
        }
        exitIdentifer.addProperty("hasExitStatement", context.scope, new Property(true));
        isCurrentToken(TokenClass.TWHEN, true, Handler.EXIT_WHEN_KW);
        TreeNode exit = new Exit().setMiddle(bool()).setName(exitIdentifer);
        isCurrentToken(TokenClass.TSEMI, true, Handler.EXIT_SEMI);
        return exit;
    }

    // Special <ifstat> ::= if <bool> then <stats> <iftail>
    protected TreeNode ifStatement() throws ErrorHandlerException{
        isCurrentToken(TokenClass.TIFKW); // Implcit check
        TreeNode condition = bool();
        isCurrentToken(TokenClass.TTHEN, true, Handler.IF_THEN_KW);
        TreeNode statements = statements();
        return ifTail().setLeft(condition).setMiddle(statements);
    }

    // If statement stuff, get to in a second
    // NIFT <iftail> ::= end if
    // NIFTE <iftail> ::= else <stats> end if
    // NIFTE <iftail> ::= elsif <bool> then <stats> <elsiftail>
    protected TreeNode ifTail() throws ErrorHandlerException{
        switch (currentToken.getTokenClass()) {
            case TENDK: {
                isCurrentToken(TokenClass.TENDK); // No check
                isCurrentToken(TokenClass.TIFKW, true, Handler.IF_IF_KW);
                return new SingleIf();
            }
            case TELSE: {
                isCurrentToken(TokenClass.TELSE); // No check
                TreeNode elseStatements = statements();
                isCurrentToken(TokenClass.TENDK, true, Handler.IF_END_KW); // recycle from above
                isCurrentToken(TokenClass.TIFKW, true, Handler.IF_IF_KW); // recycle from above
                // Set to right because we are setting the middle and left above
                return new IfThenElseConstruct().setRight(elseStatements);
            }
            case TELSF: {
                isCurrentToken(TokenClass.TELSF); // No check
                TreeNode condition = bool();
                isCurrentToken(TokenClass.TTHEN, true, Handler.IF_THEN_KW);
                TreeNode statements = statements();
                return new IfThenElseConstruct()
                        .setRight(
                            new IfThenElseConstruct(condition, statements, elsIfTail())
                        );
            }
            default: {
                UnexpectedTokenError.record("end || else || elsf", currentToken.getProperLexeme(), Handler.IF_ENDING_KEYWORD, this);
                return null; // Never reach!
            }
        }
    }

    // NIFTE <elsiftail> ::= elsif <bool> then <stats> <elsiftail>
    // Special <elsiftail> ::= else <stats> end if
    protected TreeNode elsIfTail() throws ErrorHandlerException{
        switch (currentToken.getTokenClass()) {
            case TELSE: {
                isCurrentToken(TokenClass.TELSE); // No check
                TreeNode statements = statements();
                isCurrentToken(TokenClass.TENDK, true, Handler.IF_END_KW); // recycle from above
                isCurrentToken(TokenClass.TIFKW, true, Handler.IF_IF_KW); // recycle from above
                return statements;
            }
            case TELSF: {
                isCurrentToken(TokenClass.TELSF); // No check
                TreeNode bool = bool();
                isCurrentToken(TokenClass.TTHEN, true, Handler.IF_THEN_KW); // recycle from above
                TreeNode statements = statements();
                return new IfThenElseConstruct()
                        .setLeft(bool)
                        .setMiddle(statements)
                        .setRight(elsIfTail());
            }
            default: {
                UnexpectedTokenError.record("else || elsf", currentToken.getProperLexeme(), Handler.IF_ENDING_KEYWORD, this);
                return null;
            }
        }
    }


    // Special <asgnstat> ::= <var> <asgnop> <expr> ;
    protected TreeNode assignmentStatement() throws ErrorHandlerException{
        TreeNode variable = var();
        TreeNode assignmentOperation = asgnop();
        TreeNode value = expr();
        isCurrentToken(TokenClass.TSEMI, true, Handler.ASSIGN_STAT_SEMI);
        return assignmentOperation.setLeft(variable).setRight(value);

    }

    // NASGN, NPLEQ, NMNEQ, NSTEQ, NDVEQ
    // <asgnop> ::= = | += | -= | *= | /=
    protected TreeNode asgnop() throws ErrorHandlerException{
        switch(currentToken.getTokenClass()) {
            case TASGN:     isCurrentToken(TokenClass.TASGN);return new AssignmentOperation(); // No check
            case TPLEQ:     isCurrentToken(TokenClass.TPLEQ); return new PlusEqual();          // No check
            case TMNEQ:     isCurrentToken(TokenClass.TMNEQ); return new MinusEqual();         // No check
            case TMLEQ:     isCurrentToken(TokenClass.TMLEQ); return new TimesEqual();        // No check
            case TDVEQ:     isCurrentToken(TokenClass.TDVEQ); return new DivideEqual();       // No check
            default:        {
                UnexpectedTokenError.record("assignment operator", currentToken.getProperLexeme(), Handler.ASSIGN_OP, this);
                return null;
            }
        }
    }

    // NINPUT <iostat> ::= input <vlist> ;
    // NPRINT <iostat> ::= print <prlist> ;
    // NPRLN  <iostat> ::= printline <printail>
    protected TreeNode ioStatement() throws ErrorHandlerException{
        switch (currentToken.getTokenClass()) {
            case TPRIN: {
                // print
                isCurrentToken(TokenClass.TPRIN);         // No check
                TreeNode io = new Print().setMiddle(prList());
                isCurrentToken(TokenClass.TSEMI, true, Handler.IO_STAT_SEMI);
                return io;
            }
            case TPRLN: {
                isCurrentToken(TokenClass.TPRLN);         // No check
                TreeNode io = new PrintLine().setMiddle(prinTail());
                isCurrentToken(TokenClass.TSEMI, true, Handler.IO_STAT_SEMI);
                return io;
            }
            case TINPT: {
                isCurrentToken(TokenClass.TINPT);         // No check
                TreeNode inp = new Input().setMiddle(vList());
                isCurrentToken(TokenClass.TSEMI, true,  Handler.IO_STAT_SEMI);
                return inp;
            }
            default: {
                // We weould only get here if the current token wasn't one of the ablve
                // But it has to be due to the way the method is designed
                return null; // Should never happen. PLZ never happene
            }
        }
    }


    // Special <vlist> ::= <var> <vltail>
    protected TreeNode vList() throws ErrorHandlerException {
        TreeNode var = null;
        try {
            var = var();
        } catch (SyncOnCommaCascadeOnStmntException e) {
            // Will sync and try to recover on a comma
            // otherwise, if we find a statement, will cascade up
            e.recover();
        }
        TreeNode vlTail = vlTail();
        if (vlTail == null) {
            return var;
        }
        return new InputVarList(var, vlTail);
    }

    // Special <vltail> ::= ?
    // NVLIST <vltail> ::= , <vlist>
    protected TreeNode vlTail() throws ErrorHandlerException{
        if(!isCurrentToken(TokenClass.TCOMA)) {
            return null;
        }
        return vList();
    }

    // Special <var> ::= <id> <vtail>
    protected TreeNode var() throws ErrorHandlerException{
        STRecord identifer = matchCurrentAndStoreRecord(TokenClass.TIDNT, Handler.IO_VAR_ID);
        TreeNode thing = vTail(identifer);
        return thing.setName(identifer);
    }

    // NSIMVAR <vtail> ::= ?
    // NARRVAR <vtail> ::= [<expr>]
    protected TreeNode vTail(STRecord id) throws ErrorHandlerException{
        if(isCurrentToken(TokenClass.TLBRK)) {

            if (!"array".equals(id.getPropertyValue("type", context.scope, String.class))) {
                TypeMismatchError.record("array", id.getPropertyValue("type", context.scope, String.class), id);
            }

            TreeNode indexCalc = expr();
            isCurrentToken(TokenClass.TRBRK, true, Handler.IO_ARR_RBRK);
            return new ArrayVariable().setMiddle(indexCalc);
        }
        return new SimpleVariable();
    }

    // Special <printail> ::= ;
    // Special <printail> ::= <prlist> ;
    protected TreeNode prinTail() throws ErrorHandlerException{
        if(isCurrentToken(TokenClass.TSEMI)) {
            return null;
        }
        TreeNode printList = prList();
        return printList;
    }

    // Special <prlist> ::= <printitem> <prltail>
    // Will return either a string/expression,
    // or a NPRLIST of shit to print, so same structure
    // Special <printitem> ::= <expr> // INLINED
    // NSTRG <printitem> ::= <string> // INLINED
    protected TreeNode prList() throws ErrorHandlerException{
        TreeNode printItem = null;

        STRecord string;
        try {
            if (currentToken.getTokenClass().equals(TokenClass.TSTRG)) {
                string = matchCurrentAndStoreRecord(TokenClass.TSTRG, Handler.PRINT_ITEM); // No check
                printItem = new StringLiteral(string);
            } else {
                printItem = expr();
            }
        } catch (SyncOnCommaCascadeOnStmntException e) {
            // Will escalate if we reach a statement
            e.recover();
        }

        TreeNode prTail = prTail();
        if (prTail == null) {
            return printItem;
        }
        return new PrintList(printItem, prTail);
    }

    // NPRLIST <prltail> ::= , <prlist>
    // Special <prltail> ::= ?
    protected TreeNode prTail() throws ErrorHandlerException{
        if(!isCurrentToken(TokenClass.TCOMA)) {
            return null;
        }
        return prList();
    }

    // NCALL <callstat> ::= call <id> <calltail>
    protected TreeNode callStatement() throws ErrorHandlerException{
        isCurrentToken(TokenClass.TCALL);                 // No check
        STRecord procId = matchCurrentAndStoreRecord(TokenClass.TIDNT, Handler.CALL_ID);

        // Check that what we are calling is a proc, and that it has been declared, and that it is not recursive
        if( ! procId.existsInScope(context.global) ) {
            // Trying to do something with undeclared proc
            CompilationError.record(procId, CompilationError.Type.UNDECLARED_IDENTIFIER);
        }
        if ( !"proc".equals(procId.getPropertyValue("type",  context.global, String.class))) {
            // Trying to call a proc that has the same name as some identifier
            TypeMismatchError.record("proc", procId.getPropertyValue("type", context.global, String.class), procId);
        }

        if (context.scope.equals(procId.getLexemeString())) {
            CompilationError.record(procId, CompilationError.Type.RECURSIVE_CALL);
        }

        context.currentProc = procId.getLexemeString();
        if(isCurrentToken(TokenClass.TWITH)) {

            context.callArgumentParams = 0;
            TreeNode callParams = callTail();
            TreeNode procCall = new Call();

            // Ensure that the is not being passed too many arguments
            if ( context.callArgumentParams > procId.getPropertyValue("numberOfParams", context.global, Integer.class)) {
                InvalidProcCallError.record("Too many arguments.\tExpected: "
                        + procId.getPropertyValue("numberOfParams", context.global, Integer.class) + "\tFound: "
                        + context.callArgumentParams, procId);
            }

            // Ensure that enough var params have been passed
            if (context.callArgumentParams < procId.getPropertyValue("numVarParams",context.global, Integer.class)) {
                InvalidProcCallError.record("Insufficient var arguments.\tExpected: "
                        + procId.getPropertyValue("numVarParams", context.global,Integer.class) + "\tFound: "
                        + context.callArgumentParams, procId);
            }

            context.resetCallContext();

            procCall.setName(procId);
            procCall.setMiddle(callParams);
            return procCall;
        } else {
            // Need to make sure that there shouldn't have been arguments
            if ( procId.getPropertyValue("numVarParams", context.global,Integer.class) > 0) {
                InvalidProcCallError.record("Insufficient var arguments.\tExpected: "
                        + procId.getPropertyValue("numVarParams", context.global,Integer.class) + "\tFound: "
                        + 0, procId);
            }
        }
        isCurrentToken(TokenClass.TSEMI, true, Handler.CALL_SEMI);
        return new Call().setName(procId);
    }


    // Special <calltail> ::= with <elist> ;
    // Special <calltail> ::= ;
    protected TreeNode callTail() throws ErrorHandlerException{
        if (isCurrentToken(TokenClass.TSEMI)) {
            // There are no more params
            return null;
        }
        // Else we have some more params to process
        TreeNode paramList = eList();
        isCurrentToken(TokenClass.TSEMI, true, Handler.CALL_SEMI); // recycle from above
        return paramList;
    }

    // Special <elist> ::= <expr> <eltail>
    protected TreeNode eList() throws ErrorHandlerException{
        TreeNode expression = expr();
        context.callArgumentParams++;

        // We now need to make sure that the type matches what we expect
        // get all the STRecs where type is array

        // Filter table to get all ids in the scope defined by the proc id
        // Filter ids to get an id whose order match the current callArgumentParams
        try {
            STRecord record = (STRecord) ((Map.Entry)symbolTable.filter(new Query() {
                @Override
                public Map<String, STRecord> query(SymbolTable st) {
                    Map<String, STRecord> results = new HashMap<>();
                    for (STRecord rec : st.get().values()) {
                        // Refine filter to get us the things that exist in proc scope
                        // That are tagged as being correctly ordered in the param
                        // That h
                        if (rec.existsInScope(context.currentProc)
                                && new Integer(context.callArgumentParams)
                                .equals(rec.getPropertyValue("orderInParams", context.currentProc, Integer.class))
                                ) {
                            results.put(rec.getLexemeString(), rec);
                        }
                    }
                    return results;
                }
            }).get().entrySet().toArray()[0]).getValue();   // This will work, as we have already validated the types

            if (!expression.getName().getPropertyValue("type", context.scope, String.class)
                    .equals(record.getPropertyValue("paramType", context.currentProc, String.class))) {
                // It failed the conditions.
                // It could find a match, or wasn't the right type
                TypeMismatchError.record(record.getPropertyValue("paramType", context.currentProc, String.class),
                        expression.getName().getPropertyValue("type", context.scope, String.class),
                        expression.getName()
                );
            }
        } catch (Exception e) {
            // continue
            DebugWriter.writeToFile("ST filter and lookup failed because " + e.getMessage() + e.getCause() + e.getStackTrace());
        }


        TreeNode restOfTheExpressions = elTail();
        if (restOfTheExpressions == null) {
            return expression;
        }
        return new ExpressionList(expression, restOfTheExpressions);
    }

    // NELIST <eltail> ::= , <elist>
    // Special <eltail> ::= ?
    protected TreeNode elTail() throws ErrorHandlerException{
        if(!isCurrentToken(TokenClass.TCOMA)) {
            return null;
        }
        return eList();
    }

    // Special <expr> ::= <term> <etail>
    protected TreeNode expr() throws ErrorHandlerException{
        TreeNode term = term();
        return eTail(term);
    }

    // NADD <etail> ::= + <term> <etail>
    // NSUB <etail> ::= - <term> <etail>
    // Special <etail> ::= ?
    protected TreeNode eTail(TreeNode leftSide) throws ErrorHandlerException{
        switch (currentToken.getTokenClass()) {
            case TPLUS: {
                isCurrentToken(TokenClass.TPLUS);
                TreeNode addition = new Addition(leftSide, term());     // No check
                return eTail(addition);
            }
            case TSUBT: {
                isCurrentToken(TokenClass.TSUBT);
                TreeNode subtraction = new Subtraction(leftSide, term());   // No check
                return eTail(subtraction);
            }
            default: return leftSide; // Dont kill the children         // No err, let it cascade
        }
    }

    // Special <term> ::= <fact> <ttail>
    protected TreeNode term() throws ErrorHandlerException{
        TreeNode fact = fact();
        return tTail(fact);
    }

    // Special <fact> ::= <id> <ftail>
    // NILIT <fact> ::= <intlit>
    // NFLIT <fact> ::= <reallit>
    // Special <fact> ::= ( <bool> )
    protected TreeNode fact() throws ErrorHandlerException {
        switch(currentToken.getTokenClass()) {
            case TIDNT: {
                STRecord identifer = matchCurrentAndStoreRecord(TokenClass.TIDNT, Handler.FACT_COMPONENT);
                if (!identifer.existsInScope(context.scope) && !identifer.existsInScope(context.global)) {
                    CompilationError.record(identifer, CompilationError.Type.UNDECLARED_IDENTIFIER);
                }
                return fTail(identifer).setName(identifer);
            }
            case TILIT: {
                return new IntegerLiteral().setType(matchCurrentAndStoreRecord(TokenClass.TILIT, Handler.FACT_COMPONENT));
            }
            case TFLIT: {
                return new FloatingLiteral().setType(matchCurrentAndStoreRecord(TokenClass.TFLIT, Handler.FACT_COMPONENT));
            }
            case TLPAR: {
                isCurrentToken(TokenClass.TLPAR); // No check
                TreeNode booleanExpression = bool();
                isCurrentToken(TokenClass.TRPAR, true, Handler.FACT_BOOL_RPAR);
                return booleanExpression;
            }
            // Record and throw if not matched
            default : UnexpectedTokenError.record("int, float or identifier", currentToken.getProperLexeme(), Handler.FACT_COMPONENT, this);
        }
        DebugWriter.writeEverywhere("Expected a fact but didn't find one. Parsing stream " +
                "may be corrupt");
        return null; // Should never reach this point
    }

    // Special <ftail> ::= <vtail>
    // or a plain array => vTail
    // NLEN <ftail> ::= . length
    protected TreeNode fTail(STRecord potentialArray) throws ErrorHandlerException{
        if(isCurrentToken(TokenClass.TDOTT)) {
            if(!"array".equals(potentialArray.getPropertyValue("type", context.scope, String.class))) {
                TypeMismatchError.record("array", potentialArray.getPropertyValue("type",
                        context.scope, String.class), potentialArray);
            }
            // We have a length thing
            if(isCurrentToken(TokenClass.TLENG, true, Handler.FACT_ARR_LENGTH)) {
                return new ArrayLength();
            } else {
                // TODO throw error here
                return null;
            }
        }
        return vTail(potentialArray);
    }

    // NMUL <ttail> ::= * <fact> <ttail>
    // NDIV <ttail> ::= / <fact> <ttail>
    // NIDIV <ttail> ::= div <fact> <ttatil>
    // Special <ttail> ::= ?
    protected TreeNode tTail(TreeNode leftSide) throws ErrorHandlerException{
        switch (currentToken.getTokenClass()) {
            case TMULT: {
                isCurrentToken(TokenClass.TMULT);
                TreeNode mult = new Multiplication(leftSide, fact());   // No checks
                return tTail(mult);
            }
            case TIDIV: {
                isCurrentToken(TokenClass.TIDIV);
                TreeNode iDiv = new IntegerDivide(leftSide, fact());    // No check
                return tTail(iDiv);
            }
            case TDIVD: {
                isCurrentToken(TokenClass.TDIVD);
                TreeNode division = new Division(leftSide, fact());     // No check
                return tTail(division);
            }
            default: return leftSide; // Dont kill the children         // No err. let it cascade
        }
    }




    // Special <bool> ::= <rel> <btail>
    protected TreeNode bool() throws ErrorHandlerException {
        return bTail(rel());
    }

    // NNOT <rel> ::= not <rel>
    // Special <rel> ::= <expr> <reltail>
    protected TreeNode rel() throws ErrorHandlerException{
        if(isCurrentToken(TokenClass.TNOTK)) {
            TreeNode invertedBool = relTail(expr());
            return new Not().setMiddle(invertedBool);
        }
        return relTail(expr());
    }


    // Special <reltail> ::= <relop> <expr>
    // Special <reltail> ::= ?
    protected TreeNode relTail(TreeNode leftSide) throws ErrorHandlerException{
        TreeNode relationalOperator = relOp();
        if (relationalOperator == null) {
            return leftSide;
        } // else
        return relationalOperator.setLeft(leftSide).setRight(expr());
    }

    // Special <btail> ::= <logop> <rel> <btail>
    // Special <btail> ::= ?
    protected TreeNode bTail(TreeNode leftSide) throws ErrorHandlerException{
        TreeNode logicOperator = logOp();
        if (logicOperator == null) {
            return leftSide;
        } // else
        // wow. Dat method chaining will be so easy to debug -_-
        return bTail(logicOperator.setLeft(leftSide).setRight(rel()));
    }


    // NAND, NOR, NXOR
    // <logop> ::= and | or | xor
    protected TreeNode logOp()  throws ErrorHandlerException{
        switch (currentToken.getTokenClass()) {
            case TANDK: isCurrentToken(TokenClass.TANDK); return new And();
            case TORKW: isCurrentToken(TokenClass.TORKW); return new Or();
            case TXORK: isCurrentToken(TokenClass.TXORK); return new Xor();
        }
        return null; // Never reach due to exception
    }


//    NEQL, NNEQ, NGTR, NLEQ, NLESS, NGEQ
//            <relop> ::= == | != | > | <= | < | >=
    protected TreeNode relOp() throws ErrorHandlerException{
        switch (currentToken.getTokenClass()) {
            case TDEQL: isCurrentToken(TokenClass.TDEQL); return new EqlEql();
            case TNEQL: isCurrentToken(TokenClass.TNEQL); return new NotEquals();
            case TGRTR: isCurrentToken(TokenClass.TGRTR); return new Greater();
            case TLESS: isCurrentToken(TokenClass.TLESS); return new Less();
            case TGREQ: isCurrentToken(TokenClass.TGREQ); return new GreaterEquals();
            case TLEQL: isCurrentToken(TokenClass.TLEQL); return new LessEquals();
        }
        return null; // Never reach due to exception
    }








}