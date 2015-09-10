package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    10/09/15
 * File Name:       ProcLocals
 * Project Name:    CD15
 * Description:
 */
public class ProcLocals extends TreeNode {

    public ProcLocals() {
        super(Node.NDLIST);
    }

    public ProcLocals( STRecord st) {
        super(Node.NDLIST, st);
    }

    public ProcLocals( TreeNode l, TreeNode r) {
        super(Node.NDLIST, l, r);
    }

    public ProcLocals( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NDLIST, l, m, r);
    }
}
