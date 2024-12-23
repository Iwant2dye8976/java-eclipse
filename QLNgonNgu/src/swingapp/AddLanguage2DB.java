package swingapp;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;

public class AddLanguage2DB {
	private JPanel add_panel;
	private JTextField idT, nameT, authorT, release_yearT, usageT, otherT;
	private JLabel title, id, name, author, release_year, usage, other, type;
	private JButton add_database;
	private JComboBox<String> cbx;

	public AddLanguage2DB(DisplayLanguages dl) {
		GridBagConstraints gbc = new GridBagConstraints();
		add_panel = new JPanel(new GridBagLayout());

		// Tạo các JLabel
		title = new JLabel("Thêm ngôn ngữ vào CSDL", JLabel.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 24));
		id = new JLabel("ID");
		name = new JLabel("Tên ngôn ngữ lập trình");
		author = new JLabel("Tác giả");
		release_year = new JLabel("Năm phát hành");
		usage = new JLabel("Ứng dụng");
		other = new JLabel("Phiên bản JDK");
		type = new JLabel("Ngôn ngữ lập trình");

		add_database = new JButton("Thêm vào CSDL");

		// Tạo các JTextField
		idT = new JTextField(15);
		nameT = new JTextField(15);
		authorT = new JTextField(15);
		release_yearT = new JTextField(15);
		usageT = new JTextField(15);
		otherT = new JTextField(15);

		String[] types = { "Java", "Python" };
		cbx = new JComboBox<>(types);

		cbx.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if ((String) cbx.getSelectedItem() == "Java") {
					other.setText("Phiên bản JDK");
				} else {
					other.setText("Khả năng tùy biến(Yes hoặc No)");
				}
			}
		});

		// Thiết lập GridBagConstraints cho từng thành phần
//		gbc.anchor = GridBagConstraints.NORTHWEST; // Căn lên góc trên bên trái
		gbc.insets = new Insets(5, 5, 5, 5); // Khoảng cách giữa các thành phần

		gbc.gridx = 0; // Cột 0
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		add_panel.add(title, gbc);
		
		gbc.anchor = GridBagConstraints.NORTHWEST; // Căn lên góc trên bên trái
		gbc.gridx = 0; // Cột 0
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		add_panel.add(id, gbc);

		gbc.gridx = 1; // Cột 1
		add_panel.add(idT, gbc); // Thêm JTextField

		// Thêm "Tên ngôn ngữ lập trình" và JTextField vào panel
		gbc.gridx = 0; // Cột 0
		gbc.gridy = 2; // Hàng 0
		add_panel.add(name, gbc); // Thêm JLabel

		gbc.gridx = 1; // Cột 1
		add_panel.add(nameT, gbc); // Thêm JTextField

		// Thêm "Tác giả" và JTextField vào panel
		gbc.gridx = 0;
		gbc.gridy = 3;
		add_panel.add(author, gbc);

		gbc.gridx = 1;
		add_panel.add(authorT, gbc);

		// Thêm "Năm phát hành" và JTextField vào panel
		gbc.gridx = 0;
		gbc.gridy = 4;
		add_panel.add(release_year, gbc);

		gbc.gridx = 1;
		add_panel.add(release_yearT, gbc);

		// Thêm "Ứng dụng" và JTextField vào panel
		gbc.gridx = 0;
		gbc.gridy = 5;
		add_panel.add(usage, gbc);

		gbc.gridx = 1;
		add_panel.add(usageT, gbc);

		gbc.gridx = 0;
		gbc.gridy = 6;
		add_panel.add(type, gbc);

		gbc.gridx = 1;
		add_panel.add(cbx, gbc);

		// Thêm "Ghi chú" và JTextField vào panel
		gbc.gridx = 0;
		gbc.gridy = 7;
		add_panel.add(other, gbc);

		gbc.gridx = 1;
		add_panel.add(otherT, gbc);

		gbc.gridx = 0;
		gbc.gridy = 8;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		add_panel.add(add_database, gbc);

		addAction(dl);

	}

	public void addAction(DisplayLanguages dl) {
		InputMap inputMap = add_database.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = add_database.getActionMap();
		inputMap.put(KeyStroke.getKeyStroke("ENTER"), "pressedEnter");
		actionMap.put("pressedEnter", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				add_database.doClick();
			}
		});

		add_database.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] data = getLanguageInfo();
//				for (int i = 0; i < data.length; i++) {
//					System.out.println("data[" + i + "]: " + data[i]);
//				}
					AppHandler.addLanguage2DB(Integer.parseInt(data[0]), data[1], Integer.parseInt(data[3]), data[2],
							data[4], data[5], data[6]);
				clearTextField();
			}
		});
	}

	public Boolean convert2Boolean(String isDynamicTyped) {
		return isDynamicTyped.toLowerCase().contains("yes") ? true : false;
	}

	// Lấy thông tin từ các trường nhập thông tin
	public String[] getLanguageInfo() throws NumberFormatException {
		String[] text = {};
		try {
			int id = Integer.parseInt(idT.getText().trim());
			String name = nameT.getText().trim();
			String author = authorT.getText().trim();
			String usage = usageT.getText().trim();
			String type = (String) cbx.getSelectedItem();
			String other = otherT.getText().trim();
			int releaseYear = Integer.parseInt(release_yearT.getText().trim());
			if (releaseYear <= 0 || id < 0 || name.isEmpty() || author.isEmpty() || usage.isEmpty()
					|| other.isEmpty()) {
				throw new IllegalArgumentException();
			}
			if (type.contains("Java")) {
				JavaLanguage java = new JavaLanguage(id, releaseYear, author, name, usage, other);
				text = java.toString().split(",");
			} else {
				Boolean isDynamicTyped = convert2Boolean(other);
				PythonLanguage python = new PythonLanguage(id, releaseYear, author, name, usage, isDynamicTyped);
				text = python.toString().split(",");
			}
		} catch (IllegalArgumentException err) {
			JOptionPane.showMessageDialog(null, "Xảy ra lỗi khi thêm ngôn ngữ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
		}
		return text;
	}

	// Clear các trường nhập sau khi thêm xong
	public void clearTextField() {
		idT.setText(null);
		nameT.setText(null);
		authorT.setText(null);
		release_yearT.setText(null);
		usageT.setText(null);
		otherT.setText(null);
	}

	public JPanel getAddPanel() {
		return add_panel;
	}

}
