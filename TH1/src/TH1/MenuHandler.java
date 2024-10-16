package TH1;

import java.io.*;
import java.util.*;

public class MenuHandler implements SaveNLoad{

	// Hàm để in danh sách ngôn ngữ lập trình
	public static void printLanguages(ArrayList<Language> programmingLanguages) {
		int index = 0;
		System.out.println("Danh sách ngôn ngữ");
		for (Language lg : programmingLanguages) {
			if (lg != null) {
				System.out.println(index + ". " + lg.getName() + "\t" + lg.getReleaseYear() + "\t" + lg.getAuthor()
						+ "\t" + lg.getUsage());
				index++;
			}
		}
	}

	// Hiển thị ngôn ngữ lập trình
	
	
	
	public static void displayLanguages(ArrayList<Language> langs) {
		for (Language lang : langs) {
			lang.output();
		}
	}

	public static ArrayList<Language> loadBinLanguages() throws IOException, ClassNotFoundException {
		ArrayList<Language> programmingLanguages = new ArrayList<Language>();
		FileInputStream fis = new FileInputStream("Language.dat");
		ObjectInputStream ois = new ObjectInputStream(fis);
		programmingLanguages = (ArrayList<Language>) ois.readObject();
		fis.close();
		ois.close();
		return programmingLanguages;
	}
	
	public static ArrayList<Language> loadTextLanguages() throws IOException, ClassNotFoundException {
		ArrayList<Language> programmingLanguages= new ArrayList<>();
		FileReader fr = new FileReader("Language.txt");
		BufferedReader br = new BufferedReader(fr);
		int release_year;
		String name,author,usage;
		String line;
		while(br.ready()) {
			line = br.readLine();
			String[] sp = line.split(",");
			if(sp[5].contains("Java")) {
				release_year = Integer.parseInt(sp[0]);
				name = sp[1];
				author = sp[2];
				usage = sp[3];
				String jdkVersion = sp[4];
				programmingLanguages.add(new JavaLanguage(release_year,author,name,usage,jdkVersion));
			}
			else {
				release_year = Integer.parseInt(sp[0]);
				name = sp[1];
				author = sp[2];
				usage = sp[3];
				Boolean isDynamictyped = Boolean.parseBoolean(sp[4]);
				programmingLanguages.add(new PythonLanguage(release_year,author,name,usage,isDynamictyped));
			}
		}
		return programmingLanguages;
	}

	// Thêm ngôn ngữ lập trình
	public static void addAndSaveBinLanguage(Scanner sc) {
		int c;
		File fi = new File("Language.dat");
		ArrayList<Language> lang = new ArrayList<>();
		// Đọc danh sách hiện có từ tệp nếu tồn tại
		if (fi.exists()) {
			try (FileInputStream fis = new FileInputStream(fi);
					ObjectInputStream ois = new ObjectInputStream(fis)) {
				lang = (ArrayList<Language>) ois.readObject();
			} catch (IOException | ClassNotFoundException e) {
				System.out.println("Lỗi khi đọc tệp: " + e.getMessage());
			}
		}

		while (true) {
			try (FileOutputStream fos = new FileOutputStream(fi);
					ObjectOutputStream oos = new ObjectOutputStream(fos)) {
				System.out.println("Chọn ngôn ngữ để thêm: ");
				System.out.println("1. Java");
				System.out.println("2. Python");
				System.out.println("3. Thoát");
				c = sc.nextInt();
				sc.nextLine(); // Xóa bộ nhớ đệm sau khi nhập số nguyên
				switch (c) {
				case 1:
					JavaLanguage java = new JavaLanguage();
					java.input();
					lang.add(java);
					break;
				case 2:
					PythonLanguage python = new PythonLanguage();
					python.input();
					lang.add(python);
					break;
				case 3:
					oos.writeObject(lang);
					return;
				default:
					System.out.println("Yêu cầu không hợp lệ, vui lòng nhập lại!");
				}
			} catch (InputMismatchException e) {
				System.out.println("Hãy nhập số nguyên.");
				sc.nextLine(); // Xóa dữ liệu sai khỏi bộ nhớ đệm
			} catch (IOException e) {
				System.out.println("Xảy ra lỗi trong quá trình lưu: " + e.getMessage());
			}
		}
	}
	
	public static void addAndSaveTextLanguage(Scanner sc) throws ClassNotFoundException, IOException{
		int c;
		File fi = new File("Language.dat");
		ArrayList<Language> lang = new ArrayList<>();
		if(new File("Language.txt").exists()) {
			lang = loadTextLanguages();
		}	
		while (true) {
			try{
				PrintWriter pw = new PrintWriter(fi);
				System.out.println("Chọn ngôn ngữ để thêm: ");
				System.out.println("1. Java");
				System.out.println("2. Python");
				System.out.println("3. Thoát");
				c = sc.nextInt();
				sc.nextLine(); // Xóa bộ nhớ đệm sau khi nhập số nguyên
				switch (c) {
				case 1:
					JavaLanguage java = new JavaLanguage();
					java.input();
					lang.add(java);
					break;
				case 2:
					PythonLanguage python = new PythonLanguage();
					python.input();
					lang.add(python);
					break;
				case 3:
					for(int i = 0; i<lang.size();i++) {
						pw.println(lang.get(i));
					}
					pw.close();
					return;
				default:
					System.out.println("Yêu cầu không hợp lệ, vui lòng nhập lại!");
				}
			} catch (InputMismatchException e) {
				System.out.println("Hãy nhập số nguyên.");
				sc.nextLine(); // Xóa dữ liệu sai khỏi bộ nhớ đệm
			} catch (IOException e) {
				System.out.println("Xảy ra lỗi trong quá trình lưu: " + e.getMessage());
			}
		}
	}

	// Xóa ngôn ngữ lập trình
	public static void deleteLanguage(ArrayList<Language> programmingLanguages, Scanner sc)
			throws InputMismatchException, IOException {
		int c;
		int arrLength = programmingLanguages.size();
		printLanguages(programmingLanguages);
		while (true) {
			System.out.println("Chọn ngôn ngữ để xóa(chọn theo chỉ số): ");
			System.out.println("Nhập chỉ số nhỏ hơn 0 để thoát.");
			c = sc.nextInt();
			if (c >= arrLength) {
				throw new ArrayIndexOutOfBoundsException("Lựa chọn không hợp lệ! Chỉ số ngoài phạm vi.");
			} else {
				if(c >= 0) {
					if (programmingLanguages.get(c) != null) {
						programmingLanguages.remove(c);
						System.out.println("Xóa thành công!");
						printLanguages(programmingLanguages);
						File file = new File("Language.dat");

						try (FileOutputStream fos = new FileOutputStream(file);
								ObjectOutputStream oos = new ObjectOutputStream(fos)) {
							oos.writeObject(programmingLanguages);
						}
						break;
					} else {
						System.out.println("Ngôn ngữ tại chỉ số này không tồn tại.");
					}
				}
				else return;
			}
		}
	}

	public static void searchLanguage(ArrayList<Language> programmingLanguages, Scanner sc)
			throws InputMismatchException {
		int choice;
		while (true) {
			System.out.println("1. Tìm kiếm theo tên");
			System.out.println("2. Tìm kiếm theo tên tác giả");
			System.out.println("3. Tìm kiếm theo năm phát hành");
			System.out.println("4. Thoát");
			choice = sc.nextInt();
			sc.nextLine();
			if (choice < 1 || choice > 4) {
				throw new IllegalArgumentException("Lựa chọn không hợp lệ");
			} else {
				switch (choice) {
				case 1:
					System.out.println("Nhập tên ngôn ngữ cần tìm:");
					String languageName = sc.nextLine().toLowerCase();
					boolean isFound = false;
					for (Language lg : programmingLanguages) {
						if (lg != null && lg.getName().toLowerCase().contains(languageName)) {
							System.out.println("Tìm thấy ngôn ngữ!");
							System.out.println(
									"-" + lg.getName() + "\t" + lg.getReleaseYear() + "\t" + lg.getAuthor() + "\t");
							isFound = true;
						}
					}

					if (!isFound) {
						System.out.println("Không tìm thấy ngôn ngữ nào tên " + languageName);
					}
					break;
				case 2:
					System.out.println("Nhập tên tác giả của ngôn ngữ cần tìm:");
					String languageAuthorName = sc.nextLine().toLowerCase();
					isFound = false;
					for (Language lg : programmingLanguages) {
						if (lg != null && lg.getAuthor().toLowerCase().contains(languageAuthorName)) {
							System.out.println("Tìm thấy ngôn ngữ!");
							System.out.println(
									"-" + lg.getName() + "\t" + lg.getReleaseYear() + "\t" + lg.getAuthor() + "\t");
							isFound = true;
						}
					}

					if (!isFound) {
						System.out.println("Không tìm thấy ngôn ngữ nào có tác giả là: " + languageAuthorName);
					}
					break;
				case 3:
					int languageReleaseYear;
					while (true) {
						try {
							System.out.println("Nhập năm phát hành của ngôn ngữ cần tìm:");
							languageReleaseYear = sc.nextInt();
							sc.nextLine();
							break;
						} catch (InputMismatchException e) {
							System.out.println("Năm phát hành phải là số nguyên.");
							sc.nextLine();
						}
					}
					isFound = false;
					for (Language lg : programmingLanguages) {
						if (lg != null && lg.getReleaseYear() == languageReleaseYear) {
							System.out.println("Tìm thấy ngôn ngữ!");
							System.out.println("-" + lg.getName() + "\t" + lg.getReleaseYear() + "\t" + lg.getAuthor());
							isFound = true;
						}
					}

					if (!isFound) {
						System.out.println("Không tìm thấy ngôn ngữ nào phát hành vào năm: " + languageReleaseYear);
					}
					break;
				case 4:
					return;
				}
			}
		}
	}
}
