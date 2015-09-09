package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/4/2015
 * File Name:       ParameterList
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class ParameterList extends TreeNode {

    //Node.NPLIST

    public ParameterList() {
        super(Node.NPLIST);
    }

    public ParameterList( STRecord st) {
        super(Node.NPLIST, st);
    }

    public ParameterList( TreeNode l, TreeNode r) {
        super(Node.NPLIST, l, r);
    }

    public ParameterList( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NPLIST, l, m, r);
    }
}
