package com.manager.loading;

import java.awt.Image;
import java.util.LinkedList;
import java.util.List;

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
	
	// Parametric constructor
	public Loader(WebDriver driver){
		this.driver = driver;
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
			int[] IDs = new int[courseCount];
			
			// Go through course elements
			for (int i = 0; i < courseCount; i++){
				
				// Get WebElement
				WebElement a = driver.findElement(By.xpath("//*[@id=\"ctl03\"]/div[6]/div/div/div[2]/div/div[3]/table/tbody/tr[" + (i + 2) + "]/td[3]/a"));

				// Store variables
				hrefs[i] = a.getAttribute("href");
				names[i] = (a.getAttribute("innerHTML")).substring(6, 13);
				IDs[i] = Integer.parseInt(hrefs[i].substring(48, hrefs[i].length()));
				
			}
			
			// Navigate to hrefs to obtain resources page url
			for (int i = 0; i < hrefs.length; i++){
				
				// Define and build temporar Course class
				Course course = new Course();
				
				// Navigate to href and find resources page url
				driver.navigate().to(hrefs[i]);
				String resources = driver.findElement(By.xpath("//*[@id=\"link-resources\"]")).getAttribute("href");
				
				
				// Initialise course attributes
				course.setName(names[i]);
				course.setID(IDs[i]);
				course.setResourcesURL(resources);
				courses.add(course);
			}
			
			// Return the resulting Course LinkedList
			return courses;
			
		}
		// Load from settings
		else {
			
			return null;
		}
		
	}

	// Loading resources
	public Tree<Element> loadResources(Course course, From side){
		
		// Resulting Tree
		Tree<Element> resources = new Tree<Element>();
					
		// Load from itsLearning
		if (side == From.ITSLEARNING){
						
			// Define and build root node
			Element rootElement = new Element("Resources", "/Resources/" + course.getName(), "folder", course.getResourcesURL(), null);
			TreeNode<Element> rootNode = traverseFolders(new TreeNode<Element>(rootElement));
			resources.setRoot(rootNode);
						
			// Assign resulting tree to the course
			return resources;
		}
		// Load from settings
		else {		
			return null;
		}
	}
	
	public TreeNode<Element> traverseFolders(TreeNode<Element> root){
		
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
			String name = entry.getAttribute("title");
			String path = (root.getData()).getPath() + "/" + name;
			String href = entry.getAttribute("href");
			String type = (href.substring(29, 30).equals("F") ? ("folder") : ("file"));
			Image icon = null;
			
			// Define and build Element
			Element element = new Element(name, path, type, href, icon);
			
			// Define and build TreeNode
			TreeNode<Element> node = new TreeNode<Element>(element);
			
			// Recursively add child nodes
			if (type.equals("folder")){
				root.addChild(traverseFolders(node));
				System.out.println(" ");
			} else {
				root.addChild(node);
			}
		}
		
		return root;
		
	}
	
}
