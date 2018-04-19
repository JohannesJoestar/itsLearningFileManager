import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DownloadDialog extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DownloadDialog frame = new DownloadDialog();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DownloadDialog() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 405, 410);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 369, 338);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblReviewChanges = new JLabel("Review Changes");
		lblReviewChanges.setBounds(10, 11, 149, 22);
		panel.add(lblReviewChanges);
		lblReviewChanges.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JButton btnUpOneLevel = new JButton("Up One Level");
		btnUpOneLevel.setBounds(10, 51, 153, 23);
		panel.add(btnUpOneLevel);
		
		JList list = new JList();
		list.setBounds(10, 92, 159, 235);
		panel.add(list);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(179, 120, 180, 101);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(10, 11, 46, 14);
		panel_1.add(lblName);
		
		JLabel lblType = new JLabel("Type");
		lblType.setBounds(10, 39, 46, 14);
		panel_1.add(lblType);
		
		textField = new JTextField();
		textField.setBounds(64, 8, 106, 20);
		panel_1.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(64, 36, 106, 20);
		panel_1.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.setBounds(46, 67, 89, 23);
		panel_1.add(btnRemove);
		
		JButton btnDownloadNewFiles = new JButton("Download New Files");
		btnDownloadNewFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDownloadNewFiles.setBounds(199, 232, 149, 23);
		panel.add(btnDownloadNewFiles);
		
		JLabel lblSelectedElement = new JLabel("Selected Element");
		lblSelectedElement.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSelectedElement.setBounds(206, 87, 126, 22);
		panel.add(lblSelectedElement);
	}

}
