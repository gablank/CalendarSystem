package fellesprosjekt.gruppe30.Controller;


import fellesprosjekt.gruppe30.Client;

import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public class AppointmentController implements ActionListener, KeyListener, ListSelectionListener {
    private final Client client;


    public AppointmentController(Client client) {
        this.client = client;
    }

    public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        String cmd = actionEvent.getActionCommand();

        if(cmd == "Save") {
            // Save
        } else if(cmd == "Cancel") {
            client.close("Appointment");
        } else if(cmd == "Cancel") {

        } else if(cmd == "Cancel") {

        } else if(cmd == "Cancel") {

        } else if(cmd == "Cancel") {

        } else if(cmd == "Select...") {
            client.open("BookMeetingRoom");
        }
    }

    public void keyTyped(java.awt.event.KeyEvent keyEvent) {

    }

    public void keyPressed(java.awt.event.KeyEvent keyEvent) {

    }

    public void keyReleased(java.awt.event.KeyEvent keyEvent) {

    }

    public void valueChanged(javax.swing.event.ListSelectionEvent listSelectionEvent) {

    }
}
