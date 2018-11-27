package views;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

/**
 * @author Ethan Parks
 * View for the Tower Edit window.
 */
public class TowerEditView {
	
	/**
	 * Frame for the Tower Edit window.
	 */
	private JFrame frame;
	/**
	 * Combobox for selecting the edit to be applied to the tower.
	 */
	private JComboBox<String> comboBox;
	/**
	 * Confirm button to apply the selected edit to the tower.
	 */
	private JButton confirmBtn;
	
	/**
	 * Create a show frame for the Tower Edit window.
	 */
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

	/**
	 * Returns the frame for the Tower Edit window.
	 * @return The frame for the Tower Edit window.
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * Returns the combobox from the Tower Edit window.
	 * @return The combobox from the Tower Edit window.
	 */
	public JComboBox<String> getComboBox() {
		return comboBox;
	}

	/**
	 * Returns the confirm button from the Tower Edit window.
	 * @return The confirm button from the Tower Edit window.
	 */
	public JButton getConfirmBtn() {
		return confirmBtn;
	}
}
