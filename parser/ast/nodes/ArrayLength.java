package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/14/2015
 * File Name:       ArrayLength
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class ArrayLength extends TreeNode {

    public ArrayLength() {
        super(Node.NLEN);
    }

    public ArrayLength( STRecord st) {
        super(Node.NLEN, st);
    }

    public ArrayLength( TreeNode l, TreeNode r) {
        super(Node.NLEN, l, r);
    }

    public ArrayLength( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NLEN, l, m, r);
    }
}
