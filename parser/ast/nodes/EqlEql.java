package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/15/2015
 * File Name:       EqlEql
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class EqlEql extends TreeNode {

    public EqlEql() {
        super(Node.NEQL);
    }

    public EqlEql( STRecord st) {
        super(Node.NEQL, st);
    }

    public EqlEql( TreeNode l, TreeNode r) {
        super(Node.NEQL, l, r);
    }

    public EqlEql( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NEQL, l, m, r);
    }
}
