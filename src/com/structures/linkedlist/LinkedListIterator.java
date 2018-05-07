package com.structures.linkedlist;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListIterator<T> implements Iterator<T> {

	// Properties and references
	private LinkedList<T> list;
	private int index;
	
	// Parametric constructor
	public LinkedListIterator(LinkedList<T> list) {
		this.list = list;
		index = 0;
	}

	@Override
	public boolean hasNext() {
		return (index < list.size());
	}

	@Override
	public T next() {
		if (this.hasNext()) {
			return list.get(index++);
        }
        throw new NoSuchElementException();
	}
}
