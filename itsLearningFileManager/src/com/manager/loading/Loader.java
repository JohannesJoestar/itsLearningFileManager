package com.manager.loading;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.structures.itsLearning.Course;
import com.structures.itsLearning.Element;
import com.structures.tree.Tree;
import com.structures.tree.TreeNode;

// Class for loading Resources

public class Loader {
	
	// Attributes and references
	private String defaultDownloadFolderPath;
	private WebDriver driver;
	private Settings settings;
	
	// Parametric constructor
	public Loader(WebDriver driver, Settings settings){
		this.setDefaultDownloadFolderPath(System.getProperty("user.home") + "/Downloads");
		this.driver = driver;
		this.settings = settings;
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
				
			}
			
			// Navigate to hrefs to obtain resources page url
			for (int i = 0; i < hrefs.length; i++){
				
				// Navigate to href and find resources page url
				driver.navigate().to(hrefs[i]);
				String resources = driver.findElement(By.xpath("//*[@id=\"link-resources\"]")).getAttribute("href");
				
				
				// Build course and add to the list
				courses.add(new Course(names[i], resources));
			}
			
			// Return the resulting Course LinkedList
			return courses;
			
		}
		// Load from settings
		else {
			
			// Navigate to resources and get coursenames
			String resourcesPath = settings.getInstallationPath() + "/Resources" ;
			File resourcesFolder = new File(resourcesPath);
			String courseNames[] = resourcesFolder.list();
			
			// Create courses from course names found under /Resources
			for (String courseName : courseNames){
				courses.add(new Course(courseName, null));
			}
			
			return courses;
		}
		
	}

	// Loading resources
	public Tree<Element> loadResources(Course course, From side){
		
		// Resulting Tree
		Tree<Element> resources = new Tree<Element>();
		Element rootElement = new Element(course.getName(), "/" + course.getName(), "folder", course.getResourcesURL());
					
		// Load from itsLearning
		if (side == From.ITSLEARNING){
						
			// Define and build root node
			TreeNode<Element> rootNode = traverseFolders(new TreeNode<Element>(rootElement), From.ITSLEARNING);
			resources.setRoot(rootNode);
						
			// Assign resulting tree to the course
			return resources;
		}
		// Load from settings
		else {
			
			// Define and build root node
			TreeNode<Element> rootNode = traverseFolders(new TreeNode<Element>(rootElement), From.SETTINGS);
			resources.setRoot(rootNode);
			
			return resources;
		}
	}
	
	// Downloads a give list of Elements
	public void download(LinkedList<Element> elements){
		
		LinkedList<String> hrefs = new LinkedList<String>();
		
		// Collect download links
		for (int i = 0; i < elements.size(); i++){
			
			Element element = elements.get(i);
			
			// Layout the folder structure first
			try {
				File folder = new File(settings.getInstallationPath() + (element.getPath().substring(0, (element.getPath().length() - element.getName().length()))));
				folder.mkdirs();
			} catch (Exception e){
				e.printStackTrace();
			}
			
			// Navigate to frames where download buttons (should be) present
			driver.navigate().to(element.getHref());
			driver.switchTo().frame("ctl00_ContentPlaceHolder_ExtensionIframe");
			
			String href;
			
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
					
					// Element does not have a download option
					// Element is not suitable for download
					elements.remove(element);
					i--;
					continue;
				}
			} catch (Exception e){
				
				// Element is not suitable for download
				elements.remove(element);
				i--;
				continue;
			}
			
			// Check if Element name is matching the name of the file that is actually going to be downloaded
			// (Because that's a thing apparently)
			if (downloadButton != null){
				String downloadName = downloadButton.getAttribute("download");
				if (downloadName != element.getName()){
					element.setPath((element.getPath().substring(0, element.getPath().length() - element.getName().length())) + downloadName);
					element.setName(downloadName);
				}
			}
			
			hrefs.add(href);
		}
		
		// Go through the download links
		for (int i = 0; i < elements.size(); i++){
			
			Element element = elements.get(i);
			String href = hrefs.get(i);
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
						JOptionPane.showMessageDialog(null, "Download process has been interrupted.");
						continue;
					}
				}
				
				// Move the file from the default download location to where element path points to
				if (!moveFile(file.getAbsolutePath(), settings.getInstallationPath() + element.getPath())){
					continue;
				} else {
					break;
				}
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
	private TreeNode<Element> traverseFolders(TreeNode<Element> root, From side){
		
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
				String type = (href.substring(29, 30).equals("F") ? ("folder") : ("file"));
				
				// Define and build TreeNode
				TreeNode<Element> node = new TreeNode<Element>(new Element(name, path, type, href));
				
				// Recursively add child nodes
				if (type.equals("folder")){
					root.addChild(traverseFolders(node, From.ITSLEARNING));
					System.out.println(" ");
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
				String type = ((files[i].isDirectory()) ? ("folder") : ("file"));
				String href = "";
				
				// Define and build TreeNode
				TreeNode<Element> node = new TreeNode<Element>(new Element(name, path, type, href));
				
				// Recursively add child nodes
				if (type.equals("folder")){
					root.addChild(traverseFolders(node, From.SETTINGS));
					System.out.println(" ");
				} else {
					root.addChild(node);
				}
				
			}
			
			return root;
		}
		
	}
	
	// Gets all non-folder elements from a given tree
	public LinkedList<Element> getAllFilesFromTree(Tree<Element> tree){
		LinkedList<Element> result = buildFileList(tree.getRoot(), new LinkedList<Element>());

		return result;
		
	}
	
	// Traverses a given tree and builds the list given as paramater
	// This is auxilary to the getAllFilesFromTree() method
	private LinkedList<Element> buildFileList(TreeNode<Element> root, LinkedList<Element> list){
		int size = root.getChildren().size();
		for (int i = 0; i < size; i++){
			
			TreeNode<Element> child = root.getChildAt(i);
			
			// Only "file" type Elements will beadded since we can't "download folders"
			if (child.getData().getType() == "folder"){
				buildFileList(child, list);
			} else {
				list.add(child.getData());
				continue;
			}
		}
		return list;
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
