package swingapp;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;

public class DisplayLanguageFromDataBase {
	private int display_mode;
	private int[] ids_;
	private JLabel label;
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
		label = new JLabel("Danh sách ngôn ngữ", JLabel.CENTER);
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
			updateChanged();
			data_table.setModel(tableModel);
			if (tableName.contains("Java")) {
				label.setText("Danh sách ngôn ngữ Java");
			} else {
				label.setText("Danh sách ngôn ngữ Python");
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Xảy ra lỗi khi load ngôn ngữ từ CSDL!", "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	public void updateSearch(String tableName, ArrayList<Language> dt) {
		try {
			if (tableName.toLowerCase().contains("java")) {
				setDisplay_mode(0);
			} else
				setDisplay_mode(1);
			data = AppHandler.ArrayList2Array(dt);
//			for(int i = 0;i<data.length;i++) {
//				for(int j = 0;j<6;j++) {
//					System.out.println("Data[" + i + "][" + j+"]: " +data[i][j]);
//				}
//				System.out.println();
//			}
			tableModel = new DefaultTableModel(data, columns);
			updateChanged();
			data_table.setModel(tableModel);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Xảy ra lỗi khi load ngôn ngữ từ CSDL!", "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	public void updateChanged() {
		tableModel.addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					int ID = Integer.parseInt(data_table.getValueAt(data_table.getSelectedRow(), 0).toString());
					String name = (String) data_table.getValueAt(data_table.getSelectedRow(), 1);
					String author = (String) data_table.getValueAt(data_table.getSelectedRow(), 2);
					int r_year = Integer.parseInt(data_table.getValueAt(data_table.getSelectedRow(), 3).toString());
					String usage = (String) data_table.getValueAt(data_table.getSelectedRow(), 4);
					String other = (String) data_table.getValueAt(data_table.getSelectedRow(), 5);
					if(getDisplay_mode() == 0) {
						other = other.replaceAll("JDK Version: ", "");
						AppHandler.updateChange2DB("JavaLanguage", ID, name, r_year, author, usage, other);
						setDisplay_mode(0);
					}
					else {
						other = other.replaceAll("Is Dynamic Typed: ", "");
						AppHandler.updateChange2DB("PythonLanguage", ID, name, r_year, author, usage, other);
						setDisplay_mode(1);
					}
                }
			}
		});
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

	public int[] getID(int[] ids_) {
		int[] IDs = new int[ids_.length];
		for (int i = 0; i < ids_.length; i++) {
			IDs[i] = Integer.parseInt(data_table.getValueAt(ids_[i], 0).toString());
		}
		return IDs;
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
