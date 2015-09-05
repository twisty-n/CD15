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

    protected Main main() {
        return null;
    }

    protected ProcDecList procs() {
        return null;
    }



//    --NPROG <program>::= program <id> <arrays> <procs> <main> end program <id>
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
        return new Program(arrays, procs, main);
    }

//    Special <arrays> ::= arrays <arraydecl> <arrdltail>
//    Special <arrays> ::= ?
    /**
     *
     * @return
     */
    protected ArrayDecList arrays() {
        // Match empty
        if (!isCurrentToken(TokenClass.TARRS, false)) {
            return null;
        }
        // Otherwise we have a set of valid arrays to work with

        // Get an array decl
        ArrayDeclaration dec = arrayDeclaration();
        ArrayDecList rest = arrayTail();

        // Set the branches of the tree up
        return new ArrayDecList(dec, rest);
    }

//    --NARRYL <arrdltail> ::= , <arraydecl> <arrdltail>
//    Special <arrdltail> ::= ?
    protected ArrayDecList arrayTail() {
        // If not a comma, we've finished the array declarations
        if(!isCurrentToken(TokenClass.TCOMA, false)) {
            //TODO: Fix this up in error checking
            return null;
        }
        // Else we've seen a comma
        //So match an array declaration, set to left,
        ArrayDeclaration dec = arrayDeclaration();
        // Match an arrayTail. Set to right
        ArrayDecList rest = arrayTail();
        return new ArrayDecList(dec, rest); // Set left and right
    }

//    --NARRDEC <arraydecl> ::= <id> [ <intlit> ]
    protected ArrayDeclaration arrayDeclaration() {
        // Match an ID
        ArrayDeclaration decl = new ArrayDeclaration();
        decl.setName(matchCurrentAndStoreRecord(TokenClass.TIDNT));
        // Match a left brace
        isCurrentToken(TokenClass.TLBRK, true);
        // Match an IntLit
        decl.setType(matchCurrentAndStoreRecord(TokenClass.TILIT));
        // Match a Right brace
        isCurrentToken(TokenClass.TRBRK, true);
        return decl;
    }

//    --NPROCL <procs> ::= <proc> <procs>
//    Special <procs> ::= ?
//    --NMAIN <main> ::= local <idlist> ; <stats>
//    --NPROC <proc> ::= proc <id> <parameters> <locals> <stats> end proc <id>
//    --NPARAMS <parameters> ::= var <plist> <paramstail>
//    --NPARAMS <parameters> ::= val <pidlist>
//    Special <parameters> ::= ?
//    Special <paramstail> ::= val <pidlist>
//    Special <paramstail> ::= ?
//    Special <plist> ::= <param> <plisttail>
//    --NPLIST <plisttail> ::= , <plist>
//    Special <plisttail> ::= ?
//    Special <param> ::= <id> <paramtail>
//    --NSIMPAR <paramtail> ::= ?
//    --NARRPAR <paramtail> ::= [ ]
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
//    NSIMDEC <idlist> ::= <id> <idltail>
//    NIDLST <idltail> ::= , <idlist>
//    Special <idltail> ::= ?


}