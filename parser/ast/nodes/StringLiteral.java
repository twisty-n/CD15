package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    11/09/15
 * File Name:       StringLiteral
 * Project Name:    CD15
 * Description:
 */
public class StringLiteral extends TreeNode {

    public StringLiteral() {
        super(Node.NSTRG);
    }

    public StringLiteral( STRecord st) {
        super(Node.NSTRG, st);
    }

    public StringLiteral( TreeNode l, TreeNode r) {
        super(Node.NSTRG, l, r);
    }

    public StringLiteral( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NSTRG, l, m, r);
    }
}
