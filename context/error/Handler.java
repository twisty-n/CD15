package context.error;

import context.error.handlers.*;
import parser.Parser;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    10/7/2015
 * File Name:       Handler
 * Project Name:    CD15 Compiler
 * Description:     Defines all of the possible errournous states of the parser
 */
public enum Handler {

    // All handlers implented as anonymous classes.
    // So that we can pretend that callbacks exists for Hava

    FATAL(new FatalException()),

    OPENING_PROG_KW(new SimpleMissingTokenException()),
    PROG_ID_DECL(new SimpleMissingTokenException()),
    END_PROG_KW(new SimpleMissingTokenException()),
    END_PROG_END_KW(new SimpleMissingTokenException()),
    END_PROG_ID(new SimpleMissingTokenException()),
    PROGRAM_LOCAL_DECL(new SyncAtStatementKeywordException()),
    PROGRAM_LOCAL_END_SEMI(new SimpleMissingTokenException()),

    LOCAL_DECL_ID(new SyncAtStatementKeywordException()),
    LOCAL_DECL_SEMI(new SimpleMissingTokenException()),

    PROC_DECL_ID(new SyncOnProcLocalOrStat()),
    PROC_DECL_END(new SimpleMissingTokenException()),
    PROC_DECL_END_PROC(new SimpleMissingTokenException()),
    PROC_DECL_ID_END(new SimpleMissingTokenException()),

    PARAMS_VALP_ALONE(new SyncOnProcLocalOrStat()),
    PARAMS_ID(new SyncOnProcLocalOrStat()), // Handle for both VAR and VAL. We will sync be skipping them all
    PARAMS_ARR_RBRK(new SyncOnProcLocalOrStat()),

    PROC_LOCAL_END_SEMI(new SimpleMissingTokenException()),
    PROC_LOCAL_ID(new SyncAtStatementKeywordException()),
    PROC_LOCAL_ARR_LENGTH(new SyncAtStatementKeywordException()),
    PROC_LOCAL_ARR_RBRK(new SyncAtStatementKeywordException()),

    ARRAY_DECL_ID(new SyncOnProcLocalOrStat()),
    ARRAY_LBRK(new SyncOnProcLocalOrStat()),
    ARRAY_LENGTH(new SyncOnProcLocalOrStat()),
    ARRAY_RBRK(new SyncOnProcLocalOrStat()),

    LOOP_DECL_ID(new SimpleMissingTokenException()),
    LOOP_END_KW(new SimpleMissingTokenException()),
    LOOP_END_LOOP_KW(new SimpleMissingTokenException()),
    LOOP_END_ID(new SimpleMissingTokenException()),

    EXIT_LOOP_ID(new ExitLoopIdHandler()),
    EXIT_WHEN_KW(new SimpleMissingTokenException()),
    EXIT_SEMI(new SimpleMissingTokenException()),

    IF_THEN_KW(new SimpleMissingTokenException()),
    IF_END_KW(new SimpleMissingTokenException()),
    IF_IF_KW(new SimpleMissingTokenException()),
    IF_ELSE_THEN_KW(new IfElseThenKw()),
    IF_ENDING_KEYWORD(new IfEndingKeyword()),

    ASSIGN_OP(new AssignOp()),
    ASSIGN_STAT_SEMI(new SimpleMissingTokenException()),

    IO_STAT_SEMI(new SimpleMissingTokenException()),
    IO_VAR_ID(new IoVarId()),
    IO_ARR_RBRK(new IoVarRbrk),

    CALL_ID(new CallId),
    CALL_SEMI(new  SimpleMissingTokenException()),

    FACT_COMPONENT(new FactIdentifier()),
    FACT_BOOL_RPAR(new FactBoolRpar()),
    FACT_ARR_LENGTH(new FactArrLength()),
    LOG_OP(new LogOp()),
    REL_OP(new RolOp())

    private ErrorHandlerException h;

    Handler(ErrorHandlerException h) {
        this.h = h;
    }

    public void handle(Parser context) throws ErrorHandlerException { this.h.handle(context); }
}
