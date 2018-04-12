package com.manager.login;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.manager.loading.Downloader;
import com.manager.loading.From;
import com.manager.loading.Loader;
import com.manager.loading.Settings;
import com.structures.itsLearning.Course;
import com.structures.tree.TreeTraversal;

import java.awt.TextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

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
					frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
					frame.addWindowListener(new java.awt.event.WindowAdapter() {
					    @Override
					    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
					        if (JOptionPane.showConfirmDialog(frame, 
					            "Are you sure to close this window?", "Exit", 
					            JOptionPane.YES_NO_OPTION,
					            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					        	frame.getDriver().quit();
					            System.exit(0);
					        }
					    }
					});
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
		setBounds(1100, 100, 330, 358);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		TextField txtUsername = new TextField();
		txtUsername.setName("txtUsername");
		txtUsername.setFont(new Font("Dialog", Font.PLAIN, 31));
		txtUsername.setBounds(10, 57, 294, 58);
		contentPane.add(txtUsername);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblUsername.setBounds(10, 11, 294, 40);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblPassword.setBounds(10, 121, 294, 40);
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
					
					// Check for an element that is only available after succesful login to determine login status.
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
				loader = new Loader(driver, settings);		
				
				courses = loader.loadCourses(From.ITSLEARNING);
				for (Course course : courses){
					course.setResources(loader.loadResources(course, From.ITSLEARNING));
				}
				
				Downloader downloader = new Downloader(driver, settings);
				for (Course course : courses){
					downloader.download(loader.getAllFilesFromTree(course.getResources()));
				}
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnNewButton.setBounds(10, 241, 294, 67);
		contentPane.add(btnNewButton);
		
		txtPassword = new JPasswordField();
		txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 31));
		txtPassword.setBounds(10, 172, 294, 58);
		contentPane.add(txtPassword);
	}

	// Get & Set
	// Courses
	public LinkedList<Course> getCourses() {
		return courses;
	}
	public void setCourses(LinkedList<Course> courses) {
		this.courses = courses;
	}
	// Driver
	public WebDriver getDriver() {
		return driver;
	}
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
}
