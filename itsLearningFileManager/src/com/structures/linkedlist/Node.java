package com.structures.linkedlist;

// Node class for LinkedList
public class Node<T> {
	
	// Properties and references
	private Node<T> next;
	private T data;
	
	// Default constructor
	public Node(){
		this.setNext(null);
		this.setData(null);
	}
	
	// Parametric constructor
	public Node(T data){
		this.setNext(null);
		this.setData(data);
	}
	
	// Get & Set
	// Get
	public Node<T> getNext(){
		return this.next;
	}
	public T getData(){
		return this.data;
	}
	// Set
	public void setNext(Node<T> next){
		this.next = next; 
	}
	public void setData(T data){
		this.data = data;
	}

}
