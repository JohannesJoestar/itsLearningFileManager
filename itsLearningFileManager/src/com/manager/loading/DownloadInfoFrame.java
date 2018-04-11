package com.manager.loading;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;

public class DownloadInfoFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblInformation;
	private JLabel lblFilename;

	/**
	 * Create the frame.
	 */
	public DownloadInfoFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 826, 141);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblInformation = new JLabel("Downloading and moving file:");
		lblInformation.setFont(new Font("Tahoma", Font.PLAIN, 29));
		lblInformation.setBounds(10, 11, 392, 82);
		contentPane.add(lblInformation);
		
		lblFilename = new JLabel("none");
		lblFilename.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblFilename.setBounds(414, 32, 375, 48);
		contentPane.add(lblFilename);
	}
	
	public void update(String file){
		lblFilename.setText(file);
	}
}
