package fellesprosjekt.gruppe30;


import fellesprosjekt.gruppe30.Controller.Database;
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


    public Server() {

    }

    public static void main(String args[]) {
        Server server = new Server();
    }
}
