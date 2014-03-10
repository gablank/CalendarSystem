package fellesprosjekt.gruppe30.Controller;

import fellesprosjekt.gruppe30.Client;

public class LoginController {
	private final Client client;
	
	public LoginController(Client client) {
		this.client = client;
		client.getLoginView().addListener(this);
	}
	
	public boolean checkCredentials() {
		String username = client.getLoginView().getUsername();
		String password = client.getLoginView().getPassword();
		if (isValid(username, password)) {
			/*
			 * Set user information
			 */
			return true;
		} return false;
	}
	
	
	private boolean isValid(String username, String password) {
		return true;
	}
	
	public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        String cmd = actionEvent.getActionCommand();
        
        if (cmd.equalsIgnoreCase("login")) {
        	if (checkCredentials()) {
        		client.open("CalendarView");
        	} else client.getLoginView().viewNotifier(); // Shows wrong username/password notifier
        } else if (cmd.equalsIgnoreCase("quit")) {
        	
        }
	}
}
