package fellesprosjekt.gruppe30.Controller;

import fellesprosjekt.gruppe30.Client;
import fellesprosjekt.gruppe30.Model.InternalUser;
import fellesprosjekt.gruppe30.View.LoginView;
import org.json.JSONObject;

import java.awt.event.ActionListener;

public class LoginController implements ActionListener {
	private final Client client;
	private final LoginView loginView;

	public LoginController(Client client) {
		this.client = client;
		this.loginView = new LoginView();
		this.loginView.addListener(this);
	}

	public void sendLoginRequest() {
		String username = loginView.getUsername();
		String password = loginView.getPassword();

		JSONObject obj = new JSONObject();
		obj.put("type", "login");
		obj.put("username", username);
		obj.put("password", InternalUser.hashPassword(password));

		client.network.send(obj);
	}

	public void handleLoginResponse(boolean success, String username) {
		if(success) {
			client.setLoggedIn(username);
		} else {
			loginView.displayNotifier();
		}
	}


	public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
		String cmd = actionEvent.getActionCommand();

		if(cmd.equalsIgnoreCase("log in")) {
			sendLoginRequest();
		} else if(cmd.equalsIgnoreCase("quit")) {
			client.quit(0);
		}
	}

	public void setVisible(boolean state) {
		this.loginView.setVisible(state);
	}
}
