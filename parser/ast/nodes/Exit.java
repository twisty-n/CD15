package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/5/2015
 * File Name:       Exit
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class Exit extends TreeNode {

    public Exit() {
        super(Node.NEXIT);
    }

    public Exit( STRecord st) {
        super(Node.NEXIT, st);
    }

    public Exit( TreeNode l, TreeNode r) {
        super(Node.NEXIT, l, r);
    }

    public Exit( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NEXIT, l, m, r);
    }
}
