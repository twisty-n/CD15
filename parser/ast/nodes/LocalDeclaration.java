package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/09/15
 * File Name:       LocalDeclaration
 * Project Name:    CD15
 * Description:
 */
public class LocalDeclaration extends TreeNode {

    public LocalDeclaration() {
        super(Node.NSIMDEC);
    }

    public LocalDeclaration( STRecord st) {
        super(Node.NSIMDEC, st);
    }

    public LocalDeclaration(TreeNode l, TreeNode r) {
        super(Node.NSIMDEC, l, r);
    }

    public LocalDeclaration(TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NSIMDEC, l, m, r);
    }
}
