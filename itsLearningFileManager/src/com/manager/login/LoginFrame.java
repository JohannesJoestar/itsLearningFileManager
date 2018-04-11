package com.manager.login;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.manager.loading.From;
import com.manager.loading.Loader;
import com.manager.loading.Settings;
import com.structures.itsLearning.Course;
import com.structures.itsLearning.Element;
import com.structures.tree.Tree;
import com.structures.tree.TreeNode;

import java.awt.TextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JList;

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private WebDriver driver;
	private Settings settings;
	private Loader loader;
	private JButton btnNewButton;
	private JPasswordField txtPassword;
	private LinkedList<Course> courses;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginFrame() {
		
		initialiseComponents();
		
		// Locate ChromeDriver and build WebDriver
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") +  "/lib/chromedriver.exe");
		this.driver = new ChromeDriver();
		
		// Build references
		courses = new LinkedList<Course>();

	}
	
	public boolean isNullOrWhiteSpace(String text){
		if (text.trim().isEmpty() || text.isEmpty()){
			return true;
		} else {
			return false;
		}
	}
	
	public void initialiseComponents(){
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(1100, 100, 136, 185);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		TextField txtUsername = new TextField();
		txtUsername.setBounds(10, 31, 99, 22);
		contentPane.add(txtUsername);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblUsername.setBounds(10, 11, 99, 14);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPassword.setBounds(10, 59, 99, 14);
		contentPane.add(lblPassword);
		
		btnNewButton = new JButton("LOGIN");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// Login button clicked
				// Validate input fields
				// Validate username
				if (isNullOrWhiteSpace(txtUsername.getText())){
					JOptionPane.showMessageDialog(null, "Please enter a valid username");
					txtUsername.setText("");
					return;
				}
				
				// Validate password
				if (isNullOrWhiteSpace(String.valueOf(txtPassword.getPassword()))){
					JOptionPane.showMessageDialog(null, "Please enter a valid password");
					txtPassword.setText("");
					return;
				}
				
				// itsLearning login sequence
				driver.navigate().to("https://buei.itslearning.com/");
				
				// Input fields
				WebElement username = driver.findElement(By.xpath("//*[@id=\"ctl00_ContentPlaceHolder1_Username_input\"]"));
				WebElement password = driver.findElement(By.xpath("//*[@id=\"ctl00_ContentPlaceHolder1_Password_input\"]"));
				WebElement login = driver.findElement(By.xpath("//*[@id=\"ctl00_ContentPlaceHolder1_nativeLoginButton\"]"));
				username.sendKeys(txtUsername.getText());
				password.sendKeys(String.valueOf(txtPassword.getPassword()));
				login.click();
				
				// Login validation breakpoint
				try {
					
					// Check for an element that is only available after succesful login.
					driver.findElement(By.xpath("//*[@id=\"l-header\"]/nav[3]/ul/li[2]/a/img"));
					
				} catch (NoSuchElementException e){
					
					JOptionPane.showMessageDialog(null, "Incorrect username/password, try again.");
					txtUsername.setText("");
					txtPassword.setText("");
					return;
					
				} 
				
				// Prompt user for the settings file
				settings = new Settings();
				
				JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

				// Settings file validation loop
				while (true){
				
					int result = chooser.showOpenDialog(null);
					
					if (result == JFileChooser.APPROVE_OPTION) {
						File selectedFile = chooser.getSelectedFile();
						boolean success = settings.loadSettingsFromPath(selectedFile.getAbsolutePath());
						if (success){
							JOptionPane.showMessageDialog(null, "Settings loaded!");
							break;
						} else {
							int answer = JOptionPane.showConfirmDialog(
						            null,
						            "Something went wrong with the loading process, try again ? \n Answerin \"No\" will load default settings.",
						            "Error",
						            JOptionPane.YES_NO_OPTION);
							if (answer == JOptionPane.YES_OPTION){
								continue;
							} else {
								settings.loadDefault();
								JOptionPane.showMessageDialog(null, "Loaded default settings.");
								break;
							}
						}
					} else if (result == JFileChooser.CANCEL_OPTION){
						int answer = JOptionPane.showConfirmDialog(null, "Load default settings instead ?");
						if (answer == JOptionPane.YES_OPTION){
							settings.loadDefault();
							JOptionPane.showMessageDialog(null, "Loaded default settings.");
							break;
						} else {
							JOptionPane.showMessageDialog(null, "Please try again.");
							continue;
						}
					} else if (result == JFileChooser.ERROR_OPTION){
						JOptionPane.showMessageDialog(null, "Some kind of error occured, try again.");
						continue;
					}
				}
				
				// Login succesful, from now on is course loading process
				loader = new Loader(driver);
				
				courses = loader.loadCourses(From.ITSLEARNING);
				for (Course course : courses){
					course.setResources(loader.loadResources(course, From.ITSLEARNING));
				}
				
				
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnNewButton.setBounds(10, 107, 99, 33);
		contentPane.add(btnNewButton);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(10, 76, 99, 20);
		contentPane.add(txtPassword);
	}
	
	public void loadResources(){
		
		// Define and build Tree
		Tree<Element> resources = new Tree<Element>();
		Element resourcesNode = new Element("Resources", "/resources", "folder", courses.get(0).getResourcesURL(), null);
		TreeNode<Element> root = initialiseFolder(new TreeNode<Element>(resourcesNode));
		resources.setRoot(root);
	}
	
	public TreeNode<Element> initialiseFolder(TreeNode<Element> root){
		
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
				root.addChild(initialiseFolder(node));
				System.out.println(" ");
			} else {
				root.addChild(node);
			}
		}
		
		return root;
		
	}

	// Get & Set
	// Get
	public LinkedList<Course> getCourses() {
		return courses;
	}
	// Set
	public void setCourses(LinkedList<Course> courses) {
		this.courses = courses;
	}
}
