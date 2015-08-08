package scanner.tokenizer;

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

    private boolean isComplete;

    public Lexeme() {
        this(0, 0, 0, "");
        this.isComplete = false;
    }

    public Lexeme(int startLineIndex, int endLineIndex, int lineIndexInFile, String file) {
        this.lexeme = new StringBuffer();
        this.startLineIndex = startLineIndex;
        this.endLineIndex = endLineIndex;
        this.lineIndexInFile = lineIndexInFile;
        this.file = file;
    }

    /**
     * Adds a character to the lexeme
     * @param c
     */
    public void addCharToLexeme(char c) {
        this.lexeme.append(c);
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

    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

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

    @Override
    public String toString() {
        return "Lexeme{" +
                "lexeme=" + lexeme +
                ", startLineIndex=" + startLineIndex +
                ", endLineIndex=" + endLineIndex +
                ", lineIndexInFile=" + lineIndexInFile +
                ", isComplete=" + isComplete +
                '}';
    }
}
