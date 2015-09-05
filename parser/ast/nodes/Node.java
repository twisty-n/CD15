package parser.ast.nodes;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/4/2015
 * File Name:       Node
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public enum Node {
    NUNDEF, NPROG, NARRYL, NPROCL, NMAIN, NPROC, NPLIST, NSIMPAR, NARRPAR,
    NDLIST, NSIMDEC, NARRDEC, NARRVAR, NSIMVAR, NSLIST,
    NLOOP, NEXIT, NIFT, NIFTE, NINPUT, NPRINT, NPRLN, NCALL, NELIST,
    NASGN, NPLEQ, NMNEQ, NSTEQ, NDVEQ, NADD, NSUB, NMUL, NDIV, NIDIV,
    NNOT, NAND, NOR, NXOR, NEQL, NNEQ, NGTR, NLESS, NGEQ, NLEQ,
    NILIT, NFLIT, NIDLST, NVLIST, NPRLIST, NLEN, NSTRG
};