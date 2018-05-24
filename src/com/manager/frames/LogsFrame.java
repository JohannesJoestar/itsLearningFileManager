package com.manager.frames;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class LogsFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	// Components
	DefaultListModel<String> model;
	JList<String> list;

	public LogsFrame() {
		
		super("Logs");
		initialiseComponents();
	}
	
	// Methods
	public void log(String text) {
		model.addElement(text);
	}
	
	private void initialiseComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(1100, 500, 650, 358);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		model = new DefaultListModel<String>();
		list = new JList<String>(model);
		JScrollPane pane = new JScrollPane(list);
		pane.setBounds(10, 10, 610, 300);
		contentPane.add(pane);
	}

}
