package fellesprosjekt.gruppe30.Model;


import org.json.JSONObject;

import java.security.MessageDigest;

public class InternalUser extends User {
	private String firstName;
	private String lastName;
	private String password;


	public InternalUser(String firstName, String lastname, String email) {
		super(email);

		this.firstName = firstName;
		this.lastName = lastname;
	}

	public String getName() {
		return this.getFirstName() + " " + this.getLastName();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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
		obj.put("email", this.email);
		obj.put("firstName", this.firstName);
		obj.put("lastName", this.lastName);
		return obj;
	}

	public static void main(String[] args) {
		InternalUser me = new InternalUser("Emil", "Heien", "email");
		System.out.println("\nName: " + me.getFirstName() + " " +
 me.getLastName() + "\nEmail: " + me.getEmail());
	}
}
