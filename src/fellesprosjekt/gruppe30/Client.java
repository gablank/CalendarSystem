package fellesprosjekt.gruppe30;


import fellesprosjekt.gruppe30.Controller.AppointmentController;
import fellesprosjekt.gruppe30.View.AppointmentView;
import fellesprosjekt.gruppe30.View.BookMeetingRoomView;
import fellesprosjekt.gruppe30.View.LoginView;

import javax.swing.*;
import javax.xml.bind.Marshaller;

public class Client {
    private final LoginView loginView;
    private final AppointmentView appointmentView;
    private final BookMeetingRoomView bookMeetingRoomView;
    private final AppointmentController appointmentController;

    public Client() {
        this.loginView = new LoginView();
        this.appointmentView = new AppointmentView();
        this.bookMeetingRoomView = new BookMeetingRoomView();

        this.appointmentController = new AppointmentController(this);
        this.appointmentView.addListener(this.appointmentController);


        this.close("all");
        this.open("login");
    }

    public void open(String view) {
        this.setViewVisible(view, true);
    }

    public void close(String view) {
        this.setViewVisible(view, false);
    }

    public void setViewVisible(String view, boolean state) {
        view = view.toLowerCase();
        if(view == "all" || view == "login") {
            this.loginView.setVisible(state);
        } else if(view == "all" || view == "week") {
            // TODO
        } else if(view == "all" || view == "appointment") {
            this.appointmentView.setVisible(state);
        } else if(view == "all" || view == "bookmeetingroom") {
            this.bookMeetingRoomView.setVisible(state);
        }
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
            }
        });

    }
}
