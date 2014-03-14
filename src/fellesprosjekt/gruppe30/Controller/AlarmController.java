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
		System.out.println("I'm alive!");
		List<Alarm> alarms;
		while(this.run) {
			System.out.println("Checking for alarms!");

			alarms = server.getAlarms();
			Date curTime = new Date(new Date().getTime() + 30 * 000);
			Date oneMinBeforeCurTime = new Date(new Date().getTime() - 30 * 000);

			for(int i = 0; i < alarms.size(); i++) {
				// If now is after alarm date, send mail
				if(curTime.after(alarms.get(i).getDate()) && oneMinBeforeCurTime.before(alarms.get(i).getDate())) {
					this.server.sendMail(alarms.get(i).getUser().getEmail(), "Meeting notification", alarms.get(i).getAppointment().getTitle() + " is starting in "
				+ ((alarms.get(i).getAppointment().getStart().getTime() - alarms.get(i).getDate().getTime()) / 1000 / 60) + " minutes");
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
