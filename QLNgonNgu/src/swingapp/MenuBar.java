package swingapp;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.channels.AcceptPendingException;
import java.util.ArrayList;

import javax.swing.*;

public class MenuBar {
	private JMenuBar menubar;
	private JMenu features, about;
	private JMenuItem home, view, view_database, add, add2_db,exit;
	private CardLayout cardLayout;
	private JPanel contentPanel;

	public MenuBar(CardLayout cardLayout, JPanel contenPanel, DisplayLanguages dl, DisplayLanguageFromDataBase dl_database) {
		this.cardLayout = cardLayout;
		this.contentPanel = contenPanel;
		CreateMenuBar();
		addActionListeners(dl, dl_database);
	}

	public void CreateMenuBar() {
		menubar = new JMenuBar();
		features = new JMenu("Menu");
		about = new JMenu("About");

		home = new JMenuItem("Home");
		view = new JMenuItem("Xem danh sách ngôn ngữ lập trình");
		view_database = new JMenuItem("Xem CSDL ngôn ngữ lập trình");
		add = new JMenuItem("Thêm ngôn ngữ lập trình");
		add2_db = new JMenuItem("Thêm ngôn ngữ vào CSDL");
		exit = new JMenuItem("Thoát");

		features.add(home);
		features.addSeparator();
		features.add(view);
		features.add(add);
		features.addSeparator();
		features.add(view_database);
		features.add(add2_db);
		features.addSeparator();
		features.add(exit);

		menubar.add(features);
		menubar.add(about);
	}

	private void addActionListeners(DisplayLanguages dl , DisplayLanguageFromDataBase dl_database) {
		home.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPanel, "Default");
			}
		});

		// Xử lý khi chọn "Xem danh sách ngôn ngữ lập trình"
		view.addActionListener(e -> {
			cardLayout.show(contentPanel, "Data");
			if (dl.getDisplay_mode() == 0) {
				dl.updateText();
			} else {
				dl.updateBin();
			}
		});
		
		view_database.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPanel, "Database_Data");
				if(dl_database.getDisplay_mode() == 0) {
					dl_database.updateFromDataBase("JavaLanguage");
				}
				else {
					dl_database.updateFromDataBase("PythonLanguage");
				}
			}
		});
		
		// Xử lý khi chọn "Thêm ngôn ngữ lập trình"
		add.addActionListener(e -> {
			cardLayout.show(contentPanel, "Add_Language");
		});
		
		add2_db.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(contentPanel, "Add_Language_DB");
			}
		});

		// Xử lý khi chọn "Thoát"
		exit.addActionListener(e -> {
			int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn thoát?", "Xác nhận",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		});
	}

	public CardLayout getCardLayout() {
		return cardLayout;
	}

	public JPanel getContenPanel() {
		return contentPanel;
	}

	public JMenuBar getMenuBar() {
		return menubar;
	}

	public JMenuItem getView() {
		return view;
	}

	public JMenuItem getAdd() {
		return add;
	}
}
