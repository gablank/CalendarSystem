package fellesprosjekt.gruppe30;

public class User {
	
	private String firstname;
	private String lastname;
	private String username;
	private String email;
	
	public User(String firstname, String lastname, String username, String email) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.email = email;
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

	public String get_email() {
		return email;
	}

	public void set_email(String email) {
		this.email = email;
	}
	
	
}
