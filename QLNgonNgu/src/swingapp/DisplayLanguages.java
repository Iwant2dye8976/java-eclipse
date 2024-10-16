package swingapp;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class DisplayLanguages {
	private int display_mode = 0;
	private int index;
	private int[] indexes;
	private JLabel loadMode;
	private JScrollPane data_panel;
	private JPanel f_panel;
	private JTable data_table;
	private DefaultTableModel tableModel;
	private ArrayList<Language> dt;
	private String[][] data;
	private String[] columns = {"ID", "Tên gọi", "Tác giả", "Năm phát hành", "Ứng dụng", "Ghi chú" };

//	public DisplayLanguages() {};

	public DisplayLanguages() {
		if (getDisplay_mode() == 0) {
			try {
				dt = AppHandler.getTextLanguages();
				data = AppHandler.ArrayList2Array(dt);
			} catch (IOException | ClassNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Xảy ra lỗi khi thêm ngôn ngữ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			try {
				dt = AppHandler.getBinLanguages();
				data = AppHandler.ArrayList2Array(dt);
			} catch (ClassNotFoundException | IOException e) {
				JOptionPane.showMessageDialog(null, "Xảy ra lỗi khi thêm ngôn ngữ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
			}
		}
		data_table = new JTable(data, columns);
		data_table.getTableHeader().setReorderingAllowed(false);
//		data_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		data_table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				{
					if (!e.getValueIsAdjusting()) {
						if (data_table.getSelectedRowCount() < 2)
							index = data_table.getSelectedRow();
						else {
							indexes = data_table.getSelectedRows();
						}
					}
				}
			}
		});

		// Tạo JScrollPane chứa bảng
		data_panel = new JScrollPane(data_table);

		// Tạo JPanel và đặt Layout theo BoxLayout
		f_panel = new JPanel();
		f_panel.setLayout(new BoxLayout(f_panel, BoxLayout.Y_AXIS));

		// Thêm JScrollPane chứa bảng
		loadMode = new JLabel("Chế độ đọc file: Văn bản");

		f_panel.add(loadMode);
		f_panel.add(data_panel);
		AddmouseListener();
//		updateText();
	};

	public void updateText() {
		try {
			// Lấy lại dữ liệu mới từ nguồn
			ArrayList<Language> dt = AppHandler.getTextLanguages();
			data = AppHandler.ArrayList2Array(dt);
			loadMode.setText("Chế độ đọc file: Văn bản");
			tableModel = new DefaultTableModel(data, columns);
			data_table.setModel(tableModel);
		} catch (IOException | ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Xảy ra lỗi khi cập nhật ngôn ngữ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void updateBin() {
		try {
			// Lấy lại dữ liệu mới từ nguồn
			ArrayList<Language> dt = AppHandler.getBinLanguages();
			data = AppHandler.ArrayList2Array(dt);
			loadMode.setText("Chế độ đọc file: Nhị phân");
			tableModel = new DefaultTableModel(data, columns);
			data_table.setModel(tableModel);
		} catch (IOException | ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Xảy ra lỗi khi cập nhật ngôn ngữ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void AddmouseListener() {
		Popup_menu popup_menu = new Popup_menu(this);
		data_table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showPopup(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showPopup(e);
				}
			}

			public void showPopup(MouseEvent e) {

				popup_menu.getPopup_menu().show(e.getComponent(), e.getX(), e.getY());
				;
			}
		});

		data_panel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showPopup(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showPopup(e);
				}
			}

			public void showPopup(MouseEvent e) {

				popup_menu.getPopup_menu().show(e.getComponent(), e.getX(), e.getY());
				;
			}
		});
	}

	public void setDisplay_mode(int display_mode) {
		this.display_mode = display_mode;
	}

	public int getDisplay_mode() {
		return display_mode;
	}

	public int getSelectedIndex() {
		return index;
	}

	public int[] getSelectedIndexes() {
		return indexes;
	}

	public JTable getDataTable() {
		return data_table;
	}

	public JScrollPane getDataPanel() {
		return data_panel;
	}

	public JPanel getFPanel() {
		return f_panel;
	}

	public String[] getColumns() {
		return columns;
	}
}
