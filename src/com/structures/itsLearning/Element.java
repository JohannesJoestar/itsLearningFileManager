package com.structures.itsLearning;

import com.manager.enums.Type;

public class Element {
	
	// Attributes
	private Type type;
	private String name;
	private String path;
	private String href;
	
	// Parametric Constructor
	public Element(String name, String path, Type type, String href){
		this.setName(name);
		this.setPath(path);
		this.setType(type);
		this.setHref(href);
	}
	
	// equals() override
	@Override
	public boolean equals(Object object){
		
		if (!(object instanceof Element)) {
			return false;
		} else {
			Element element = (Element) object;
			boolean name = (this.getName().equals((element.getName())));
			boolean path = (this.getPath().equals((element.getPath())));
			boolean type = (this.getType().equals((element.getType())));
			
			return (name && path && type);
		}
	}
	
	// toString() override
	@Override
	public String toString(){
		return (this.getName());
	}
	
	public String toStringFull(){
		return (this.getName() + "%" + this.getPath() + "%" + this.getType() + "%" + this.getHref());
	}

	// Get & Set
	// Name
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	// Path
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	// Type
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	// Href
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
}
