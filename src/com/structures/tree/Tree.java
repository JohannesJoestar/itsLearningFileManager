package com.structures.tree;

import com.structures.linkedlist.LinkedList;

public class Tree<T> {
	
	// Properties and references
	private TNode<T> root;

	// Default constructor
	public Tree() {
		this.setRoot(null);
	}
	
	// Parametric constructor
	public Tree(TNode<T> root) {
		this.setRoot(root);
	}
	
	// Methods
	public LinkedList<T> traverse(TreeTraversal traversal){
		return traversal(this.getRoot(), traversal, new LinkedList<T>());
	}
	private LinkedList<T> traversal(TNode<T> root, TreeTraversal traversal, LinkedList<T> list){
		
		if (traversal == TreeTraversal.PREORDER) {
			list.add(root.getData());
		}
		
		if (root.getChildren().size() == 0) {
			return list;
		} else {
			for (TNode<T> child : root.getChildren()) {
				list = traversal(child, traversal, list);
			}
		}
		
		if (traversal == TreeTraversal.POSTORDER) {
			list.add(root.getData());
		}
		
		return list;
	}
	
	// Get & Set
	// root
	public TNode<T> getRoot() {
		return root;
	}
	public void setRoot(TNode<T> root) {
		this.root = root;
	}

}
