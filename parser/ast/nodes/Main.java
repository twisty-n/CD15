package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/4/2015
 * File Name:       Main
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class Main extends TreeNode {

    //NMAIN


    public Main() {
        super(Node.NMAIN);
    }

    public Main( STRecord st) {
        super(Node.NMAIN, st);
    }

    public Main( TreeNode l, TreeNode r) {
        super(Node.NMAIN, l, r);
    }

    public Main( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NMAIN, l, m, r);
    }
}
