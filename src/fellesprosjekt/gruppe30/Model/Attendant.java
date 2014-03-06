package fellesprosjekt.gruppe30.Model;

import java.util.Date;

public class Attendant {
	
	public User user;
	public Appointment appointment;
	private boolean alarm;
	private Date alarm_clock;
	public Status status;
	private boolean hidden_from_calendar;
	
	public Attendant(User user) {
		this.user = user;
		alarm = false;
		alarm_clock.setTime(appointment.get_start().getTime() - 15*60*1000);
		status = status.NOT_ANSWERED;
		hidden_from_calendar = false;
	}
	
	public static enum Status {
		ATTENDTING, NOT_ATTENDING, NOT_ANSWERED
	}
	
	public boolean get_alarm_status() {
		return alarm;
	}
	
	public void set_alarm(boolean alarm) {
		this.alarm = alarm;
	}
	
	public Date get_alarm_clock() {
		return alarm_clock;
	}
	
	public void set_alarm_clock(Date time) {
		alarm_clock.setTime(time.getTime());
	}
	
	public boolean get_hidden_from_calendar() {
		return hidden_from_calendar;
	}
	
	public void set_hidden_from_calendar(boolean hide) {
		hidden_from_calendar = hide;
	}
}


