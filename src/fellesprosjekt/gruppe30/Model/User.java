package fellesprosjekt.gruppe30.Model;


import org.json.JSONObject;

public abstract class User {
	protected String email;


	public User(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public abstract JSONObject getJSON();
}
