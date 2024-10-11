package swingapp;

import java.awt.Font;

import javax.swing.*;

public class DefaultPanel {
	private JPanel panel;

	public DefaultPanel() {
		panel = new JPanel();
		JLabel label = new JLabel("Quản lý ngôn ngữ lập trình!");
		label.setFont(new Font("Arial", Font.BOLD, 24));
		panel.add(label);
	}

	public JPanel getPanel() {
		return panel;
	}
}
