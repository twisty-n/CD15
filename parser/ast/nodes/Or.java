package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/15/2015
 * File Name:       Or
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class Or extends TreeNode {

    public Or() {
        super(Node.NOR);
    }

    public Or( STRecord st) {
        super(Node.NOR, st);
    }

    public Or( TreeNode l, TreeNode r) {
        super(Node.NOR, l, r);
    }

    public Or( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NOR, l, m, r);
    }
}
