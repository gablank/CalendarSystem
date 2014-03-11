package fellesprosjekt.gruppe30.Model;

import java.security.MessageDigest;

public class User {
    private int id;
	private String firstname;
	private String lastname;
	private String username;
    private String password;
	private String email;

    public User(String firstname, String lastname, String username, String email) {
        this(firstname, lastname, username, "", email);
    }

    // This should only be used server side!
    public User(String firstname, String lastname, String username, String password, String email) {
        this.id = -1;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] passwordHash = digest.digest(password.getBytes("UTF-8")); 
			StringBuffer sb = new StringBuffer();
			for(byte b : passwordHash) {
				sb.append(Integer.toHexString(b & 0xff));
			}
			this.password = sb.toString();
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public static void main(String[] args) {
		User me = new User("Emil", "Heien", "uberjew", "password", "email");
		System.out.print("ID: " + me.getId() + "\nName: " + me.getFirstname() + " " + 
		me.getLastname() + "\nUsername: " + me.getUsername() + "\nEmail: " + me.getEmail() + 
		"\nPassword hash: " + me.getPassword());
	}
}
