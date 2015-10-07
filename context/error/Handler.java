package context.error;

import context.error.handlers.ErrorHandlerException;
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

    OPENING_PROG_KW(new OpeningProgKwHandler()),
    PROG_ID_DECL(new ProgramIdDeclHandler()),
    END_PROG_KW(new EndProgKwHandler()),
    END_PROG_ID(new EndProgramIdHandler()),
    PROGRAM_LOCAL_DECL(new ProgramLocalDeclHandler()),
    PROGRAM_LOCAL_END_SEMI(new ProgramLocalEndSemiHandler()),
    LOCAL_DECL_ID(new LocalDeclIdHandler()),
    PROC_DECL_ID(new ProcDeclIdHandler()),
    PROC_DECL_END(new ProcDeclEndHandler()),
    PROC_DECL_END_PROC(new ProcDeclEndProcHandler()),
    PROC_DECL_ID_END(new ProcDeclIdEndHandler()),
    PARAMS_VALP_ALONE(new ParamsValpAloneHandler()),
    PARAMS_ID(new ParamsIdHandler()), // Handle for both VAR and VAL. We will sync be skipping them all
    PARAMS_ARR_RBRK(new ParamsArrRbrkHandler()),
    PROC_LOCAL_END_SEMI(new ProcLocalEndSemiHandler()),
    PROC_LOCAL_ID(new ProcLocalIdHandler()),
    PROC_LOCAL_ARR_RBRK(new ProcLocalRbrkHandler()),
    ARRAY_DECL_ID(new ArrayDeclIdHandler()),
    ARRAY_LENGTH(new ArrayLengthHandler()),
    LOOP_DECL_ID(new LoopDeclIdHandler()),
    LOOP_END_KW(new LoopEndKwHandler()),
    LOOP_END_LOOP_KW(new LoopEndLoopKwHandler()),
    LOOP_END_ID(new LoopEndIdHandler()),
    EXIT_LOOP_ID(new ExitLoopIdHandler()),
    EXIT_WHEN_KW(new ExitWhenKwHandler()),
    EXIT_SEMI(new ExitSemiHandler()),
    IF_THEN_KW(new IfThenKw()),
    IF_END_KW(new IfEndKw()),
    IF_IF_KW(new IfIFKw()),
    IF_ELSE_THEN_KW(new IfElseThenKw()),
    IF_ENDING_KEYWORD(new IfEndingKeyword()),
    ASSIGN_OP(new AssignOp()),
    ASSIGN_STAT_SEMI(new AssignStatSemi),
    IO_STAT_SEMI(new IoStatSemi()),
    IO_VAR_ID(new IoVarId()),
    IO_ARR_RBRK(new IoVarRbrk),
    CALL_ID(new CallId),
    CALL_SEMI(new CallSemi()),
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
