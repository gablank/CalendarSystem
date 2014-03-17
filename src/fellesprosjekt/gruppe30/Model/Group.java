package fellesprosjekt.gruppe30.Model;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Group {
	private int        id;
	private List<InternalUser> members;
	private String     name;


	public Group(String name) {
		this.id = -1;
		this.name = name;
		members = new ArrayList<InternalUser>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<InternalUser> getMembers() {
		return this.members;
	}

	public void setMembers(List<InternalUser> members) {
		this.members = members;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addMember(InternalUser newMember) {
		this.members.add(newMember);
	}

	@Override
	public String toString() {
		return "Group: " + this.name;
	}

	public JSONObject getJSON() {
		JSONObject obj = new JSONObject();
		obj.put("type", "group");
		obj.put("name", name);
		JSONArray members = new JSONArray();
		for (InternalUser member : this.members) {
			members.put(member.getEmail());
		}
		obj.put("members", members);
		return obj;
	}
}
