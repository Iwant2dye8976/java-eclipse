package swingapp;

import java.io.*;
import java.util.*;

public class AppHandler {
	public static void SaveToText(String name, String author, int r_year, String usage, String other, String type) {
		File fi = new File("Language.txt");
		ArrayList<Language> lang = new ArrayList<>();
		try {
			if (fi.exists()) {
				lang = loadTextLanguages();
			}
			try (PrintWriter pw = new PrintWriter(fi)) {
				if (type.contains("Java")) {
					JavaLanguage java = new JavaLanguage(r_year, author, name, usage, other);
					lang.add(java);
				} else {
					Boolean isDynamicTyped = other.equalsIgnoreCase("Yes");
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

	public static ArrayList<Language> loadTextLanguages() throws IOException, ClassNotFoundException {
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
				data[i][4] = "Is Dynamic Typed: " + (Boolean.parseBoolean(details[4]) ? "Yes" : " No");
			}
			for (int j = 0; j < columns - 1; j++) {
				data[i][j] = details[j];
			}
		}
		return data;
	}
}
