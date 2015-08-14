package scanner.tokenizer;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    8/8/2015
 * File Name:       TokenClass
 * Project Name:    CD15
 * Description:     See description for provided class below
 */


// COMP3290 CD15 Compiler
//
//	TokId enum -	Enumeration of Token Values for Scanner.
//		  	Ensures that the Token values are public to whole compiler.
//
//			The enumeration values are the ones expected to be printed as strings in part 1.
//
//			Enumeration values can be turned into Strings with t1.name().
//			Enumeration values are Comparable, i.e. t1.compareTo(t2) gives the usual answers,
//				   however != and == still work directly.
//
//	Rules of Use:   Please look at the rules for the released OutputController.
//			Similar rules apply here.
//
// M.Hannaford
// 04-Aug-2015
//

public enum TokenClass {	TEOF,   // End of File Token

    // The 25 keywords come first
    TPROG, TENDK, TARRS, TPROC, TVARP, TVALP, TLOCL, TLOOP, TEXIT, TWHEN, TCALL,
    TWITH, TIFKW, TTHEN, TELSE, TELSF, TINPT, TPRIN, TPRLN, TNOTK, TANDK, TORKW,
    TXORK, TIDIV, TLENG,

    // then the operators and delimiters
    TLBRK, TRBRK, TLPAR, TRPAR, TSEMI, TCOMA, TDOTT, TASGN, TPLEQ, TMNEQ, TMLEQ,
    TDVEQ, TDEQL, TNEQL, TGRTR, TLEQL, TLESS, TGREQ, TPLUS, TSUBT, TMULT, TDIVD,

    // then the tokens (or pseudo-tokens) with non-null tuple values
    TIDNT, TILIT, TFLIT, TSTRG, TUNDF
};