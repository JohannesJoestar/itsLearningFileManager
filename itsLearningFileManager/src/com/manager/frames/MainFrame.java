package com.manager.frames;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.openqa.selenium.WebDriver;

import com.manager.loading.Downloader;
import com.manager.loading.Loader;
import com.manager.loading.Settings;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

public class MainFrame extends JFrame {

	// Properties and references
	private static final long serialVersionUID = 1L;
	private WebDriver driver;
	private Settings settings;
	private Downloader downloader;
	private Loader loader;
	private JPanel contentPane;
	private JTextField txtElementNameItsLearning;
	private JTextField txtElementTypeItsLearning;
	private JTextField txtElementNameSettings;
	private JTextField txtElementTypeSettings;

	// Default constructor
	public MainFrame() {
		setTitle("itsLearning File Manager");
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
		setBounds(100, 100, 955, 514);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel pnlSetting = new JPanel();
		pnlSetting.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnlSetting.setBounds(473, 11, 452, 452);
		contentPane.add(pnlSetting);
		pnlSetting.setLayout(null);
		
		JList listSettings = new JList();
		listSettings.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		listSettings.setBounds(10, 75, 226, 364);
		pnlSetting.add(listSettings);
		
		JLabel lblSettings = new JLabel("Your Computer");
		lblSettings.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSettings.setBounds(10, 13, 163, 23);
		pnlSetting.add(lblSettings);
		
		JButton btnReload = new JButton("Reload");
		btnReload.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnReload.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnReload.setBounds(127, 42, 109, 23);
		pnlSetting.add(btnReload);
		
		JPanel pnlElementSettings = new JPanel();
		pnlElementSettings.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnlElementSettings.setBounds(246, 126, 198, 134);
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
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnRemove.setEnabled(false);
		btnRemove.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnRemove.setBounds(59, 73, 89, 23);
		pnlElementSettings.add(btnRemove);
		
		JButton btnRename = new JButton("Rename");
		btnRename.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnRename.setEnabled(false);
		btnRename.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnRename.setBounds(59, 98, 89, 23);
		pnlElementSettings.add(btnRename);
		
		JLabel lblSelectedSettings = new JLabel("Selected Element");
		lblSelectedSettings.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSelectedSettings.setBounds(284, 105, 131, 17);
		pnlSetting.add(lblSelectedSettings);
		
		JButton btnImportChanges = new JButton("Import Changes From itsLearning");
		btnImportChanges.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnImportChanges.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnImportChanges.setBounds(248, 273, 192, 31);
		pnlSetting.add(btnImportChanges);
		
		JButton btnChangeFolder = new JButton("Change Installation Folder");
		btnChangeFolder.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnChangeFolder.setBounds(248, 308, 192, 31);
		pnlSetting.add(btnChangeFolder);
		
		JButton btnUpOneLevel_1 = new JButton("Up One Level");
		btnUpOneLevel_1.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnUpOneLevel_1.setBounds(10, 42, 107, 23);
		pnlSetting.add(btnUpOneLevel_1);
		
		JPanel pnlItsLearning = new JPanel();
		pnlItsLearning.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnlItsLearning.setBounds(10, 11, 452, 452);
		contentPane.add(pnlItsLearning);
		pnlItsLearning.setLayout(null);
		
		JList listItsLearning = new JList();
		listItsLearning.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		listItsLearning.setBounds(10, 75, 226, 364);
		pnlItsLearning.add(listItsLearning);
		
		JButton btnUpitsLearning = new JButton("Up One Level");
		btnUpitsLearning.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnUpitsLearning.setBounds(10, 42, 107, 23);
		pnlItsLearning.add(btnUpitsLearning);
		
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
	}
}
