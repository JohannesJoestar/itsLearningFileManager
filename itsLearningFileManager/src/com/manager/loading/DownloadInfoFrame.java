package com.manager.loading;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JProgressBar;

public class DownloadInfoFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblInformation;
	private JLabel lblFilename;
	private JProgressBar progressBar;
	/**
	 * Create the frame.
	 */
	public DownloadInfoFrame(int min, int max) {
		
		this.initialiseComponents();
		progressBar.setMinimum(min);
		progressBar.setMaximum(max);
		
	}
	
	// Update the label
	public void update(String file){
		lblFilename.setText(file);
	}
	
	// Update the value
	public void setValue(int value){
		progressBar.setValue(value);
	}
	
	// Components
	public void initialiseComponents(){
		
		setTitle("Downloading...");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 826, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblInformation = new JLabel("Downloading and moving file:");
		lblInformation.setFont(new Font("Tahoma", Font.PLAIN, 29));
		lblInformation.setBounds(20, 11, 382, 82);
		contentPane.add(lblInformation);
		
		lblFilename = new JLabel("none");
		lblFilename.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblFilename.setBounds(414, 32, 375, 48);
		contentPane.add(lblFilename);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(20, 104, 769, 46);
		contentPane.add(progressBar);
		
		revalidate();
	}
	
	// Get & Set
	// Get
	public JProgressBar getProgressBar(){
		return this.progressBar;
	}
	
}
