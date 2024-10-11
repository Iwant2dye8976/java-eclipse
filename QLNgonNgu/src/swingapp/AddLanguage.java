package swingapp;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class AddLanguage {
	private JPanel add_panel;
	private JTextField nameT, authorT, release_yearT, usageT, otherT;
	private JLabel name, author, release_year, usage, other, type;
	private JButton add_btn;
	private JComboBox<String> cbx;
	private ArrayList<Language> data;

	public AddLanguage(DisplayLanguages dl) {
		GridBagConstraints gbc = new GridBagConstraints();
		add_panel = new JPanel(new GridBagLayout());

		// Tạo các JLabel
		name = new JLabel("Tên ngôn ngữ lập trình");
		author = new JLabel("Tác giả");
		release_year = new JLabel("Năm phát hành");
		usage = new JLabel("Ứng dụng");
		other = new JLabel("Phiên bản JDK");
		type = new JLabel("Ngôn ngữ lập trình");

		add_btn = new JButton("Thêm");

		// Tạo các JTextField
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
		
		add_btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] data = getLanguageInfo();
				if(dl.getDisplay_mode() == 0) { //Display moede = 0: Hiển thị file text | Display mode = 1: Hiển thị file nhị phân 
					AppHandler.SaveToText(data[0], data[1], Integer.parseInt(data[2]), data[3], data[4].toString(), data[5]);
				}
				else {
					ArrayList<Language> l = AppHandler.getDataTable(dl.getDataTable());
					if(data[5].contains("Java")) {
						l.add(new JavaLanguage(Integer.parseInt(data[2]), data[1], data[0], data[3], data[4].toString()));
					}
					else {
						l.add(new PythonLanguage(Integer.parseInt(data[2]), data[1], data[0], data[3], Boolean.parseBoolean(data[4])));
//						System.out.println(data[4]);
					}
					AppHandler.SaveBinLanguage(l);
				}
				JOptionPane.showMessageDialog(null, "Thêm thành công!", null, JOptionPane.INFORMATION_MESSAGE);
				clearTextField();
			}
		});

		// Thiết lập GridBagConstraints cho từng thành phần
		gbc.anchor = GridBagConstraints.NORTHWEST; // Căn lên góc trên bên trái
		gbc.insets = new Insets(5, 5, 5, 5); // Khoảng cách giữa các thành phần

		// Thêm "Tên ngôn ngữ lập trình" và JTextField vào panel
		gbc.gridx = 0; // Cột 0
		gbc.gridy = 0; // Hàng 0
		add_panel.add(name, gbc); // Thêm JLabel

		gbc.gridx = 1; // Cột 1
		add_panel.add(nameT, gbc); // Thêm JTextField

		// Thêm "Tác giả" và JTextField vào panel
		gbc.gridx = 0;
		gbc.gridy = 1;
		add_panel.add(author, gbc);

		gbc.gridx = 1;
		add_panel.add(authorT, gbc);

		// Thêm "Năm phát hành" và JTextField vào panel
		gbc.gridx = 0;
		gbc.gridy = 2;
		add_panel.add(release_year, gbc);

		gbc.gridx = 1;
		add_panel.add(release_yearT, gbc);

		// Thêm "Ứng dụng" và JTextField vào panel
		gbc.gridx = 0;
		gbc.gridy = 3;
		add_panel.add(usage, gbc);

		gbc.gridx = 1;
		add_panel.add(usageT, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		add_panel.add(type, gbc);

		gbc.gridx = 1;
		add_panel.add(cbx, gbc);

		// Thêm "Ghi chú" và JTextField vào panel
		gbc.gridx = 0;
		gbc.gridy = 5;
		add_panel.add(other, gbc);

		gbc.gridx = 1;
		add_panel.add(otherT, gbc);

		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		add_panel.add(add_btn, gbc);

	}

	public Boolean convert2Boolean(String isDynamicTyped) {
		return isDynamicTyped.toLowerCase().contains("yes") ? true : false;
	}

	
	//Lấy thông tin từ các trường nhập thông tin
	public String[] getLanguageInfo() throws NumberFormatException {
		String[] text = {};
		try {
			String name = nameT.getText();
			String author = authorT.getText();
			int releaseYear = Integer.parseInt(release_yearT.getText());
			if (releaseYear <= 0) {
				throw new IllegalArgumentException();
			}
			String usage = usageT.getText();
			String type = (String) cbx.getSelectedItem();
			if (type.contains("Java")) {
				String other = otherT.getText();
				JavaLanguage java = new JavaLanguage(releaseYear, author, name, usage, other);
				text = java.toString().split(",");
			} else {
				String other = otherT.getText();
				Boolean isDynamicTyped = convert2Boolean(other);
				PythonLanguage python = new PythonLanguage(releaseYear, author, name, usage, isDynamicTyped);
				text = python.toString().split(",");
			}
		} catch (IllegalArgumentException err) {
			JOptionPane.showMessageDialog(null, "Xảy ra lỗi khi thêm ngôn ngữ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
		}
		return text;
	}
	
	//Clear các trường nhập sau khi thêm xong
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
