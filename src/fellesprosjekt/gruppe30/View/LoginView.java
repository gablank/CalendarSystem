package fellesprosjekt.gruppe30.View;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


//blir veldig kompakt i toppen, m� f� inn luft.
public class LoginView extends JPanel{
	private JLabel username_label, password_label;
	private JTextField usernameTextField;
	private JPasswordField passwordTextField;
	private JButton login_button, quit_button;
	
	public LoginView(){
		GridBagLayout gridBag = new GridBagLayout();
		setLayout(gridBag); // f�r feil, ingen anelse hvorfor.
		GridBagConstraints c_left = new GridBagConstraints();
		GridBagConstraints c_right = new GridBagConstraints();
		
		c_left.insets = new Insets(10,0,0,0);
		c_left.weightx = 0.5;
		c_left.gridx = 0;
		
		c_right.insets = new Insets(10,0,0,0);
		c_right.weightx = 0.5;
		c_right.gridx = 1;

		
		
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
		
		c_left.gridy = 2;
		c_right.gridy = 2;
		add(login_button, c_left);
		add(quit_button, c_right);
		
		
		
	}
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		LoginView panel = new LoginView();
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(350, 125);
	}
}
