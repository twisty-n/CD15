package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/14/2015
 * File Name:       Not
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class Not extends TreeNode {

    public Not() {
        super(Node.NNOT);
    }

    public Not( STRecord st) {
        super(Node.NNOT, st);
    }

    public Not( TreeNode l, TreeNode r) {
        super(Node.NNOT, l, r);
    }

    public Not( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NNOT, l, m, r);
    }
}
