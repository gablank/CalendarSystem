package fellesprosjekt.gruppe30;


import fellesprosjekt.gruppe30.Controller.AlarmController;
import fellesprosjekt.gruppe30.Controller.Database;
import fellesprosjekt.gruppe30.Controller.ServerListener;
import fellesprosjekt.gruppe30.Model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Server {
	private final Database database = Database.getInstance();

	private       List<User>        users;
	private       List<Appointment> appointments;
	private       List<MeetingRoom> meetingRooms;
	private       List<Alarm>       alarms;
	private final Thread            alarmController;
	private final Thread            serverListener;
	private       List<Group>       groups;

	public Server() {
		this.loadDatabase();

		serverListener = new Thread(new ServerListener(this));
		serverListener.start();

		alarmController = new Thread(new AlarmController(this));
		alarmController.start();

		try {
			serverListener.join();
			alarmController.join();
		} catch(InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadDatabase() {
		this.database.loadDatabase(this);
	}

	public synchronized List<Alarm> getAlarms() {
		return (List<Alarm>) ((ArrayList<Alarm>) this.alarms).clone();
	}

	public synchronized void sendMail(String recipient, String subject, String body) {
		System.out.println("Sending mail to " + recipient);
	}

	public void shutdown() {
		this.alarmController.interrupt();
		this.serverListener.interrupt();
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void setAlarms(List<Alarm> alarms) {
		this.alarms = alarms;
	}

	public void setMeetingRooms(List<MeetingRoom> meetingRooms) {
		this.meetingRooms = meetingRooms;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void insertAppointment(Appointment appointment) {
		int id = database.insertAppointment(appointment);

		if(appointment.getId() == -1) {
			appointment.setId(id);
			appointments.add(appointment);
		} else {
			Appointment oldAppointment = Utilities.getAppointmentById(id, appointments);

			oldAppointment.setDescription(appointment.getDescription());
			oldAppointment.setEnd(appointment.getEnd());
			oldAppointment.setLastUpdated(new Date());
			oldAppointment.setMeetingPlace(appointment.getMeetingPlace());
			oldAppointment.setOwner(appointment.getOwner());
			oldAppointment.setMeetingRoom(appointment.getMeetingRoom());
			oldAppointment.setStart(appointment.getStart());
			oldAppointment.setTitle(appointment.getTitle());
			oldAppointment.setAttendants(appointment.getAttendants());
		}

	}

	public void removeAppointment(Appointment appointment) {
		database.deleteAppointment(appointment);
		appointments.remove(appointment);
	}

	public boolean verifyLogin(String username, String password) {
		for(User user : users) {
			if(user instanceof InternalUser && user.getEmail().equals(username)) {
				if(((InternalUser) user).getPassword().equals(password)) {
					return true;
				}
			} else {
				return false;
			}
		}
		return false;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public List<User> getUsers() {
		return users;
	}

	public Database getDatabase() {
		return database;
	}

	public List<MeetingRoom> getMeetingRooms() {
		return meetingRooms;
	}


	public static void main(String[] args) {
		Server server = new Server();
	}
}
