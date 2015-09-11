package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/5/2015
 * File Name:       Print
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class Print extends TreeNode {

    public Print() {
        super(Node.NPRINT);
    }

    public Print( STRecord st) {
        super(Node.NPRINT, st);
    }

    public Print( TreeNode l, TreeNode r) {
        super(Node.NPRINT, l, r);
    }

    public Print( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NPRINT, l, m, r);
    }
}
