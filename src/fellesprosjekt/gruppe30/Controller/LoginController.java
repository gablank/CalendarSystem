package fellesprosjekt.gruppe30.Controller;

import fellesprosjekt.gruppe30.Client;

public class LoginController {
	private final Client client;
	
	public LoginController(Client client) {
		this.client = client;
	}
	
	public boolean check_credentials() {
		String username = client.get_login_view().get_username();
		String password = client.get_login_view().get_password();
		if (is_valid(username, password)) {
			/*
			 * Set user information
			 */
			return true;
		} return false;
	}
	
	private boolean is_valid(String username, String password) {
		return true;
	}
	
	public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        String cmd = actionEvent.getActionCommand();
        
        if (cmd.equalsIgnoreCase("login")) {
        	if (check_credentials()) {
        		client.open("CalendarView");
        	} else client.get_login_view().view_notifier(); // Shows wrong username/password notifier
        } else if (cmd.equalsIgnoreCase("quit")) {
        	
        }
	}
}
