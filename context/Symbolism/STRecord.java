package context.symbolism;

import context.error.Lexemable;
import scanner.tokenizer.TokenClass;

import java.util.ArrayList;
import java.util.Collection;
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
public class STRecord implements Lexemable{

    private TokenClass tokenClass;
    private String lexemeString;
    private ArrayList<LCTuple> references;

    /*
    A scope of a STRecord is a collection of scopes in whith it was used
    Each scope has a set of properties associated with the variables use in that scope
     */
    private HashMap<String, HashMap<String, Property>> scopes;

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
        this.tokenClass = tokenClass;
        this.references = new ArrayList<>();
       scopes = new HashMap<>();
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
    public STRecord attachProperties(Map<String, Property> properties, String scope) {
        HashMap scopeMap = this.scopes.get(scope);
        if (scopeMap == null) {
            return null;
        }
        scopeMap.putAll(properties);
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
     *
     * // TODO specify a generic type that we are passing in in order to autocast on the way out
     *
     * @param descriptor
     * @return
     */
    public Property getProperty(String descriptor, String scope) {
        if (this.scopes.get(scope) == null) return null;
        return this.scopes.get(scope).get(descriptor);
    }

    public <K> K getPropertyValue(String descriptor, String scope, Class<K>a) {
        HashMap scopeCol = this.scopes.get(scope);
        if (scopeCol == null) return null;
        Property prop = (Property) scopeCol.get(descriptor);
        if (prop == null) { return null; }
        try {
            K val = (K)prop.getValue();
            return val;
        } catch (ClassCastException e) {
            return null;
        }
    }

    /**
     * Set and return a property on this STRecord
     *
     * A scope is only created when the first property is added to it
     *
     * @param descriptor
     * @param property
     * @return
     */
    public Property addProperty(String descriptor, String scope, Property property) {
        HashMap scopeMap = scopes.get(scope);
        if (scopeMap == null) {
            scopes.put(scope, new HashMap<String, Property>());
        }
        this.scopes.get(scope).put(descriptor, property);
        return property;
    }

    /**
     * Returns a syb
     * @param scope
     * @return
     */
    public HashMap scope(String scope) {
        return this.scopes.get(scope);
    }

    /**
     *
     * @param scope
     * @return
     */
    public boolean existsInScope(String scope) {
        return scopes.get(scope) != null;
    }

    @Override
    public int getLineIndexInFile() {
        return this.references.get(this.references.size()-1).getLineNumber();
    }

    @Override
    public int getStartLineIndex() {
        return this.references.get(this.references.size() - 1).getColumnNumberStart();
    }

    @Override
    public int getEndLineIndex() {
        return this.references.get(this.references.size()-1).getColumnNumberEnd();
    }

    @Override
    public String getLexemeVal() {
        return this.getLexemeString();
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
