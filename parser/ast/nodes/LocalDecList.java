package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/5/2015
 * File Name:       LocalDecList
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class LocalDecList extends TreeNode {

    //NDLIST


    public LocalDecList() {
        super(Node.NIDLST);
    }

    public LocalDecList( STRecord st) {
        super(Node.NIDLST, st);
    }

    public LocalDecList(TreeNode l, TreeNode r) {
        super(Node.NIDLST, l, r);
    }

    public LocalDecList(TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NDLIST, l, m, r);
    }
}
