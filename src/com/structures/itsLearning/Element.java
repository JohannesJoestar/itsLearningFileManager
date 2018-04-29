package com.structures.itsLearning;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.manager.enums.Type;

public class Element {
	
	// Attributes
	private Image icon;
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
		try {
			if (type == Type.FILE){
				this.setIcon(ImageIO.read(new File("./resources/file_icon.png")));
			} else {
				this.setIcon(ImageIO.read(new File("./resources/folder_icon.png")));
			}
		} catch (IOException e) {
			System.out.println("Failed to load the icon for the element" + name);
		}
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
	// Icon
	public Image getIcon() {
		return icon;
	}
	public void setIcon(Image icon) {
		this.icon = icon;
	}
}
