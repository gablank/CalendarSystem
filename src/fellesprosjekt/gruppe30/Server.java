package fellesprosjekt.gruppe30;


import fellesprosjekt.gruppe30.Controller.Database;
import fellesprosjekt.gruppe30.Controller.ServerListener;
import fellesprosjekt.gruppe30.Model.Alarm;
import fellesprosjekt.gruppe30.Model.Appointment;
import fellesprosjekt.gruppe30.Model.MeetingRoom;
import fellesprosjekt.gruppe30.Model.User;

import java.util.ArrayList;
import java.util.List;

public class Server {
    private final Database database = Database.getInstance();
    private List<User> users = new ArrayList<User>();
    private List<Appointment> appointments = new ArrayList<Appointment>();
    private List<MeetingRoom> meetingRooms = new ArrayList<MeetingRoom>();
    private List<Alarm> alarms = new ArrayList<Alarm>();

	private ServerListener listener;
	
	private QueueWorker queueWorker;

    public Server() {
		queueWorker = new QueueWorker();
		Thread queueWorkerThread = new Thread(queueWorker);
		queueWorkerThread.start();

		listener = new ServerListener();
		Thread listenerThread = new Thread(listener);
		listenerThread.start();
		

		try {
			listenerThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public static void main(String args[]) {
        Server server = new Server();
    }
}
