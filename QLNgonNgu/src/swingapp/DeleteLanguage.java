package swingapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class DeleteLanguage extends JButton {
	private JButton delete_btn;

	public DeleteLanguage() {
		delete_btn = new JButton("Xóa");
	}

	public DeleteLanguage(DisplayLanguages dl) {
		delete_btn = new JButton("Xóa");

		delete_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int rowCount = dl.getDataTable().getSelectedRowCount();
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
						System.out.println(lang.size());
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

	public JButton getDelete_btn() {
		return delete_btn;
	}
}
