package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/5/2015
 * File Name:       ElseEndIf
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class IfThenElseConstruct extends TreeNode {

    public IfThenElseConstruct() {
        super(Node.NIFTE);
    }

    public IfThenElseConstruct( STRecord st) {
        super(Node.NIFTE, st);
    }

    public IfThenElseConstruct( TreeNode l, TreeNode r) {
        super(Node.NIFTE, l, r);
    }

    public IfThenElseConstruct( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NIFTE, l, m, r);
    }
}
