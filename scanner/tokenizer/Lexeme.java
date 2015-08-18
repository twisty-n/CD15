package scanner.tokenizer;

import io.ReturnCharacter;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    8/8/2015
 * File Name:       Lexeme
 * Project Name:    CD15 Compiler
 * Description:     The FSM will spit out a lexeme which the Tokenizer will use to build a Token
 */
public class Lexeme {

    private StringBuffer lexeme;
    private int startLineIndex;
    private int endLineIndex;
    private int lineIndexInFile;
    private String file;
    private boolean isValid;
    private boolean isComment;

    private boolean justBuilt;
    private boolean isComplete;
    private TokenClass tokenSuggestion;     // Assigned by the FSM if lexeme requires classification from state
                                            // That is, if we can't use implicit classification
                                            // it will take a value of INT_LIT, FLT_LIT or STRING_CONSTANT
                                            // OR TUNDF if we are invalid

    public boolean justBuilt() {
        return this.justBuilt;
    }


    public Lexeme() {
        this(0, 0, 0, "");
        this.isComplete = false;
        this.isValid = false;
        this.isComment = false;
        this.tokenSuggestion = null;
        this.justBuilt = true;
    }

    public Lexeme(int startLineIndex, int endLineIndex, int lineIndexInFile, String file) {
        this.lexeme = new StringBuffer();
        this.startLineIndex = startLineIndex;
        this.endLineIndex = endLineIndex;
        this.lineIndexInFile = lineIndexInFile;
        this.file = file;
        this.isValid = false;
        this.isComment = false;
        this.tokenSuggestion = null;
        this.justBuilt = true;
    }

    /**
     * Adds a character to the lexeme
     * The first time you push a character to the lexeme, it will set its starting information
     * @param c
     */
    public void addCharToLexeme(ReturnCharacter c) {

        if (this.lexeme.length() == 0) {
            this.startLineIndex = c.getIndexOnLine();
            this.lineIndexInFile = c.getLineIndexInFile();
            this.justBuilt = false;
        }
        this.lexeme.append(c.getCharacter());
    }

    /**
     * Returns the value of the lexeme as a string
     * @return
     */
    public String getLexemeVal() {
        return this.lexeme.toString();
    }

    /* Access / Mutate */

    public boolean isComplete() {
        return isComplete;
    }

    /**
     * When setting the lexeme as being complete, we will also store the end line index for it
     *
     * @param isComplete        Should always be passed true, I dont know why this is here
     * @param endLineIndex      The column number in which this leexeme ended
     * @param isValid           Indicates if the state machine finished in an accepting state when
     *                          constructing this lexeme
     */
    public void setIsComplete(boolean isComplete, int endLineIndex, boolean isValid) {
        this.endLineIndex = endLineIndex;
        this.isComplete = isComplete;
        this.isValid = isValid;
    }

    /**
     * Sets the lexeme as complete stores the provided information
     * @param isComplete        As for above
     * @param endLineIndex      As for above
     * @param isValid           As for above
     * @param isComment         Pass true to set this lexeme as a comment listing
     */
    public void setIsComplete(boolean isComplete, int endLineIndex, boolean isValid, boolean isComment) {
        this.setIsComplete(isComplete, endLineIndex, isValid);
        this.isComment = isComment;
    }

    /**
     * Set a lexeme as being complete and mark it with a classification suggestion
     * @param isComplete
     * @param endLineIndex
     * @param isValid
     * @param tokenSuggestion
     */
    public void setIsComplete(boolean isComplete, int endLineIndex, boolean isValid, TokenClass tokenSuggestion) {
        this.setIsComplete(isComplete, endLineIndex, isValid);
        this.tokenSuggestion = tokenSuggestion;
    }

    /**
     * Returns true if this token has a token suggestion
     * @return
     */
    public boolean hasSuggestion() { return this.tokenSuggestion != null; }

    public boolean isComment() { return this.isComment; }

    public boolean isValid() { return this.isValid; }

    public int getStartLineIndex() {
        return startLineIndex;
    }

    public void setStartLineIndex(int startLineIndex) {
        this.startLineIndex = startLineIndex;
    }

    public int getEndLineIndex() {
        return endLineIndex;
    }

    public void setEndLineIndex(int endLineIndex) {
        this.endLineIndex = endLineIndex;
    }

    public int getLineIndexInFile() {
        return lineIndexInFile;
    }

    public void setLineIndexInFile(int lineIndexInFile) {
        this.lineIndexInFile = lineIndexInFile;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public TokenClass getTokenSuggestion() {

        // If this token is invalid, we will return TUNDF
        // if it has a suggestion, return it, else return null
        if ( ! this.isValid ) {
            return TokenClass.TUNDF;
        }
        return this.tokenSuggestion;

    }

    /**
     * Wraps the current lexeme in quotation marks
     */
    public void wrapLexemeInQuotes() {

        this.lexeme.append('"');
        this.lexeme.insert(0, '"');

    }

    @Override
    public String toString() {
        return "Lexeme{" +
                "lexeme=" + lexeme +
                ", \tstartLineIndex=" + startLineIndex +
                ", \tendLineIndex=" + endLineIndex +
                ", \tlineIndexInFile=" + lineIndexInFile +
                ", \tisComplete=" + isComplete +
                ", \tisValid=" + isValid +
                ", \tisComment=" + isComment +
                '}';
    }
}
