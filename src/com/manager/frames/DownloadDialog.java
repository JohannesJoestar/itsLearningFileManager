package com.manager.frames;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import com.structures.linkedlist.LinkedList;
import com.structures.tree.TNode;

import java.awt.event.ActionEvent;
import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import com.manager.eventhandlers.MouseListener;
import com.manager.operators.FileListModel;
import com.manager.operators.Loader;
import com.manager.operators.Settings;
import com.structures.itsLearning.Element;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DownloadDialog extends JFrame {

	// Properties
	private static final long serialVersionUID = 1L;
	
	// References
	private LinkedList<Element> downloadElements;
	private Loader loader;
	
	// Components
	private JPanel contentPane;
	private JPanel pnlChanges;
	private JPanel pnlElementDownload;
	private JList<TNode<Element>> listChanges;
	private FileListModel operator;
	private JTextField txtElementNameDownload;
	private JTextField txtElementTypeDownload;
	
	// Parametric constructor//
	public DownloadDialog(Settings settings, Loader loader, LinkedList<Element> downloadElements) {
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				setVisible(false);
			}
		});
		
		this.loader = loader;
		this.downloadElements = downloadElements;	
		
		operator = new FileListModel();
		initialiseComponents();
		listChanges.setCellRenderer(new ElementListCellRenderer());
		
		operator.setInfoPanel(pnlElementDownload);
		
		for (Element element : downloadElements) {
			operator.addElement(new TNode<Element>(element));
		}
		
		listChanges.addMouseListener(new MouseListener(settings, operator, pnlElementDownload));
		
	}

	private void initialiseComponents() {
		setTitle("Downloader");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 512);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		pnlChanges = new JPanel();
		pnlChanges.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnlChanges.setBounds(10, 11, 410, 441);
		contentPane.add(pnlChanges);
		pnlChanges.setLayout(null);
		
		JLabel lblReviewChanges = new JLabel("Review Changes");
		lblReviewChanges.setBounds(10, 11, 149, 22);
		pnlChanges.add(lblReviewChanges);
		lblReviewChanges.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		listChanges = new JList<TNode<Element>>(operator);
		listChanges.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		listChanges.setBounds(10, 35, 196, 396);
		pnlChanges.add(listChanges);
		
		pnlElementDownload = new JPanel();
		pnlElementDownload.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnlElementDownload.setBounds(218, 125, 180, 68);
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
		
		JButton btnDownload = new JButton("Download New Files");
		btnDownload.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				loader.download(downloadElements);
			}
		});
		btnDownload.setBounds(228, 213, 149, 23);
		pnlChanges.add(btnDownload);
		
		JLabel lblSelectedElement = new JLabel("Selected Element");
		lblSelectedElement.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSelectedElement.setBounds(245, 92, 126, 22);
		pnlChanges.add(lblSelectedElement);
		
	}

}
