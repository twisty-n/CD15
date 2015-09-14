package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/15/2015
 * File Name:       GreaterEquals
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class GreaterEquals extends TreeNode {

    public GreaterEquals() {
        super(Node.NGEQ);
    }

    public GreaterEquals( STRecord st) {
        super(Node.NGEQ, st);
    }

    public GreaterEquals( TreeNode l, TreeNode r) {
        super(Node.NGEQ, l, r);
    }

    public GreaterEquals( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NGEQ, l, m, r);
    }
}
