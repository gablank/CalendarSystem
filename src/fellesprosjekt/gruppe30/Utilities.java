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

	public static Alarm getAlarm(int appointmentId, User user, List<Alarm> alarms) {
		for(Alarm alarm : alarms) {
			if (alarm.getAppointment() != null && alarm.getAppointment().getId() == appointmentId && alarm.getUser() == user)
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

	public static Attendant getAttendantByUserAppointment(Appointment appointment, User user) {
		for(Attendant attendant : appointment.getAttendants()) {
			if(attendant.getUser() == user) {
				return attendant;
			}
		}

		return null;
	}

	public static int getNumberOfWeeksInYear(int year) {
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.set(java.util.Calendar.YEAR, year);
		cal.set(java.util.Calendar.MONTH, java.util.Calendar.DECEMBER);
		cal.set(java.util.Calendar.DAY_OF_MONTH, 31);

		int ordinalDay = cal.get(java.util.Calendar.DAY_OF_YEAR);
		int weekDay = cal.get(java.util.Calendar.DAY_OF_WEEK) - 1; // Sunday = 0
		int numberOfWeeks = (ordinalDay - weekDay + 10) / 7;

		return numberOfWeeks;
	}
	public static String dateToFormattedString(Date date) {
		return Utilities.dateToFormattedString(date, true);
	}

	public static String dateToFormattedString(Date date, boolean displayYear) {
		String res = "";

		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(date);
		String day = Integer.toString(gregorianCalendar.get(Calendar.DAY_OF_MONTH));
		if(day.length() == 1) {
			day = "0" + day;
		}
		res += day;
		String month = Integer.toString(gregorianCalendar.get(Calendar.MONTH) + 1);
		if(month.length() == 1) {
			month = "0" + month;
		}
		res += "." + month;
		if(displayYear) {
			res += "." + Integer.toString(gregorianCalendar.get(Calendar.YEAR));
		}

		return res;
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

	public static boolean isAvailable(MeetingRoom room, Appointment isForAppointment, List<Appointment> appointments) {
		if (room == null)
			return true;

		int roomId = room.getId();

		for (Appointment appointment : appointments) {
			if (appointment.getMeetingRoom() == null)
				continue;

			if (appointment.getMeetingRoom().getId() != roomId)
				continue;

			if (appointment.getId() == isForAppointment.getId())
				continue;

			// (StartDate1 <= EndDate2) && (StartDate2 <= EndDate1)
			if (appointment.getStart().before(isForAppointment.getEnd()) && isForAppointment.getStart().before(appointment.getEnd())) {
				// there is overlap
				return false;
			}
		}

		return true;
	}
}
