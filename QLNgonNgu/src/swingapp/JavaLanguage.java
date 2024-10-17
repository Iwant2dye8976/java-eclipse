package swingapp;

import java.io.*;
import java.util.*;

public class JavaLanguage extends Language{
	private static final long serialVersionUID = 1L;
	private String jdkVersion;

	public JavaLanguage() {
		super();
	}

	public JavaLanguage(int id, int release_year, String author, String name, String usage, String jdkVersion) {
		super(id, release_year, author, name, usage); // Gọi constructor của lớp cha
		this.jdkVersion = jdkVersion;
	}

	public String getJdkVersion() {
		return this.jdkVersion;
	}

	public void setJdkVersion(String jdkVersion) {
		this.jdkVersion = jdkVersion;
	}

	@Override
	public void input() {
		super.input();
		Scanner sc = new Scanner(System.in);
		while (true) {
			try {
				System.out.print("Nhập phiên bản JDK: ");
				jdkVersion = sc.nextLine();

				float jdk = Float.parseFloat(jdkVersion);
				if (jdk < 0) {
					System.out.println("Phiên bản không được nhỏ hơn 0.");
				} else {
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("Phiên bản vừa nhập không hợp lệ.");
			}
		}
	}

	@Override
	public void output() {
		super.output();
		System.out.println("Phiên bản JDK: " + this.jdkVersion);
		System.out.println("=================");
	}
	
	@Override
	public String toString() {
		return super.toString() + "," + this.jdkVersion + ",Java\n";
	}
}
