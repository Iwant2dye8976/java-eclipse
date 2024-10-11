package swingapp;

import java.io.*;
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

	public static void SaveToText(String name, String author, int r_year, String usage, String other, String type) {
		File fi = new File("Language.txt");
		ArrayList<Language> lang = new ArrayList<>();
		try {
			if (fi.exists()) {
				lang = getTextLanguages();
			}
			try (PrintWriter pw = new PrintWriter(fi)) {
				if (type.contains("Java")) {
					JavaLanguage java = new JavaLanguage(r_year, author, name, usage, other);
					lang.add(java);
				} else {
					Boolean isDynamicTyped = Boolean.parseBoolean(other);
					PythonLanguage python = new PythonLanguage(r_year, author, name, usage, isDynamicTyped);
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
		int release_year;
		String name, author, usage;
		String line;
		while (br.ready()) {
			line = br.readLine();
			String[] sp = line.split(",");
			if (sp[5].contains("Java")) {
				release_year = Integer.parseInt(sp[2]);
				name = sp[0];
				author = sp[1];
				usage = sp[3];
				String jdkVersion = sp[4];
				programmingLanguages.add(new JavaLanguage(release_year, author, name, usage, jdkVersion));
			} else {
				release_year = Integer.parseInt(sp[2]);
				name = sp[0];
				author = sp[1];
				usage = sp[3];
				Boolean isDynamictyped = Boolean.parseBoolean(sp[4]);
				programmingLanguages.add(new PythonLanguage(release_year, author, name, usage, isDynamictyped));
			}
		}
		return programmingLanguages;
	}

	public static String[][] ArrayList2Array(ArrayList<Language> lg) {
		int rows = lg.size();
		int columns = 5;
		String[][] data = new String[rows][columns];
		for (int i = 0; i < rows; i++) {
			String[] details = lg.get(i).toString().split(",");
			if (details[5].contains("Java")) {
				data[i][4] = "JDK Version: " + details[4];
			} else {
				data[i][4] = "Is Dynamic Typed: " + (Boolean.parseBoolean(details[4].toString()) ? "Yes" : "No");
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
						langs.add(new JavaLanguage(Integer.parseInt(sp[2]), sp[1], sp[0], sp[3], sp[4].toString()));
					} else {
						langs.add(new PythonLanguage(Integer.parseInt(sp[2]), sp[1], sp[0], sp[3],
								sp[4].equalsIgnoreCase("yes")));
					}
				}
			}
		}
		return langs;
	}

}
