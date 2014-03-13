package fellesprosjekt.gruppe30;


import fellesprosjekt.gruppe30.Model.*;

import java.util.List;

public abstract class Application {
	protected List<User>        users;
	protected List<Appointment> appointments;
	protected List<MeetingRoom> meetingRooms;
	protected List<Alarm>       alarms;
	protected List<Group>       groups;



	/*
	Adders and removers
	 */

	public void addUser(User user) {
		users.add(user);
	}

	public void removeUser(User user) {
		users.remove(user);
	}

	public void addAppointment(Appointment appointment) {
		appointments.add(appointment);
	}

	public void removeAppointment(Appointment appointment) {
		appointments.remove(appointment);
	}

	public void addMeetingRoom(MeetingRoom meetingRoom) {
		meetingRooms.add(meetingRoom);
	}

	public void removeMeetingRoom(MeetingRoom meetingRoom) {
		meetingRooms.remove(meetingRoom);
	}

	public void addAlarm(Alarm alarm) {
		alarms.add(alarm);
	}

	public void removeAlarm(Appointment appointment, User user) {
		if(user instanceof InternalUser) {
			this.removeAlarm(Utilities.getAlarm(appointment, user, this.alarms));
		}
	}

	public void removeAlarm(Alarm alarm) {
		alarms.remove(alarm);
	}

	public void addGroup(Group group) {
		groups.add(group);
	}

	public void removeGroup(Group group) {
		groups.remove(group);
	}




	/*
	Getters and setters
	 */

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public List<MeetingRoom> getMeetingRooms() {
		return meetingRooms;
	}

	public void setMeetingRooms(List<MeetingRoom> meetingRooms) {
		this.meetingRooms = meetingRooms;
	}

	public List<Alarm> getAlarms() {
		return alarms;
	}

	public void setAlarms(List<Alarm> alarms) {
		this.alarms = alarms;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
}
