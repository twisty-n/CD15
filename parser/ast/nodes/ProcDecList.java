package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/4/2015
 * File Name:       ProcDecList
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class ProcDecList extends TreeNode {

    //NPROCL

    public ProcDecList() {
        super(Node.NPROCL);
    }

    public ProcDecList( STRecord st) {
        super(Node.NPROCL, st);
    }

    public ProcDecList( TreeNode l, TreeNode r) {
        super(Node.NPROCL, l, r);
    }

    public ProcDecList( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NPROCL, l, m, r);
    }
}
