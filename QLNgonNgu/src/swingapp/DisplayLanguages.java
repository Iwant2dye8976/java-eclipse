package swingapp;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class DisplayLanguages {
	private int index;
	private int[] indexes;
	private JLabel title;
	private JScrollPane data_panel;
	private JPanel f_panel;
	private JTable data_table;
	private DefaultTableModel tableModel;
	private String[][] data;
	private String[] columns = { "Tên gọi", "Tác giả", "Năm phát hành", "Ứng dụng", "Ghi chú" };

//	public DisplayLanguages() {};

	public DisplayLanguages() {
		try {
			ArrayList<Language> dt = AppHandler.loadTextLanguages();
			data = AppHandler.ArrayList2Array(dt);
		} catch (IOException | ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Xảy ra lỗi khi thêm ngôn ngữ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
		title = new JLabel("Danh sách ngôn ngữ");

		// Tạo JScrollPane chứa bảng
		data_panel = new JScrollPane(data_table);

		// Tạo JPanel và đặt Layout theo BoxLayout
		f_panel = new JPanel();
		f_panel.setLayout(new BoxLayout(f_panel, BoxLayout.Y_AXIS));

		// Căn chỉnh tiêu đề ở giữa
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		f_panel.add(title);
		f_panel.add(Box.createRigidArea(new Dimension(0, 10))); // Khoảng cách giữa tiêu đề và bảng

		// Thêm JScrollPane chứa bảng
		f_panel.add(data_panel);
	};

	public void update() {
		try {
			// Lấy lại dữ liệu mới từ nguồn
			ArrayList<Language> dt = AppHandler.loadTextLanguages();
			data = AppHandler.ArrayList2Array(dt);
			tableModel = new DefaultTableModel(data, columns);
			data_table.setModel(tableModel);

		} catch (IOException | ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Xảy ra lỗi khi cập nhật ngôn ngữ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
		}
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
}
