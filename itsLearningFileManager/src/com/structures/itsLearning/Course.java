package com.structures.itsLearning;
import com.structures.tree.Tree;

//Data structure for Courses
public class Course {
		
	// Properties and references
	private String name;
	private int ID;
	private String resourcesURL;
	private Tree<?> resources;
	
	// Default constructor
	public Course(){
		this.setName("Default Course");
		this.setID(0);
	}
	
	// Parametric constructor
	public Course(String name, int ID, String resourcesURL){
		this.setName(name);
		this.setID(ID);
		this.setResourcesURL(resourcesURL);
	}
		
	// Get & Set
	// Get
	public String getName(){
		return this.name;
	}
	public int getID(){
		return this.ID;
	}
	public Tree<?> getResources(){
		return this.resources;
	}
	public String getResourcesURL(){
		return this.resourcesURL;
	}
	// Set
	public void setName(String name){
		this.name = name;
	}
	public void setID(int ID){
		this.ID = ID;
	}	
	public void setResources(Tree<?> resources){
		this.resources = resources;
	}
	public void setResourcesURL(String resourcesURL){
		this.resourcesURL = resourcesURL;
	}
	
	// toString() override
	@Override
	public String toString(){
		return (this.getName() + "-" + this.getID() + "-" + this.getResourcesURL());
	}
	
	// equals() override
	public boolean equals(Course course){
		
		if (this.getName() != course.getName()){
			return false;
		} else {
			if (this.getID() != course.getID()){
				return false;
			} else {
				if ((this.getResourcesURL() != null) && (course.getResourcesURL() != null)){
					if (this.getResourcesURL() != course.getResourcesURL()){
						return false;
					}
				}
			}
		}
		
		return true;
	}

}
