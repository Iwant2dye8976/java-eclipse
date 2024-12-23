package TH1;

import java.io.*;
import java.util.*;

public class Language implements ProgrammingLanguage, Serializable {
	private static final long serialVersionUID = 1L;
	private int release_year;
	private String author, name, usage;

	public Language() {
	}

	public Language(int release_year_, String author_, String name_, String usage_) {
		release_year = release_year_;
		author = author_;
		name = name_;
		usage = usage_;
	}

	@Override
	// Cac getter va setter
	public int getReleaseYear() {
		return this.release_year;
	}

	@Override
	public String getAuthor() {
		return this.author;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getUsage() {
		return this.usage;
	}

	public void setReleaseYear(int release_year) {
		this.release_year = release_year;
	}

	public void setAuthor(String author_) {
		this.author = author_;
	}

	public void setName(String name_) {
		this.name = name_;
	}

	public void setUsages(String usages_) {
		this.usage = usages_;
	}

	@Override
	public void input() {
		Scanner sc = new Scanner(System.in);
		while (true) {
			try {
				System.out.println("=================");
				System.out.print("Năm phát hành: ");
				release_year = sc.nextInt();
				sc.nextLine();
				while (release_year <= 0) {
					System.out.println("Năm không được <= 0.");
					System.out.println("Hãy nhập lại năm phát hành: ");
					release_year = sc.nextInt();
					sc.nextLine();
				}
				break;
			} catch (InputMismatchException e) {
				System.out.println("Sai định dạng dữ liệu, hãy nhập lại!");
				sc.nextLine();
			}
		}
		System.out.print("Tên tác giả: ");
		author = sc.nextLine();

		System.out.print("Tên ngôn ngữ: ");
		name = sc.nextLine();

		System.out.println("Nhập ứng dụng của ngôn ngữ: ");
		usage = sc.nextLine();
	}

	@Override
	public void output() {
		System.out.println("Thông tin về ngôn ngữ:");
		System.out.println("Tên: " + name);
		System.out.println("Năm phát hành: " + release_year);
		System.out.println("Tác giả: " + author);
		System.out.println("Ứng dụng của ngôn ngữ: " + usage);
	}

	public String toText() {
		return getName() + "," + getReleaseYear() + "," + getAuthor() + "," + getUsage();
	}

	@Override
	public String toString() {
		return getName() + "," + getAuthor() + "," + getReleaseYear() + "," + getUsage();
	}

}
