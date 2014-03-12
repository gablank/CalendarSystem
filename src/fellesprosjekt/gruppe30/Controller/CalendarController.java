package fellesprosjekt.gruppe30.Controller;

import fellesprosjekt.gruppe30.Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalendarController implements ActionListener {
    private final Client client;

    public CalendarController(Client client) {
        this.client = client;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        String cmd = actionEvent.getActionCommand();

        if (cmd.equalsIgnoreCase("log out")) {
            client.logout();
        } else if (cmd.equalsIgnoreCase(">")) {
            client.getCalendar().nextWeek();
        } else if (cmd.equalsIgnoreCase("<")) {
            client.getCalendar().previousWeek();
        } else if (cmd.equalsIgnoreCase("new appointment")) {
            client.newAppointment();
        } else if (cmd.equalsIgnoreCase("add")) {
            //client.getCalendar().addUser(client.getCalendarView().getUser());
        } else if (cmd.equalsIgnoreCase("remove")) {
            //client.getCalendar().removeUser(client.getCalendarView().getUser());
        } else if (cmd.equalsIgnoreCase("AppointmentSummaryView")) {
            client.open(Client.ViewEnum.VIEWAPPOINTMENTVIEW);
        }
    }


}
