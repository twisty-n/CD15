package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/15/2015
 * File Name:       LessEquals
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class LessEquals extends TreeNode {

    public LessEquals() {
        super(Node.NLEQ);
    }

    public LessEquals( STRecord st) {
        super(Node.NLEQ, st);
    }

    public LessEquals( TreeNode l, TreeNode r) {
        super(Node.NLEQ, l, r);
    }

    public LessEquals( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NLEQ, l, m, r);
    }
}
