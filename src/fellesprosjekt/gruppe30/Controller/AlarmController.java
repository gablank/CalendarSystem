package fellesprosjekt.gruppe30.Controller;


import fellesprosjekt.gruppe30.Model.Alarm;
import fellesprosjekt.gruppe30.Server;

import java.util.Date;
import java.util.List;

public class AlarmController extends Thread {
    private Server server;
    private boolean run;

    public AlarmController(Server server) {
        this.server = server;
        this.run = true;
    }

    // Runs then sleeps 60 secs
    public void run() {
        System.out.println("I'm alive!");
        List<Alarm> alarms;
        while(this.run) {
            System.out.println("Checking for alarms!");

            alarms = server.getAlarms();
            Date curTime = new Date(new Date().getTime() + 30 * 000);

            for(int i = 0; i < alarms.size(); i++) {
                // If now is after alarm date, send mail
                if(curTime.after(alarms.get(i).getDate())) {
                    this.server.sendMail(Integer.toString(alarms.get(i).getUserid()), "ALARM!", "This is an alarm!!!!!");
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

    public void shutdown() {
        this.run = false;
        this.interrupt();
    }
}
