package fellesprosjekt.gruppe30.Controller;


import fellesprosjekt.gruppe30.Client;
import fellesprosjekt.gruppe30.Client.ViewEnum;
import fellesprosjekt.gruppe30.Model.*;
import fellesprosjekt.gruppe30.Utilities;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;

import fellesprosjekt.gruppe30.View.AppointmentView;
import fellesprosjekt.gruppe30.View.ViewAppointmentView;
import org.json.JSONObject;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class AppointmentController implements ActionListener, KeyListener, ListSelectionListener, MouseListener {
	private final Client client;
	private AppointmentView appointmentView;
	private ViewAppointmentView viewAppointmentView;


	public AppointmentController(Client client) {
		this.client = client;
		this.appointmentView = new AppointmentView();
		this.viewAppointmentView = new ViewAppointmentView();
		appointmentView.addListener(this);
	}

	public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
		String cmd = actionEvent.getActionCommand().toLowerCase();

		JComponent source = (JComponent) actionEvent.getSource();
		String name = source.getName();
		if(name.equalsIgnoreCase("inviteUser")) {
			System.out.println("In invite user");
			List<User> invitees = appointmentView.getSelectedUsers();
			for(User invitee : invitees) {
				appointmentView.getAppointmentModel().addUser(invitee);
				System.out.println("Adding " + invitee.getEmail());
			}
		} else if(name.equalsIgnoreCase("save")) {
			Appointment appointment = appointmentView.getAppointmentModel();
			JSONObject message = appointment.getJSON();
			if (appointment.getId() == -1) {
				message.put("action", "new");
			} else {
				message.put("action", "change");
			}
			client.network.send(message);
			
			if (appointmentView.setAlarmIsSelected()) {
				JSONObject alarmMessage = new JSONObject();
				alarmMessage.put("type", "alarm");
				if (Utilities.getAlarm(appointment, client.getLoggedInUser(), client.getAlarms()) == null) {
					alarmMessage.put("action", "new");
				} else alarmMessage.put("action", "change");
				alarmMessage.put("userEmail", client.getLoggedInUser().getEmail());
				alarmMessage.put("appointmentId", appointment.getId());
				long alarm = appointmentView.getAlarmInMinutes() * 60 * 1000;
				alarmMessage.put("time", appointment.getStart().getTime() - alarm);
				client.network.send(alarmMessage);
			}
			client.close(Client.ViewEnum.APPOINTMENT);

		} else if(name.equalsIgnoreCase("selectRoom")) {
			client.open(Client.ViewEnum.BOOKMEETINGROOM);
			
		} else if(name.equalsIgnoreCase("cancel")) {
			client.close(Client.ViewEnum.APPOINTMENT);
			
		} else if(name.equalsIgnoreCase("delete")) {
			client.open(Client.ViewEnum.AREYOUSUREVIEW);
		} else if(name.equalsIgnoreCase("no")) {
			client.close(Client.ViewEnum.AREYOUSUREVIEW);
		} else if(name.equalsIgnoreCase("yes")) {
			if (appointmentView.getAppointmentModel().getId() != -1) {
				JSONObject message = new JSONObject();
				message.put("type", "appointment");
				message.put("action", "delete");
				message.put("id", appointmentView.getAppointmentModel().getId());
				client.network.send(message);
			}
			client.close(ViewEnum.APPOINTMENT);
		} else if(name.equalsIgnoreCase("removeUser")) {
			Attendant selectedAttendant = appointmentView.getSelectedAttendant();
			appointmentView.getAppointmentModel().removeAttendant(selectedAttendant);
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

	public void openNew() {
		Appointment newAppointment = new Appointment(client.getLoggedInUser());
		InternalAttendant newAttendant = new InternalAttendant(client.getLoggedInUser(), newAppointment);
		Alarm newAlarm = new Alarm(client.getLoggedInUser(), newAppointment, new java.util.Date(0));
		appointmentView.setModel(newAppointment, newAttendant, newAlarm);
		appointmentView.setInternalUsersAndGroups(client.getInternalUsers(), client.getGroups());
		appointmentView.setVisible(true);
	}

	public void open(Appointment appointment) {
		if(appointment.getOwner() == client.getLoggedInUser()) {
			appointmentView.setModel(appointment);
			appointmentView.setVisible(true);
		} else {
			viewAppointmentView.setModel(appointment);
			viewAppointmentView.setVisible(true);
		}
	}

	public void setVisible(boolean state) {
		appointmentView.setVisible(state);
		viewAppointmentView.setVisible(state);
	}
}
