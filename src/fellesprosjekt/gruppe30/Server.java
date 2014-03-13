package fellesprosjekt.gruppe30;


import fellesprosjekt.gruppe30.Controller.AlarmController;
import fellesprosjekt.gruppe30.Controller.Database;
import fellesprosjekt.gruppe30.Controller.ServerListener;
import fellesprosjekt.gruppe30.Model.*;

import java.util.ArrayList;
import java.util.List;

public class Server extends Application {
	private final Database database = Database.getInstance();

	private final Thread            alarmController;
	private final Thread            serverListener;

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
		for(User user : users) {
			if(user instanceof InternalUser && user.getEmail().equals(username)) {
				if(((InternalUser) user).getPassword().equals(password)) {
					return true;
				}
			}
		}
		return false;
	}

	public void shutdown() {
		this.alarmController.interrupt();
		this.serverListener.interrupt();
	}

	public static void main(String[] args) {
		Server server = new Server();
	}
}
