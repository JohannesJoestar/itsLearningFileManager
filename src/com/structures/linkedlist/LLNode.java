package com.structures.linkedlist;

public class LLNode<T> {

	// Properties and references
	private LLNode<T> next;
	private LLNode<T> prev;
	private T data;
	
	// Parametric constructors
	public LLNode(T data) {
		this.setNext(null);
		this.setPrev(null);
		this.setData(data);
	}
	public LLNode(LLNode<T> prev, T data, LLNode<T> next) {
		this.setNext(next);
		this.setPrev(prev);
		this.setData(data);
	}
	
	// Default constructor
	public LLNode() {
		this.setNext(null);
		this.setPrev(null);
		this.setData(null);
	}

	// Get & Set
	// next
	public LLNode<T> getNext() {
		return next;
	}
	public void setNext(LLNode<T> next) {
		this.next = next;
	}
	// prev
	public LLNode <T>getPrev() {
		return prev;
	}
	public void setPrev(LLNode<T> prev) {
		this.prev = prev;
	}
	// data
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
}
