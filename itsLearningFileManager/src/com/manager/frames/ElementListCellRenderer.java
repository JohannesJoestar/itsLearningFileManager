package com.manager.frames;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.structures.itsLearning.Element;

public class ElementListCellRenderer extends JLabel implements ListCellRenderer<Element> {
	
	private static final long serialVersionUID = 1L;
	private JLabel label;
	  
	public Component getListCellRendererComponent(JList<? extends Element> list, Element element, int index, boolean isSelected, boolean cellHasFocus) {
		
		setText(element.toString());
		setIcon(new ImageIcon(element.getIcon()));
		
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		}
		else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		
		setEnabled(list.isEnabled());
		setFont(list.getFont());
		setOpaque(true);
	  
		return this;
	}

}
