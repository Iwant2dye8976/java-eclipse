package TH1;

import java.io.*;
import java.util.*;

public class Main {
	public static void main(String[] args) {
		ArrayList<Language> programming_languages = new ArrayList<>();
//		String[] usages = { "2", "3", "4" };
//		programming_languages.add(new PythonLanguage(2020,"Hehe","Java",usages,true));
//		try(FileOutputStream fos = new FileOutputStream("Language.dat",true);
//			ObjectOutputStream oos = new ObjectOutputStream(fos)){
//			oos.writeObject(programming_languages);
//		}
//		catch(IOException e) {}

		Scanner sc = new Scanner(System.in);
		int choice;

		while (true) {
			// Hiển thị menu
			System.out.println("Menu:");
			System.out.println("1. Xem danh sách ngôn ngữ lập trình");
			System.out.println("2. Thêm ngôn ngữ lập trình");
			System.out.println("3. Xóa ngôn ngữ lập trình");
			System.out.println("4. Tìm kiếm ngôn ngữ lập trình");
			System.out.println("5. Thoát");
			System.out.print("Hãy nhập lựa chọn của bạn: ");

			while (true) {
				// Nhập lựa chọn từ người dùng
				try {
//					programming_languages_bin = MenuHandler.loadBinLanguages();
					choice = sc.nextInt(); // Xóa bộ đệm dòng sau khi đọc số nguyên
					sc.nextLine();
					break;
				} catch (InputMismatchException e) {
					System.out.println("Hãy nhập số nguyên hợp lệ!"); // Loại bỏ đầu vào không hợp lệ
					sc.nextLine();
					// Quay lại vòng lặp và yêu cầu nhập lại
				}
			}
			// Xử lý lựa chọn bằng switch-case
			switch (choice) {
			case 1:
				try {
					programming_languages = MenuHandler.loadTextLanguages();
				} catch (ClassNotFoundException e) {
					System.out.println("Xảy ra lỗi: " + e.getMessage());
				} catch (IOException e) {
					System.out.println("File không tồn tại hoặc file trống.");
				}
				MenuHandler.displayLanguages(programming_languages);
				break;
			case 2:
				try {
					MenuHandler.addAndSaveTextLanguage(sc);
				} catch (IOException |ClassNotFoundException e) {
					System.out.println("Xảy ra lỗi: " + e.getMessage());
				}
				break;
			case 3:
				try {
					MenuHandler.deleteLanguage(programming_languages, sc);
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Lựa chọn không hợp lệ! Chỉ số ngoài phạm vi.");
				} catch (InputMismatchException e) {
					System.out.println("Hãy nhập số nguyên.");
				} catch (IOException e) {
					System.out.println("Lỗi trong quá trình lưu dữ liệu: " + e.getMessage());
				}
				break;
			case 4:
				try {
					MenuHandler.searchLanguage(programming_languages, sc);
				} catch (IllegalArgumentException e) {
					System.out.println("Lựa chọn không hợp lệ.");
				} catch (InputMismatchException e) {
					System.out.println("Hãy nhập số nguyên.");
				}
				break;
			case 5:
				System.out.println("Thoát chương trình.");
				sc.close();
				return; // Thoát khỏi phương thức main (kết thúc chương trình)
			default:
				System.out.println("Yêu cầu không hợp lệ, vui lòng nhập lại!");
			}
		}
	}
}
