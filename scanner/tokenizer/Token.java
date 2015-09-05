package scanner.tokenizer;

import context.symbolism.STRecord;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    8/8/2015
 * File Name:       Token
 * Project Name:    CD15 Compiler
 * Description:     See description for the provided code class below
 */
// COMP3290 CD15 Compiler
//
//	Token class	- constructs a token on behalf of the scanner for it to be sent to the parser.
//			- IDNTs/ILITs/FLITs/Strings do not have their symbol table reference set in this class,
//			    this is best done within the parser as it makes things easier in later phases,
//			    when we are dealing with things like variable scoping.
//
//			The class contains the usual constructor, get and set methods required of such a class.
//			Method checkKeyWords is a skeleton for changing <id>s into relevant Keywords as required.
//			Method toString() provides a dump of the Token contents useful for debugging.
//			Method shortString() provides a String that may be useful for Part 1 output.
//
//    Rules of Use: The text for this class has been extracted from a working CD15 scanner.
//			  Code released via Blackboard may not be passed on to anyone outside this
//			  semester's COMP3290 class.
//			  You may not complain or expect any consideration if the code does not work
//			  the way you expect it to.
//			  It is supplied as an assistance and may be used in your project if you wish.
//
//	M.Hannaford	04-Aug-2015
//
//
public class Token {

    private TokenClass tokenClass;	            // token identifier
    private int lineIndexInFile;		        // lineIndexInFile number on listing
    private int characterStartPositionOnLine;   // character starting position within lineIndexInFile
    private int characterEndPositionOnLine;		// character ending position within lineIndexInFile
    private String file;                        // The physical file that the token belongs to. Potential support for multifile
    private String lexeme;		                // actual lexeme character string from scanner
    private STRecord symbol;	// symbol table entry - used in Pt3 for the Parser, not needed in Pt1

    public Token(TokenClass tokenClass, int lineStartingPosition,
                 int lineEndingPosition,  int lineIndexInFile,
                 String file, String lexeme) {
        this.tokenClass = tokenClass;
        this.lineIndexInFile = lineIndexInFile;
        this.characterStartPositionOnLine = lineStartingPosition;
        this.characterEndPositionOnLine = lineEndingPosition;
        this.lexeme = lexeme;
        this.file = file;
		symbol = null;
    }

    public TokenClass getTokenClass() { return tokenClass; }
    public int getLineIndexInFile() { return lineIndexInFile; }
    public int getCharacterStartPositionOnLine() { return characterStartPositionOnLine; }
    public int getCharacterEndPositionOnLine() { return characterEndPositionOnLine; }
    public String getLexeme() { return lexeme; }
    public String getFile() { return this.file; }

    public STRecord getSymbol() {
        return symbol;
    }

    public void setSymbol(STRecord symbol) {
        this.symbol = symbol;
    }

    public Lexeme getProperLexeme() {
        if (this.lexeme == null)
            this.lexeme = this.tokenClass.val();
        return new Lexeme(this.characterStartPositionOnLine, this.characterEndPositionOnLine, this.lineIndexInFile, this.file, this.lexeme);
    }

    // HERE BE DRAGONS

    public String toString() {		// toString method is only meant to be used for debug printing
        String s = tokenClass.toString();
        while (s.length() % 6 != 0) s = s + " ";
        s = s +" " + lineIndexInFile + " " + characterStartPositionOnLine;
        if (lexeme == null) return s;
        if (tokenClass != TokenClass.TUNDF)
            s += " " + lexeme;
        else {
            s += " ";
            for (int i=0; i< lexeme.length(); i++) { // output non-printables as ascii codes
                char ch = lexeme.charAt(i);
                int j = (int)ch;
                if (j <= 31 || j >= 127) s += "\\" +j; else s += ch;
            }
        }
        return s;
    }

    public String shortString() {		// provides a String that may be useful for Part 1 printed output
        String s = tokenClass.name() + " ";
        if (lexeme == null) return s;
        if (tokenClass != TokenClass.TUNDF) {
            if (tokenClass == TokenClass.TSTRG)
                s += "\"" + lexeme + "\" ";
            else
                s += lexeme + " ";
            int j = (6 - s.length()%6) % 6;
            for (int i=0; i<j; i++)
                s += " ";
            return s;
        }
        //s = "\n" + s;
        for (int i=0; i< lexeme.length(); i++) { // output non-printables as ascii codes
            char ch = lexeme.charAt(i);
            int j = (int)ch;
            if (j <= 31 || j >= 127) s += "\\" +j; else s += ch;
        }
        //s += "\n";
        return s;
    }

    public void forceTokenLexemeMutation(String lex) {
        this.lexeme = lex;
    }

    public static Map<String, STRecord> generateKeywords() {
        // TODO
        return new HashMap<>();
    }

}