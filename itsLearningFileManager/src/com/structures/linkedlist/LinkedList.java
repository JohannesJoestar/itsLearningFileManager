package com.structures.linkedlist;

public class LinkedList<T> {
	
	// Properties and references
	private Node<T> first;
	
	// Default constructor
	public LinkedList(){
		this.setFirst(null);
	}
	
	// Parametric constructor
	public LinkedList(Node<T> first){
		this.setFirst(first);
	}
	
	// Insert data
	public void add(T data){
		
		// Node to be inserted
		Node<T> node = new Node<T>(data);
		
		// Insert
		if (this.isEmpty()){
			this.setFirst(node);
		} else {
			
			Node<T> current = this.getFirst();
			
			// Find the last node and add
			while (current.getNext() != null){
				current = current.getNext();
			}
			current.setNext(node);
		}
		
	}
	
	public boolean remove(T data){
		
		// Search for the node
		if (this.isEmpty()){
			return false;
		} else {
			
			Node<T> previous = null;
			Node<T> current = this.getFirst();
			
			while (current != null){
				if ((current.getData()).equals(data)){
					if ((current.getNext()) == null){
						previous.setNext(null);
						return true;
					} else {
						previous.setNext(current.getNext());
						current.setNext(null);
						return true;
					}
				} else {
					previous = current;
					current = current.getNext();
				}
			}
		}
		
		// Removal has failed
		return false;
		
	}
	
	// Get & Set
	// Get
	public Node<T> getFirst(){
		return this.first;
	}
	// Set
	public void setFirst(Node<T> first){
		this.first = first;
	}
	
	// Check if empty
	public boolean isEmpty(){
		return this.isEmpty();
	}
	
	// toString() override
	
	@Override
	public String toString(){
		
		String result = "";
		
		Node<T> current = this.getFirst();
		while (current != null){
			result = result + (current.getData()).toString() + " ";
		}
		
		return result;
	}

}
