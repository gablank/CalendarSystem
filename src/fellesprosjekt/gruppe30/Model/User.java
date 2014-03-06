package fellesprosjekt.gruppe30.Model;

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
        // TODO: Password should be hashed with sha-256 here
        this.password = password;
		this.email = email;
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
        this.password = password;
    }

	public String get_email() {
		return email;
	}

	public void set_email(String email) {
		this.email = email;
	}
}
