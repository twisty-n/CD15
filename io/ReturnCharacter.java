package io;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    8/8/2015
 * File Name:       ReturnCharacter
 * Project Name:    CD15 Compiler
 * Description:     Encapsulates a character read from the InputController as passed to the Scanner
 */
public class ReturnCharacter {

    private char character;         // The consideration character
    private int indexOnLine;        // The index on the line of the character
    private int lineIndexInFile;    // The lines index in the file
    private String file;            // The file that the character belongs to

    public ReturnCharacter() {
        this.character = '~';
        this.indexOnLine = -1;
        this.lineIndexInFile = -1;
        this.file = null;
    }

    /**
     * Constructor
     * @param character
     * @param indexOnLine
     * @param lineIndexInFile
     * @param file
     */
    public ReturnCharacter( char character, int indexOnLine, int lineIndexInFile, String file ) {
        this.setCharacter(character);
        this.setIndexOnLine(indexOnLine);
        this.setLineIndexInFile(lineIndexInFile);
        this.setFile(file);
    }

    /* Access / mutate  */

    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    public int getIndexOnLine() {
        return indexOnLine;
    }

    public void setIndexOnLine(int indexOnLine) {
        this.indexOnLine = indexOnLine;
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
        String character = new Character(this.character).toString();
        switch (character) {
            case "\n" : character = "\\n"; break;
            case "\t" : character = "\\t"; break;
            case " " : character = "\\s";  break;
            default: break;
        }
        if (this.character == '\n') {
            character = "\\n";
        }
        return "ReturnCharacter{" +
                "character=" + character +
                ", indexOnLine=" + indexOnLine +
                ", lineIndexInFile=" + lineIndexInFile +
                ", file='" + file + '\'' +
                '}';
    }
}
