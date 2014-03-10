package fellesprosjekt.gruppe30.Model;


import java.util.Date;

public class Alarm {
    private final int userid;
    private final int appointmentid;
    private Date date;

    public Alarm(int userid, int appointmentid, Date date) {
        this.userid = userid;
        this.appointmentid = appointmentid;
        this.date = date;
    }
}
