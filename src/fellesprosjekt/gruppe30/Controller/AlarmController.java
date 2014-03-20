package fellesprosjekt.gruppe30.Controller;


import fellesprosjekt.gruppe30.Model.Alarm;
import fellesprosjekt.gruppe30.Server;

import java.util.Date;
import java.util.List;

public class AlarmController implements Runnable {
	private Server  server;
	private boolean run;

	public AlarmController(Server server) {
		this.server = server;
		this.run = true;
	}

	// Runs then sleeps 60 secs
	@Override
	public void run() {
		List<Alarm> alarms;
		while(this.run) {

			alarms = server.getAlarms();
			Date halfAMinuteFromNow = new Date(new Date().getTime() + 30 * 1000);
			Date halfAMinuteAgo = new Date(new Date().getTime() - 30 * 1000);

			System.out.println("checking alarms (" + new Date().toString() + " + " + halfAMinuteFromNow.getTime() % 1000 + "ms)  +/- 30s");

			for(int i = 0; i < alarms.size(); i++) {
				// If alarm falls within now +/- 30 seconds, send email
				if (halfAMinuteFromNow.after(alarms.get(i).getDate()) && halfAMinuteAgo.before(alarms.get(i).getDate())) {
					String recipientEmail = alarms.get(i).getUser().getEmail();
					String subject = "Meeting notification";
					String body = alarms.get(i).getAppointment().getTitle() + " is starting in " + ((alarms.get(i).getAppointment().getStart().getTime() - alarms.get(i).getDate().getTime()) / 1000 / 60) + " minutes";

					this.server.sendMail(recipientEmail, subject, body);
				}
			}

			// Wait until next whole minute
			try {
				Date now = new Date();
				Thread.sleep(60000 - now.getTime() % 60000);

				// Server is shutting down
			} catch(InterruptedException e) {
				System.out.println("AlarmController shutting down!");
				return;
			}

		}
	}
}
