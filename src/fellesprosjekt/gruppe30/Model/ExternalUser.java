package fellesprosjekt.gruppe30.Model;


import org.json.JSONObject;

public class ExternalUser extends User {
    public ExternalUser(String email) {
        super(email);
    }

    public JSONObject getJSON() {
        JSONObject obj = new JSONObject();
        obj.put("type", "externalUser");
        obj.put("email", this.email);
        return obj;
    }
}
