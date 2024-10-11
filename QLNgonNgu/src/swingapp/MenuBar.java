package swingapp;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

public class MenuBar {
	private JMenuBar menubar;
	private JMenu features, about;
	private JMenuItem view, add, exit;
	private CardLayout cardLayout;
	private JPanel contentPanel;

	public MenuBar(CardLayout cardLayout, JPanel contenPanel, DisplayLanguages dl) {
		this.cardLayout = cardLayout;
		this.contentPanel = contenPanel;
		CreateMenuBar();
		addActionListeners(dl);
	}

	public void CreateMenuBar() {
		menubar = new JMenuBar();
		features = new JMenu("Menu");
		about = new JMenu("About");

		view = new JMenuItem("Xem danh sách ngôn ngữ lập trình");
		add = new JMenuItem("Thêm ngôn ngữ lập trình");
		exit = new JMenuItem("Thoát");

		features.add(view);
		features.add(add);
		features.addSeparator();
		features.add(exit);

		menubar.add(features);
		menubar.add(about);
	}

	private void addActionListeners(DisplayLanguages dl) {
		// Xử lý khi chọn "Xem danh sách ngôn ngữ lập trình"
		view.addActionListener(e -> {
			cardLayout.show(contentPanel, "Data");
			if (dl.getDisplay_mode() == 0) {
				dl.updateText();
			} else {
				dl.updateBin();
			}
		});

		// Xử lý khi chọn "Thêm ngôn ngữ lập trình"
		add.addActionListener(e -> {
			cardLayout.show(contentPanel, "Add_Language");
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
