package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    11/09/15
 * File Name:       PrintList
 * Project Name:    CD15
 * Description:
 */
public class PrintList extends TreeNode {


    public PrintList() {
        super(Node.NPRLIST);
    }

    public PrintList( STRecord st) {
        super(Node.NPRLIST, st);
    }

    public PrintList( TreeNode l, TreeNode r) {
        super(Node.NPRLIST, l, r);
    }

    public PrintList( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NPRLIST, l, m, r);
    }
}
