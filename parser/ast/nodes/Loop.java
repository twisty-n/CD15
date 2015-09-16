package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/5/2015
 * File Name:       Loop
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class Loop extends TreeNode {

    public Loop() {
        super(Node.NLOOP);
    }

    public Loop( STRecord st) {
        super(Node.NLOOP, st);
    }

    public Loop( TreeNode l, TreeNode r) {
        super(Node.NLOOP, l, r);
    }

    public Loop( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NLOOP, l, m, r);
    }
}
