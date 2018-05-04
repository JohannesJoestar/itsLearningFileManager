package com.manager.frames;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.openqa.selenium.WebDriver;

import com.manager.enums.From;
import com.manager.eventhandlers.MouseListener;
import com.manager.operators.FileListModel;
import com.manager.operators.Loader;
import com.manager.operators.Settings;
import com.structures.itsLearning.Course;
import com.structures.itsLearning.Element;
import com.structures.tree.TreeNode;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.JLabel;

import java.awt.Component;
import java.awt.Font;
import java.util.LinkedList;

import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

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
	private JPanel pnlElementSettings;
	private JPanel pnlElementItsLearning;
	private JLabel lblStatus;
	private JList<TreeNode<Element>> listSettings;
	private JList<TreeNode<Element>> listItsLearning;
	private FileListModel settingsOperator;
	private FileListModel itsLearningOperator;
	private JTextField txtElementNameItsLearning;
	private JTextField txtElementTypeItsLearning;
	private JTextField txtElementNameSettings;
	private JTextField txtElementTypeSettings;

	// Parametric constructor
	public MainFrame(WebDriver driver, Settings settings, Loader loader, LinkedList<Course> itsLearningCourses) {

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
		
		// JList
		itsLearningOperator = new FileListModel();
		settingsOperator = new FileListModel();
		
		initialiseComponents();
		
		itsLearningOperator.setInfoPanel(pnlElementItsLearning);
		settingsOperator.setInfoPanel(pnlElementSettings);
		
		ElementListCellRenderer listRenderer = new ElementListCellRenderer();
		listItsLearning.setCellRenderer(listRenderer);
		listSettings.setCellRenderer(listRenderer);
		
		listItsLearning.addMouseListener(new MouseListener(settings, itsLearningOperator, pnlElementItsLearning));
		listSettings.addMouseListener(new MouseListener(settings, settingsOperator, pnlElementSettings));
		
		// Load elements into JListModels
		TreeNode<Element> rootItsLearning = new TreeNode<Element>(new Element("Resources", "/", com.manager.enums.Type.FOLDER, "...", false));
		for (Course course : itsLearningCourses){
			rootItsLearning.addChild(course.getRoot());
		}
		itsLearningOperator.update(rootItsLearning);

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

		listSettings = new JList<TreeNode<Element>>(settingsOperator);
		listSettings.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		listSettings.setBounds(10, 75, 226, 364);
		pnlSetting.add(listSettings);

		JLabel lblSettings = new JLabel("Your Computer");
		lblSettings.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSettings.setBounds(10, 13, 163, 23);
		pnlSetting.add(lblSettings);

		JButton btnImportChanges = new JButton("Import Changes From itsLearning");
		btnImportChanges.setEnabled(false);
		btnImportChanges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 
				LinkedList<Element> elementsToBeDownloaded = new LinkedList<Element>();
				
				// Filter existent items
				// Build Element Lists
				LinkedList<Element> itsLearningList = new LinkedList<Element>();
				for (Course course : itsLearningCourses){
					itsLearningList.addAll(loader.buildFileList(course.getRoot(), new LinkedList<Element>()));
				}
				LinkedList<Element> settingsList = new LinkedList<Element>();
				for (Course course : settingsCourses) {
					settingsList.addAll(loader.buildFileList(course.getRoot(), new LinkedList<Element>()));
				}
				
				// Filter existent elements
				for (Element element : itsLearningList) {
					if (element.getType() == com.manager.enums.Type.FOLDER) {
						continue;
					} else {
						boolean result = false;
						for (Element selement : settingsList) {
							if (element.equalsTo(selement)) {
								result = true;
								break;
							} else {
								continue;
							}
						}
						if (result) {
							continue;
						} else {
							elementsToBeDownloaded.add(element);
						}
					}
				}

				DownloadDialog dialog = new DownloadDialog(settings, loader, elementsToBeDownloaded);
				dialog.setVisible(true);
			}
		});
		btnImportChanges.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnImportChanges.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnImportChanges.setBounds(248, 223, 192, 31);
		pnlSetting.add(btnImportChanges);
		
		JButton btnLoad = new JButton("Load");
		btnLoad.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				// Load courses from user computer
				settingsCourses = loader.loadCourses(From.SETTINGS);
				
				// Load elements into JListModels
				TreeNode<Element> rootSettings = new TreeNode<Element>(new Element("Resources", "/", com.manager.enums.Type.FOLDER, "...", false));
				for (Course course : settingsCourses){
					rootSettings.addChild(course.getRoot());
				}
				if (!btnImportChanges.isEnabled()){
					btnImportChanges.setEnabled(true);
				}
				lblStatus.setText("Files loaded from computer, ready for changes import.");
				settingsOperator.update(rootSettings);
				
				if (btnLoad.getText() == "Load"){
					btnLoad.setText("Reload");
				}
			}
		});
		btnLoad.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnLoad.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnLoad.setBounds(127, 42, 109, 23);
		pnlSetting.add(btnLoad);

		pnlElementSettings = new JPanel();
		pnlElementSettings.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnlElementSettings.setBounds(245, 136, 198, 74);
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
		lblSelectedSettings.setBounds(281, 115, 131, 17);
		pnlSetting.add(lblSelectedSettings);
		
		JButton btnChangeFolder = new JButton("Change Installation Folder");
		btnChangeFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				settings.promptInstallationPath();
			}
		});
		btnChangeFolder.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnChangeFolder.setBounds(248, 267, 192, 31);
		pnlSetting.add(btnChangeFolder);

		JButton btnUpSettings = new JButton("Up One Level");
		btnUpSettings.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnUpSettings.setBounds(10, 42, 107, 23);
		btnUpSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (settingsOperator.hasUpperLevel()){
					settingsOperator.goUpOneLevel();
				}
			}
		});
		pnlSetting.add(btnUpSettings);

		JPanel pnlItsLearning = new JPanel();
		pnlItsLearning.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnlItsLearning.setBounds(10, 11, 452, 452);
		contentPane.add(pnlItsLearning);
		pnlItsLearning.setLayout(null);

		listItsLearning = new JList<TreeNode<Element>>(itsLearningOperator);
		listItsLearning.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		listItsLearning.setBounds(10, 75, 226, 364);
		pnlItsLearning.add(listItsLearning);

		JButton btnUpItsLearning = new JButton("Up One Level");
		btnUpItsLearning.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (itsLearningOperator.hasUpperLevel()){
					itsLearningOperator.goUpOneLevel();
				}
			}
		});
		btnUpItsLearning.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnUpItsLearning.setBounds(10, 42, 107, 23);
		pnlItsLearning.add(btnUpItsLearning);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				itsLearningCourses = loader.loadCourses(From.ITSLEARNING);

				itsLearningOperator.clear();
				for (Course course : itsLearningCourses) {
					itsLearningOperator.addElement(course.getRoot());
				}

			}
		});

		btnUpdate.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnUpdate.setBounds(127, 42, 109, 23);
		pnlItsLearning.add(btnUpdate);

		pnlElementItsLearning = new JPanel();
		pnlElementItsLearning.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnlElementItsLearning.setBounds(247, 187, 196, 100);
		pnlItsLearning.add(pnlElementItsLearning);
		pnlElementItsLearning.setLayout(null);

		JButton btnBlockElement = new JButton("Block Element");
		btnBlockElement.setName("btnBlockElement");
		btnBlockElement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				TreeNode<Element> node = listItsLearning.getSelectedValue();

				try {
					Element element = node.getData();
					if (btnBlockElement.getText().equals("Block Element")) {
						
						// Adjust element icon
						if (element.getType() == com.manager.enums.Type.FILE){
							element.setIcon(ImageIO.read(new File("./resources/blocked_file_icon.png")));
						} else {
							element.setIcon(ImageIO.read(new File("./resources/blocked_folder_icon.png")));
						}
						
						// Block the element
						settings.getBlockedElements().add(node.getData());
						
					} else {
						
						// Adjust element icon
						if (element.getType() == com.manager.enums.Type.FILE){
							element.setIcon(ImageIO.read(new File("./resources/file_icon.png")));
						} else {
							element.setIcon(ImageIO.read(new File("./resources/folder_icon.png")));
						}
						
						// Unblock the element
						settings.getBlockedElements().remove(settings.getBlockedElements().indexOf(element));
					}
				} catch (Exception E) {
					return;
				}
				
				// Update list
				settings.save();
				itsLearningOperator.update(node.getParent());
			}
		});
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

		lblStatus = new JLabel("");
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
		return settingsOperator;
	}

	public ListModel<TreeNode<Element>> getListItsLearningModel() {
		return itsLearningOperator;
	}
}
