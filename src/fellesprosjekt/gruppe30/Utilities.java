package fellesprosjekt.gruppe30;


import fellesprosjekt.gruppe30.Model.Alarm;
import fellesprosjekt.gruppe30.Model.Appointment;
import fellesprosjekt.gruppe30.Model.MeetingRoom;
import fellesprosjekt.gruppe30.Model.User;

import java.util.List;

public class Utilities {


	public static User getUserByEmail(String email, List<User> users) {
		for (User user : users) {
			if (user.getEmail().equals(email))
				return user;
		}

		return null;
	}

	public static Appointment getAppointmentById(int id, List<Appointment> appointments) {
		for (Appointment appointment : appointments) {
			if (appointment.getId() == id)
				return appointment;
		}

		return null;
	}

	public static Alarm getAlarm(Appointment appointment, User user, List<Alarm> alarms) {
		for (Alarm alarm : alarms) {
			if (alarm.getAppointment() == appointment && alarm.getUser() == user)
				return alarm;
		}

		return null;
	}

	public static MeetingRoom getMeetingRoomById(int id, List<MeetingRoom> meetingRooms) {
		for (MeetingRoom meetingRoom : meetingRooms) {
			if (meetingRoom.getId() == id)
				return meetingRoom;
		}

		return null;
	}
}
