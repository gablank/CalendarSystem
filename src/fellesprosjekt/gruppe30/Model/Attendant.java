package fellesprosjekt.gruppe30.Model;

import java.util.Date;

public class Attendant {
	
	public User user;
	public Appointment appointment;
	private Date alarmClock;
	public int status;
	private boolean visibleOnCalendar;
	
	public Attendant(User user, Appointment appointment) {
        this.appointment = appointment;
		this.user = user;
		alarmClock = null;
		status = Status.NOT_ANSWERED;
		visibleOnCalendar = true;
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

    public User getUser() {
        return user;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public int getStatus() {
        return status;
    }
}


