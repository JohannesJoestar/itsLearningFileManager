package com.structures.itsLearning;

import java.awt.Image;

public class Element {
	
	// Attributes
	private Image icon;
	private String name;
	private String path;
	private String type;
	private String href;
	
	// Parametric Constructor
	public Element(String name, String path, String type, String href, Image icon){
		this.setName(name);
		this.setPath(path);
		this.setType(type);
		this.setHref(href);
		this.setIcon(icon);
	}
	
	// equals() override
	public boolean equals(Element element){
		
		boolean name = (this.getName() == element.getName());
		boolean path = (this.getPath() == element.getPath());
		boolean type = (this.getType() == element.getType());
		
		if (name && path && type){
			return true;
		} else {
			return false;
		}
	}
	
	// toString() override
	@Override
	public String toString(){
		return (this.getName());
	}
	
	public String toStringFull(){
		return (this.getName() + "-" + this.getPath() + "-" + this.getType() + "-" + this.getHref());
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	// Href
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	// Icon
	public Image getIcon() {
		return icon;
	}
	public void setIcon(Image icon) {
		this.icon = icon;
	}
}
