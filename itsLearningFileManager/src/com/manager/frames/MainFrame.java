package com.manager.frames;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.openqa.selenium.WebDriver;

import com.manager.loading.Downloader;
import com.manager.loading.Loader;
import com.manager.loading.Settings;

public class MainFrame extends JFrame {

	// Properties and references
	private static final long serialVersionUID = 1L;
	private WebDriver driver;
	private Settings settings;
	private Downloader downloader;
	private Loader loader;
	private JPanel contentPane;

	// Default constructor
	public MainFrame() {
		initialiseComponents();
	}
	
	// Parametric constructor
	public MainFrame(WebDriver driver, Settings settings, Downloader downloader, Loader loader){
		
		initialiseComponents();
		this.driver = driver;
		this.settings = settings;
		this.downloader = downloader;
		this.loader = loader;
	}

	private void initialiseComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
