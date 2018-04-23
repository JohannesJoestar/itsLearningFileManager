package com.manager.frames;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.openqa.selenium.WebDriver;

import com.manager.operators.Loader;
import com.manager.operators.Settings;
import com.structures.itsLearning.Course;
import com.structures.itsLearning.Element;
import com.structures.tree.Tree;
import com.structures.tree.TreeNode;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.JLabel;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.Color;

public class MainFrame extends JFrame {

	// Properties
	private static final long serialVersionUID = 1L;

	// References
	private LinkedList<Course> itsLearningCourses;
	private LinkedList<Course> settingsCourses;
	private WebDriver driver;
	private Settings settings;
	private Loader loader;

	// Components
	private JPanel contentPane;
	private JLabel lblStatus;
	private JList<TreeNode<Element>> listSettings;
	private JList<TreeNode<Element>> listItsLearning;
	private DefaultListModel<TreeNode<Element>> listSettingsModel;
	private DefaultListModel<TreeNode<Element>> listItsLearningModel;
	private JTextField txtElementNameItsLearning;
	private JTextField txtElementTypeItsLearning;
	private JTextField txtElementNameSettings;
	private JTextField txtElementTypeSettings;

	// Default constructor
	public MainFrame() {

		// ListModel settings
		listSettingsModel = new DefaultListModel<TreeNode<Element>>();
		listItsLearningModel = new DefaultListModel<TreeNode<Element>>();

		initialiseComponents();

		ElementListCellRenderer listRenderer = new ElementListCellRenderer();
		listItsLearning.setCellRenderer(listRenderer);
		listSettings.setCellRenderer(listRenderer);

	}

	// Parametric constructor
	public MainFrame(WebDriver driver, Settings settings, Loader loader, LinkedList<Course> itsLearningCourses){
		
		// ListModel settings
		listSettingsModel = new DefaultListModel<TreeNode<Element>>();
		listItsLearningModel = new DefaultListModel<TreeNode<Element>>();
		
		
		for (int i = 0; i < itsLearningCourses.size(); i++) {
			
			TreeNode<Element> course = itsLearningCourses.get(i).getResources().getRoot();
			course.addMouseListener(new MouseAdapter() { 
                public void mouseClicked(MouseEvent me) { 

                   if(me.getClickCount() == 2) {
                	   listItsLearningModel.clear();
                	   for (int j = 0; j < course.getNumberOfChildren(); j++) {
						 listItsLearningModel.addElement(course.getChildAt(j));
					}
                	  
                   }
                	   
               } 
        });        
		}
			
			
		
		initialiseComponents();
		
		ElementListCellRenderer listRenderer = new ElementListCellRenderer();
		listSettings.setCellRenderer(listRenderer);
		listSettings.setCellRenderer(listRenderer);
		
		this.driver = driver;
		this.settings = settings;
		this.loader = loader;
		this.itsLearningCourses = itsLearningCourses;
		this.settingsCourses = new LinkedList<Course>();
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        if (JOptionPane.showConfirmDialog(null, 
		            "Are you sure to close this window?", "Exit", 
		            JOptionPane.YES_NO_OPTION,
		            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
		        	driver.quit();
		            System.exit(0);
		        }
		    }
		});
		
		for (int i = 0; i < itsLearningCourses.size(); i++) {
			listItsLearningModel.addElement(itsLearningCourses.get(i).getResources().getRoot());
		}
		 
	}

	// Component status
	public void setComponentStatus(boolean enabled) {
		for (Component component : this.getComponents()) {
			component.setEnabled(enabled);
		}
	}

	// Updates the information text below the MainFrame
	public void setStatus(String status) {
		lblStatus.setText(status);
	}

	private void initialiseComponents() {
		setTitle("itsLearning File Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 955, 532);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel pnlSetting = new JPanel();
		pnlSetting.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnlSetting.setBounds(473, 11, 452, 452);
		contentPane.add(pnlSetting);
		pnlSetting.setLayout(null);

		listSettings = new JList<TreeNode<Element>>(listSettingsModel);
		listSettings.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		listSettings.setBounds(10, 75, 226, 364);
		pnlSetting.add(listSettings);

		JLabel lblSettings = new JLabel("Your Computer");
		lblSettings.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSettings.setBounds(10, 13, 163, 23);
		pnlSetting.add(lblSettings);

		JButton btnLoad = new JButton("Load");
		btnLoad.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnLoad.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnLoad.setBounds(127, 42, 109, 23);
		pnlSetting.add(btnLoad);

		JPanel pnlElementSettings = new JPanel();
		pnlElementSettings.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnlElementSettings.setBounds(246, 126, 198, 84);
		pnlSetting.add(pnlElementSettings);
		pnlElementSettings.setLayout(null);

		txtElementNameSettings = new JTextField();
		txtElementNameSettings.setEnabled(false);
		txtElementNameSettings.setBounds(66, 14, 120, 20);
		pnlElementSettings.add(txtElementNameSettings);
		txtElementNameSettings.setColumns(10);

		txtElementTypeSettings = new JTextField();
		txtElementTypeSettings.setEnabled(false);
		txtElementTypeSettings.setBounds(66, 40, 120, 20);
		pnlElementSettings.add(txtElementTypeSettings);
		txtElementTypeSettings.setColumns(10);

		JLabel lblElementNameSettings = new JLabel("Name:");
		lblElementNameSettings.setEnabled(false);
		lblElementNameSettings.setBounds(8, 17, 46, 14);
		pnlElementSettings.add(lblElementNameSettings);

		JLabel lblElementTypeSettings = new JLabel("Type:");
		lblElementTypeSettings.setEnabled(false);
		lblElementTypeSettings.setBounds(10, 43, 46, 14);
		pnlElementSettings.add(lblElementTypeSettings);

		JLabel lblSelectedSettings = new JLabel("Selected Element");
		lblSelectedSettings.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSelectedSettings.setBounds(284, 105, 131, 17);
		pnlSetting.add(lblSelectedSettings);

		JButton btnImportChanges = new JButton("Import Changes From itsLearning");
		btnImportChanges.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnImportChanges.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnImportChanges.setBounds(248, 223, 192, 31);
		pnlSetting.add(btnImportChanges);

		JButton btnChangeFolder = new JButton("Change Installation Folder");
		btnChangeFolder.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnChangeFolder.setBounds(248, 267, 192, 31);
		pnlSetting.add(btnChangeFolder);

		JButton btnUpSettings = new JButton("Up One Level");
		btnUpSettings.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnUpSettings.setBounds(10, 42, 107, 23);
		pnlSetting.add(btnUpSettings);

		JPanel pnlItsLearning = new JPanel();
		pnlItsLearning.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnlItsLearning.setBounds(10, 11, 452, 452);
		contentPane.add(pnlItsLearning);
		pnlItsLearning.setLayout(null);

		listItsLearning = new JList<TreeNode<Element>>(listItsLearningModel);
		listItsLearning.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		listItsLearning.setBounds(10, 75, 226, 364);
		pnlItsLearning.add(listItsLearning);

		JButton btnUpItsLearning = new JButton("Up One Level");
		btnUpItsLearning.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnUpItsLearning.setBounds(10, 42, 107, 23);
		pnlItsLearning.add(btnUpItsLearning);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnUpdate.setBounds(127, 42, 109, 23);
		pnlItsLearning.add(btnUpdate);

		JPanel pnlElementItsLearning = new JPanel();
		pnlElementItsLearning.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnlElementItsLearning.setBounds(247, 187, 196, 100);
		pnlItsLearning.add(pnlElementItsLearning);
		pnlElementItsLearning.setLayout(null);

		JButton btnBlockElement = new JButton("Block Element");
		btnBlockElement.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnBlockElement.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnBlockElement.setEnabled(false);
		btnBlockElement.setBounds(42, 64, 113, 23);
		pnlElementItsLearning.add(btnBlockElement);

		txtElementNameItsLearning = new JTextField();
		txtElementNameItsLearning.setEnabled(false);
		txtElementNameItsLearning.setBounds(52, 10, 134, 20);
		pnlElementItsLearning.add(txtElementNameItsLearning);
		txtElementNameItsLearning.setColumns(10);

		txtElementTypeItsLearning = new JTextField();
		txtElementTypeItsLearning.setEnabled(false);
		txtElementTypeItsLearning.setBounds(52, 36, 134, 20);
		pnlElementItsLearning.add(txtElementTypeItsLearning);
		txtElementTypeItsLearning.setColumns(10);

		JLabel lblElementNameItsLearning = new JLabel("Name:");
		lblElementNameItsLearning.setEnabled(false);
		lblElementNameItsLearning.setBounds(10, 13, 46, 14);
		pnlElementItsLearning.add(lblElementNameItsLearning);

		JLabel lblElementTypeItsLearning = new JLabel("Type:");
		lblElementTypeItsLearning.setEnabled(false);
		lblElementTypeItsLearning.setBounds(10, 39, 46, 14);
		pnlElementItsLearning.add(lblElementTypeItsLearning);

		JLabel lblItslearning = new JLabel("itsLearning");
		lblItslearning.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblItslearning.setBounds(10, 13, 139, 23);
		pnlItsLearning.add(lblItslearning);

		JLabel lblSelectedItsLearning = new JLabel("Selected Element");
		lblSelectedItsLearning.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSelectedItsLearning.setBounds(279, 165, 133, 20);
		pnlItsLearning.add(lblSelectedItsLearning);

		JLabel lblInfoStatus = new JLabel("Status: ");
		lblInfoStatus.setForeground(Color.BLACK);
		lblInfoStatus.setBounds(12, 466, 56, 16);
		contentPane.add(lblInfoStatus);

		lblStatus = new JLabel("status");
		lblStatus.setForeground(Color.GRAY);
		lblStatus.setBounds(56, 466, 406, 16);
		contentPane.add(lblStatus);
	}
	


	


	// Get & Set
	// Driver
	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	// Settings
	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	// Loader
	public Loader getLoader() {
		return loader;
	}

	public void setLoader(Loader loader) {
		this.loader = loader;
	}

	// itsLearning courses
	public LinkedList<Course> getItsLearningCourses() {
		return itsLearningCourses;
	}

	public void setItsLearningCourses(LinkedList<Course> itsLearningCourses) {
		this.itsLearningCourses = itsLearningCourses;
	}

	// Settings courses
	public LinkedList<Course> getSettingsCourses() {
		return settingsCourses;
	}

	public void setSettingsCourses(LinkedList<Course> settingsCourses) {
		this.settingsCourses = settingsCourses;
	}

	// ListModels
	public ListModel<TreeNode<Element>> getListSettingsModel() {
		return listSettingsModel;
	}

	public ListModel<TreeNode<Element>> getListItsLearningModel() {
		return listItsLearningModel;
	}
}
