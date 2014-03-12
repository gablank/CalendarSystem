package fellesprosjekt.gruppe30.Model;

import org.json.JSONObject;

import java.util.Date;

public class InternalAttendant extends Attendant {
	private boolean        visibleOnCalendar;
	private java.util.Date lastChecked;

	public InternalAttendant(InternalUser user, Appointment appointment) {
		super(user, appointment);

		visibleOnCalendar = true;
		lastChecked = new Date(0);
	}

	// Sets lastChecked to now
	public void setLastChecked() {
		this.lastChecked = new Date();
	}

	public void setLastChecked(Date date) {
		this.lastChecked = date;
	}

	public java.util.Date getLastChecked() {
		return lastChecked;
	}

	public boolean getVisibleOnCalendar() {
		return visibleOnCalendar;
	}

	public void setVisibleOnCalendar(boolean show) {
		visibleOnCalendar = show;
	}


	public JSONObject getJSON() {
		JSONObject obj = new JSONObject();
		obj.put("type", "internalAttendant");
		obj.put("email", this.user.getEmail());
		obj.put("appointmentId", this.appointment.getId());
		obj.put("status", this.status);
		obj.put("visibleOnCalendar", this.visibleOnCalendar);
		obj.put("lastChecked", this.lastChecked.getTime());
		return obj;
	}
}


