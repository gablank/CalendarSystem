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
		String cmd = actionEvent.getActionCommand().toLowerCase();

		System.out.println(cmd);
		if(cmd.equals("save")) {
			// Save
		} else if(cmd.equals("select...")) {
			client.open(Client.ViewEnum.BOOKMEETINGROOM);
		} else if(cmd.equals("cancel")) {
			client.close(Client.ViewEnum.APPOINTMENT);
		} else if(cmd.equals("cancel")) {

		} else if(cmd.equals("cancel")) {

		} else if(cmd.equals("cancel")) {

		} else if(cmd.equals("cancel")) {

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
