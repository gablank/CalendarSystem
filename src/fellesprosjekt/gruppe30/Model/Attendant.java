package fellesprosjekt.gruppe30.Model;

import java.util.Date;

public class Attendant {
	
	public User user;
	public Appointment appointment;
	private Date alarm_clock;
	public int status;
	private boolean visible_on_calendar;
	
	public Attendant(User user, Appointment appointment) {
        this.appointment = appointment;
		this.user = user;
		alarm_clock = null;
		status = Status.NOT_ANSWERED;
		visible_on_calendar = true;
	}
	
	public static class Status {
		public static final int ATTENDTING = 0;
        public static final int NOT_ATTENDING = 1;
        public static final int NOT_ANSWERED = 2;
	}
	
	public boolean get_alarm_status() {
		return alarm_clock != null;
	}
	
	public void set_alarm(Date alarm_time) {
		this.alarm_clock = alarm_time;
	}
	
	public Date get_alarm_clock() {
		return alarm_clock;
	}
	
	public void set_alarm_clock(Date time) {
		alarm_clock.setTime(time.getTime());
	}
	
	public boolean get_visible_on_calendar() {
		return visible_on_calendar;
	}
	
	public void set_visible_on_calendar(boolean show) {
		visible_on_calendar = show;
	}

    public User get_user() {
        return user;
    }

    public Appointment get_appointment() {
        return appointment;
    }

    public int get_status() {
        return status;
    }
}


