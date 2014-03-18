package fellesprosjekt.gruppe30.View;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
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

		// Creating and adding the nameLabel
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

	public void addListener(ActionListener controller) {
		yesButton.addActionListener(controller);
		noButton.addActionListener(controller);
	}
	
	public void setVisible(boolean visible) {
		this.frame.setVisible(visible);
	}

	public static void main(String[] args) {

		AreYouSureView view = new AreYouSureView();
		view.setVisible(true);
	}

}