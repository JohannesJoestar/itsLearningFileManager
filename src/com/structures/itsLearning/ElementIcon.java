package com.structures.itsLearning;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ElementIcon {
	
	// Properties and references
	public Image FOLDER;
	public Image BLOCKED_FOLDER;
	public Image FILE;
	public Image BLOCKED_FILE;
	
	// Default constructor
	public ElementIcon() throws IOException {
		this.BLOCKED_FILE = ImageIO.read(new File("./resources/blocked_file_icon.png"));
		this.BLOCKED_FOLDER = ImageIO.read(new File("./resources/blocked_folder_icon.png"));
		this.FILE = ImageIO.read(new File("./resources/file_icon.png"));
		this.FOLDER = ImageIO.read(new File("./resources/folder_icon.png"));
	}

}
