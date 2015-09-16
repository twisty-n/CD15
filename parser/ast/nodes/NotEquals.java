package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/15/2015
 * File Name:       NotEquals
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class NotEquals extends TreeNode {

    public NotEquals() {
        super(Node.NNEQ);
    }

    public NotEquals( STRecord st) {
        super(Node.NNEQ, st);
    }

    public NotEquals( TreeNode l, TreeNode r) {
        super(Node.NNEQ, l, r);
    }

    public NotEquals( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NNEQ, l, m, r);
    }
}