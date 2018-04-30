package com.manager.eventhandlers;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.manager.enums.Type;
import com.manager.operators.FileListModel;
import com.manager.operators.Settings;
import com.structures.itsLearning.Element;
import com.structures.tree.TreeNode;

public class MouseListener extends MouseAdapter{
	
	// Properties and references
	private Settings settings;
	private JPanel infoPanel;
	private FileListModel operator;
	private boolean hasClickedOnce;
	
	// Parametric constructor
	public MouseListener(Settings settings, FileListModel operator,  JPanel infoPanel){
		this.operator = operator;
		this.settings = settings;
		this.infoPanel = infoPanel;
	}
	
	@SuppressWarnings("unchecked")
	public void mouseClicked(MouseEvent e) {
		
		JList<TreeNode<Element>> list = (JList<TreeNode<Element>>) e.getSource();
		
		// Clear information if selected index is nothing
		if (list.getSelectedIndex() < 0){
			clearPanel();
			setButtonStatus(false);
			return;
		} else {
			setButtonStatus(true);
		}
		
		TreeNode<Element> node = list.getSelectedValue();
		Element element = node.getData();
		
		if (settings.getBlockedElements().contains(element)) {
			setButtonStatus("Unblock Element");
		} else {
			setButtonStatus("Block Element");
		}
		
		// Differentiate between double click and single click
		// Double click
	    if (hasClickedOnce) {
	    	
	    	if (node.getData().getType() == Type.FOLDER){
	    		operator.update(node);
	    	}
	        hasClickedOnce = false;
	        
	    } else { // Single click
	    	
	    	// Update Element panel information
	    	LinkedList<JTextField> fields = new LinkedList<JTextField>();
	    	for (Component component : infoPanel.getComponents()){
	    		if (component instanceof JTextField){
	    			fields.add((JTextField) component);
	    		}
	    	}
	    	fields.get(0).setText(node.getData().getName());
	    	fields.get(1).setText((node.getData().getType() == Type.FOLDER) ? ("folder") : ("file"));
	    	
	        hasClickedOnce = true;
	        Timer timer = new Timer("doubleclickTimer", false);
	        timer.schedule(new TimerTask() {

	            @Override
	            public void run() {
	                hasClickedOnce = false;
	            }
	            
	        }, 200);
	    }
	}
	
	// Clear information on the panel
	private void clearPanel(){
		for (Component component : infoPanel.getComponents()){
			if (component instanceof JTextField){
				((JTextField) component).setText("");
			}
		}
	}
	
	// Updates Button status of given panel
	private void setButtonStatus(boolean enabled){
		for (Component component : infoPanel.getComponents()){
			if (component instanceof JButton){
				component.setEnabled(enabled);
			}
		}
	}
	
	private void setButtonStatus(String text){
		for (Component component : infoPanel.getComponents()){
			if (component instanceof JButton){
				if (component.getName().equals("btnBlockElement")) {
					((JButton)component).setText(text);
				}
			}
		}
	}
	
}
