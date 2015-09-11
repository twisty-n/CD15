package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/5/2015
 * File Name:       PrintLine
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class PrintLine extends TreeNode {

    public PrintLine() {
        super(Node.NPRLN);
    }

    public PrintLine( STRecord st) {
        super(Node.NPRLN, st);
    }

    public PrintLine( TreeNode l, TreeNode r) {
        super(Node.NPRLN, l, r);
    }

    public PrintLine( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NPRLN, l, m, r);
    }
}
