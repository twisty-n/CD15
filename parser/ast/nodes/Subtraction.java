package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/14/2015
 * File Name:       Subtraction
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class Subtraction extends TreeNode {

    public Subtraction() {
        super(Node.NSUB);
    }

    public Subtraction( STRecord st) {
        super(Node.NSUB, st);
    }

    public Subtraction( TreeNode l, TreeNode r) {
        super(Node.NSUB, l, r);
    }

    public Subtraction( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NSUB, l, m, r);
    }
}
