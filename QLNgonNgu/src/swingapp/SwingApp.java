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

		MenuBar menuBar = new MenuBar();
		frame.setJMenuBar(menuBar.getMenuBar());

		// Panel mặc định
        DefaultPanel default_panel = new DefaultPanel();
        contentPanel.add(default_panel.getPanel(), "Default");

		// Panel hiển thị dữ liệu
		JPanel display_panel = new JPanel();
		display_panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0; // Mở rộng theo chiều ngang
		gbc.weighty = 1.0; // Mở rộng theo chiều dọc
		gbc.fill = GridBagConstraints.BOTH;

		DisplayLanguages dl = new DisplayLanguages();
		display_panel.add(dl.getFPanel(), gbc);

		DeleteLanguage dlt_l = new DeleteLanguage();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 0; // Mở rộng theo chiều ngang
		gbc.weighty = 0; // Mở rộng theo chiều dọc
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.NONE;
		display_panel.add(dlt_l.getDelete_btn(), gbc);

		contentPanel.add(display_panel, "Data");
//            contentPanel.add(dl.getFPanel(), "Data");

		// Panel thêm ngôn ngữ
		AddLanguage al = new AddLanguage();
		al.addComboboxListener();
		al.getAddButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				data = al.getLanguageInfo();
				AppHandler.SaveToText(data[0], data[1], Integer.parseInt(data[2]), data[3], data[4], data[5]);
				JOptionPane.showMessageDialog(null, "Thêm thành công!", null, JOptionPane.INFORMATION_MESSAGE);
				al.clearTextField();
			}
		});
		contentPanel.add(al.getAddPanel(), "Add_Language");

		menuBar.getView().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dl.update();
				cardLayout.show(contentPanel, "Data");
			}
		});
		menuBar.getAdd().addActionListener(e -> cardLayout.show(contentPanel, "Add_Language"));
		dlt_l.getDelete_btn().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ArrayList<Language> lang = AppHandler.loadTextLanguages();
					int rowCount = dl.getDataTable().getSelectedRowCount();
					int[] removelist = dl.getSelectedIndexes();
					if (rowCount < 2) {
						lang.remove(dl.getSelectedIndex());
					} else {
						System.out.println(lang.size());
						for (int i = removelist.length - 1; i >= 0; i--) {
							lang.remove(removelist[i]);
						}
					}
					AppHandler.SaveToText2(lang);
					dl.update();
					JOptionPane.showMessageDialog(null, "Xóa thành công!", null, JOptionPane.INFORMATION_MESSAGE);
				} catch (ClassNotFoundException | IOException e1) {
					JOptionPane.showMessageDialog(null, "Có lỗi xảy ra!", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		Image icon = Toolkit.getDefaultToolkit().getImage("HelloNigga.jpg");
		frame.setIconImage(icon);
		frame.add(contentPanel);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(SwingApp::new);
	}
}
