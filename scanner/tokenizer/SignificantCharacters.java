package scanner.tokenizer;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    8/12/2015
 * File Name:       SignificantCharacters
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public enum SignificantCharacters {

    DOT_OP('.'),
    NEW_LINE('\n'),
    TAB('\t'),
    SPACE(' '),
    LEFT_BRACE('['),
    RIGHT_BRACE(']'),
    COMMA(','),
    LEFT_PARAN('('),
    RIGHT_PARAN(')'),
    ASSIGN_OP('='),
    PLUS_OP('+'),
    MINUS_OP('-'),
    STAR_OP('*'),
    SLASH_OP('/'),
    LESS_OP('<'),
    GREAT_OP('>'),
    QUOTE('"'),
    SEMICOLON(';'),
    EXCLAM('!'),
    ZERO('0'),
    ;


    private final char asChar;

    public char asChar() {
        return asChar;
    }

    private SignificantCharacters(char asChar) {
        this.asChar = asChar;
    }

    /**
     * Returns true if the character is by itself an operator or delimiter, or forms a part of a
     * compound operator or delimiter
     * @param consider
     * @return
     */
    public static boolean isOperatorOrDelimiter(char consider) {

        switch ( consider ) {

            case ';': return true;
            case '[': return true;
            case ']': return true;
            case ',': return true;
            case '(': return true;
            case ')': return true;
            case '=': return true;
            case '+': return true;
            case '-': return true;
            case '*': return true;
            case '/': return true;
            case '<': return true;
            case '>': return true;
            case '"': return true;
            case '.': return true;
            //case '!': return true;
            default : return false;

        }

    }

    /**
     * Returns true if the character is a general component in a
     * multi character operator
     * This includes '<', '>', '+', '-', '*'
     * @param character
     * @return
     */
    public static boolean isGeneralMultiCharOpComponent(char character) {

        return  character == LESS_OP.asChar ||
                character == GREAT_OP.asChar ||
                character == PLUS_OP.asChar ||
                character == MINUS_OP.asChar ||
                character == STAR_OP.asChar;

    }

}
