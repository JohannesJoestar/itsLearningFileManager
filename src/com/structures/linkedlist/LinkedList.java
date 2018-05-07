package com.structures.linkedlist;

import java.util.Iterator;

public class LinkedList<T> implements Iterable<T> {

	// Properties and references
	private LLNode<T> first;
	private LLNode<T> last;
	private int size;
	

	// Parametric constructors
	public LinkedList(LLNode<T> first) {
		this.setFirst(first);
		this.setLast(first);
	}
	
	// Methods
	public void add(T data) {
		LLNode<T> node = new LLNode<T>(data);
		if (this.isEmpty()) {
			this.setFirst(node);
			this.setLast(node);
		} else {
			this.getLast().setNext(node);
			node.setPrev(this.getLast());
			this.setLast(node);
		}
	}
	
	public boolean remove(T data) {
		int index = this.indexOf(data);
		if (index == -1 || this.isEmpty()) {
			return false;
		} else {
			LLNode<T> current = this.getFirst();
			while (index >= 0) {
				current = current.getNext();
				index--;
			}
			LLNode<T> previous = current.getPrev();
			LLNode<T> next = current.getNext();
			previous.setNext(next);
			next.setPrev(previous);
			current.setNext(null);
			current.setPrev(null);
			return true;
		}
	}
	
	public T get(int index) {
		if (index >= this.size()) {
			return null;
		} else {
			LLNode<T> current = this.getFirst();
			for (int i = 0; i <= index; i++) {
				current = current.getNext();
			}
			return current.getData();
		}
	}
	
	public int indexOf(T data) {
		int index = -1;
		for (int i = 0; i < this.size(); i++) {
			T current = this.get(i);
			if (data.equals(current)) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	public boolean isEmpty() {
		return (this.getFirst() == null);
	}
	
	@Override
	public Iterator<T> iterator() {
		return new LinkedListIterator<T>(this);
	}
	
	@Override
	public String toString() {
		String header = "[Linked List]: ";
		for (T data : this) {
			header = header + data.toString();
		}
		return header;
	}
	
	// Default constructor
	public LinkedList(){
		this.setFirst(null);
		this.setLast(null);
	}
	
	// Get & Set
	// first
	public LLNode<T> getFirst() {
		return first;
	}
	public void setFirst(LLNode<T> first) {
		this.first = first;
	}
	// last
	public LLNode<T> getLast() {
		return last;
	}
	public void setLast(LLNode<T> last) {
		this.last = last;
	}
	// size
	public int size() {
		return size;
	}

	
}
