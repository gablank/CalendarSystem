package fellesprosjekt.gruppe30.View;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AreYouSureView extends JPanel {
	private JLabel label;
	private JButton yesButton, noButton;
	GridBagConstraints gbc = new GridBagConstraints();
	
	public AreYouSureView() {
		
		//Sets the layout
		setLayout(new GridBagLayout());
		
		//Creating and adding the label
		label = new JLabel("Are you sure?");
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		add(label, gbc);
		
		//Adding buttons to panel
		yesButton = new JButton("Yes");
		noButton = new JButton("No");	
		gbc.gridy = 1;
		
		JPanel panel = new JPanel();
		panel.add(yesButton);
		panel.add(noButton);
		add(panel, gbc);
		
	}
	
	public static void main(String[] args) {
		
		AreYouSureView view = new AreYouSureView();
		JFrame frame = new JFrame();
		
		frame.setTitle("Calendar System");
		frame.setSize(220, 120);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.add(view);
	}
	
}
