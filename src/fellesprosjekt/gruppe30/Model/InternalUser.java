package fellesprosjekt.gruppe30.Model;


import org.json.JSONObject;

import java.security.MessageDigest;

public class InternalUser extends User {
	private String firstName;
	private String lastName;
	private String password;


	public InternalUser(String email, String firstName, String lastname) {
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
	public void setPassword(String password, boolean hash) {
		if(hash) {
			this.password = InternalUser.hashPassword(password);
		} else {
			this.password = password;
		}
	}

	public void setPassword(String password) {
		this.setPassword(password, true);
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

	public String toString() {
		return this.getName();
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
		InternalUser me = new InternalUser("Email", "Emil", "Heien");
		System.out.println("\nName: " + me.getFirstName() + " " +
				me.getLastName() + "\nEmail: " + me.getEmail());
	}
}
