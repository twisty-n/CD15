package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/5/2015
 * File Name:       StatementList
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class StatementList extends TreeNode {

    public StatementList() {
        super(Node.NSLIST);
    }

    public StatementList( STRecord st) {
        super(Node.NSLIST, st);
    }

    public StatementList( TreeNode l, TreeNode r) {
        super(Node.NSLIST, l, r);
    }

    public StatementList( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NSLIST, l, m, r);
    }
}
