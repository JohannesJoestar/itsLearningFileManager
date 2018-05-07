package com.manager.frames;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.structures.itsLearning.Element;
import com.structures.tree.TNode;
// This class is responsible for rendering the icons of the elements on the JList.

public class ElementListCellRenderer extends JLabel implements ListCellRenderer<TNode<Element>> {
	
	private static final long serialVersionUID = 1L;
	  
	public Component getListCellRendererComponent(JList<? extends TNode<Element>> list, TNode<Element> node, int index, boolean isSelected, boolean cellHasFocus) {
		
		setText(node.getData().getName());
		setIcon(new ImageIcon(node.getData().getIcon()));
		
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
