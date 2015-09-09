package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/4/2015
 * File Name:       ProcedureDeclaration
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class ProcedureDeclaration extends TreeNode {

    //NPROC

    public ProcedureDeclaration() {
        super(Node.NPROC);
    }

    public ProcedureDeclaration(STRecord st) {
        super(Node.NPROC, st);
    }

    public ProcedureDeclaration( TreeNode l, TreeNode r) {
        super(Node.NPROC, l, r);
    }

    public ProcedureDeclaration( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NPROC, l, m, r);
    }
}
