package fellesprosjekt.gruppe30.View;

import fellesprosjekt.gruppe30.Controller.LoginController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LoginView extends JPanel implements KeyListener {
	private JLabel         usernameLabel, passwordLabel, spaceLabel, notifier;
	private JTextField     usernameTextField;
	private JPasswordField passwordTextField;
	public  JButton        loginButton, quitButton;
	private JFrame         frame;

	public LoginView() {
		GridBagLayout gridBag = new GridBagLayout();
		setLayout(gridBag);
		GridBagConstraints cLeft = new GridBagConstraints();
		GridBagConstraints cRight = new GridBagConstraints();

		cLeft.insets = new Insets(10, 0, 0, 0);
		cLeft.weightx = 0.5;
		cLeft.gridx = 0;

		cRight.insets = new Insets(10, 0, 0, 0);
		cRight.weightx = 0.5;
		cRight.gridx = 1;


		spaceLabel = new JLabel("      ");
		// username
		usernameLabel = new JLabel("E-mail:");
		usernameTextField = new JTextField(20);
		usernameTextField.addKeyListener(this);

		//password
		passwordLabel = new JLabel("Password:");
		passwordTextField = new JPasswordField(20);
		passwordTextField.addKeyListener(this);

		//notifier
		notifier = new JLabel("     Wrong username/password");

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
		cRight.anchor = GridBagConstraints.WEST;
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

	public void displayNotifier() {
		notifier.setVisible(true);
	}



	@Override
	public void keyPressed(KeyEvent arg0) {	
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int key=e.getKeyCode();
		if (e.getSource().equals(usernameTextField) || e.getSource().equals(passwordTextField)){
			if(key==KeyEvent.VK_ENTER){
				loginButton.doClick();
			}
		}
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
	}
	
	public static void main(String[] args) {
		LoginView panel = new LoginView();
		panel.setVisible(true);
		panel.passwordTextField.setText("abc");
		System.out.println(panel.passwordTextField.getText());
	}
}
