package scanner.tokenizer;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    14/08/15
 * File Name:       TokenFactory
 * Project Name:    CD15
 * Description:
 */
public class TokenFactory {

    public static Token constructToken(Lexeme lex) {

        // Build the token
        if ( ! lex.isValid() ) {

            return new Token(
                    TokenClass.TUNDF, lex.getStartLineIndex(),
                    lex.getEndLineIndex(), lex.getLineIndexInFile(),
                    lex.getFile(), lex.getLexemeVal());

        }

        if ( lex.isComment() ) {
            return null;
        }

        if ( lex.hasSuggestion() ) {

            // if we have a token class provided by the FSM use it
            if ( lex.getTokenSuggestion() == TokenClass.TIDNT ) {

                // Make sure that we haven't got a keyword
                Token t = new Token(
                        TokenFactory.getIdentOrKeywordClass(lex.getLexemeVal()),
                        lex.getStartLineIndex(),
                        lex.getEndLineIndex(), lex.getLineIndexInFile(),
                        lex.getFile(), lex.getLexemeVal()
                );
                if ( t.getTokenClass() != TokenClass.TIDNT ) {
                    // its a keyword, force lexeme to null
                    t.forceTokenLexemeMutation(null);
                }
                return t;

            } else {

                // Return return the token using the token suggestion
                return new Token(
                        lex.getTokenSuggestion(),
                        lex.getStartLineIndex(),
                        lex.getEndLineIndex(),
                        lex.getLineIndexInFile(),
                        lex.getFile(),
                        lex.getLexemeVal()
                );
            }
        } else {

            // The lexeme has no suggestion, so it must be an op/delim. Classify
            return new Token(
                    TokenFactory.getOpOrDelimClass(lex.getLexemeVal()),
                    lex.getStartLineIndex(),
                    lex.getEndLineIndex(),
                    lex.getLineIndexInFile(),
                    lex.getFile(),
                    null
            );
        }
    }

    private static TokenClass getOpOrDelimClass(String s) {

        switch ( s ) {

            case ";"        :    return  TokenClass.TSEMI;
            case "["        :    return  TokenClass.TLBRK;
            case "]"        :    return  TokenClass.TRBRK;
            case ","        :    return  TokenClass.TCOMA;
            case "("        :    return  TokenClass.TLPAR;
            case ")"        :    return  TokenClass.TRPAR;
            case "="        :    return  TokenClass.TASGN;
            case "+"        :    return  TokenClass.TPLUS;
            case "-"        :    return  TokenClass.TSUBT;
            case "*"        :    return  TokenClass.TMULT;
            case "/"        :    return  TokenClass.TDIVD;
            case "<"        :    return  TokenClass.TGRTR;
            case ">"        :    return  TokenClass.TLESS;
            case "."        :    return  TokenClass.TDOTT;
            case "<="       :    return  TokenClass.TLEQL;
            case ">="       :    return  TokenClass.TGREQ;
            case "!="       :    return  TokenClass.TNEQL;
            case "=="       :    return  TokenClass.TDEQL;
            case "+="       :    return  TokenClass.TPLEQ;
            case "-="       :    return  TokenClass.TMNEQ;
            case "*="       :    return  TokenClass.TMLEQ;
            case "/="       :    return  TokenClass.TDVEQ;
            default         :    return  TokenClass.TUNDF;      // Hopefully we dont have this happening
        }

    }

    private static TokenClass getIdentOrKeywordClass(String s) {

        s = s.toLowerCase();		// change to lower case before checking

        switch ( s ) {

            case "program"  : return TokenClass.TPROG;
            case "end"      : return TokenClass.TENDK;
            case "arrays"   : return TokenClass.TARRS;
            case "proc"     : return TokenClass.TPROC;
            case "var"      : return TokenClass.TVARP;
            case "val"      : return TokenClass.TVALP;
            case "local"    : return TokenClass.TLOCL;
            case "loop"     : return TokenClass.TLOOP;
            case "exit"     : return TokenClass.TEXIT;
            case "when"     : return TokenClass.TWHEN;
            case "if"       : return TokenClass.TIFKW;
            case "else"     : return TokenClass.TELSE;
            case "then"     : return TokenClass.TTHEN;
            case "elsif"    : return TokenClass.TELSF;
            case "call"     : return TokenClass.TCALL;
            case "with"     : return TokenClass.TWITH;
            case "input"    : return TokenClass.TINPT;
            case "print"    : return TokenClass.TPRIN;
            case "printline": return TokenClass.TPRLN;
            case "not"      : return TokenClass.TNOTK;
            case "and"      : return TokenClass.TANDK;
            case "or"       : return TokenClass.TORKW;
            case "xor"      : return TokenClass.TXORK;
            case "div"      : return TokenClass.TIDIV;
            case "length"   : return TokenClass.TLENG;
            default         : return TokenClass.TIDNT;

        }
    }
}
