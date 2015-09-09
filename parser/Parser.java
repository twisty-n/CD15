package parser;

import context.error.UnexpectedTokenError;
import context.symbolism.STRecord;
import parser.ast.TreeNode;
import parser.ast.nodes.*;
import scanner.Scanner;
import scanner.tokenizer.Token;
import scanner.tokenizer.TokenClass;

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

        // Check for an ID, it should be an ID now
        // Dont forget to set the symTabRecs
        // Pretty much we call the localVariables now
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
            // Call paramstail
            return varParams;
        } else {
            // Only have val params

            // Call PID list
            return null;
        }
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

    //    Special <param> ::= <id> <paramtail>
    //    --NSIMPAR <paramtail> ::= ?
    //    --NARRPAR <paramtail> ::= [ ]
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

    protected TreeNode procedureLocals() {
        return null;
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

    // TODO
    protected TreeNode statements() {
        return null;
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

//    Special <procs> ::= ?
//



//    Special <paramstail> ::= val <pidlist>
//    Special <paramstail> ::= ?

//    --NPLIST <plisttail> ::= , <plist>
//    Special <plisttail> ::= ?

//            --NSIMPAR <pidlist> ::= <id> <pidltail>
//    --NPLIST <pidltail> ::= , <pidlist>
//    Special <pidltail> ::= ?
//    Special <locals> ::= local <decllist> ; | ?
//    Special <decllist> ::= <decl> <dltail>
//    --NDLIST <dltail> ::= , <decllist>
//    Special <dltail> ::= ?
//    Special <decl> ::= <id> <dtail>
//    --NARRDEC <dtail> ::= [ <intlit> ]
//            --NSIMDEC <dtail> ::= ?
//    Special <stats> ::= <stat> <sltail>
//    --NSLIST <sltail> ::= <stat> <sltail>
//    Special <sltail> ::= ?
//    Special <stat> ::= <loopstat> | <exitstat> | <ifstat>
//    Special <stat> ::= <asgnstat> | <iostat> | <callstat>
//    --NLOOP <loopstat> ::= loop <id> <stats> end loop <id>
//    --NEXIT <exitstat> ::= exit <id> when <bool> ;
//    Special <ifstat> ::= if <bool> then <stats> <iftail>
//    --NIFT <iftail> ::= end if
//            --NIFTE <iftail> ::= else <stats> end if
//            --NIFTE <iftail> ::= elsif <bool> then <stats> <elsiftail>
//    --NIFTE <elsiftail> ::= elsif <bool> then <stats> <elsiftail>
//    Special <elsiftail> ::= else <stats> end if
//    Special <asgnstat> ::= <var> <asgnop> <expr> ;
//    --NASGN, NPLEQ, NMNEQ, NSTEQ, NDVEQ
//            <asgnop> ::= = | += | -= | *= | /=
//            --NINPUT <iostat> ::= input <vlist> ;
//    --NPRINT <iostat> ::= print <prlist> ;
//    --NPRLN <iostat> ::= printline <printail>
//    Special <printail> ::= <prlist> ;
//    Special <printail> ::= ;
//    --NCALL <callstat> ::= call <id> <calltail>
//    Special <calltail> ::= with <elist> ;
//    Special <calltail> ::= ;
//    Special <vlist> ::= <var> <vltail>
//    NVLIST <vltail> ::= , <vlist>
//    Special <vltail> ::= ?
//    Special <var> ::= <id> <vtail>
//    NARRVAR <vtail> ::= [<expr>]
//    NSIMVAR <vtail> ::= ?
//    Special <elist> ::= <expr> <eltail>
//    NELIST <eltail> ::= , <elist>
//    Special <eltail> ::= ?
//    Special <bool> ::= <rel> <btail>
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
//    Special <prlist> ::= <printitem> <prltail>
//    NPRLIST <prltail> ::= , <prlist>
//    Special <prltail> ::= ?
//    Special <printitem> ::= <expr>
//    NSTRG <printitem> ::= <string>
//    Special <idltail> ::= ?


}