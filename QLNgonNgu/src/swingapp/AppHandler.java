package swingapp;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

public class AppHandler {

	public static ArrayList<Language> getBinLanguages() throws IOException, ClassNotFoundException {
		ArrayList<Language> programmingLanguages = new ArrayList<Language>();
		FileInputStream fis = new FileInputStream("Language.dat");
		ObjectInputStream ois = new ObjectInputStream(fis);
		programmingLanguages = (ArrayList<Language>) ois.readObject();
		fis.close();
		ois.close();
		return programmingLanguages;
	}

	public static void SaveBinLanguage(ArrayList<Language> lang) {
		File fi = new File("Language.dat");
		try (FileOutputStream fos = new FileOutputStream(fi); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(lang);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void SaveToText(int id, String name, String author, int r_year, String usage, String other,
			String type) {
		File fi = new File("Language.txt");
		ArrayList<Language> lang = new ArrayList<>();
		try {
			if (fi.exists()) {
				lang = getTextLanguages();
			}
			try (PrintWriter pw = new PrintWriter(fi)) {
				if (type.contains("Java")) {
					JavaLanguage java = new JavaLanguage(id, r_year, author, name, usage, other);
					lang.add(java);
				} else {
					Boolean isDynamicTyped = Boolean.parseBoolean(other);
					PythonLanguage python = new PythonLanguage(id, r_year, author, name, usage, isDynamicTyped);
					lang.add(python);
				}
				for (int i = 0; i < lang.size(); i++) {
					pw.write(lang.get(i).toString());
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Có lỗi xảy ra: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void SaveToText2(ArrayList<Language> lang) {
		File fi = new File("Language.txt");
		try (PrintWriter pw = new PrintWriter(fi)) {
			for (int i = 0; i < lang.size(); i++) {
				pw.write(lang.get(i).toString());
			}
		} catch (IOException e) {
			System.out.println("Có lỗi xảy ra: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static ArrayList<Language> getTextLanguages() throws IOException, ClassNotFoundException {
		ArrayList<Language> programmingLanguages = new ArrayList<>();
		FileReader fr = new FileReader("Language.txt");
		BufferedReader br = new BufferedReader(fr);
		int id, release_year;
		String name, author, usage;
		String line;
		while (br.ready()) {
			line = br.readLine();
			String[] sp = line.split(",");
			if (sp[5].contains("Java")) {
				release_year = Integer.parseInt(sp[3]);
				id = Integer.parseInt(sp[0]);
				name = sp[1];
				author = sp[2];
				usage = sp[4];
				String jdkVersion = sp[5];
				programmingLanguages.add(new JavaLanguage(id, release_year, author, name, usage, jdkVersion));
			} else {
				release_year = Integer.parseInt(sp[3]);
				id = Integer.parseInt(sp[0]);
				name = sp[1];
				author = sp[2];
				usage = sp[4];
				Boolean isDynamictyped = Boolean.parseBoolean(sp[5]);
				programmingLanguages.add(new PythonLanguage(id, release_year, author, name, usage, isDynamictyped));
			}
		}
		return programmingLanguages;
	}

	public static String[][] ArrayList2Array(ArrayList<Language> lg) {
		int rows = lg.size();
		int columns = 6;
		String[][] data = new String[rows][columns];
		for (int i = 0; i < rows; i++) {
			String[] details = lg.get(i).toString().split(",");
			if (details[6].contains("Java")) {
				data[i][5] = "JDK Version: " + details[5];
			} else {
				data[i][5] = "Is Dynamic Typed: " + (Boolean.parseBoolean(details[5].toString()) ? "Yes" : "No");
			}
			for (int j = 0; j < columns - 1; j++) {
				data[i][j] = details[j];
			}
		}
		return data;
	}

	public static void update(String[][] data, JTable data_table, String[] columns) {
		try {
			// Lấy lại dữ liệu mới từ nguồn
			ArrayList<Language> dt = AppHandler.getTextLanguages();
			data = AppHandler.ArrayList2Array(dt);
			DefaultTableModel tableModel = new DefaultTableModel(data, columns);
			data_table.setModel(tableModel);

		} catch (IOException | ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Xảy ra lỗi khi cập nhật ngôn ngữ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static ArrayList<Language> getDataTable(JTable data_table) {
		ArrayList<Language> langs = new ArrayList<>();
		int rows = data_table.getRowCount();
		int columns = data_table.getColumnCount();
//		System.out.println(rows + "\n" + columns);
		for (int i = 0; i < rows; i++) {
			String data = "";
			for (int j = 0; j < columns; j++) {
				String temp = "";
				int check = 0;
				if (data_table.getValueAt(i, j).toString().contains("JDK")) {
					temp = data_table.getValueAt(i, j).toString().replaceAll("JDK Version: ", "");
					data += temp;
				} else {
					if (data_table.getValueAt(i, j).toString().contains("Is Dynamic Typed: ")) {
						temp = data_table.getValueAt(i, j).toString().replaceAll("Is Dynamic Typed: ", "");
						data += temp;
						check += 1;
					} else {
						data += data_table.getValueAt(i, j).toString();
					}
				}
//				data += data_table.getValueAt(i, j).toString();
				if (j != columns - 1) {
					data += ",";
				} else {
					String[] sp = data.split(",");
					if (check == 0) {
						langs.add(new JavaLanguage(Integer.parseInt(sp[0]), Integer.parseInt(sp[3]), sp[2], sp[1],
								sp[3], sp[5].toString()));
					} else {
						langs.add(new PythonLanguage(Integer.parseInt(sp[0]), Integer.parseInt(sp[3]), sp[2], sp[1],
								sp[4], sp[5].equalsIgnoreCase("yes")));
					}
				}
			}
		}
		return langs;
	}

	public static ArrayList<Language> getLanguageDataBase(String tableName) {
		ArrayList<Language> lang = new ArrayList<>();
		try {
			ConnectDB cdb = new ConnectDB();
			ResultSet rs = cdb.getLanguage(tableName);
			while (rs.next()) {
				if (tableName.toLowerCase().contains("java")) {
					lang.add(new JavaLanguage(rs.getInt(1), rs.getInt(3), rs.getString(4), rs.getString(2),
							rs.getString(5), rs.getString(6)));
				} else {
					lang.add(new PythonLanguage(rs.getInt(1), rs.getInt(3), rs.getString(4), rs.getString(2),
							rs.getString(5), rs.getBoolean(6)));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lang;
	}

	public static void addLanguage2DB(int ID, String name, int r_year, String author, String usage, String other,
			String type) {
		try {
			ConnectDB cdb = new ConnectDB();
			cdb.addLanguage(ID, name, r_year, author, usage, other, type);
			JOptionPane.showMessageDialog(null, "Thêm thành công vào CSDL!", null, JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Xảy ra lỗi khi thêm ngôn ngữ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	public static void deleteLanguageDatabase(int display_mode, int[] IDs) {
		try {
			ConnectDB cdb = new ConnectDB();
			if (display_mode == 0) {
				for (int id : IDs) {
					cdb.deleteLaguage("JavaLanguage", id);
				}
			} else {
				for (int id : IDs) {
					cdb.deleteLaguage("PythonLanguage", id);
				}
			}
			JOptionPane.showMessageDialog(null, "Xóa thành công!", null, JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Xảy ra lỗi khi xóa ngôn ngữ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	public static ArrayList<Language> searchLanguageDatabase(String tableName, String column, String toFind) {
		ArrayList<Language> lang = new ArrayList<>();
		try {
			ConnectDB cdb = new ConnectDB();
			ResultSet rs = cdb.searchLanguage(tableName, column, toFind);
			while (rs.next()) {
				if (tableName.toLowerCase().contains("java")) {
					lang.add(new JavaLanguage(rs.getInt(1), rs.getInt(3), rs.getString(4), rs.getString(2),
							rs.getString(5), rs.getString(6)));
				} else {
					lang.add(new PythonLanguage(rs.getInt(1), rs.getInt(3), rs.getString(4), rs.getString(2),
							rs.getString(5), rs.getBoolean(6)));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lang;
	}

	public static void updateChange2DB(String tableName, int ID, String name, int r_year, String author, String usage,
			String other) {
		ConnectDB cdb = new ConnectDB();
		cdb.updateLanguage(tableName, ID, name, r_year, author, usage, other);
	}
}
