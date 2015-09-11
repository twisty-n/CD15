package parser.ast;

import context.symbolism.STRecord;
import parser.ast.nodes.Node;

import java.util.Random;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/4/2015
 * File Name:       TreeNode
 * Project Name:    CD15 Compiler
 * Description:     TODO Write DEscription
 */

public class TreeNode {

    private static int count = 0;       // Not sure what these are for
    private static int index = 0;
    private Node nodeValue;             // The node value
    private int idx;                    // No idea about this either
    private TreeNode left,middle,right; // The children of this node
    private STRecord name, type;           // Any SymbolTableRecords that we need for this element

    String uniqueName;

    public TreeNode () {
        nodeValue = Node.NUNDEF;
        index++;
        idx = index;
        left = null;
        middle = null;
        right = null;
        name = null;
        type = null;
        uniqueName = null;
    }

    public String getUniqueName() {
        if (this.uniqueName == null) {
            this.uniqueName =
                nodeValue.name()
                + "_" + ((left != null) ? left.getValue().toString() : "NEMPTY")
                + "_" + ((middle != null) ? middle.getValue().toString() : "NEMPTY")
                + "_" + ((right != null) ? right.getValue().toString() : "NEMPTY")
                + new Random().nextInt(Integer.MAX_VALUE);
        }
        return uniqueName;
    }

    public TreeNode (Node value) {
        this();
        nodeValue = value;
    }

    public TreeNode (Node value, STRecord st) {
        this(value);
        name = st;
    }

    public TreeNode (Node value, TreeNode l, TreeNode r) {
        this(value);
        left = l;
        right = r;
    }

    public TreeNode (Node value, TreeNode l, TreeNode m, TreeNode r) {
        this(value,l,r);
        middle = m;
    }

    public Node getValue() { return nodeValue; }

    public TreeNode getLeft() { return left; }

    public TreeNode getMiddle() { return middle; }

    public TreeNode getRight() { return right; }

    public STRecord getName() { return name; }

    public STRecord getType() { return type; }

    public TreeNode setValue(Node value) { nodeValue = value; return this;}

    public TreeNode setLeft(TreeNode l) { left = l;return this; }

    public TreeNode setMiddle(TreeNode m) { middle = m; return this;}

    public TreeNode setRight(TreeNode r) { right = r; return this;}

    public TreeNode setName(STRecord st) { name = st; return this;}

    public TreeNode setType(STRecord st) { type = st; return this;}

    public static void resetIndex() {
        index = 0;
    }
}