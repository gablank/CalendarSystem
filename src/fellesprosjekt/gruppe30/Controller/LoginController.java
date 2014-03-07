package fellesprosjekt.gruppe30.Controller;

import fellesprosjekt.gruppe30.Client;

public class LoginController {
	private final Client client;
	
	public LoginController(Client client) {
		this.client = client;
	}
	
	public void get_credentials() {
		String username = client.loginView.get_username();
		String password = client.loginView.get_password();
		if (is_valid(username, password)) {
			System.out.println("YAY");
		}
	}
	
	private boolean is_valid(String username, String password) {
		return true;
	}
}
