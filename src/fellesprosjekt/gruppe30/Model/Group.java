package fellesprosjekt.gruppe30.Model;


import java.util.List;

public class Group {
	private List<User> members;

	public Group() {

	}

	public void addMember(User newMember) {
		this.members.add(newMember);
	}

	public List<User> getMembers() {
		return this.members;
	}
}
