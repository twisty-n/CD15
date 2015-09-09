package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/4/2015
 * File Name:       SimpleParameter
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */
public class SimpleParameter extends TreeNode {

    //NSIMPAR

    public SimpleParameter() {
        super(Node.NSIMPAR);
    }

    public SimpleParameter( STRecord st) {
        super(Node.NSIMPAR, st);
    }

    public SimpleParameter( TreeNode l, TreeNode r) {
        super(Node.NSIMPAR, l, r);
    }

    public SimpleParameter( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NSIMPAR, l, m, r);
    }
}
