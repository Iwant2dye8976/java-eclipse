package swingapp;

import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Popup_menu {
	private JPopupMenu popup_menu;
	private JMenuItem save2bin, save2text, loadAsbin, loadAstext;

	public Popup_menu(DisplayLanguages dl) {
		popup_menu = new JPopupMenu();
		save2bin = new JMenuItem("Lưu dưới dạng nhị phân");
		save2text = new JMenuItem("Lưu dưới dạng văn bản");
		loadAsbin = new JMenuItem("Tải dữ liệu nhị phân");
		loadAstext = new JMenuItem("Tải dữ liệu văn bản");

		popup_menu.add(save2bin);
		popup_menu.add(save2text);
		popup_menu.add(loadAsbin);
		popup_menu.add(loadAstext);
		menuItemHandler(dl.getDataTable(), dl);
	}

	public void menuItemHandler(JTable data_table, DisplayLanguages dl) {
		save2text.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Language> lang = AppHandler.getDataTable(data_table);
				AppHandler.SaveToText2(lang);
				JOptionPane.showMessageDialog(null, "Thêm thành công!", null, JOptionPane.INFORMATION_MESSAGE);
				dl.updateText();
			}
		});
		loadAstext.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ArrayList<Language> lang = AppHandler.getTextLanguages();
					DefaultTableModel tableModel = new DefaultTableModel(AppHandler.ArrayList2Array(lang), dl.getColumns());
					dl.getDataTable().setModel(tableModel);
					JOptionPane.showMessageDialog(null, "Tải thành công file văn bản!", null, JOptionPane.INFORMATION_MESSAGE);
					dl.updateText();
					dl.setDisplay_mode(0);
				} catch (ClassNotFoundException | IOException e1) {
					JOptionPane.showMessageDialog(null, "Xảy ra lỗi!", null, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		save2bin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Language> lang = AppHandler.getDataTable(data_table);
				AppHandler.SaveBinLanguage(lang);
				JOptionPane.showMessageDialog(null, "Lưu thành công!", null, JOptionPane.INFORMATION_MESSAGE);
				dl.updateBin();
			}
		});
		
		loadAsbin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ArrayList<Language> lang = AppHandler.getBinLanguages();
					DefaultTableModel tableModel = new DefaultTableModel(AppHandler.ArrayList2Array(lang), dl.getColumns());
					dl.getDataTable().setModel(tableModel);
					JOptionPane.showMessageDialog(null, "Tải thành công file nhị phân!", null, JOptionPane.INFORMATION_MESSAGE);
					dl.updateBin();
					dl.setDisplay_mode(1);
				} catch (ClassNotFoundException | IOException err) {
					JOptionPane.showMessageDialog(null, "Xảy ra lỗi!", null, JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
	}

	public JPopupMenu getPopup_menu() {
		return popup_menu;
	}
}