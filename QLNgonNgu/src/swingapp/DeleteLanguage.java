package swingapp;

import javax.swing.*;
import java.awt.*;

public class DeleteLanguage {
	private JButton delete_btn;
	
	public DeleteLanguage() {
		delete_btn = new JButton("Xóa");
	}
	
	public JButton getDelete_btn() {
		return delete_btn;
	}
}
