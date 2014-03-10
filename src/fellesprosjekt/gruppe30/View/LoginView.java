package fellesprosjekt.gruppe30.View;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.EventListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.xml.bind.Marshaller.Listener;

import fellesprosjekt.gruppe30.Controller.LoginController;

public class LoginView extends JPanel{
	private JLabel usernameLabel, passwordLabel, spaceLabel, notifier;
	private JTextField usernameTextField;
	private JPasswordField passwordTextField;
	private JButton loginButton, quitButton;
	private JFrame frame;
	
	public LoginView(){
		GridBagLayout gridBag = new GridBagLayout();
		setLayout(gridBag);
		GridBagConstraints cLeft = new GridBagConstraints();
		GridBagConstraints cRight = new GridBagConstraints();
		
		cLeft.insets = new Insets(10,0,0,0);
		cLeft.weightx = 0.5;
		cLeft.gridx = 0;
		
		cRight.insets = new Insets(10,0,0,0);
		cRight.weightx = 0.5;
		cRight.gridx = 1;

		
		spaceLabel = new JLabel("      ");
		// username
		usernameLabel = new JLabel("Username:");
		usernameTextField = new JTextField(20);
		
		//password
		passwordLabel = new JLabel("Password:");
		passwordTextField = new JPasswordField(20);
		
		//notifier
		notifier = new JLabel("Wrong username/password");
		
		//buttons
		loginButton = new JButton("Log in");
		quitButton = new JButton("Quit");
		
		//legg til buttons
		
		cLeft.gridy = 0;
		cRight.gridy = 0;
		add(usernameLabel, cLeft);
		add(usernameTextField, cRight);
		
		cLeft.gridy = 1;
		cRight.gridy = 1;
		add(passwordLabel, cLeft);
		add(passwordTextField, cRight);
		
		cRight.gridy = 2;
		add(notifier, cRight);
		notifier.setForeground(Color.RED);
		notifier.setVisible(false);
		
		cRight.gridy = 3;
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(loginButton);
		buttonPanel.add(spaceLabel);
		buttonPanel.add(quitButton);
		add(buttonPanel, cRight);
		
		frame = new JFrame("Calendar System");
		frame.add(this);
		frame.setVisible(false);
		frame.setResizable(false);
		frame.setSize(350, 160);
		frame.setLocationRelativeTo(null);
		
		
		
	}
	
	public void setVisible(boolean visible) {
		this.frame.setVisible(visible);
	}
	
	public void addListener(LoginController controller) {
		this.addActionListener((ActionListener) controller);
	}
	
	public void addActionListener(ActionListener controller) {
		loginButton.addActionListener(controller);
		quitButton.addActionListener(controller);
	}
	
	public String getUsername() {
		return usernameTextField.getText();
	}
	
	public String getPassword() {
		return passwordTextField.getText();
	}
	
	public void viewNotifier() {
		notifier.setVisible(true);
	}
	
	public static void main(String[] args) {
		LoginView panel = new LoginView();
		panel.setVisible(true);
		panel.passwordTextField.setText("abc");
		System.out.println(panel.passwordTextField.getText());
	}
}
