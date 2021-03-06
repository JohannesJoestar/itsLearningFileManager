package com.manager.operators;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.manager.enums.DownloadProcedureType;
import com.manager.enums.From;
import com.manager.enums.Type;
import com.manager.frames.LogsFrame;
import com.structures.itsLearning.Course;
import com.structures.itsLearning.Element;
import com.structures.linkedlist.LinkedList;
import com.structures.tree.TNode;

// Class for loading Resources

public class Loader {
	
	// Attributes and references
	private String defaultDownloadFolderPath;
	private LogsFrame logs;
	private WebDriver driver;
	private Settings settings;
	
	// Parametric constructor
	public Loader(WebDriver driver, Settings settings, LogsFrame logs){
		this.setDefaultDownloadFolderPath(System.getProperty("user.home") + "/Downloads");
		this.driver = driver;
		this.settings = settings;
		this.logs = logs;
	}
	
	// Loading methods
	// Loading courses
	public LinkedList<Course> loadCourses(From side){
		
		// Resulting Course LinkedList
		LinkedList<Course> courses = new LinkedList<Course>();
		
		// Load from itsLearning
		if (side == From.ITSLEARNING){
			
			// Navigate to courses page
			driver.navigate().to("https://buei.itslearning.com/main.aspx?TextURL=Course%2fAllCourses.aspx&Item=l-menu-course");
			driver.switchTo().frame("ctl00_ContentAreaIframe");
			
			int courseCount = driver.findElements(By.xpath("//*[@id=\"ctl03\"]/div[6]/div/div/div[2]/div/div[3]/table/tbody/tr/td[3]/a")).size();
			
			// Variables
			String[] hrefs = new String[courseCount];
			String[] names = new String[courseCount];
			
			// Go through course elements
			for (int i = 0; i < courseCount; i++){
				
				// Get WebElement
				WebElement a = driver.findElement(By.xpath("//*[@id=\"ctl03\"]/div[6]/div/div/div[2]/div/div[3]/table/tbody/tr[" + (i + 2) + "]/td[3]/a"));

				// Store variables
				hrefs[i] = a.getAttribute("href");
				names[i] = (a.getAttribute("innerHTML")).substring(6, 13);
				logs.log("Found course: " + names[i]);
				
			}
			
			// Navigate to hrefs to obtain resources page url
			for (int i = 0; i < hrefs.length; i++){
				
				// Navigate to href and find resources page url
				driver.navigate().to(hrefs[i]);
				String resourcesURL = driver.findElement(By.xpath("//*[@id=\"link-resources\"]")).getAttribute("href");
				
				// Load Resources
				Course course = new Course(names[i], resourcesURL);
				course.setRoot(loadResources(course, From.ITSLEARNING));
				
				// Build course and add to the list
				courses.add(course);
				logs.log("Succesfully loaded resources for " + course.getName() + ".");
			}
			
			// Return the resulting Course LinkedList
			return courses;
			
		}
		// Load from settings
		else {
			
			// Navigate to resources and get coursenames
			String resourcesPath = settings.getInstallationPath();
			File resourcesFolder = new File(resourcesPath);
			String courseNames[] = resourcesFolder.list();
			
			// Create courses from course names found under /Resources
			try {
				for (String courseName : courseNames){
					
					Course course = new Course(courseName, null);
					logs.log("Found course: " + course.getName());
					
					course.setRoot(loadResources(course, From.SETTINGS));
					logs.log("Succesfully loaded resources for " + course.getName() + ".");
					
					courses.add(course);
					
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Your download path does not exist anymore.");
				settings.promptInstallationPath();
			}
			
			return courses;
		}
		
	}

	// Loading resources
	private TNode<Element> loadResources(Course course, From side){

		Element rootElement = new Element(course.getName(), "/" + course.getName(), Type.FOLDER, course.getResourcesURL());
					
		// Load from itsLearning
		if (side == From.ITSLEARNING){
						
			// Define and build root node
			TNode<Element> rootNode = traverseFolders(new TNode<Element>(rootElement), From.ITSLEARNING);
						
			// Assign resulting tree to the course
			return rootNode;
		}
		// Load from settings
		else {
			
			// Define and build root node
			TNode<Element> rootNode = traverseFolders(new TNode<Element>(rootElement), From.SETTINGS);
			
			return rootNode;
		}
	}
	
	// Downloads a give list of Elements
	public void download(LinkedList<Element> elements){
		
		// Collect download links
		for (int i = 0; i < elements.size(); i++){
			
			Element element = elements.get(i);
			
			// Layout the folder structure first
			File folder = new File(settings.getInstallationPath() + (element.getPath().substring(0, (element.getPath().length() - element.getName().length()))));
			folder.mkdirs();
			
			DownloadProcedureType procedure = null;
			
			// Navigate to frames where download buttons (should be) present
			driver.navigate().to(element.getHref());
			try {
				
				// Try for general files
				driver.switchTo().frame("ctl00_ContentPlaceHolder_ExtensionIframe");
				procedure = DownloadProcedureType.FILE;
				
			} catch (NoSuchFrameException NSFE) {
				
				// Try for video file
				try {
					
					driver.switchTo().frame("main_weblink");
					driver.findElement(By.cssSelector("source[type='video/mp4']"));
					procedure = DownloadProcedureType.VIDEO;
					
				} catch (Exception E) {

				}
				
			}
			
			if (procedure == null) {
				logs.log("Element \"" + element.getName() + "\" is not directly downloadable.");
				elements.remove(element);
				i--;
				continue;
			} else if (procedure == DownloadProcedureType.FILE) {
				String href = null;
				
				// Differentiate between 2 types of download buttons
				WebElement downloadButton;
				try {
					downloadButton = driver.findElement(By.xpath("//*[@id=\"ctl00_ctl00_MainFormContent_DownloadLinkForViewType\"]"));
					href = downloadButton.getAttribute("href");
				} catch (NoSuchElementException e){
					try {
						
						downloadButton = driver.findElement(By.xpath("//*[@id=\"ctl00_ctl00_MainFormContent_ResourceContent_DownloadButton_DownloadLink\"]"));
						href = downloadButton.getAttribute("href");
						
					} catch (NoSuchElementException nsee){
						
						logs.log("Element \"" + element.getName() + "\" is not directly downloadable.");
						elements.remove(element);
						i--;
						continue;
					}
					
				} catch (Exception e){
					
					logs.log("Element \"" + element.getName() + "\" is not directly downloadable.");
					elements.remove(element);
					i--;
					continue;
				}
				
				// Check if Element name is matching the name of the file that is actually going to be downloaded
				// (Because that's a thing)
				if (downloadButton != null){
					String downloadName = downloadButton.getAttribute("download");
					if (downloadName != element.getName()){
						element.setPath((element.getPath().substring(0, element.getPath().length() - element.getName().length())) + downloadName);
						logs.log("Element \"" + element.getName() + "\" has been renamed to " + downloadName + ".");
						element.setName(downloadName);
						
					}
				}
				
				driver.navigate().to(href);
				File file = new File(this.getDefaultDownloadFolderPath() + "/" + element.getName());
				
				// Check if target file already exists.
				if (new File(settings.getInstallationPath() + element.getPath()).exists()){
					continue;
				}
				
				// Move file loop
				while (true) {
					
					// Check if download has finished
					while (!file.exists()) {
						
					    try {
							Thread.sleep(300);
						} catch (InterruptedException e) {
							logs.log("Download process has been interrupted.");
							return;
						}
					}
					
					// Move the file from the default download location to where element path points to
					if (!moveFile(file.getAbsolutePath(), settings.getInstallationPath() + element.getPath())){
						continue;
					} else {
						break;
					}
				}
			} else {
				
				// Video download procedure
				/*
				WebElement source = driver.findElement(By.cssSelector("source[type='video/mp4']"));
				String href = source.getAttribute("src");
				
				driver.navigate().to(href);
				
				try {
					
					String save = Keys.chord(Keys.CONTROL, "s");
					driver.findElement(By.cssSelector("video[name='media']")).sendKeys(save);
					
					Robot controller = new Robot();
					
					// Get to the adress bar
					for (int c = 0; c < 5; c++) {
						controller.keyPress(KeyEvent.VK_TAB);
						controller.keyRelease(KeyEvent.VK_TAB);
					}
					
					controller.keyPress(KeyEvent.VK_ENTER);
					controller.keyRelease(KeyEvent.VK_ENTER);
					
					// Copy to Clipboard
					String text = (settings.getInstallationPath() + element.getPath()).substring(0, (settings.getInstallationPath() + element.getPath()).length() - element.getName().length());
					StringSelection stringSelection = new StringSelection(text);
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					clipboard.setContents(stringSelection, stringSelection);

					// CTRL + V
					controller.keyPress(KeyEvent.VK_CONTROL);
					controller.keyPress(KeyEvent.VK_V);
					controller.keyRelease(KeyEvent.VK_V);
					controller.keyRelease(KeyEvent.VK_CONTROL);
					
					controller.keyPress(KeyEvent.VK_ENTER);
					controller.keyRelease(KeyEvent.VK_ENTER);
					
					// Get to "Save" button
					for (int c = 0; c < 9; c++) {
						controller.keyPress(KeyEvent.VK_TAB);
						controller.keyRelease(KeyEvent.VK_TAB);
					}
					
					controller.keyPress(KeyEvent.VK_ENTER);
					controller.keyRelease(KeyEvent.VK_ENTER);
					
				} catch (AWTException e) {
					logs.log("Download process has been interrupted.");
					logs.log("Element \"" + element.getName() + "\" is not directly downloadable.");
					elements.remove(element);
					i--;
					continue;
				}
				*/
			}
		}
		
	}

	private boolean moveFile(String oldPath, String newPath){
		
		File file = new File(oldPath);
        
        // renaming the file and moving it to a new location
        if(file.renameTo(new File(newPath)))
        {
            // if file copied successfully then delete the original file
            file.delete();
            return true;
        }
        else
        {
            return false;
        }
        
	}
	
	// Auxilary method for loadResources() method
	private TNode<Element> traverseFolders(TNode<Element> root, From side){
		
		if (side == From.ITSLEARNING){
			
			// Navigate to root folder url
			driver.navigate().to((root.getData().getHref()));
			List<WebElement> entries = driver.findElements(By.xpath("//*[@id=\"ctl00_ContentPlaceHolder_ProcessFolderGrid_TB\"]/tr/td[2]/a"));
			int size = entries.size();
			
			// Initialise child node attributes
			for (int i = 0; i < size; i++){
				
				// Build WebElements for readability
				WebElement entry = entries.get(i);
				
				// Initialise properties
				// Post-recursion page validation breakpoint
				try {
					entry.getAttribute("title"); // Test
				} catch (StaleElementReferenceException e){
					
					// Navigate to root folder url
					driver.navigate().to((root.getData().getHref()));
					entries = driver.findElements(By.xpath("//*[@id=\"ctl00_ContentPlaceHolder_ProcessFolderGrid_TB\"]/tr/td[2]/a"));
					
					// Build WebElements for readability
					entry = entries.get(i);
					
				}
				
				// Element attributes
				String name = entry.getAttribute("title");
				String path = (root.getData()).getPath() + "/" + name;
				String href = entry.getAttribute("href");
				Type type = (href.substring(29, 35).equals("Folder") ? (Type.FOLDER) : (Type.FILE));
				
				// Define and build TreeNode
				TNode<Element> node = new TNode<Element>(new Element(name, path, type, href));
				
				// Recursively add child nodes
				if (type == (Type.FOLDER)){
					root.addChild(traverseFolders(node, From.ITSLEARNING));
				} else {
					root.addChild(node);
				}
			}
			
			return root;
			
		} else {
			
			// Get subfolders
			File folder = new File(settings.getInstallationPath()+ root.getData().getPath());
			File[] files = folder.listFiles();
			int size = files.length;
			
			// Go through the files
			for (int i = 0; i < size; i++){
				
				// Attributes
				String name = files[i].getName();
				String path = (root.getData().getPath()) + "/" + name;
				String href = "";
				Type type = ((files[i].isDirectory()) ? (Type.FOLDER) : (Type.FILE));
				
				// Define and build TreeNode
				TNode<Element> node = new TNode<Element>(new Element(name, path, type, href));
				
				// Recursively add child nodes
				if (type == (Type.FOLDER)){
					root.addChild(traverseFolders(node, From.SETTINGS));
				} else {
					root.addChild(node);
				}
				
			}
			
			return root;
		}
		
	}



	// Get & Set
	// defaultDownloadFolderPath
	public String getDefaultDownloadFolderPath() {
		return defaultDownloadFolderPath;
	}
	public void setDefaultDownloadFolderPath(String defaultDownloadFolderPath) {
		this.defaultDownloadFolderPath = defaultDownloadFolderPath;
	}
	
}
