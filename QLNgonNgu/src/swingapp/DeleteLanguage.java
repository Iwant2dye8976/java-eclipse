package swingapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class DeleteLanguage extends JButton {
	private JButton delete_btn;
	private JPanel del_panel;

	public DeleteLanguage(DisplayLanguages dl) {
		delete_btn = new JButton("Xóa");

		delete_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int rowCount = dl.getDataTable().getSelectedRowCount();
					if (rowCount == 0) {
						JOptionPane.showMessageDialog(null, "Vui lòng chọn ngôn ngữ muốn xóa!", null,
								JOptionPane.WARNING_MESSAGE);
						return; // Thoát ra nếu chưa có dữ liệu được chọn
					}

					int[] removelist = dl.getSelectedIndexes();
					ArrayList<Language> lang = null;
					if (dl.getDisplay_mode() == 0) {
						lang = AppHandler.getTextLanguages();
					} else {
						lang = AppHandler.getBinLanguages();
					}

					if (rowCount < 2) {
						lang.remove(dl.getSelectedIndex());
					} else {
//						System.out.println(lang.size());
						for (int i = removelist.length - 1; i >= 0; i--) {
							lang.remove(removelist[i]);
						}
					}

					if (dl.getDisplay_mode() == 0) {
						AppHandler.SaveToText2(lang);
						dl.updateText();
					} else {
						AppHandler.SaveBinLanguage(lang);
						dl.updateBin();
					}

					JOptionPane.showMessageDialog(null, "Xóa thành công!", null, JOptionPane.INFORMATION_MESSAGE);
				} catch (ClassNotFoundException | IOException e1) {
					JOptionPane.showMessageDialog(null, "Có lỗi xảy ra!", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});

	}

	public DeleteLanguage(DisplayLanguageFromDataBase dl_database) {
		createDeleteButton();
		addActionListener(dl_database);
	}

	public void createDeleteButton() {
		GridBagLayout gridbagLayout = new GridBagLayout();
		del_panel = new JPanel(gridbagLayout);
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		delete_btn = new JButton("Xóa");

		del_panel.add(delete_btn, gbc);
	}

	public void addActionListener(DisplayLanguageFromDataBase dl_database) {
		delete_btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (dl_database.getDataTable().getSelectedRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn ngôn ngữ muốn xóa!", null,
							JOptionPane.WARNING_MESSAGE);
					return;
				} else {
					AppHandler.deleteLanguageDatabase(dl_database.getDisplay_mode(), dl_database.getID(dl_database.getSelectedIds_()));
					dl_database
							.updateFromDataBase(dl_database.getDisplay_mode() == 0 ? "JavaLanguage" : "PythonLanguage");
				}
			}
		});
	}

	public JPanel getDel_panel() {
		return del_panel;
	}

	public JButton getDelete_btn() {
		return delete_btn;
	}
}
