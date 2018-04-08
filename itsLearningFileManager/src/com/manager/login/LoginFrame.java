package com.manager.login;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.structures.itsLearning.Course;

import java.awt.TextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JList;

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private WebDriver driver;
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
					
				} // Login succesful, from now on is course loading process
				
				// Initialise course attributes for later use
				loadCourses();
				
				// Load resources
				
				
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnNewButton.setBounds(10, 107, 99, 33);
		contentPane.add(btnNewButton);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(10, 76, 99, 20);
		contentPane.add(txtPassword);
	}
	
	public void loadCourses(){
		
		// Overwrite courses
		courses.clear();
		
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
			names[i] = (a.getAttribute("innerHTML")).substring(6, ((a.getAttribute("innerHTML")).length() - 7));
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
