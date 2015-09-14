package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/15/2015
 * File Name:       Less
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class Less extends TreeNode {

    public Less() {
        super(Node.NLESS);
    }

    public Less( STRecord st) {
        super(Node.NLESS, st);
    }

    public Less( TreeNode l, TreeNode r) {
        super(Node.NLESS, l, r);
    }

    public Less( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NLESS, l, m, r);
    }
}
