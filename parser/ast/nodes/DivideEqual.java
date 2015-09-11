package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/5/2015
 * File Name:       DivideEqual
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class DivideEqual extends TreeNode {

    public DivideEqual() {
        super(Node.NDVEQ);
    }

    public DivideEqual( STRecord st) {
        super(Node.NDVEQ, st);
    }

    public DivideEqual( TreeNode l, TreeNode r) {
        super(Node.NDVEQ, l, r);
    }

    public DivideEqual( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NDVEQ, l, m, r);
    }
}
