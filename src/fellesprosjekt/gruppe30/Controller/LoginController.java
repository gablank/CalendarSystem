package fellesprosjekt.gruppe30.Controller;

import fellesprosjekt.gruppe30.Client;
import fellesprosjekt.gruppe30.Model.InternalUser;
import org.json.JSONObject;

import java.awt.event.ActionListener;

public class LoginController implements ActionListener {
    private final Client client;

    public LoginController(Client client) {
        this.client = client;
    }

    public void sendLoginRequest() {
        String username = client.getLoginView().getUsername();
        String password = client.getLoginView().getPassword();

        JSONObject obj = new JSONObject();
        obj.put("type", "login");
        obj.put("username", username);
        obj.put("password", InternalUser.hashPassword(password));

        client.network.send(obj);
    }

    public void handleLoginResponse(boolean success, String username) {
        if (success) {
            client.setLoggedin(username);
        } else {
            client.getLoginView().viewNotifier();
        }
    }


    public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        String cmd = actionEvent.getActionCommand();

        if (cmd.equalsIgnoreCase("log in")) {
            sendLoginRequest();
        } else if (cmd.equalsIgnoreCase("quit")) {
            client.quit(0);
        }
    }
}
