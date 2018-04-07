package com.structures;

import com.structures.tree.Tree;

// Data structure for Courses

public class Course {
	
	// Properties and references
	private String name;
	private int ID;
	private Tree resources;
	
	// Default constructor
	public Course(){
		this.setName("Default Course");
		this.setID(0);
	}
	
	// Get & Set
	// Get
	public String getName(){
		return this.name;
	}
	public int getID(){
		return this.ID;
	}
	public Tree getResources(){
		return this.resources;
	}
	// Set
	public void setName(String name){
		this.name = name;
	}
	public void setID(int ID){
		this.ID = ID;
	}
	public void setResources(Tree resources){
		this.resources = resources;
	}
	
	

}
