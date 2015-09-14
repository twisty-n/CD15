package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/14/2015
 * File Name:       Addition
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class Addition extends TreeNode {

    public Addition() {
        super(Node.NADD);
    }

    public Addition( STRecord st) {
        super(Node.NADD, st);
    }

    public Addition( TreeNode l, TreeNode r) {
        super(Node.NADD, l, r);
    }

    public Addition( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NADD, l, m, r);
    }
}
