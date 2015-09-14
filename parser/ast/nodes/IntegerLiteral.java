package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/14/2015
 * File Name:       IntegerLiteral
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class IntegerLiteral extends TreeNode {

    public IntegerLiteral() {
        super(Node.NILIT);
    }

    public IntegerLiteral( STRecord st) {
        super(Node.NILIT, st);
    }

    public IntegerLiteral( TreeNode l, TreeNode r) {
        super(Node.NILIT, l, r);
    }

    public IntegerLiteral( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NILIT, l, m, r);
    }
}
