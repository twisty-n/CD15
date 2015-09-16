package io;

import parser.ast.TreeNode;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    9/5/2015
 * File Name:       DotTreeCrawler
 * Project Name:    CD15 Compiler
 * Description:     Traverses an AST in order to produce
 *                  a dot language implementation of what
 *                  the tree should look like
 */
public class DotTreeCrawler {


    public static StringBuffer crawl(TreeNode ast) {

        StringBuffer dotAST = new StringBuffer("graph AST {\n\t");

        crawl(dotAST, ast);

        dotAST.append("}");
        return dotAST;
    }

    private static void crawl(StringBuffer buffer, TreeNode currentNode) {

        if (currentNode == null) return;

        String nodeName = currentNode.getUniqueName();

        // Each node is responsible for setting up its own name
        String currentNodeSetup = nodeName + " [label=\"" + currentNode.getValue().toString();
        if (currentNode.getName()!= null) {
            currentNodeSetup += "\\nN: " + currentNode.getName().getLexemeString();
        }
        if (currentNode.getType() != null) {
            currentNodeSetup += "\\nV: "+currentNode.getType().getLexemeString();
        }
        currentNodeSetup += "\"];\n\t";
        buffer.append(currentNodeSetup);

        if (currentNode.getLeft() != null) {
            String leftChildName = (currentNode.getLeft() != null) ? currentNode.getLeft().getUniqueName() : "";
            buffer.append(nodeName   +" -- " + leftChildName + ";\n\t");
        }

        if (currentNode.getMiddle() != null) {
            String middleChildName = (currentNode.getMiddle() != null) ? currentNode.getMiddle().getUniqueName() : "";
            buffer.append(nodeName   +" -- " + middleChildName +  ";\n\t");
        }

        if (currentNode.getRight() != null) {
            String rightChildName = (currentNode.getRight() != null) ? currentNode.getRight().getUniqueName() : "";
            buffer.append(nodeName   +" -- " + rightChildName +  ";\n\t");
        }


        crawl(buffer, currentNode.getLeft());
        crawl(buffer, currentNode.getMiddle());
        crawl(buffer, currentNode.getRight());
    }

}
