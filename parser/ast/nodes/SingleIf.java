package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/5/2015
 * File Name:       EndIf
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class SingleIf extends TreeNode {

    public SingleIf() {
        super(Node.NIFT);
    }

    public SingleIf( STRecord st) {
        super(Node.NIFT, st);
    }

    public SingleIf( TreeNode l, TreeNode r) {
        super(Node.NIFT, l, r);
    }

    public SingleIf( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NIFT, l, m, r);
    }
}
