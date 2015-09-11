package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    11/09/15
 * File Name:       ExpressionList
 * Project Name:    CD15
 * Description:
 */
public class ExpressionList extends TreeNode {

    public ExpressionList() {
        super(Node.NELIST);
    }

    public ExpressionList( STRecord st) {
        super(Node.NELIST, st);
    }

    public ExpressionList( TreeNode l, TreeNode r) {
        super(Node.NELIST, l, r);
    }

    public ExpressionList( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NELIST, l, m, r);
    }
}
