package fellesprosjekt.gruppe30.Model;

import org.json.JSONObject;

import java.util.Date;

public class Attendant {
	public InternalUser user;
	public ExternalUser externalUser;
	public Appointment appointment;
	private Date alarmClock;
	public int status;
	private boolean visibleOnCalendar;
    private java.util.Date lastChecked;

    public Attendant(InternalUser user, Appointment appointment) {
        this.appointment = appointment;
		this.user = user;
		alarmClock = null;
		status = Status.NOT_ANSWERED;
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

    public static class Status {
		public static final int ATTENDTING = 0;
        public static final int NOT_ATTENDING = 1;
        public static final int NOT_ANSWERED = 2;
	}
	
	public boolean getAlarmStatus() {
		return alarmClock != null;
	}
	
	public void setAlarm(Date alarmTime) {
		this.alarmClock = alarmTime;
	}
	
	public Date getAlarmClock() {
		return alarmClock;
	}
	
	public void setAlarmClock(Date time) {
		alarmClock.setTime(time.getTime());
	}
	
	public boolean getVisibleOnCalendar() {
		return visibleOnCalendar;
	}
	
	public void setVisibleOnCalendar(boolean show) {
		visibleOnCalendar = show;
	}

    public InternalUser getUser() {
        return user;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public int getStatus() {
        return status;
    }


	public JSONObject getJSON() {
		JSONObject obj = new JSONObject();
		obj.put("userid", this.user.getId());
		obj.put("appointmentid", this.appointment.getId());
		obj.put("alarmClock", this.alarmClock.getTime());
		obj.put("status", this.status);
		obj.put("visibleOnCalendar", this.visibleOnCalendar);
		obj.put("lastChecked", this.lastChecked);
		return obj;
	}
}


