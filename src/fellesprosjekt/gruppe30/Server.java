package fellesprosjekt.gruppe30;


import fellesprosjekt.gruppe30.Controller.AlarmController;
import fellesprosjekt.gruppe30.Controller.Database;
import fellesprosjekt.gruppe30.Controller.ServerListener;
import fellesprosjekt.gruppe30.Model.*;

import java.util.ArrayList;
import java.util.List;

public class Server {
    private final Database database = Database.getInstance();
    private List<User> users;
    private List<Appointment> appointments;
    private List<MeetingRoom> meetingRooms;
    private List<Alarm> alarms;
    private final Thread alarmController;
    private final Thread serverListener;


    public Server() {
        this.loadDatabase();
		
        serverListener = new Thread(new ServerListener(this));
		serverListener.start();

        alarmController = new Thread(new AlarmController(this));
        alarmController.start();
        
		try {
			serverListener.join();
            alarmController.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void loadDatabase() {
        this.database.loadDatabase(this);
    }

    public synchronized List<Alarm> getAlarms() {
        return (List<Alarm>) ((ArrayList<Alarm>)this.alarms).clone();
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
    
    public void insertAppointment(Appointment appointment){
   		int id = database.insertAppointment(appointment);

		if(appointment.getId() == -1){
			appointment.setId(id);
			appointments.add(appointment);
		}else{
			Appointment oldAppointment = getAppointmentById(id);
			
			oldAppointment.setDescription(appointment.getDescription());
			oldAppointment.setEnd(appointment.getEnd());
//			oldAppointment.setLastUpdated(lastUpdated);
			oldAppointment.setMeetingPlace(appointment.getMeetingPlace());
//			oldAppointment.setOwner(owner);
			oldAppointment.setRoom(appointment.getRoom());
			oldAppointment.setStart(appointment.getStart());
			oldAppointment.setTitle(appointment.getTitle());
			oldAppointment.setAttendants(appointment.getAttendants());
			
			
//			appointments.remove(getAppointmentById(id));
//			appointments.add(appointment);
		}
		
    }
    
    public void removeAppointment(int id){
    	
    	// todo: database.remove...
    	
		appointments.remove(getAppointmentById(id));
    }

	public User getUserById(int id) {
		for (User u : users){
			if(u.getId() == id)
				return u;
		}
		
		return null;
	}
	
	public Appointment getAppointmentById(int id) {
		for (Appointment a : appointments){
			if(a.getId() == id)
				return a;
		}
		
		return null;
	}

	public MeetingRoom getMeetingRoomById(int id) {
		for (MeetingRoom m : meetingRooms){
			if(m.getId() == id)
				return m;
		}
		
		return null;
	}

    public boolean verifyLogin(String username, String password) {
        for(User user : users) {
            if(user.getUsername().equals(username)) {
                if(user.getPassword().equals(password)) {
                    return true;
                }
            } else {
                return false;
            }
        }
        return false;
    }


    public static void main(String[] args) {
        Server server = new Server();
    }
}
