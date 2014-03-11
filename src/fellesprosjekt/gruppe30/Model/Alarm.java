package fellesprosjekt.gruppe30.Model;


import java.util.Date;

public class Alarm {
    private final User user;
    private final Appointment appointment;
    private Date date;

    public Alarm(User user, Appointment appointment, Date date) {
        this.user = user;
        this.appointment = appointment;
        this.date = date;
    }

    public Date getDate() {
        return this.date;
    }

    public User getUser() {
        return this.user;
    }

    public Appointment getAppointment() {
        return this.appointment;
    }
}
