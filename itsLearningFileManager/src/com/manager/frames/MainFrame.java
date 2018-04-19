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
import javax.swing.JToolBar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;

public class MainFrame extends JFrame {

	// Properties and references
	private static final long serialVersionUID = 1L;
	private WebDriver driver;
	private Settings settings;
	private Downloader downloader;
	private Loader loader;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

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
		setBounds(100, 100, 954, 512);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_YourCpu = new JPanel();
		panel_YourCpu.setBounds(481, 11, 447, 451);
		contentPane.add(panel_YourCpu);
		panel_YourCpu.setLayout(null);
		
		JList list_1 = new JList();
		list_1.setBounds(10, 95, 226, 345);
		panel_YourCpu.add(list_1);
		
		JLabel lblYourComputer = new JLabel("Your Computer");
		lblYourComputer.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblYourComputer.setBounds(10, 11, 183, 31);
		panel_YourCpu.add(lblYourComputer);
		
		JButton btnUpOneLevel_1 = new JButton("Up One Level");
		btnUpOneLevel_1.setBounds(10, 61, 103, 23);
		panel_YourCpu.add(btnUpOneLevel_1);
		
		JButton btnReload = new JButton("Reload");
		btnReload.setBounds(133, 61, 103, 23);
		panel_YourCpu.add(btnReload);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(246, 126, 196, 134);
		panel_YourCpu.add(panel_1);
		panel_1.setLayout(null);
		
		textField_2 = new JTextField();
		textField_2.setBounds(66, 11, 120, 20);
		panel_1.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(66, 42, 120, 20);
		panel_1.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblName_1 = new JLabel("Name");
		lblName_1.setBounds(10, 14, 46, 14);
		panel_1.add(lblName_1);
		
		JLabel lblType_1 = new JLabel("Type");
		lblType_1.setBounds(10, 45, 46, 14);
		panel_1.add(lblType_1);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.setBounds(55, 73, 89, 23);
		panel_1.add(btnRemove);
		
		JButton btnRename = new JButton("Rename");
		btnRename.setBounds(55, 107, 89, 23);
		panel_1.add(btnRename);
		
		JLabel lblSelectedElement_1 = new JLabel("Selected Element");
		lblSelectedElement_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSelectedElement_1.setBounds(284, 105, 131, 17);
		panel_YourCpu.add(lblSelectedElement_1);
		
		JButton btnImportChangesFrom = new JButton("Import Changes From itsLearning");
		btnImportChangesFrom.setBounds(246, 302, 196, 31);
		panel_YourCpu.add(btnImportChangesFrom);
		
		JButton btnChangeInstallationFolder = new JButton("Change Installation Folder");
		btnChangeInstallationFolder.setBounds(246, 353, 196, 31);
		panel_YourCpu.add(btnChangeInstallationFolder);
		
		JPanel panel_itsLearning = new JPanel();
		panel_itsLearning.setBounds(10, 11, 452, 451);
		contentPane.add(panel_itsLearning);
		panel_itsLearning.setLayout(null);
		
		JList list = new JList();
		list.setBounds(10, 95, 226, 345);
		panel_itsLearning.add(list);
		
		JButton btnUpOneLevel = new JButton("Up One Level");
		btnUpOneLevel.setBounds(10, 61, 107, 23);
		panel_itsLearning.add(btnUpOneLevel);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(127, 61, 109, 23);
		panel_itsLearning.add(btnUpdate);
		
		JPanel panel = new JPanel();
		panel.setBounds(246, 195, 196, 119);
		panel_itsLearning.add(panel);
		panel.setLayout(null);
		
		JButton btnBlockElement = new JButton("Block Element");
		btnBlockElement.setBounds(38, 87, 113, 23);
		panel.add(btnBlockElement);
		
		textField = new JTextField();
		textField.setBounds(52, 25, 134, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(52, 56, 134, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(10, 28, 46, 14);
		panel.add(lblName);
		
		JLabel lblType = new JLabel("Type");
		lblType.setBounds(10, 59, 46, 14);
		panel.add(lblType);
		
		JLabel lblItslearning = new JLabel("itsLearning");
		lblItslearning.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblItslearning.setBounds(10, 11, 139, 39);
		panel_itsLearning.add(lblItslearning);
		
		JLabel lblSelectedElement = new JLabel("Selected Element");
		lblSelectedElement.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSelectedElement.setBounds(279, 175, 133, 20);
		panel_itsLearning.add(lblSelectedElement);
	}
}
