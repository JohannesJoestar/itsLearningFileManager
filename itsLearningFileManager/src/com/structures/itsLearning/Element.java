package com.structures.itsLearning;

import java.awt.Image;

public class Element {
	
	// Attributes
	private Image icon;
	private String name;
	private String path;
	private String type;
	private String href;
	
	// Default Constructor
	public Element(){
		this.setName(null);
		this.setPath(null);
		this.setType(null);
		this.setHref(null);
	}
	
	// Parametric Constructor
	public Element(String name, String path, String type, String href){
		this.setName(name);
		this.setPath(path);
		this.setType(type);
		this.setHref(href);
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
