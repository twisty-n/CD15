package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/4/2015
 * File Name:       ArrayDeclaration
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class ArrayDeclaration extends TreeNode {
    
    // NARRDEC

    public ArrayDeclaration() {
        super(Node.NARRDEC);
    }

    public ArrayDeclaration(STRecord st) {
        super(Node.NARRDEC, st);
    }

    public ArrayDeclaration(TreeNode l, TreeNode r) {
        super(Node.NARRDEC, l, r);
    }

    public ArrayDeclaration( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NARRDEC, l, m, r);
    }
}
