package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/14/2015
 * File Name:       Division
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class Division extends TreeNode {

    public Division() {
        super(Node.NDIV);
    }

    public Division( STRecord st) {
        super(Node.NDIV, st);
    }

    public Division( TreeNode l, TreeNode r) {
        super(Node.NDIV, l, r);
    }

    public Division( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NDIV, l, m, r);
    }
}
