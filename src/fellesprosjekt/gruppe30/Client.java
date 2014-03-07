package fellesprosjekt.gruppe30;


import fellesprosjekt.gruppe30.View.AppointmentView;
import fellesprosjekt.gruppe30.View.BookMeetingRoomView;
import fellesprosjekt.gruppe30.View.LoginView;

import javax.swing.*;

public class Client {
    private final LoginView loginView;
    private final AppointmentView appointmentView;
    private final BookMeetingRoomView bookMeetingRoomView;

    public Client() {
        this.loginView = new LoginView();
        this.appointmentView = new AppointmentView();
        this.bookMeetingRoomView = new BookMeetingRoomView();
    }

    /**
     *
     * Application entry point
     *
     */
    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Client client = new Client();
                client.loginView.setVisible(true);
            }
        });

    }
}
