package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/5/2015
 * File Name:       Input
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class Input extends TreeNode {

    public Input() {
        super(Node.NINPUT);
    }

    public Input( STRecord st) {
        super(Node.NINPUT, st);
    }

    public Input( TreeNode l, TreeNode r) {
        super(Node.NINPUT, l, r);
    }

    public Input( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NINPUT, l, m, r);
    }
}
