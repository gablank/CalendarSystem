package fellesprosjekt.gruppe30;

import fellesprosjekt.gruppe30.Model.*;

import java.beans.PropertyChangeSupport;
import java.util.List;

public abstract class Application {
	protected List<User>			users;
	protected List<Appointment>		appointments;
	protected List<MeetingRoom>		meetingRooms;
	protected List<Alarm>			alarms;
	protected List<Group>			groups;
	protected PropertyChangeSupport	pcs;

	/*
	 * Adders and removers
	 */

	public void addUser(User user) {
		users.add(user);
		if (pcs != null)
			pcs.firePropertyChange("addUser", 1 ,2);
	}

	public void removeUser(User user) {
		users.remove(user);
		if (pcs != null)
			pcs.firePropertyChange("removeUser", 1 ,2);
	}

	public void addAppointment(Appointment appointment) {
		appointments.add(appointment);
		if (pcs != null)
			pcs.firePropertyChange("addAppointment", null, appointment);
	}

	public void removeAppointment(Appointment appointment) {
		appointments.remove(appointment);
		if (pcs != null)
			pcs.firePropertyChange("removeAppointment", 1 ,2);
	}

	public void addMeetingRoom(MeetingRoom meetingRoom) {
		meetingRooms.add(meetingRoom);
		if (pcs != null)
			pcs.firePropertyChange("addMeetingRoom", 1 ,2);
	}

	public void removeMeetingRoom(MeetingRoom meetingRoom) {
		meetingRooms.remove(meetingRoom);
		if (pcs != null)
			pcs.firePropertyChange("removeMeetingRoom", 1 ,2);
	}

	public void addAlarm(Alarm alarm) {
		alarms.add(alarm);
		if (pcs != null)
			pcs.firePropertyChange("addAlarm", 1 ,2);
	}

	public void removeAlarm(Appointment appointment, User user) {
		if (user instanceof InternalUser) {
			this.removeAlarm(Utilities.getAlarm(appointment, user, this.alarms));
		}
	}

	public void removeAlarm(Alarm alarm) {
		alarms.remove(alarm);
		if (pcs != null)
			pcs.firePropertyChange("removeAlarm", 1 ,2);
	}

	public void addGroup(Group group) {
		groups.add(group);
		if (pcs != null)
			pcs.firePropertyChange("addGroup", 1 ,2);
	}

	public void removeGroup(Group group) {
		groups.remove(group);
		if (pcs != null)
			pcs.firePropertyChange("removeGroup", 1 ,2);
	}

	/*
	 * Getters and setters
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
