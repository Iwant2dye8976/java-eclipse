package swingapp;

import java.io.*;
import java.util.*;

public class PythonLanguage extends Language{
	private static final long serialVersionUID = 1L;
	private boolean isDynamicTyped;

	public PythonLanguage() {
	}

	public PythonLanguage(int id, int release_year, String author, String name, String usage, boolean isDynamicTyped) {
		super(id, release_year, author, name, usage); // Constructor cua lop cha
		this.isDynamicTyped = isDynamicTyped;
	}

	public boolean isDynamicTyped() {
		return this.isDynamicTyped;
	}

	public void setDynamicTyped(boolean isDynamicTyped) {
		this.isDynamicTyped = isDynamicTyped;
	}

	@Override
	public void input() {
		Scanner sc = new Scanner(System.in);
		super.input();
		System.out.print("Khả năng tùy biến(Yes or No): ");
		isDynamicTyped = sc.nextLine().equalsIgnoreCase("Yes");
	}

	@Override
	public void output() {
		super.output();
		System.out.println("Is Dynamic Type: " + (this.isDynamicTyped ? "Yes" : "No")); // In ra yes, no thay vi true,
																						// false
		System.out.println("=================");
	}
	
	@Override
	public String toString() {
		return super.toString() + "," + this.isDynamicTyped+",Python\n";
	}
}
