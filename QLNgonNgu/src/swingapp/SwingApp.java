package swingapp;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

public class SwingApp {
	private JPanel contentPanel;
	private CardLayout cardLayout;
	private String[] data;
	private final int APP_WIDTH = 800, APP_HEIGHT = 400;

	public SwingApp() {
		JFrame frame = new JFrame("Quản lý ngôn ngữ");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(APP_WIDTH, APP_HEIGHT);

		cardLayout = new CardLayout();
		contentPanel = new JPanel(cardLayout);

		// Panel mặc định
		DefaultPanel default_panel = new DefaultPanel();
		contentPanel.add(default_panel.getPanel(), "Default");

		DisplayLanguages dl = new DisplayLanguages();
		
		Popup_menu popupMenu = new Popup_menu(dl);

		// Panel hiển thị dữ liệu
		JPanel display_panel = new JPanel();
		display_panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 0;
		JLabel label = new JLabel("Danh sách ngôn ngữ");
		label.setFont(new Font("Arial", Font.BOLD, 24));
		display_panel.add(label);
		display_panel.add(Box.createRigidArea(new Dimension(0, 50)));

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;

		SearchLanguage sl = new SearchLanguage(dl);
		display_panel.add(sl.getSearchPanel(), gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1.0; // Mở rộng theo chiều ngang
		gbc.weighty = 1.0; // Mở rộng theo chiều dọc
		gbc.fill = GridBagConstraints.BOTH;

		display_panel.add(dl.getFPanel(), gbc);

		DeleteLanguage dlt_l = new DeleteLanguage(dl);
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.weightx = 0; // Mở rộng theo chiều ngang
		gbc.weighty = 0; // Mở rộng theo chiều dọc
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.NONE;
		display_panel.add(dlt_l.getDelete_btn(), gbc);

		contentPanel.add(display_panel, "Data");
		
		DisplayLanguageFromDataBase dl_database = new DisplayLanguageFromDataBase();
		contentPanel.add(dl_database.getFPanel(), "Database_Data");

		// Panel thêm ngôn ngữ
		AddLanguage al = new AddLanguage(dl);
		contentPanel.add(al.getAddPanel(), "Add_Language");

		AddLanguage2DB al2_db = new AddLanguage2DB(dl);
		contentPanel.add(al2_db.getAddPanel(), "Add_Language_DB");

		MenuBar menuBar = new MenuBar(cardLayout, contentPanel, dl, dl_database);
		frame.setJMenuBar(menuBar.getMenuBar());

		Image icon = Toolkit.getDefaultToolkit().getImage("BoxIcon.png");
		frame.setIconImage(icon);
		frame.add(contentPanel);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(SwingApp::new);
	}
}
