package com.manager.loading;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import org.openqa.selenium.By;
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
	private WebDriver driver;
	private Settings settings;
	
	// Parametric constructor
	public Loader(WebDriver driver, Settings settings){
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
	
	// Auxilary method for loadResources() method
	public TreeNode<Element> traverseFolders(TreeNode<Element> root, From side){
		
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
				
				// DEBUG
				System.out.println(path);
				
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
	public LinkedList<Element> buildFileList(TreeNode<Element> root, LinkedList<Element> list){
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
	
}
