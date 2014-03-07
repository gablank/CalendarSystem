package fellesprosjekt.gruppe30.Model;

import java.security.MessageDigest;

public class User {
    private int id;
	private String firstname;
	private String lastname;
	private String username;
    private String password;
	private String email;
	
	public User(String firstname, String lastname, String username, String password, String email) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.email = email;
		set_password(password);
			
	}

    public int get_id() {
        return id;
    }

    public void set_id(int id) {
        this.id = id;
    }

    public String get_name() {
        return this.get_firstname() + " " + this.get_lastname();
    }

	public String get_firstname() {
		return firstname;
	}

	public void set_firstname(String firstname) {
		this.firstname = firstname;
	}

	public String get_lastname() {
		return lastname;
	}

	public void set_lastname(String lastname) {
		this.lastname = lastname;
	}

    public String get_username() {
        return username;
    }

    public void set_username(String username) {
        this.username = username;
    }

    public String get_password() {
        return password;
    }

    public void set_password(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] password_hash = digest.digest(password.getBytes("UTF-8")); 
			StringBuffer sb = new StringBuffer();
			for(byte b : password_hash) {
				sb.append(Integer.toHexString(b & 0xff));
			}
			this.password = sb.toString();
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
    }

	public String get_email() {
		return email;
	}

	public void set_email(String email) {
		this.email = email;
	}
	
	public static void main(String[] args) {
		User me = new User("Emil", "Heien", "uberjew", "password", "email");
		System.out.print("ID: " + me.get_id() + "\nName: " + me.get_firstname() + " " + 
		me.get_lastname() + "\nUsername: " + me.get_username() + "\nEmail: " + me.get_email() + 
		"\nPassword hash: " + me.get_password());
	}
}
