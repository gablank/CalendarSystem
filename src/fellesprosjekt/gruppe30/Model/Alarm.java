package fellesprosjekt.gruppe30.Model;


import org.json.JSONObject;

import java.util.Date;

public class Alarm {
	private final InternalUser user;
	private       Appointment  appointment;
	private       Date         date;
	private 	  boolean	   isSet;

	public Alarm(InternalUser user, Appointment appointment, Date date) {
		this.user = user;
		this.appointment = appointment;
		this.date = date;
		isSet = false;
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
		obj.put("userEmail", this.user.getEmail());
		obj.put("appointmentId", this.appointment.getId());
		obj.put("time", this.date.getTime());
		return obj;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isSet() {
		return isSet;
	}

	public void setSet(boolean isSet) {
		this.isSet = isSet;
	}

	public Alarm myClone() {
		Alarm result = new Alarm(this.user, this.appointment, this.date);
		result.setSet(this.isSet);

		return result;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}
}
