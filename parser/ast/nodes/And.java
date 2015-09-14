package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/15/2015
 * File Name:       And
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class And extends TreeNode {

    public And() {
        super(Node.NAND);
    }

    public And( STRecord st) {
        super(Node.NAND, st);
    }

    public And( TreeNode l, TreeNode r) {
        super(Node.NAND, l, r);
    }

    public And( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NAND, l, m, r);
    }
}
