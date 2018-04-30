package com.structures.itsLearning;
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
