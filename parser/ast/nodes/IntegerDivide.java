package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/14/2015
 * File Name:       IntegerDivide
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class IntegerDivide extends TreeNode {

    public IntegerDivide() {
        super(Node.NIDIV);
    }

    public IntegerDivide( STRecord st) {
        super(Node.NIDIV, st);
    }

    public IntegerDivide( TreeNode l, TreeNode r) {
        super(Node.NIDIV, l, r);
    }

    public IntegerDivide( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NIDIV, l, m, r);
    }
}
