package context.symbolism;

import scanner.tokenizer.TokenClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/4/2015
 * File Name:       STRecord
 * Project Name:    CD15 Compiler
 * Description:     A flexible base symbol table record that can be used
 *                  for general symbol items
 */
public class STRecord {

    private TokenClass tokenClass;
    private String lexemeString;
    private ArrayList<LCTuple> references;
    private HashMap<String, Property> properties;

    /**
     *
     * @param lexemeString      The lexeme for this item
     * @param initialLine       The initial line this item was recorded at being
     * @param initialColumnStart     The initial column of this record
     */
    public STRecord(TokenClass tokenClass,
                    String lexemeString,
                    int initialLine,
                    int initialColumnStart,
                    int initialColumnEnd) {
        this.lexemeString = lexemeString;
        this.tokenClass =tokenClass;
        this.references = new ArrayList<>();
        this.properties = new HashMap<>();
        this.references.add(new LCTuple(initialLine,
                initialColumnStart, initialColumnEnd));
    }

    /**
     * Adds another program refernce to this STRecord
     * @param lineNumber
     * @param columnNumberStart
     * @param columnNumberStart
     */
    public void addReference(int lineNumber, int columnNumberStart, int columnNumberEnd) {
        this.references.add(new LCTuple(lineNumber, columnNumberStart, columnNumberEnd));
    }

    /**
     * Returns the amount of times this STRecord has been referenced
     * @return
     */
    public int getReferenceCount() {
        return this.references.size();
    }

    /**
     * Attaches a group of properties to this STRecord
     * @param properties
     * @return
     */
    public STRecord attachProperties(Map<String, Property> properties) {
        this.properties.putAll(properties);
        return this;
    }

    public TokenClass getTokenClass() {
        return tokenClass;
    }

    public void setTokenClass(TokenClass tokenClass) {
        this.tokenClass = tokenClass;
    }

    public String getLexemeString() {
        return lexemeString;
    }

    public void setLexemeString(String lexemeString) {
        this.lexemeString = lexemeString;
    }

    public ArrayList<LCTuple> getReferences() {
        return references;
    }

    public void setReferences(ArrayList<LCTuple> references) {
        this.references = references;
    }

    /**
     * Retrieves a property of this STRecord
     * @param descriptor
     * @return
     */
    public Property getProperty(String descriptor) {
        return properties.get(descriptor);
    }

    /**
     * Set and return a property on this STRecord
     * @param descriptor
     * @param property
     * @return
     */
    public Property addProperty(String descriptor, Property property) {
        this.properties.put(descriptor, property);
        return property;
    }

    /**
     * Encapsulates a line/column number pair for some particular instance
     */
    public class LCTuple {
        int lineNumber;
        int columnNumberStart;
        int columnNumberEnd;

        public LCTuple(int lineNumber, int columnNumberStart, int columnNumberEnd) {
            this.lineNumber = lineNumber;
            this.columnNumberStart = columnNumberStart;
            this.columnNumberEnd = columnNumberEnd;
        }

        public int getLineNumber() {
            return lineNumber;
        }

        public int getColumnNumberStart() {
            return columnNumberStart;
        }

        public int getColumnNumberEnd() {
            return columnNumberEnd;
        }

    }

}
