package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    11/09/15
 * File Name:       SimpleVariable
 * Project Name:    CD15
 * Description:
 */
public class SimpleVariable extends TreeNode {

    public SimpleVariable() {
        super(Node.NSIMVAR);
    }

    public SimpleVariable( STRecord st) {
        super(Node.NSIMVAR, st);
    }

    public SimpleVariable( TreeNode l, TreeNode r) {
        super(Node.NSIMVAR, l, r);
    }

    public SimpleVariable( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NSIMVAR, l, m, r);
    }
}
