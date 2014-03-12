package fellesprosjekt.gruppe30.Model;


import org.json.JSONObject;

import java.util.List;

public class Group {
    private List<User> members;
    private String     name;


    public Group(String name) {
        this.name = name;
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

    public List<User> getMembers() {
        return this.members;
    }

    public JSONObject getJSON() {
        return null;
    }
}
