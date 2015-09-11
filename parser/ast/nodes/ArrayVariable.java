package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    11/09/15
 * File Name:       ArrayVariable
 * Project Name:    CD15
 * Description:
 */
public class ArrayVariable extends TreeNode {

    public ArrayVariable() {
        super(Node.NARRVAR);
    }

    public ArrayVariable( STRecord st) {
        super(Node.NARRVAR, st);
    }

    public ArrayVariable( TreeNode l, TreeNode r) {
        super(Node.NARRVAR, l, r);
    }

    public ArrayVariable( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NARRVAR, l, m, r);
    }
}
