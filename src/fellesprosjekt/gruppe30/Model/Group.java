package fellesprosjekt.gruppe30.Model;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Group {
	private int        id;
	private List<User> members;
	private String     name;


	public Group(String name) {
		this.name = name;
		members = new ArrayList<User>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<User> getMembers() {
		return this.members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addMember(User newMember) {
		this.members.add(newMember);
	}

	public JSONObject getJSON() {
		JSONObject obj = new JSONObject();
		obj.put("type", "group");
		obj.put("name", name);
		JSONArray members = new JSONArray();
		for (User member : this.members) {
			members.put(member.getEmail());
		}
		obj.put("members", members);
		return obj;
	}
}
