package swingapp;

import javax.swing.*;

public class MenuBar {
	private JMenuBar menubar;
	private JMenu features, about;
	private JMenuItem view, add, delete, search;

	public MenuBar() {
		menubar = new JMenuBar();
		features = new JMenu("Menu");
		about = new JMenu("About");

		view = new JMenuItem("Xem danh sách ngôn ngữ lập trình");
		add = new JMenuItem("Thêm ngôn ngữ lập trình");
		delete = new JMenuItem("Xóa ngôn ngữ lập trình");
		search = new JMenuItem("Tìm kiếm ngôn ngữ lập trình");

		features.add(view);
		features.add(add);
		features.add(delete);
		features.add(search);

		menubar.add(features);
		menubar.add(about);
	}

	public JMenuBar getMenuBar() {
		return menubar;
	}

	public JMenuItem getView() {
		return view;
	}
	
	public JMenuItem getAdd() {
		return add;
	}
	
	public JMenuItem getDelete() {
		return delete;
	}
	
	public JMenuItem getSearch() {
		return search;
	}
}
