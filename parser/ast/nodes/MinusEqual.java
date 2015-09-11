package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/5/2015
 * File Name:       MinusEqual
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class MinusEqual extends TreeNode {

    public MinusEqual() {
        super(Node.NMNEQ);
    }

    public MinusEqual( STRecord st) {
        super(Node.NMNEQ, st);
    }

    public MinusEqual( TreeNode l, TreeNode r) {
        super(Node.NMNEQ, l, r);
    }

    public MinusEqual( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NMNEQ, l, m, r);
    }
}
