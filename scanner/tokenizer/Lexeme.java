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

    private String lexeme;
    private int startLineIndex;
    private int endLineIndex;
    private int lineIndexInFile;
    private String file;

    /* Access / Mutate */

    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
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
}
