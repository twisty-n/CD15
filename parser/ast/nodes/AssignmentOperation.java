package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/5/2015
 * File Name:       Assign
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class AssignmentOperation extends TreeNode {

    public AssignmentOperation() {
        super(Node.NASGN);
    }

    public AssignmentOperation( STRecord st) {
        super(Node.NASGN, st);
    }

    public AssignmentOperation( TreeNode l, TreeNode r) {
        super(Node.NASGN, l, r);
    }

    public AssignmentOperation( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NASGN, l, m, r);
    }
}
