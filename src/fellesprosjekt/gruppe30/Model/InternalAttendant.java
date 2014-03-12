package fellesprosjekt.gruppe30.Model;

import org.json.JSONObject;

import java.util.Date;

public class InternalAttendant extends Attendant {
	private boolean visibleOnCalendar;
    private java.util.Date lastChecked;

	public InternalAttendant(InternalUser user, Appointment appointment) {
		super(user, appointment);

		visibleOnCalendar = true;
		lastChecked = new Date(0);
	}

    // Set last checked to now
    public void setLastChecked() {
        this.lastChecked = new Date();
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

    public int getStatus() {
        return status;
    }


	public JSONObject getJSON() {
		JSONObject obj = new JSONObject();
		obj.put("type", "internalAttendant");
		obj.put("email", this.user.getEmail());
		obj.put("appointmentid", this.appointment.getId());
		obj.put("status", this.status);
		obj.put("visibleOnCalendar", this.visibleOnCalendar);
		obj.put("lastChecked", this.lastChecked);
		return obj;
	}
}


