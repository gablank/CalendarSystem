package fellesprosjekt.gruppe30.Model;


import org.json.JSONObject;

import java.util.Date;

public class Alarm {
	private final InternalUser user;
	private final Appointment  appointment;
	private       Date         date;

	public Alarm(InternalUser user, Appointment appointment, Date date) {
		this.user = user;
		this.appointment = appointment;
		this.date = date;
	}

	public Date getDate() {
		return this.date;
	}

	public InternalUser getUser() {
		return this.user;
	}

	public Appointment getAppointment() {
		return this.appointment;
	}

	public JSONObject getJSON() {
		JSONObject obj = new JSONObject();
		obj.put("type", "alarm");
		obj.put("useEmail", this.user.getEmail());
		obj.put("appointmentId", this.appointment.getId());
		obj.put("time", this.date.getTime());
		return obj;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
