package parser;

import context.error.UnexpectedTokenError;
import context.symbolism.STRecord;
import parser.ast.TreeNode;
import parser.ast.nodes.*;
import scanner.Scanner;
import scanner.tokenizer.Token;
import scanner.tokenizer.TokenClass;
import utils.DebugWriter;

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

    /**
     * We need the scanner in order to parse
     * The rest of the things we need are attached to
     * the compilation context
     * @param scanner
     */
    public Parser(Scanner scanner) {
        this.scanner = scanner;
        this.currentToken = scanner.getNextValidToken();
    }

    public TreeNode parse() {
        return program();
    }

    /**
     * Sets the current token to be the next token from the scanner
     */
    protected void nextToken() {
        this.currentToken = scanner.getNextValidToken();
    }

    /**
     * Obtains the next token from the scanner and compares it to the class
     * If the classes match, return true else false
     * @param tokenClass
     */
    protected boolean IsNextToken(TokenClass tokenClass) {
        this.nextToken();
        if (tokenClass.equals(this.currentToken.getTokenClass())) return true;
        return false;
    }

    /**
     * Checks the current token to see if the classes match
     * if so, will obtain the next token and return true
     * else will leave current token alone and return false
     * @param tokenClass
     * @return
     */
    protected boolean isCurrentToken(TokenClass tokenClass, boolean reportError) {
        if (tokenClass.equals(currentToken.getTokenClass())) {
            nextToken();
            return true;
        }
        if (reportError)
            UnexpectedTokenError.record(tokenClass.val(), currentToken.getProperLexeme());
        return false;
    }

    /**
     * Checks the current token to see if the classes match
     * if so, will store an STRecord andobtain the next token and return true
     * else will leave current token alone and return false
     * @param tokenClass
     * @return
     */
    protected STRecord matchCurrentAndStoreRecord(TokenClass tokenClass) {
        if (tokenClass.equals(currentToken.getTokenClass())) {
            STRecord rec = currentToken.getSymbol();
            nextToken();
            return rec;
        }
        UnexpectedTokenError.record(tokenClass.val(), currentToken.getProperLexeme());
        return null;
    }

    /**
     * Returns the completed AST for the Program
     * @return
     * program <id> <arrays> <procs> <main> end program <id>
     */
    protected TreeNode program() {

        isCurrentToken(TokenClass.TPROG, true);
        isCurrentToken(TokenClass.TIDNT, true);
        TreeNode arrays = arrays();
        TreeNode procs = procs();
        TreeNode main = main();
        isCurrentToken(TokenClass.TENDK, true);
        isCurrentToken(TokenClass.TPROG, true);
        isCurrentToken(TokenClass.TIDNT, true);
        return new Program(arrays, main, procs);
    }

    /**
     *
     *  <arrays> ::= arrays <arraydecl> <arrdltail>
     * @return
     */
    protected TreeNode arrays() {
        // Match empty
        if (!isCurrentToken(TokenClass.TARRS, false)) {
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
    protected TreeNode arrayTail() {
        // If not a comma, we've finished the array declarations
        if(!isCurrentToken(TokenClass.TCOMA, false)) {
            //TODO: Fix this up in error checking
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

    //--NMAIN <main> ::= local <idlist> ; <stats>
    protected Main main() {
        isCurrentToken(TokenClass.TLOCL, true);
        TreeNode localVars = localVariables();
        isCurrentToken(TokenClass.TSEMI, true);
        TreeNode statementList = statements();

        // Return the built main node
        Main main = new Main(localVars, statementList);
        return main;
    }

    // Returns a NDLIST OR a single local if there is only one
    //    NSIMDEC <idlist> ::= <id> <idltail>
    protected TreeNode localVariables() {

        // Build an ID: Match the ID, store it
        LocalDeclaration local = new LocalDeclaration();
        local.setName(matchCurrentAndStoreRecord(TokenClass.TIDNT));

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
    protected TreeNode localVarsTail() {
        // Check a comma, if it is, continue
        if (!isCurrentToken(TokenClass.TCOMA, false)) {
            return null;
        }
        return localVariables();
    }

    // NPROC <proc> ::= proc <id> <parameters> <locals> <stats> end proc <id>
    protected ProcedureDeclaration procedure() {
        // Match proc
        ProcedureDeclaration dec = new ProcedureDeclaration();
        if(!isCurrentToken(TokenClass.TPROC, false)) {
            // If we dont see a proc, assume that there are none
            return null;
        }
        // Match id
        dec.setName(matchCurrentAndStoreRecord(TokenClass.TIDNT));
        // Call proc_params
        TreeNode procedureParams = procedureParams();
        // Call proc locals
        TreeNode procedureLocals = procedureLocals();
        // call stats
        TreeNode procedureStatements = statements();
        // Match end
        isCurrentToken(TokenClass.TENDK, true);
        // Match proc
        isCurrentToken(TokenClass.TPROC, true);
        // Match id
        isCurrentToken(TokenClass.TIDNT, true);  // <<-- Check for sem anal
        dec.setLeft(procedureParams);
        dec.setMiddle(procedureLocals);
        dec.setRight(procedureStatements);
        return dec;
    }

    // NPARAMS <parameters> ::= var <plist> <paramstail>
    // NPARAMS <parameters> ::= val <pidlist>
    // Special <parameters> ::= ?
    protected TreeNode procedureParams() {

        // Proc can have var | val | Epsilon to start with
        TokenClass tokenClass = currentToken.getTokenClass();
        if (!tokenClass.equals(TokenClass.TVALP) && !tokenClass.equals(TokenClass.TVARP)) {
            // If its not var or val, there are no params
            return null;
        }

        // It must be VAR or VAL
        if (isCurrentToken(TokenClass.TVARP, false)) {
            // Have a set of var params
            // Call plist
            TreeNode varParams = procVarParams();
            TreeNode valParams = paramsTail();
            return new ProcParameters(varParams, valParams);
            // Call paramstail
        } else {
            // Call PID list
            isCurrentToken(TokenClass.TVALP, true);
            ProcParameters params = new ProcParameters();
            params.setMiddle(pIdList());
            return params;
        }
    }

    // Special <paramstail> ::= val <pidlist>
    // Special <paramstail> ::= ?
    protected TreeNode paramsTail() {
        if(!isCurrentToken(TokenClass.TVALP, false)) {
            // There are no val params
            return null;
        }
        // Otherwise, match them up
        return pIdList();
    }

    // NSIMPAR <pidlist> ::= <id> <pidltail>
    protected TreeNode pIdList() {
        // Match an id
        STRecord id = matchCurrentAndStoreRecord(TokenClass.TIDNT);
        TreeNode simPar = new SimpleParameter();
        simPar.setName(id);
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
    protected TreeNode pIdLTail() {
        if(!isCurrentToken(TokenClass.TCOMA, false)) {
            return null;
        }
        return pIdList();
    }

    // <plist> ::= <param> <plisttail>
    protected TreeNode procVarParams() {
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

    protected TreeNode procVarList() {
        if(!isCurrentToken(TokenClass.TCOMA, false)) {
            return null;
        }
        return procVarParams();
    }

    // Special <param> ::= <id> <paramtail>
    // NSIMPAR <paramtail> ::= ?
    // NARRPAR <paramtail> ::= [ ]
    protected TreeNode varParam() {
        STRecord id = matchCurrentAndStoreRecord(TokenClass.TIDNT);
        if (isCurrentToken(TokenClass.TLBRK, false)) {
            isCurrentToken(TokenClass.TRBRK, true);
            // We just matched an ArrayParam
            ArrayParameter arrPam = new ArrayParameter();
            arrPam.setName(id);
            return arrPam;
        } else {
            // We matched a simple param
            SimpleParameter simParam = new SimpleParameter();
            simParam.setName(id);
            return simParam;
        }
    }

    // Special <locals> ::= local <decllist> ; | ?
    protected TreeNode procedureLocals() {
        // Match local
        if(!isCurrentToken(TokenClass.TLOCL, false)) {
            // There were no locals
            return null;
        }
        // Call and return decllist
        TreeNode locals = declList();
        isCurrentToken(TokenClass.TSEMI, true);
        return locals;
    }


    // Special <decllist> ::= <decl> <dltail>
    protected TreeNode declList() {
        TreeNode delcaration = decl();
        TreeNode restOfTheDeclarations = dlTail();
        if (restOfTheDeclarations == null) {
            return delcaration;
        }
        // Other wise, return up the built list
        ProcLocals locals = new ProcLocals();
        locals.setLeft(delcaration);
        locals.setRight(restOfTheDeclarations);
        return locals;
    }

    // NDLIST <dltail> ::= , <decllist>
    // Special <dltail> ::= ?
    protected TreeNode dlTail() {
        // Match a comma or nothing
        if(!isCurrentToken(TokenClass.TCOMA, false)) {
            return null;
        }
        // call declList
        return declList();
    }

    // Special <decl> ::= <id> <dtail>
    protected TreeNode decl() {
        STRecord id = matchCurrentAndStoreRecord(TokenClass.TIDNT);
        // We store the Id in the thing that comes back to us from dtail
        TreeNode thing = dTail();
        thing.setName(id);
        return thing;
    }

    // NARRDEC <dtail> ::= [ <intlit> ]
    // NSIMDEC <dtail> ::= ?
    protected TreeNode dTail() {
        if (isCurrentToken(TokenClass.TLBRK, false)) {
            STRecord arrayLength = matchCurrentAndStoreRecord(TokenClass.TILIT);
            if (isCurrentToken(TokenClass.TRBRK, true)) {
                return new ArrayDeclaration().setType(arrayLength);
            }
        }
        return new LocalDeclaration();
    }

    // NPROCL <procs> ::= <proc> <procs>
    protected TreeNode procs() {
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

    // NARRDEC <arraydecl> ::= <id> [ <intlit> ]
    protected TreeNode arrayDeclaration() {
        // Match an ID
        TreeNode decl = new ArrayDeclaration();
        decl.setName(matchCurrentAndStoreRecord(TokenClass.TIDNT));
        // Match a left brace
        isCurrentToken(TokenClass.TLBRK, true);
        // Match an IntLit
        decl.setType(matchCurrentAndStoreRecord(TokenClass.TILIT));
        // Match a Right brace
        isCurrentToken(TokenClass.TRBRK, true);
        return decl;
    }


    // Special <stats> ::= <stat> <sltail>
    protected TreeNode statements() {
        TreeNode statement = statement();
        // Call sltail
        TreeNode statementList = slTail();
        if (statementList == null) {
            return statement;
        }
        return new StatementList(statement, statementList);
    }

    // Special <stat> ::= <loopstat> | <exitstat> | <ifstat>
    // Special <stat> ::= <asgnstat> | <iostat> | <callstat>
    protected TreeNode statement() {
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
        }
        // Should never happen
        DebugWriter.writeEverywhere("Error: located a statement, but no matching statement found" +
                ". Parsing probably corrupt :/");
        return null;
    }

    // In this case, just recycle the method after checking that we still have statements
    // NSLIST <sltail> ::= <stat> <sltail>
    // Special <sltail> ::= ?
    protected TreeNode slTail() {
        // if the current token thing doesn't match a statement, or id kill it
        if (!currentToken.getTokenClass().isStatementKeyword()
                || !currentToken.getTokenClass().isIdentifier()) {
            return null;
        }
        return statements();
    }

    // NLOOP <loopstat> ::= loop <id> <stats> end loop <id>
    protected TreeNode loopStatement() {
        isCurrentToken(TokenClass.TLOOP, true); // It really should be loop
        STRecord loopId = matchCurrentAndStoreRecord(TokenClass.TIDNT);
        TreeNode loopStatements = statements();
        // If there are no statements we have an error
        // TODO: No statements in Loop error: empty control structure
        isCurrentToken(TokenClass.TENDK, true);
        isCurrentToken(TokenClass.TLOOP, true);
        isCurrentToken(TokenClass.TIDNT, true);
        TreeNode loop = new Loop();
        loop.setMiddle(loopStatements);
        loop.setName(loopId);
        return loop;
    }

    // NEXIT <exitstat> ::= exit <id> when <bool> ;
    protected TreeNode exitStatement() {
        isCurrentToken(TokenClass.TEXIT, true);
        STRecord exitIdentifer = matchCurrentAndStoreRecord(TokenClass.TIDNT);
        isCurrentToken(TokenClass.TWHEN, true);
        return bool().setName(exitIdentifer);
    }

    // Special <ifstat> ::= if <bool> then <stats> <iftail>
    protected TreeNode ifStatement() {
        return null;
    }

    // Special <asgnstat> ::= <var> <asgnop> <expr> ;
    protected TreeNode assignmentStatement() {
        TreeNode variable = var();
        TreeNode assignmentOperation = asgnop();
        TreeNode value = expr();
        isCurrentToken(TokenClass.TSEMI, true);
        return assignmentOperation.setLeft(variable).setRight(value);

    }

    // NASGN, NPLEQ, NMNEQ, NSTEQ, NDVEQ
    // <asgnop> ::= = | += | -= | *= | /=
    protected TreeNode asgnop() {
        switch(currentToken.getTokenClass()) {
            case TASGN:     return new AssignmentOperation();
            case TPLEQ:     return new PlusEqual();
            case TMNEQ:     return new MinusEqual();
            case TMLEQ:     return new TimesEqual();
            case TDEQL:     return new DivideEqual();
            default:        {
                DebugWriter.writeEverywhere("Expected an assignment opp but didn't find. Parsing corrupt");
                return null;
            }
        }
    }

    // NINPUT <iostat> ::= input <vlist> ;
    // NPRINT <iostat> ::= print <prlist> ;
    // NPRLN  <iostat> ::= printline <printail>
    protected TreeNode ioStatement() {
        switch (currentToken.getTokenClass()) {
            case TPRIN: {
                // print
                isCurrentToken(TokenClass.TPRIN, true);
                return new Print().setMiddle(prList());
            }
            case TPRLN: {
                isCurrentToken(TokenClass.TPRLN, true);
                return new PrintLine().setMiddle(prinTail());
            }
            case TINPT: {
                isCurrentToken(TokenClass.TINPT, true);
                return new Input().setMiddle(vList());
            }
            default: {
                DebugWriter.writeEverywhere("Expected IO statement but none found. Parsing may be corrupt");
                return null; // Should never happen. PLZ never happene
            }
        }
    }


    // Special <vlist> ::= <var> <vltail>
    protected TreeNode vList() {
        TreeNode var = var();
        TreeNode vlTail = vlTail();
        if (vlTail == null) {
            return var;
        }
        return new InputVarList(var, vlTail);
    }

    // Special <vltail> ::= ?
    // NVLIST <vltail> ::= , <vlist>
    protected TreeNode vlTail() {
        if(!isCurrentToken(TokenClass.TCOMA, false)) {
            return null;
        }
        return vList();
    }

    // Special <var> ::= <id> <vtail>
    protected TreeNode var() {
        STRecord identifer = matchCurrentAndStoreRecord(TokenClass.TIDNT);
        TreeNode thing = vTail();
        return thing.setName(identifer);
    }

    // NSIMVAR <vtail> ::= ?
    // NARRVAR <vtail> ::= [<expr>]
    protected TreeNode vTail() {
        if(isCurrentToken(TokenClass.TLBRK, false)) {
            TreeNode indexCalc = expr();
            isCurrentToken(TokenClass.TRBRK, true);
            return new ArrayVariable().setMiddle(indexCalc);
        }
        return new SimpleVariable();
    }

    // Special <printail> ::= ;
    // Special <printail> ::= <prlist> ;
    protected TreeNode prinTail() {
        if(isCurrentToken(TokenClass.TSEMI, false)) {
            return null;
        }
        TreeNode printList = prList();
        isCurrentToken(TokenClass.TSEMI, true);
        return printList;
    }

    // Special <prlist> ::= <printitem> <prltail>
    // Will return either a string/expression,
    // or a NPRLIST of shit to print, so same structure
    // Special <printitem> ::= <expr> // INLINED
    // NSTRG <printitem> ::= <string> // INLINED
    protected TreeNode prList() {
        TreeNode printItem;
        if (currentToken.getTokenClass().equals(TokenClass.TSTRG)) {
            STRecord string = matchCurrentAndStoreRecord(TokenClass.TSTRG);
            printItem = new StringLiteral(string);
        } else {
            printItem = expr();
        }
        TreeNode prTail = prTail();
        if (prTail == null) {
            return printItem;
        }
        return new PrintList(printItem, prTail);
    }

    // NPRLIST <prltail> ::= , <prlist>
    // Special <prltail> ::= ?
    protected TreeNode prTail() {
        if(!isCurrentToken(TokenClass.TCOMA, false)) {
            return null;
        }
        return prList();
    }

    // NCALL <callstat> ::= call <id> <calltail>
    protected TreeNode callStatement() {
        isCurrentToken(TokenClass.TCALL, true);
        STRecord procId = matchCurrentAndStoreRecord(TokenClass.TIDNT);
        TreeNode callParams = callTail();
        TreeNode procCall = new Call();
        procCall.setName(procId);
        procCall.setMiddle(callParams);
        return procCall;
    }


    // Special <calltail> ::= with <elist> ;
    // Special <calltail> ::= ;
    protected TreeNode callTail() {
        if (isCurrentToken(TokenClass.TSEMI, false)) {
            // There are no more params
            return null;
        }
        // Else we have some more params to process
        TreeNode paramList = eList();
        isCurrentToken(TokenClass.TSEMI, true);
        return paramList;
    }

    // Special <elist> ::= <expr> <eltail>
    protected TreeNode eList() {
        TreeNode expression = expr();
        TreeNode restOfTheExpressions = elTail();
        if (restOfTheExpressions == null) {
            return expression;
        }
        return new ExpressionList(expression, restOfTheExpressions);
    }

    // NELIST <eltail> ::= , <elist>
    // Special <eltail> ::= ?
    protected TreeNode elTail() {
        if(!isCurrentToken(TokenClass.TCOMA, false)) {
            return null;
        }
        return eList();
    }

    protected TreeNode expr() {
        return null;
    }


    // Special <bool> ::= <rel> <btail>
    protected TreeNode bool() {

    }


//    --NIFT <iftail> ::= end if
//            --NIFTE <iftail> ::= else <stats> end if
//            --NIFTE <iftail> ::= elsif <bool> then <stats> <elsiftail>
//    --NIFTE <elsiftail> ::= elsif <bool> then <stats> <elsiftail>
//    Special <elsiftail> ::= else <stats> end if
//    Special <btail> ::= <logop> <rel> <btail>
//    Special <btail> ::= ?
//            NAND, NOR, NXOR
//    <logop> ::= and | or | xor
//    NNOT <rel> ::= not <rel>
//    Special <rel> ::= <expr> <relop> <expr>
//    NEQL, NNEQ, NGTR, NLEQ, NLESS, NGEQ
//            <relop> ::= == | != | > | <= | < | >=
//    Special <expr> ::= <term> <etail>
//    NADD <etail> ::= + <term> <etail>
//    NSUB <etail> ::= - <term> <etail>
//    Special <etail> ::= ?
//    Special <term> ::= <fact> <ttail>
//    NMUL <ttail> ::= * <fact> <ttail>
//    NDIV <ttail> ::= / <fact> <ttail>
//    NIDIV <ttail> ::= div <fact> <ttatil>
//    Special <ttail> ::= ?
//    Special <fact> ::= <id> <ftail>
//    NILIT <fact> ::= <intlit>
//    NFLIT <fact> ::= <reallit>
//    Special <fact> ::= ( <bool> )
//    Special <ftail> ::= <vtail>
//    NLEN <ftail> ::= . length

//    Special <idltail> ::= ?


}