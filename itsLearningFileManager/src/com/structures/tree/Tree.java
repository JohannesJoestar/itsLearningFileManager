/*
 Copyright 2010 Vivin Suresh Paliath
 Distributed under the BSD License
*/

package com.structures.tree;

import java.util.*;

public class Tree<T> {

    private TreeNode<T> root;

    public Tree() {
        super();
    }

    public TreeNode<T> getRoot() {
        return this.root;
    }

    public void setRoot(TreeNode<T> root) {
        this.root = root;
    }

    public int getNumberOfNodes() {
        int numberOfNodes = 0;

        if(root != null) {
            numberOfNodes = auxiliaryGetNumberOfNodes(root) + 1; //1 for the root!
        }

        return numberOfNodes;
    }

    private int auxiliaryGetNumberOfNodes(TreeNode<T> node) {
        int numberOfNodes = node.getNumberOfChildren();

        for(TreeNode<T> child : node.getChildren()) {
            numberOfNodes += auxiliaryGetNumberOfNodes(child);
        }

        return numberOfNodes;
    }

    public boolean exists(T dataToFind) {
        return (find(dataToFind) != null);
    }

    public TreeNode<T> find(T dataToFind) {
        TreeNode<T> returnNode = null;

        if(root != null) {
            returnNode = auxiliaryFind(root, dataToFind);
        }

        return returnNode;
    }

    private TreeNode<T> auxiliaryFind(TreeNode<T> currentNode, T dataToFind) {
        TreeNode<T> returnNode = null;
        int i = 0;

        if (currentNode.getData().equals(dataToFind)) {
            returnNode = currentNode;
        }

        else if(currentNode.hasChildren()) {
            i = 0;
            while(returnNode == null && i < currentNode.getNumberOfChildren()) {
                returnNode = auxiliaryFind(currentNode.getChildAt(i), dataToFind);
                i++;
            }
        }

        return returnNode;
    }

    public boolean isEmpty() {
        return (root == null);
    }

    public List<TreeNode<T>> build(TreeTraversal traversalOrder) {
        List<TreeNode<T>> returnList = null;

        if(root != null) {
            returnList = build(root, traversalOrder);
        }

        return returnList;
    }

    public List<TreeNode<T>> build(TreeNode<T> node, TreeTraversal traversalOrder) {
        List<TreeNode<T>> traversalResult = new ArrayList<TreeNode<T>>();

        if(traversalOrder == TreeTraversal.PRE_ORDER) {
            buildPreOrder(node, traversalResult);
        }

        else if(traversalOrder == TreeTraversal.POST_ORDER) {
            buildPostOrder(node, traversalResult);
        }

        return traversalResult;
    }

    private void buildPreOrder(TreeNode<T> node, List<TreeNode<T>> traversalResult) {
        traversalResult.add(node);

        for(TreeNode<T> child : node.getChildren()) {
            buildPreOrder(child, traversalResult);
        }
    }

    private void buildPostOrder(TreeNode<T> node, List<TreeNode<T>> traversalResult) {
        for(TreeNode<T> child : node.getChildren()) {
            buildPostOrder(child, traversalResult);
        }

        traversalResult.add(node);
    }

    public Map<TreeNode<T>, Integer> buildWithDepth(TreeTraversal traversalOrder) {
        Map<TreeNode<T>, Integer> returnMap = null;

        if(root != null) {
            returnMap = buildWithDepth(root, traversalOrder);
        }

        return returnMap;
    }

    public Map<TreeNode<T>, Integer> buildWithDepth(TreeNode<T> node, TreeTraversal traversalOrder) {
        Map<TreeNode<T>, Integer> traversalResult = new LinkedHashMap<TreeNode<T>, Integer>();

        if(traversalOrder == TreeTraversal.PRE_ORDER) {
            buildPreOrderWithDepth(node, traversalResult, 0);
        }

        else if(traversalOrder == TreeTraversal.POST_ORDER) {
            buildPostOrderWithDepth(node, traversalResult, 0);
        }

        return traversalResult;
    }

    private void buildPreOrderWithDepth(TreeNode<T> node, Map<TreeNode<T>, Integer> traversalResult, int depth) {
        traversalResult.put(node, depth);

        for(TreeNode<T> child : node.getChildren()) {
            buildPreOrderWithDepth(child, traversalResult, depth + 1);
        }
    }

    private void buildPostOrderWithDepth(TreeNode<T> node, Map<TreeNode<T>, Integer> traversalResult, int depth) {
        for(TreeNode<T> child : node.getChildren()) {
            buildPostOrderWithDepth(child, traversalResult, depth + 1);
        }

        traversalResult.put(node, depth);
    }
    
    public String toString() {
        /*
        We're going to assume a pre-order traversal by default
         */

        String stringRepresentation = "";

        if(root != null) {
            stringRepresentation = build(TreeTraversal.PRE_ORDER).toString();

        }

        return stringRepresentation;
    }

    public String toStringWithDepth() {
        /*
        We're going to assume a pre-order traversal by default
         */

        String stringRepresentation = "";

        if(root != null) {
            stringRepresentation = buildWithDepth(TreeTraversal.PRE_ORDER).toString();
        }

        return stringRepresentation;
    }
}
