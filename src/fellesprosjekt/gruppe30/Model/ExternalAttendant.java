package fellesprosjekt.gruppe30.Model;


import org.json.JSONObject;

public class ExternalAttendant  extends Attendant {

	public ExternalAttendant(ExternalUser user, Appointment appointment) {
		super(user, appointment);
	}

	public JSONObject getJSON() {
		JSONObject obj = new JSONObject();
		obj.put("type", "externalAttendant");
		obj.put("email", this.user.getEmail());
		obj.put("appointmentid", this.appointment.getId());
		obj.put("status", this.status);
		return obj;
	}
}
