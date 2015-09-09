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

public enum TokenClass {	TEOF("END OF FILE"),   // End of File Token

    // The 25 keywords come first
    TPROG("program"),
    TENDK("end"),
    TARRS("arrays"),
    TPROC("proc"),
    TVARP("var"),
    TVALP("val"),
    TLOCL("local"),
    TLOOP("loop"),
    TEXIT("exit"),
    TWHEN("when"),
    TCALL("call"),
    TWITH("with"),
    TIFKW("if"),
    TTHEN("then"),
    TELSE("else"),
    TELSF("elsif"),
    TINPT("input"),
    TPRIN("print"),
    TPRLN("printline"),
    TNOTK("not"),
    TANDK("and"),
    TORKW("or"),
    TXORK("xor"),
    TIDIV("div"),
    TLENG("length"),

    // then the operators and delimiters
    TLBRK("["), TRBRK("]"), TLPAR("("), TRPAR(")"), TSEMI(";"), TCOMA(","), TDOTT("."), TASGN("="), TPLEQ("+="), TMNEQ("-="), TMLEQ("*="),
    TDVEQ("/*"), TDEQL("=="), TNEQL("!="), TGRTR(">"), TLEQL("<="), TLESS("<"), TGREQ(">="), TPLUS("+"), TSUBT("-"), TMULT("*"), TDIVD("/"),

    // then the tokens (or pseudo-tokens) with non-null tuple values
    TIDNT("identifier"), TILIT("Integer Literal"), TFLIT("FP Literal"), TSTRG("String Literal"), TUNDF("Undefined");

    public boolean isLiteral() {
         return this.equals(TILIT) || this.equals(TFLIT) || this.equals(TSTRG);
    }

    public boolean isIdentifier() {
        return this.equals(TIDNT);
    }

    public boolean isEOF() { return this.equals(TEOF); }
    public String val() {return this.val;}
    private String val;
    private TokenClass(String val) {this.val = val;}


};