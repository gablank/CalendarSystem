package fellesprosjekt.gruppe30.Controller;

import java.awt.event.ActionListener;

import javax.swing.JButton;

import fellesprosjekt.gruppe30.Client;
import fellesprosjekt.gruppe30.View.LoginView;

public class LoginController implements ActionListener {
	private final Client client;
	
	public LoginController(Client client) {
		this.client = client;
	}
	
	public boolean checkCredentials() {
		String username = client.getLoginView().getUsername();
		String password = client.getLoginView().getPassword();
		/*
		 * TODO check if input is valid
		 * set user information?
		 */
		return true;
	}
	
	
	public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        String cmd = actionEvent.getActionCommand();
        
        if (cmd.equalsIgnoreCase("log in")) {
        	if (checkCredentials()) {
        		client.close("login");
        		client.open("calendar");
        	} else client.getLoginView().viewNotifier(); // Shows wrong username/password notifier
        } else if (cmd.equalsIgnoreCase("quit")) {
        	client.quit(0);
        }
	}
}
