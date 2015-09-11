package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/5/2015
 * File Name:       PlusEqual
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class PlusEqual extends TreeNode {

    public PlusEqual() {
        super(Node.NPLEQ );
    }

    public PlusEqual( STRecord st) {
        super(Node.NPLEQ , st);
    }

    public PlusEqual( TreeNode l, TreeNode r) {
        super(Node.NPLEQ , l, r);
    }

    public PlusEqual( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NPLEQ , l, m, r);
    }
}
