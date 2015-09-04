package context.Symbolism;

import scanner.tokenizer.Token;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/4/2015
 * File Name:       SymbolTable
 * Project Name:    CD15 Compiler
 * Description:     Describes the format of a symbol table that can be used
 */
public class SymbolTable {

    private Map<String, STRecord> table;

    public SymbolTable() {
        this.table = new HashMap<>();
    }

    /**
     * Constructor that will populate the symbol table with a set of inital values
     * @param keywords
     */
    public SymbolTable(Map<String, STRecord> keywords) {
        this.table = new HashMap<>();
        this.table.putAll(keywords);
    }

    /**
     * Returns the STrecord associated with this tokens lexeme
     * OR null if the lexeme does not exist
     * @param token
     * @return
     */
    public STRecord lookup(Token token) {
        return this.lookup(token.getLexeme());
    }

    /**
     *
     * Returns the STrecord associated with this lexeme
     * OR null if the lexeme does not exist
     *
     */
    public STRecord lookup(String lexeme) {
        return table.get(lexeme);
    }

    /**
     * Creates and stores a STRecord from this token if it does not exist
     * If the STRecord does exist, it will simply be updated with referencing information
     * extracted from this token
     * @param token
     */
    public void insert(Token token) {
        if (!seenYet(token)) {
            // Insert a new STRecord associated with the lexeme
            STRecord record = this.table.put(token.getLexeme(), new STRecord(
                    token.getTokenClass(),
                    token.getLexeme(),
                    token.getLineIndexInFile(),
                    token.getCharacterStartPositionOnLine(),
                    token.getCharacterEndPositionOnLine()
            ));
            if (token.getTokenClass().isLiteral()) {
                record.addProperty("isLiteral", new Property<>(true));
            }
        } else {
            this.table.get(token.getLexeme()).addReference(
                    token.getLineIndexInFile(),
                    token.getCharacterStartPositionOnLine(),
                    token.getCharacterEndPositionOnLine()
            );
        }
    }

    /**
     * Will try to lookup a STrecord associated with the tokens lexeme
     * if there is no STRecord present, one will be created, and a reference
     * to it returned
     * @param token
     * @return
     */
    public STRecord lookupOrInsert(Token token) {
        this.insert(token);
        return this.lookup(token);
    }

    /**
     * Returns true if a particular STRecord has been placed in the table
     * OR false if no current record exists
     * @param token
     * @return
     */
    public boolean seenYet(Token token) {
        return table.containsKey(token.getLexeme());
    }

    /**
     * Returns true if a particular STRecord has been placed in the table
     * OR false if no current record exists
     * @param lexeme
     * @return
     */
    public boolean seenYet(String lexeme) {
        return table.containsKey(lexeme);
    }

}
