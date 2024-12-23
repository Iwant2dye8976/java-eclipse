package swingapp;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;

public class AddLanguage {
	private JPanel add_panel;
	private JTextField idT, nameT, authorT, release_yearT, usageT, otherT;
	private JLabel title, id, name, author, release_year, usage, other, type;
	private JButton add_btn;
	private JComboBox<String> cbx;

	public AddLanguage(DisplayLanguages dl) {
		GridBagConstraints gbc = new GridBagConstraints();
		add_panel = new JPanel(new GridBagLayout());

		// Tạo các JLabel
		title = new JLabel("Thêm ngôn ngữ vào file", JLabel.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 24));
		id = new JLabel("ID");
		name = new JLabel("Tên ngôn ngữ lập trình");
		author = new JLabel("Tác giả");
		release_year = new JLabel("Năm phát hành");
		usage = new JLabel("Ứng dụng");
		other = new JLabel("Phiên bản JDK");
		type = new JLabel("Ngôn ngữ lập trình");

		// Tạo các JTextField
		idT = new JTextField(15);
		nameT = new JTextField(15);
		authorT = new JTextField(15);
		release_yearT = new JTextField(15);
		usageT = new JTextField(15);
		otherT = new JTextField(15);

		String[] types = { "Java", "Python" };
		cbx = new JComboBox<>(types);
		
		add_btn = new JButton("Thêm");

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
		add_panel.add(add_btn, gbc);
		
		addAction(dl);
	}

	public void addAction(DisplayLanguages dl) {
		add_btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] data = getLanguageInfo();
				if (dl.getDisplay_mode() == 0) { // Display moede = 0: Hiển thị file text | Display mode = 1: Hiển thị
													// file nhị phân
					AppHandler.SaveToText(Integer.parseInt(data[0]),data[1], data[2], Integer.parseInt(data[3]), data[4], data[5].toString(),
							data[6]);
				} else {
					ArrayList<Language> l = AppHandler.getDataTable(dl.getDataTable());
					if (data[5].contains("Java")) {
						l.add(new JavaLanguage(Integer.parseInt(data[0]) ,Integer.parseInt(data[3]), data[2], data[1], data[4], data[5].toString()));
					} else {
						l.add(new PythonLanguage(Integer.parseInt(data[0]), Integer.parseInt(data[3]), data[2], data[1], data[4], Boolean.parseBoolean(data[5])));
//						System.out.println(data[4]);
					}
					AppHandler.SaveBinLanguage(l);
					dl.updateBin();
				}
				JOptionPane.showMessageDialog(null, "Thêm thành công vào file!", null, JOptionPane.INFORMATION_MESSAGE);
				clearTextField();
			}
		});

		InputMap inputMap = add_btn.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = add_btn.getActionMap();
		inputMap.put(KeyStroke.getKeyStroke("ENTER"), "pressedEnter");
		actionMap.put("pressedEnter", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				add_btn.doClick();
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
			String name = nameT.getText().trim();
			String author = authorT.getText().trim();
			String usage = usageT.getText().trim();
			String type = (String) cbx.getSelectedItem();
			String other = otherT.getText().trim();
			int releaseYear = Integer.parseInt(release_yearT.getText().trim());
			int id = Integer.parseInt(idT.getText().trim());
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
		nameT.setText(null);
		authorT.setText(null);
		release_yearT.setText(null);
		usageT.setText(null);
		otherT.setText(null);
	}

	public JPanel getAddPanel() {
		return add_panel;
	}

	public JButton getAddButton() {
		return add_btn;
	}
}
