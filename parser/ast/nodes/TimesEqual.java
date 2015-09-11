package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/5/2015
 * File Name:       TimesEqual
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class TimesEqual extends TreeNode {

    public TimesEqual() {
        super(Node.NSTEQ);
    }

    public TimesEqual( STRecord st) {
        super(Node.NSTEQ, st);
    }

    public TimesEqual( TreeNode l, TreeNode r) {
        super(Node.NSTEQ, l, r);
    }

    public TimesEqual( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NSTEQ, l, m, r);
    }
}
