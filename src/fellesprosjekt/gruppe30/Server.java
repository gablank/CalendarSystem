package fellesprosjekt.gruppe30;


import fellesprosjekt.gruppe30.Controller.AlarmController;
import fellesprosjekt.gruppe30.Controller.Database;
import fellesprosjekt.gruppe30.Controller.ServerListener;
import fellesprosjekt.gruppe30.Model.*;

import java.util.ArrayList;
import java.util.List;

public class Server extends Application {
	private final Database database = Database.getInstance();

	private AlarmController	alarmController;
	private ServerListener	serverListener;
	private Thread			serverListenerThread;
	private Thread			alarmControllerThread;

	public Server() {
		this.loadDatabase();


		serverListener = new ServerListener(this);
		serverListenerThread = new Thread(serverListener);
		serverListenerThread.start();

		alarmController = new AlarmController(this);
		alarmControllerThread = new Thread(alarmController);
		alarmControllerThread.start();

		try {
			serverListenerThread.join();
			alarmControllerThread.join();
		} catch(InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ServerListener getServerListener() {
		return serverListener;
	}

	public void loadDatabase() {
		this.database.loadDatabase(this);
	}

	public Database getDatabase() {
		return database;
	}

	@Override
	public synchronized List<Alarm> getAlarms() {
		return (List<Alarm>) ((ArrayList<Alarm>) this.alarms).clone();
	}

	public synchronized void sendMail(String recipient, String subject, String body) {
		System.out.println("Sending mail to " + recipient);
	}

	public boolean verifyLogin(String username, String password) {
		User user = Utilities.getUserByEmail(username, users);
		if(user != null && user instanceof InternalUser && ((InternalUser) user).getPassword().equals(password)) {
			return true;
		}
		return false;
	}

	public void shutdown() {
		this.alarmControllerThread.interrupt();
		this.serverListenerThread.interrupt();
	}

	public static void main(String[] args) {
		Server server = new Server();
	}
}
