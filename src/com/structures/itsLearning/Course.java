package com.structures.itsLearning;
import com.manager.enums.Type;
import com.manager.operators.Settings;
import com.structures.linkedlist.LinkedList;
import com.structures.tree.TNode;
import com.structures.tree.Tree;

//Data structure for Courses
public class Course extends Tree<Element>{
		
	// Properties and references
	private String name;
	private String resourcesURL;
	
	// Parametric constructor
	public Course(String name, String resourcesURL){
		this.setName(name);
		this.setResourcesURL(resourcesURL);
	}
		
	// Get & Set
	// Get
	public String getName(){
		return this.name;
	}
	public String getResourcesURL(){
		return this.resourcesURL;
	}
	// Set
	public void setName(String name){
		this.name = name;
	}
	public void setResourcesURL(String resourcesURL){
		this.resourcesURL = resourcesURL;
	}
	
	// toLinkedList overload withfiltering
	public LinkedList<Element> toLinkedList(TNode<Element> root, LinkedList<Element> list, Settings settings){

		// Filter blocked courses
		if (settings.getBlockedElements().contains(root.getData())) {
			return list;
		}
		
		for (TNode<Element> child : root.getChildren()){
			
			// Filter blocked elements
			if (settings.getBlockedElements().contains(child.getData())) {
				continue;
			}
			
			// Only "file" type Elements will be added since we can't "download folders"
			if (child.getData().getType() == Type.FOLDER){
				toLinkedList(child,  list, settings);
			} else {
				list.add(child.getData());
			}
		}
		
		return list;
	}
	
	// toLinkedList overload without filtering
	public LinkedList<Element> toLinkedList(TNode<Element> root, LinkedList<Element> list){

		for (TNode<Element> child : root.getChildren()){
			
			// Only "file" type Elements will be added since we can't "download folders"
			if (child.getData().getType() == Type.FOLDER){
				toLinkedList(child,  list);
			} else {
				list.add(child.getData());
			}
		}
		
		return list;
	}
	
	// toString() override
	@Override
	public String toString(){
		return (this.getName());
	}
	
	// equals() override
	public boolean equals(Course course){
		
		if (this.getName() != course.getName()){
			return false;
		} else {
			if ((this.getResourcesURL() != null) && (course.getResourcesURL() != null)){
				if (this.getResourcesURL() != course.getResourcesURL()){
					return false;
				}
			}
		}
		
		return true;
	}

}
