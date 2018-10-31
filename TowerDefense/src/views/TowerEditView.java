package views;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

public class TowerEditView {
	
	JFrame frame;
	JComboBox<String> comboBox;
	JButton confirmBtn;
	
	public TowerEditView() {
		frame = new JFrame("Upgrade Tower");
		frame.setSize(200, 200);
		frame.setLayout(new BorderLayout());
		
		String[] options = { "None", "Slow", "Area of Effect"};
		
		comboBox = new JComboBox<String>(options);
		
		confirmBtn = new JButton("Confirm");
		
		
		frame.add(comboBox, BorderLayout.CENTER);
		frame.add(confirmBtn, BorderLayout.SOUTH);
		//frame.pack();
		frame.setVisible(true);
		
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JComboBox<String> getComboBox() {
		return comboBox;
	}

	public void setComboBox(JComboBox<String> comboBox) {
		this.comboBox = comboBox;
	}

	public JButton getConfirmBtn() {
		return confirmBtn;
	}

	public void setConfirmBtn(JButton confirmBtn) {
		this.confirmBtn = confirmBtn;
	}
}
