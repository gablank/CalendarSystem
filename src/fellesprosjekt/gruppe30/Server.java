package fellesprosjekt.gruppe30;


import fellesprosjekt.gruppe30.Controller.AlarmController;
import fellesprosjekt.gruppe30.Controller.Database;
import fellesprosjekt.gruppe30.Model.Alarm;
import fellesprosjekt.gruppe30.Model.Appointment;
import fellesprosjekt.gruppe30.Model.MeetingRoom;
import fellesprosjekt.gruppe30.Model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Server {
    private final Database database = Database.getInstance();
    private List<User> users;
    private List<Appointment> appointments;
    private List<MeetingRoom> meetingRooms;
    private ArrayList<Alarm> alarms;
    private final AlarmController alarmController;


    public Server() {
        this.loadDatabase();

        Alarm alarm = new Alarm(1, 1, new Date(new Date().getTime() + 120 * 1000));
        System.out.println("Added alarm for " + alarm.getDate().getTime());
        alarms.add(alarm);

        alarmController = new AlarmController(this);
        alarmController.start();
        alarmController.interrupt();
    }

    public void loadDatabase() {
        //
    }

    public synchronized List<Alarm> getAlarms() {
        return (ArrayList<Alarm>) this.alarms.clone();
    }

    public synchronized void sendMail(String recipient, String subject, String body) {
        System.out.println("Sending mail to " + recipient);
    }

    public void shutdown() {
        this.alarmController.shutdown();
    }


    public static void main(String[] args) {
        Server server = new Server();
    }
}
