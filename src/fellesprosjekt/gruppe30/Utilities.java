package fellesprosjekt.gruppe30;


import fellesprosjekt.gruppe30.Model.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Utilities {


	public static User getUserByEmail(String email, List<User> users) {
		for(User user : users) {
			if(user.getEmail().equals(email))
				return user;
		}

		return null;
	}

	public static Appointment getAppointmentById(int id, List<Appointment> appointments) {
		for(Appointment appointment : appointments) {
			if(appointment.getId() == id)
				return appointment;
		}

		return null;
	}

	public static Alarm getAlarm(Appointment appointment, User user, List<Alarm> alarms) {
		for(Alarm alarm : alarms) {
			if(alarm.getAppointment() == appointment && alarm.getUser() == user)
				return alarm;
		}

		return null;
	}

	public static MeetingRoom getMeetingRoomById(int id, List<MeetingRoom> meetingRooms) {
		for(MeetingRoom meetingRoom : meetingRooms) {
			if(meetingRoom.getId() == id)
				return meetingRoom;
		}

		return null;
	}

	public static Attendant getAttendantByEmailAppointmentId(String attendantEmail, int appointmentId, List<Attendant> attendants) {
		for(Attendant attendant : attendants) {
			if(attendant.getAppointment().getId() == appointmentId && attendant.getUser().getEmail().equals(attendantEmail)) {
				return attendant;
			}
		}

		return null;
	}

	public static String dateToFormattedString(Date date) {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(date);
		String day = Integer.toString(gregorianCalendar.get(Calendar.DAY_OF_MONTH));
		if(day.length() == 1) {
			day = "0" + day;
		}
		String month = Integer.toString(gregorianCalendar.get(Calendar.MONTH));
		if(month.length() == 1) {
			month = "0" + month;
		}
		String year = Integer.toString(gregorianCalendar.get(Calendar.YEAR) - 2000);
		return day + "." + month + "." + year;
	}

	public static String timeToFormattedString(Date date) {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(date);
		String hour = Integer.toString(gregorianCalendar.get(Calendar.HOUR_OF_DAY));
		if(hour.length() == 1) {
			hour = "0" + hour;
		}
		String minute = Integer.toString(gregorianCalendar.get(Calendar.MINUTE));
		if(minute.length() == 1) {
			minute = "0" + minute;
		}
		return hour + ":" + minute;
	}
}
