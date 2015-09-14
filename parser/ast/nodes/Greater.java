package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/15/2015
 * File Name:       Greater
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class Greater extends TreeNode {

    public Greater() {
        super(Node.NGTR);
    }

    public Greater( STRecord st) {
        super(Node.NGTR, st);
    }

    public Greater( TreeNode l, TreeNode r) {
        super(Node.NGTR, l, r);
    }

    public Greater( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NGTR, l, m, r);
    }
}
