package com.manager.loading;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import com.structures.itsLearning.Course;
import com.structures.itsLearning.Element;

public class Settings {
	
	// Class for user settings
	// Properties and references
	private LinkedList<Course> blockedCourses;
	private LinkedList<Element> blockedElements;
	private String path;
	private String resourcesPath;
	
	// Default constructor
	public Settings(){
		this.setBlockedCourses(null);
		this.setBlockedElements(null);
		this.setResourcesPath(null);
	}
	
	// Load settings from given path
	public boolean loadSettingsFromPath(String path){
		
		this.setPath(path);
		
		blockedCourses = new LinkedList<Course>();
		blockedElements = new LinkedList<Element>();
		
		// Load settings file
		File settings = new File(path);
		String line;
		try {
			
			FileReader reader = new FileReader(settings);
			BufferedReader buffer = new BufferedReader(reader);
			
			// Go through the lines
			try{
				
				while ((line = buffer.readLine()) != null){
					
					String header = line.substring(0, 12);
					line = line.substring(12);
					
					if (header.equals("[Courses##]:")){
						String[] courses = line.split(",");
						for (String course : courses){
							String[] attributes = course.split("-");
							blockedCourses.add(new Course(attributes[0], Integer.parseInt(attributes[1]), attributes[2]));
						}
					} else if (header.equals("[Elements#]:")){
						String[] elements = line.split(",");
						for (String element : elements){
							String[] attributes = element.split("-");
							blockedElements.add(new Element(attributes[0], attributes[1], attributes[2], attributes[3], null)); 
						}
					} else {
						this.setResourcesPath(line);
					}
				}
				buffer.close();
				
			} catch (Exception e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Chosen file is not a Settings file for this application or is corrupted.");
				
				// Revert any changes
				this.getBlockedCourses().clear();
				this.getBlockedElements().clear();
				this.setResourcesPath(null);
				return false;
			}
			
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Chosen file does not exist anymore.");
			return false;
		}
	
		return true;
	}
	
	// Load default settings
	public void loadDefault(){
		
		blockedCourses = new LinkedList<Course>();
		blockedElements = new LinkedList<Element>();
		
		// Prompt user for path to resources folder
		JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		
		JOptionPane.showMessageDialog(null, "Pick the directory your Resources folder to be set up.");
		while (true){
			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
				File selectedFile = chooser.getSelectedFile();
				this.setResourcesPath(selectedFile.getAbsolutePath());
				break;
			} else {
				JOptionPane.showMessageDialog(null, "You are required to provide a folder for Resources installation to proceed.");
				continue;
			}
		}
		
		
	}
	
	// Save settings
	public boolean save(LinkedList<Course> blockedCourses, LinkedList<Element> blockedElements, String resourcesURL){
		
		// Settings file to be saved
		File settings = new File(this.getPath());
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(settings.getAbsolutePath()));
			
			String courses = "[Courses##]:";
			for (int i = 0; i < blockedCourses.size(); i++){
				if (i != (blockedCourses.size() - 1)){
					courses = courses + (blockedCourses.get(i)).toString() + ",";
				} else {
					courses = courses + (blockedCourses.get(i)).toString();
				}
			}
			
			String elements = "[Elements#]:";
			for (int i = 0; i < blockedElements.size(); i++){
				if (i != (blockedElements.size() - 1)){
					elements = elements + (blockedElements.get(i).toString()) + ",";
				} else {
					elements = elements + (blockedElements.get(i).toString());
				}
			}
			
			String url = "[Resources]:" + resourcesURL;
			
			writer.write(courses + "\n" + elements + "\n" + url);
			
			writer.close();
		} catch (IOException e) {
			
			JOptionPane.showMessageDialog(null, "Failed to write to the settings file.");
			return false;
		}
		
		return true;
	}

	// Get & Set
	// blockedCourses
	public LinkedList<Course> getBlockedCourses() {
		return blockedCourses;
	}
	public void setBlockedCourses(LinkedList<Course> blockedCourses) {
		this.blockedCourses = blockedCourses;
	}
	// blockedElements
	public LinkedList<Element> getBlockedElements() {
		return blockedElements;
	}
	public void setBlockedElements(LinkedList<Element> blockedElements) {
		this.blockedElements = blockedElements;
	}
	// resourcesPath
	public String getResourcesPath() {
		return resourcesPath;
	}
	public void setResourcesPath(String resourcesPath) {
		this.resourcesPath = resourcesPath;
	}
	// path
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}