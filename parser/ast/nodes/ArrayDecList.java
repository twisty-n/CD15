package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/4/2015
 * File Name:       ArrayDecList
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class ArrayDecList extends TreeNode {

    // NARYL

    public ArrayDecList() {
        super(Node.NARRYL);
    }

    public ArrayDecList(STRecord st) {
        super(Node.NARRYL, st);
    }

    public ArrayDecList(TreeNode l, TreeNode r) {
        super(Node.NARRYL, l, r);
    }

    public ArrayDecList(TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NARRYL, l, m, r);
    }
}
