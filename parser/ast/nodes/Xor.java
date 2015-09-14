package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/15/2015
 * File Name:       Xor
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class Xor extends TreeNode {

    public Xor() {

        super(Node.NXOR);
    }

    public Xor( STRecord st) {
        super(Node.NXOR, st);
    }

    public Xor( TreeNode l, TreeNode r) {
        super(Node.NXOR, l, r);
    }

    public Xor( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NXOR, l, m, r);
    }
}
