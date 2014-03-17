package fellesprosjekt.gruppe30.Controller;


import fellesprosjekt.gruppe30.Client;
import fellesprosjekt.gruppe30.Client.ViewEnum;
import fellesprosjekt.gruppe30.Utilities;
import fellesprosjekt.gruppe30.Model.Appointment;

import javax.swing.event.ListSelectionListener;

import org.json.JSONObject;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AppointmentController implements ActionListener, KeyListener, ListSelectionListener, MouseListener {
	private final Client client;


	public AppointmentController(Client client) {
		this.client = client;
	}

	public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
		String cmd = actionEvent.getActionCommand().toLowerCase();

		System.out.println(cmd);
		if(cmd.equals("save")) {
			Appointment appointment = client.getAppointmentView().getModel();
			JSONObject message = appointment.getJSON();
			if (appointment.getId() == -1) {
				message.put("action", "new");
			} else {
				message.put("action", "change");
			}
			client.network.send(message);
			
			if (client.getAppointmentView().setAlarmIsSelected()) {	
				JSONObject alarmMessage = new JSONObject();
				alarmMessage.put("type", "alarm");
				if (Utilities.getAlarm(appointment, client.getLoggedInUser(), client.getAlarms()) == null) {
					alarmMessage.put("action", "new");
				} else alarmMessage.put("action", "change");
				alarmMessage.put("userEmail", client.getLoggedInUser().getEmail());
				alarmMessage.put("appointmentId", appointment.getId());
				long alarm = client.getAppointmentView().getAlarmInMinutes() * 60 * 1000;
				alarmMessage.put("time", appointment.getStart().getTime() - alarm);
				client.network.send(alarmMessage);
			}
			client.close(Client.ViewEnum.APPOINTMENT);

		} else if(cmd.equals("select...")) {
			client.open(Client.ViewEnum.BOOKMEETINGROOM);
			
		} else if(cmd.equals("cancel")) {
			client.close(Client.ViewEnum.APPOINTMENT);
			
		} else if(cmd.equals("delete")) {
			client.open(Client.ViewEnum.AREYOUSUREVIEW);
		} else if(cmd.equals("no")) {
			client.close(Client.ViewEnum.AREYOUSUREVIEW);
		} else if(cmd.equals("yes")) {
			if (client.getAppointmentView().getModel().getId() != -1) {
				JSONObject message = new JSONObject();
				message.put("type", "appointment");
				message.put("action", "delete");
				message.put("id", client.getAppointmentView().getModel().getId());
				client.network.send(message);
			}
			client.close(ViewEnum.APPOINTMENT);
		} else if(cmd.equals("add")) {
			client.getAppointmentView().inviteToAppointment();
		} else if(cmd.equals("remove")) {
			client.getAppointmentView().removeFromAppointment();
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

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		System.out.println(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		System.out.println(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println(e);
	}
}
