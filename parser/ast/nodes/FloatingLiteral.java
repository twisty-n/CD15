package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/14/2015
 * File Name:       FloatingLiteral
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class FloatingLiteral extends TreeNode {

    public FloatingLiteral() {
        super(Node.NFLIT);
    }

    public FloatingLiteral( STRecord st) {
        super(Node.NFLIT, st);
    }

    public FloatingLiteral( TreeNode l, TreeNode r) {
        super(Node.NFLIT, l, r);
    }

    public FloatingLiteral( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NFLIT, l, m, r);
    }
}
