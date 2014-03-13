package fellesprosjekt.gruppe30.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.EventListener;

//Author Kristoffer

public class AreYouSureView extends JPanel {

	private JLabel     label;
	private JButton    yesButton, noButton;
	private JFrame     frame;
	

	public AreYouSureView() {

		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(new GridBagLayout());

		// Creating and adding the label
		label = new JLabel("Are you sure?");
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		add(label, gbc);

		// Adding buttons to panel
		yesButton = new JButton("Yes");
		noButton = new JButton("No");
		gbc.gridy = 1;

		JPanel panel = new JPanel();
		panel.add(yesButton);
		panel.add(noButton);
		add(panel, gbc);
		
		frame = new JFrame();
		frame.setTitle("Calendar System");
		frame.setSize(220, 120);
		frame.setVisible(false);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(this);
		
		//test code
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//end test code
	}

	// listener FEEEST
	public void addListener(EventListener controller) {
		this.addMouseListener((MouseListener) controller);
	}

	public void addMouseListener(MouseListener controller) {
		yesButton.addMouseListener(controller);
		noButton.addMouseListener(controller);
	}
	
	public void setVisible(boolean visible) {
		this.frame.setVisible(visible);
	}

	public static void main(String[] args) {

		AreYouSureView view = new AreYouSureView();
		view.setVisible(true);
	}

}