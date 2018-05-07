package com.structures.tree;

import com.structures.linkedlist.LinkedList;

public class TNode<T> {
	
	// Properties and references
	private LinkedList<TNode<T>> children;
	private TNode<T> parent;
	private T data;
	
	// Default constructor
	public TNode() {
		this.setParent(null);
		this.setData(null);
		children = new LinkedList<TNode<T>>();
	}
	
	// Parametric constructor
	public TNode(T data) {
		this.setParent(null);
		this.setData(data);
		children = new LinkedList<TNode<T>>();
	}
	
	// Methods
	public void addChild(T data) {
		TNode<T> node = new TNode<T>(data);
		node.setParent(this);
		children.add(node);
	}
	public void removeChild(T data) {
		for (TNode<T> child : children) {
			child.setParent(null);
			children.remove(child);
		}
	}

	// Get & Set
	// children
	public LinkedList<TNode<T>> getChildren() {
		return children;
	}
	public void setChildren(LinkedList<TNode<T>> children) {
		for (TNode<T> child : children) {
			child.setParent(this);
		}
		this.children = children;
	}
	// parent
	public TNode<T> getParent() {
		return parent;
	}
	public void setParent(TNode<T> parent) {
		this.parent = parent;
	}
	// data
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	

}
