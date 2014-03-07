package fellesprosjekt.gruppe30.View;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.xml.bind.Marshaller.Listener;

public class LoginView extends JPanel{
	private JLabel username_label, password_label, spaceLabel;
	private JTextField usernameTextField;
	private JPasswordField passwordTextField;
	private JButton login_button, quit_button;
	
	public LoginView(){
		GridBagLayout gridBag = new GridBagLayout();
		setLayout(gridBag);
		GridBagConstraints c_left = new GridBagConstraints();
		GridBagConstraints c_right = new GridBagConstraints();
		
		c_left.insets = new Insets(10,0,0,0);
		c_left.weightx = 0.5;
		c_left.gridx = 0;
		
		c_right.insets = new Insets(10,0,0,0);
		c_right.weightx = 0.5;
		c_right.gridx = 1;

		
		spaceLabel = new JLabel("      ");
		// username
		username_label = new JLabel("Username:");
		usernameTextField = new JTextField(20);
		
		//password
		password_label = new JLabel("Password:");
		passwordTextField = new JPasswordField(20);
		
		//buttons
		login_button = new JButton("Log in");
		quit_button = new JButton("Quit");
		
		//legg til buttons
		
		c_left.gridy = 0;
		c_right.gridy = 0;
		add(username_label, c_left);
		add(usernameTextField, c_right);
		
		c_left.gridy = 1;
		c_right.gridy = 1;
		add(password_label, c_left);
		add(passwordTextField, c_right);
		
		c_right.gridy = 2;
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(login_button);
		buttonPanel.add(spaceLabel);
		buttonPanel.add(quit_button);
		add(buttonPanel, c_right);
		
		JFrame frame = new JFrame("Calendar System");
		frame.add(this);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setSize(350, 140);
		frame.setLocationRelativeTo(null);
		
		
		
	}
	
	public void addListener(Listener controller) {
		this.addActionListener((ActionListener) controller);
	}
	
	public void addActionListener(ActionListener controller) {
		login_button.addActionListener(controller);
		quit_button.addActionListener(controller);
	}
	
	public static void main(String[] args) {
		LoginView panel = new LoginView();
	}
}
