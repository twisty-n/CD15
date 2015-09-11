package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/5/2015
 * File Name:       Call
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class Call extends TreeNode {

    public Call() {
        super(Node.NCALL);
    }

    public Call( STRecord st) {
        super(Node.NCALL, st);
    }

    public Call( TreeNode l, TreeNode r) {
        super(Node.NCALL, l, r);
    }

    public Call( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NCALL, l, m, r);
    }
}
