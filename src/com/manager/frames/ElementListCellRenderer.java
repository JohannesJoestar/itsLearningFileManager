package com.manager.frames;

import java.awt.Component;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.manager.enums.Type;
import com.manager.operators.Settings;
import com.structures.itsLearning.Element;
import com.structures.itsLearning.ElementIcon;
import com.structures.tree.TNode;
// This class is responsible for rendering the icons of the elements on the JList.

public class ElementListCellRenderer extends JLabel implements ListCellRenderer<TNode<Element>> {
	
	private static final long serialVersionUID = 1L;
	private ElementIcon icons;
	private Settings settings;
	
	public ElementListCellRenderer(Settings settings) {
		try {
			icons = new ElementIcon();
		} catch (IOException e) {
			System.out.println("Icons could not be loaded.");
		}
		this.settings = settings;
	}
	  
	public Component getListCellRendererComponent(JList<? extends TNode<Element>> list, TNode<Element> node, int index, boolean isSelected, boolean cellHasFocus) {
		
		Element element = node.getData();
		if (element.getType() == Type.FOLDER) {
			if (settings.getBlockedElements().contains(element)) {
				setIcon(new ImageIcon(icons.BLOCKED_FOLDER));
			} else {
				setIcon(new ImageIcon(icons.FOLDER));
			}
		} else {
			if (settings.getBlockedElements().contains(element)) {
				setIcon(new ImageIcon(icons.BLOCKED_FILE));
			} else {
				setIcon(new ImageIcon(icons.FILE));
			}
		}
		
		setText(node.getData().getName());
		
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
