package com.manager.operators;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import com.manager.enums.Type;
import com.structures.itsLearning.Course;
import com.structures.itsLearning.Element;

public class Settings {
	
	// Class for user settings
	// Properties and references
	private LinkedList<Element> blockedElements;
	private String path;
	private String installationPath;
	
	// Default constructor
	public Settings(){
		this.setBlockedElements(null);
		this.setInstallationPath(null);
	}
	
	// Load settings from given path
	public boolean loadSettingsFromPath(String path){
		
		this.setPath(path);
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
					
					// Load settings if the field is not empty
					if (line.length() == 12){
						
						// Installation path cannot be empty!
						if (line.substring(0, 12).equals("[Resources]:")){
							JOptionPane.showMessageDialog(null, "Your installation path is not set up.");
							promptInstallationPath();
						} else {
							
							// List of blocked courses or elements can be empty, move on
							continue;
						}
						
					// Header does have content
					} else {
						
						String header = line.substring(0, 12);
						String content = line.substring(12);
						
						if (header.equals("[Elements#]:")){
							String[] elements = content.split(",");
							for (String element : elements){
								String[] attributes = element.split("%");
								blockedElements.add(new Element(attributes[0], attributes[1], ((attributes[2] == "folder") ? Type.FOLDER : Type.FILE), attributes[3], true)); 
							}
						} else {
							this.setInstallationPath(content);
						}
					}
					
				}
				buffer.close();
				
			} catch (Exception e){
				JOptionPane.showMessageDialog(null, "Chosen file is not a Settings file for this application or is corrupted.");
				
				// Revert any changes
				this.getBlockedElements().clear();
				this.setInstallationPath(null);
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
		blockedElements = new LinkedList<Element>();
		promptInstallationPath();	
	}
	
	// Prompt user for path to resources folder
	public void promptInstallationPath(){
		
		JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
				
		JOptionPane.showMessageDialog(null, "Pick the directory your Resources folder to be set up.");
		while (true){
			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
				File selectedFile = chooser.getSelectedFile();
				this.setInstallationPath(selectedFile.getAbsolutePath());
				break;
			} else {
				JOptionPane.showMessageDialog(null, "You are required to provide a folder for Resources installation to proceed.");
				continue;
			}
		}
		this.save();
	}
	
	public boolean promptSettingsPath(){
		JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
			
		JOptionPane.showMessageDialog(null, "Pick the file where your settings should be saved.");
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			File selectedFile = chooser.getSelectedFile();
			this.setPath(selectedFile.getAbsolutePath());
			return true;
		} else {
			JOptionPane.showMessageDialog(null, "Loading default settings, your settings will not be saved.");
			this.loadDefault();
			return false;
		}
	}
	
	// Save settings
	public boolean save(){
		
		// Settings file to be saved
		if (this.getPath() == null) {
			if (promptSettingsPath()) {
				File settings = new File(this.getPath());
				BufferedWriter writer;
				try {
					writer = new BufferedWriter(new FileWriter(settings.getAbsolutePath()));
					
					String elements = "[Elements#]:";
					for (int i = 0; i < blockedElements.size(); i++){
						if (i != (blockedElements.size() - 1)){
							elements = elements + (blockedElements.get(i).toStringFull()) + ",";
						} else {
							elements = elements + (blockedElements.get(i).toStringFull());
						}
					}
					
					String url = "[Resources]:" + installationPath;
					
					writer.write(elements + "\n" + url);
					
					writer.close();
				} catch (IOException e) {
					
					JOptionPane.showMessageDialog(null, "Failed to write to the settings file.");
					return false;
				}
				
				return true;
			} else {
				return false;
			}
		} else {
			File settings = new File(this.getPath());
			BufferedWriter writer;
			try {
				writer = new BufferedWriter(new FileWriter(settings.getAbsolutePath()));
				
				String elements = "[Elements#]:";
				for (int i = 0; i < blockedElements.size(); i++){
					if (i != (blockedElements.size() - 1)){
						elements = elements + (blockedElements.get(i).toStringFull()) + ",";
					} else {
						elements = elements + (blockedElements.get(i).toStringFull());
					}
				}
				
				String url = "[Resources]:" + installationPath;
				
				writer.write(elements + "\n" + url);
				
				writer.close();
			} catch (IOException e) {
				
				JOptionPane.showMessageDialog(null, "Failed to write to the settings file.");
				return false;
			}
			
			return true;
		}
		
	}
	
	// toString()
	@Override
	public String toString(){
		return (this.blockedElements + "\n" + this.installationPath );
	}

	// Get & Set
	// blockedElements
	public LinkedList<Element> getBlockedElements() {
		return blockedElements;
	}
	public void setBlockedElements(LinkedList<Element> blockedElements) {
		this.blockedElements = blockedElements;
	}
	// resourcesPath
	public String getInstallationPath() {
		return installationPath;
	}
	public void setInstallationPath(String resourcesPath) {
		this.installationPath = resourcesPath;
	}
	// path
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
