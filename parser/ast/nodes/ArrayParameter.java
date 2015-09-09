package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/4/2015
 * File Name:       ArrayParameter
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class ArrayParameter extends TreeNode {

    //NARRPAR

    public ArrayParameter() {
        super(Node.NARRPAR);
    }

    public ArrayParameter( STRecord st) {
        super(Node.NARRPAR, st);
    }

    public ArrayParameter( TreeNode l, TreeNode r) {
        super(Node.NARRPAR, l, r);
    }

    public ArrayParameter( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NARRPAR, l, m, r);
    }
}
