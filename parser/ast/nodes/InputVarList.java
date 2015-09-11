package parser.ast.nodes;

import context.symbolism.STRecord;
import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    11/09/15
 * File Name:       InputVarList
 * Project Name:    CD15
 * Description:
 */
public class InputVarList extends TreeNode {

    public InputVarList() {
        super(Node.NVLIST);
    }

    public InputVarList( STRecord st) {
        super(Node.NVLIST, st);
    }

    public InputVarList( TreeNode l, TreeNode r) {
        super(Node.NVLIST, l, r);
    }

    public InputVarList( TreeNode l, TreeNode m, TreeNode r) {
        super(Node.NVLIST, l, m, r);
    }
}
