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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
