package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/4/2015
 * File Name:       ProcParameters
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class ProcParameters extends TreeNode {

    //NPARAMS


    public ProcParameters() {
        super(Node.NPARAMS);
    }

    public ProcParameters( STRecord st) {
        super(Node.NPARAMS, st);
    }

    public ProcParameters( TreeNode l, TreeNode r) {
        super(Node.NPARAMS, l, r);
    }

    public ProcParameters( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NPARAMS, l, m, r);
    }
}
