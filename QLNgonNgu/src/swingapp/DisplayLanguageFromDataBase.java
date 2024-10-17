package swingapp;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

public class DisplayLanguageFromDataBase {
	private int display_mode;
	private int[] ids_;
	private JScrollPane data_panel;
	private JPanel f_panel;
	private JTable data_table;
	private DefaultTableModel tableModel;
	private ArrayList<Language> dt;
	private String[][] data;
	private String[] columns = { "ID", "Tên gọi", "Tác giả", "Năm phát hành", "Ứng dụng", "Ghi chú" };

	public DisplayLanguageFromDataBase() {
		try {
			dt = AppHandler.getLanguageDataBase("JavaLanguage");
			data = AppHandler.ArrayList2Array(dt);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Xảy ra lỗi khi load ngôn ngữ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		data_table = new JTable(data, columns);
		data_table.getTableHeader().setReorderingAllowed(false);

		// Tạo JScrollPane chứa bảng
		data_panel = new JScrollPane(data_table);
		JLabel label = new JLabel("Danh sách ngôn ngữ", JLabel.CENTER);
		label.setFont(new Font("Arial", Font.BOLD, 24));
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		// Tạo JPanel và đặt Layout theo BoxLayout
		f_panel = new JPanel();
		f_panel.setLayout(new BoxLayout(f_panel, BoxLayout.Y_AXIS));

		f_panel.add(label);
		SearchLanguage sl = new SearchLanguage(this);
		f_panel.add(sl.getSearchPanel());

		f_panel.add(data_panel);

		DeleteLanguage dlt = new DeleteLanguage(this);
		f_panel.add(dlt.getDel_panel());
		AddmouseListener();
		addDataTableAction();
	}

	public void updateFromDataBase(String tableName) {
		try {
			if (tableName.toLowerCase().contains("java")) {
				setDisplay_mode(0);
			} else
				setDisplay_mode(1);
			dt = AppHandler.getLanguageDataBase(tableName);
			data = AppHandler.ArrayList2Array(dt);
//			for(int i = 0;i<data.length;i++) {
//				for(int j = 0;j<6;j++) {
//					System.out.println("Data[" + i + "][" + j+"]: " +data[i][j]);
//				}
//				System.out.println();
//			}
			tableModel = new DefaultTableModel(data, columns);
			data_table.setModel(tableModel);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Xảy ra lỗi khi load ngôn ngữ từ CSDL!", "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
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

	public void addDataTableAction() {
		data_table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					ids_ = data_table.getSelectedRows();
				}
			}
		});
	}

	public void setDisplay_mode(int display_mode) {
		this.display_mode = display_mode;
	}

	public int getDisplay_mode() {
		return display_mode;
	}

	public int[] getSelectedIds_() {
		return ids_;
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