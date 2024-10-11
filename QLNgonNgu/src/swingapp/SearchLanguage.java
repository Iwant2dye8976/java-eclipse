package swingapp;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SearchLanguage {
	private JLabel label;
	private JTextField search_box;
	private JButton search_btn;
	private JComboBox<Object> search_type;
	private String[] types = { "Tên ngôn ngữ", "Tác giả", "Năm phát hành", "Ứng dụng" };
	private JPanel SearchPanel;
	private GridBagLayout gridbag;

	public SearchLanguage(DisplayLanguages dl) {
		createSearch();
		addActionListeners(dl);
	}

	public void createSearch() {
		gridbag = new GridBagLayout();
		SearchPanel = new JPanel(gridbag);
		label = new JLabel("Tìm kiếm theo");
		search_box = new JTextField(20);
		search_btn = new JButton("Tìm kiếm");
		search_type = new JComboBox<Object>(types);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);

		gbc.gridx = 0;
		gbc.gridy = 0;
		SearchPanel.add(label, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		SearchPanel.add(search_type, gbc);

		gbc.gridx = 2;
		gbc.gridy = 0;
		SearchPanel.add(search_box, gbc);

		gbc.gridx = 3;
		gbc.gridy = 0;
		SearchPanel.add(search_btn, gbc);
	}

	public String[][] SearchFilter(ArrayList<Language> lang, String search_text, int index) {
		String[][] data = null;
		ArrayList<Language> filter = new ArrayList<>();
		for (int i = 0; i < lang.size(); i++) {
			if (lang.get(i).toString().toLowerCase().split(",")[index].contains(search_text)) {
				filter.add(lang.get(i));
			}
		}
		data = AppHandler.ArrayList2Array(filter);
		return data;
	}

	private void addActionListeners(DisplayLanguages dl) {
		search_btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
				ArrayList<Language> lang = AppHandler.getTextLanguages();
				String search_text = search_box.getText().toLowerCase().trim();
				DefaultTableModel tableModel = null;
				int index = search_type.getSelectedIndex();
				tableModel = new DefaultTableModel(SearchFilter(lang, search_text, index), dl.getColumns());
				dl.getDataTable().setModel(tableModel);
				}
				catch(IOException | ClassNotFoundException err) {
					JOptionPane.showMessageDialog(null, "Xảy ra lỗi khi tải ngôn ngữ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
					err.getStackTrace();
				}
			}
		});
	}

	public String getSelectedTypes() {
		return search_type.getSelectedItem().toString();
	}

	public JPanel getSearchPanel() {
		return SearchPanel;
	}

	public GridBagLayout getGridbag() {
		return gridbag;
	}
}