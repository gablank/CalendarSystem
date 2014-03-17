package fellesprosjekt.gruppe30;


import fellesprosjekt.gruppe30.Controller.AlarmController;
import fellesprosjekt.gruppe30.Controller.Database;
import fellesprosjekt.gruppe30.Controller.ServerListener;
import fellesprosjekt.gruppe30.Model.*;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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

		// sendMail("emilhe@stud.ntnu.no", "This is an test", "Test");
		//
		// Alarm alarm = new Alarm(new InternalUser("emilhe@stud.ntnu.no",
		// "Emil", "Heien"), appointments.get(0), new Date((new
		// Date()).getTime() + 1000 * 61));
		// addAlarm(alarm);

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
		int du = 120;
		long hei = du;
		return database;
	}

	@Override
	public synchronized List<Alarm> getAlarms() {
		return (List<Alarm>) ((ArrayList<Alarm>) this.alarms).clone();
	}

	public synchronized void sendMail(String recipient, String subject, String body) {
		
		Properties props = new Properties();
		/*props.put("mail.smtp.starttls.enable", null);
		props.put("mail.smtp.user", "user");
		props.put("mail.smtp.password", "password")
		props.put("mail.smtp.auth", true); */
		props.put("mail.smtp.host", "smtp.stud.ntnu.no");
		props.put("mail.smtp.port", "25");
		
		Session session = Session.getDefaultInstance(props);
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new Address() { //Inner class Address()

				public String toString() {
					return "CalendarSystem notification!";
				}

				public String getType() {
					return null;
				}

				public boolean equals(Object arg0) {
					if (this.toString().equals(arg0.toString()))
						return true;
					return false;
				}
			});
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
			message.setSubject(subject);
			message.setText(body);
			
			//Transport.send(message);
			System.out.println("Email sent to " + recipient + ": " + message.toString());

		} catch (MessagingException me) {
			System.out.println("Failed sending email to + " + recipient);
		}
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
