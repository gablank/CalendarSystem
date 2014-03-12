package fellesprosjekt.gruppe30.Model;


import org.json.JSONObject;

import java.security.MessageDigest;

public class InternalUser extends User {
	private int id;
	private String firstname;
	private String lastname;
	private String username;
	private String password;


	public InternalUser(String firstname, String lastname, String username, String email) {
		super(email);

		this.id = -1;
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.getFirstname() + " " + this.getLastname();
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	// Should only be used by the server!
	public void setPassword(String password) {
		this.password = password;
	}

	// Should only be used by the server!
	public String getPassword() {
		return this.password;
	}

	public static String hashPassword(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] passwordHash = digest.digest(password.getBytes("UTF-8"));
			StringBuffer sb = new StringBuffer();
			for(byte b : passwordHash) {
				sb.append(Integer.toHexString(b & 0xff));
			}
			return sb.toString();
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	public JSONObject getJSON() {
		JSONObject obj = new JSONObject();
		obj.put("type", "internalUser");
		obj.put("id", this.id);
		obj.put("username", this.username);
		obj.put("firstName", this.firstname);
		obj.put("lastName", this.id);
		obj.put("email", this.email);
		return obj;
	}

	public static void main(String[] args) {
		InternalUser me = new InternalUser("Emil", "Heien", "uberjew", "email");
		System.out.println("ID: " + me.getId() + "\nName: " + me.getFirstname() + " " +
				me.getLastname() + "\nUsername: " + me.getUsername() + "\nEmail: " + me.getEmail());
	}
}
