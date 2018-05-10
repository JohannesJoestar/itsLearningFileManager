package com.manager.operators;

import java.awt.Component;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.structures.itsLearning.Element;
import com.structures.tree.TNode;

public class FileListModel extends DefaultListModel<TNode<Element>> {

	// Properties and References
	private static final long serialVersionUID = 1L;
	private JPanel infoPanel;
	
	// Default constructor
	public FileListModel(){
		super();
		setInfoPanel(null);
	}
	
	// Renew the list with given elements
	public void update(TNode<Element> parent){
		if (!(parent.getChildren().size() == 0)) {
			this.clear();
			clearPanel();
			setInfoPanelStatus(false);
			for (TNode<Element> node : parent.getChildren()){
				this.addElement(node);
			}
		} else {
			JOptionPane.showMessageDialog(null, "That folder is empty.");
		}
	}
	
	// Move one level up
	public void goUpOneLevel(){
		TNode<Element> grandparent = this.getElementAt(0).getParent().getParent();
		clearPanel();
		setInfoPanelStatus(false);
		update(grandparent);
	}
	
	// Check if it's the top level
	public boolean hasUpperLevel(){
		
		if (this.size() < 1) {
			return false;
		} else {
			
			TNode<Element> node = this.getElementAt(0);
			
			if (node.getParent().getData().getName() == "Resources"){
				return false;
			} else {
				return true;
			}
		}
	}
	
	// Clear information on the panel
	public void clearPanel(){
		for (Component component : infoPanel.getComponents()){
			if (component instanceof JTextField){
				((JTextField)component).setText("");
			}
		}
	}
	
	// Updates Button status of given panel
	public void setInfoPanelStatus(boolean enabled){
		for (Component component : infoPanel.getComponents()){
			if (component instanceof JButton){
				component.setEnabled(enabled);
			}
		}
	}
	
	// Get & Set
	// infoPanel
	public void setInfoPanel(JPanel infoPanel){
		this.infoPanel = infoPanel;
	}

}
