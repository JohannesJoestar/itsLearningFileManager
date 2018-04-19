package com.manager.frames;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import org.openqa.selenium.WebDriver;

import com.manager.loading.Downloader;
import com.manager.loading.Loader;
import com.manager.loading.Settings;
import com.structures.itsLearning.Element;

public class DownloadDialog extends JFrame {

	// Properties
	private static final long serialVersionUID = 1L;
	
	// References
	private WebDriver driver;
	private Settings settings;
	private Downloader downloader;
	private Loader loader;
	
	public static void main(String[] args) {
		DownloadDialog download = new DownloadDialog();
		download.setVisible(true);
	}
	
	// Components
	private JPanel contentPane;
	private JList<Element> listChanges;
	private DefaultListModel<Element> listChangesModel;
	private JTextField txtElementNameDownload;
	private JTextField txtElementTypeDownload;

	// Default constructor
	public DownloadDialog() {
		
		listChangesModel = new DefaultListModel<Element>();
		
		initialiseComponents();
		
		listChanges.setCellRenderer(new ElementListCellRenderer());
	}
	
	// Parametric constructor
	public DownloadDialog(WebDriver driver, Settings settings, Downloader downloader, Loader loader) {
			
		listChangesModel = new DefaultListModel<Element>();
			
		initialiseComponents();
			
		listChanges.setCellRenderer(new ElementListCellRenderer());
		
		this.driver = driver;
		this.settings = settings;
		this.downloader = downloader;
		this.loader = loader;
	}

	private void initialiseComponents() {
		setTitle("Downloader");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 512);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel pnlChanges = new JPanel();
		pnlChanges.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnlChanges.setBounds(10, 11, 410, 441);
		contentPane.add(pnlChanges);
		pnlChanges.setLayout(null);
		
		JLabel lblReviewChanges = new JLabel("Review Changes");
		lblReviewChanges.setBounds(10, 11, 149, 22);
		pnlChanges.add(lblReviewChanges);
		lblReviewChanges.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JButton btnUpOneLevel = new JButton("Up One Level");
		btnUpOneLevel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnUpOneLevel.setBounds(10, 36, 196, 23);
		pnlChanges.add(btnUpOneLevel);
		
		listChanges = new JList<Element>(listChangesModel);
		listChanges.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		listChanges.setBounds(10, 64, 196, 367);
		pnlChanges.add(listChanges);
		
		JPanel pnlElementDownload = new JPanel();
		pnlElementDownload.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnlElementDownload.setBounds(218, 125, 180, 101);
		pnlChanges.add(pnlElementDownload);
		pnlElementDownload.setLayout(null);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setEnabled(false);
		lblName.setBounds(10, 11, 46, 14);
		pnlElementDownload.add(lblName);
		
		JLabel lblType = new JLabel("Type:");
		lblType.setEnabled(false);
		lblType.setBounds(10, 37, 46, 14);
		pnlElementDownload.add(lblType);
		
		txtElementNameDownload = new JTextField();
		txtElementNameDownload.setEnabled(false);
		txtElementNameDownload.setBounds(64, 8, 106, 20);
		pnlElementDownload.add(txtElementNameDownload);
		txtElementNameDownload.setColumns(10);
		
		txtElementTypeDownload = new JTextField();
		txtElementTypeDownload.setEnabled(false);
		txtElementTypeDownload.setBounds(64, 34, 106, 20);
		pnlElementDownload.add(txtElementTypeDownload);
		txtElementTypeDownload.setColumns(10);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnRemove.setEnabled(false);
		btnRemove.setBounds(46, 67, 89, 23);
		pnlElementDownload.add(btnRemove);
		
		JButton btnDownload = new JButton("Download New Files");
		btnDownload.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDownload.setBounds(234, 239, 149, 23);
		pnlChanges.add(btnDownload);
		
		JLabel lblSelectedElement = new JLabel("Selected Element");
		lblSelectedElement.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSelectedElement.setBounds(245, 92, 126, 22);
		pnlChanges.add(lblSelectedElement);
		
	}

}
