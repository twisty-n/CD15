package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/4/2015
 * File Name:       Program
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class Program extends TreeNode {

    // NPROG

    public Program() {
        super(Node.NPROG);
    }


    public Program(STRecord st) {
        super(Node.NPROG, st);
    }

    public Program(TreeNode l, TreeNode r) {
        super(Node.NPROG, l, r);
    }

    public Program(TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NPROG, l, m, r);
    }
}
