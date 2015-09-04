package context.Symbolism;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/4/2015
 * File Name:       Property
 * Project Name:    CD15 Compiler
 * Description:     Describes a general property of a symbol table record
 */
public class Property<K> {
    K value;
    Property(K value) { this.value = value; }
    public K getValue() { return this.value; }
}
