package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/14/2015
 * File Name:       Multiplication
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class Multiplication extends TreeNode {

    public Multiplication() {
        super(Node.NMUL);
    }

    public Multiplication( STRecord st) {
        super(Node.NMUL, st);
    }

    public Multiplication( TreeNode l, TreeNode r) {
        super(Node.NMUL, l, r);
    }

    public Multiplication( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NMUL, l, m, r);
    }
}
