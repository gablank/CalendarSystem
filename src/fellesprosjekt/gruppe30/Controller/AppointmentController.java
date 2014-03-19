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

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.SimpleTimeZone;

public class AppointmentController implements ActionListener, KeyListener, ListSelectionListener, MouseListener {
	private final Client client;
	public AppointmentView appointmentView;
	private ViewAppointmentView viewAppointmentView;


	public AppointmentController(Client client) {
		this.client = client;
		this.appointmentView = new AppointmentView();
		this.viewAppointmentView = new ViewAppointmentView();
		appointmentView.addListener(this);
		viewAppointmentView.addListener(this);
	}

	public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
		String name= ((JComponent) actionEvent.getSource()).getName();

		if(name.equalsIgnoreCase("inviteUser")) {
			List<User> invitees = appointmentView.getSelectedUsers();
			for(User invitee : invitees) {
				appointmentView.getAppointmentModel().addUser(invitee);
				System.out.println("Adding " + invitee.getEmail());
			}

		} else if(name.equalsIgnoreCase("save")) {
			Appointment appointment = appointmentView.getAppointmentModel();
			JSONObject message = appointment.getJSON();

			message.put("dateChanged", new Date().getTime());

			// don't allow saving if no meetingRoom or meetingPlace is chosen
			if (appointmentView.useMeetingRoomIsChecked()) {
				if (appointment.getMeetingRoom() == null)
					return;
				message.put("meetingPlace", "");
			} else {
				if (appointment.getMeetingPlace() == "")
					return;
				message.put("meetingRoom", -1);
			}

			// only save if this is a new appointment, or if changes have been
			if (appointment.getId() == -1) {
				message.put("action", "new");
				client.network.send(message);
			} else {
				message.put("action", "change");
				// Appointment oldAppointment = Utilities.getAppointmentById(appointment.getId(), client.getAppointments());
				// if (appointment.equals(oldAppointment)) {
				// System.out.println("not sending appointment, no changes have been made!");
				// } else {
					client.network.send(message);
				// }
			}
			
			if (appointmentView.getAlarmIsSelected()) {
				JSONObject alarmMessage = appointmentView.getAlarmModel().getJSON();

				if (Utilities.getAlarm(appointment, client.getLoggedInUser(), client.getAlarms()) == null) {
					alarmMessage.put("action", "new");
				} else {
					alarmMessage.put("action", "change");
				}

				client.network.send(alarmMessage);
			}
			client.close(Client.ViewEnum.APPOINTMENT);

		} else if(name.equalsIgnoreCase("selectRoom")) {
			client.open(Client.ViewEnum.BOOKMEETINGROOM);
			client.getBookMeetingRoomController().setModel(appointmentView.getAppointmentModel());
			
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
			client.close(Client.ViewEnum.AREYOUSUREVIEW);
			client.close(ViewEnum.APPOINTMENT);

		} else if(name.equalsIgnoreCase("removeUser")) {
			Attendant selectedAttendant = appointmentView.getSelectedAttendant();
			appointmentView.getAppointmentModel().removeAttendant(selectedAttendant);

		} else if(name.equalsIgnoreCase("viewsave")) {
			Appointment appointment = viewAppointmentView.getAppointmentModel();
			JSONObject message = appointment.getJSON();
			message.put("action", "change");
			client.network.send(message);

			if (appointmentView.getAlarmIsSelected()) {
				JSONObject alarmMessage = appointmentView.getAlarmModel().getJSON();

				if (Utilities.getAlarm(appointment, client.getLoggedInUser(), client.getAlarms()) == null) {
					alarmMessage.put("action", "new");
				} else {
					alarmMessage.put("action", "change");
				}

				client.network.send(alarmMessage);
			}
			client.close(Client.ViewEnum.VIEWAPPOINTMENTVIEW);

		} else if(name.equalsIgnoreCase("viewcancel")) {
			client.close(Client.ViewEnum.VIEWAPPOINTMENTVIEW);
		}
	}

	public void keyTyped(java.awt.event.KeyEvent keyEvent) {

	}

	public void keyPressed(java.awt.event.KeyEvent keyEvent) {

	}

	public void keyReleased(java.awt.event.KeyEvent keyEvent) {
		String source = ((Component) keyEvent.getSource()).getName().toLowerCase();

		if (source.equals("title")) {
			appointmentView.getAppointmentModel().setTitle(appointmentView.getTitleText());

		} else if (source.equals("description")) {
			appointmentView.getAppointmentModel().setDescription(appointmentView.getDescriptionText());

		} else if (source.equals("meeting_place")) {
			appointmentView.getAppointmentModel().setMeetingPlace(appointmentView.getMeetingPlaceText());

		} else if (source.equals("app_start_text") || source.equals("app_end_text") || source.equals("app_date_text")) {
			try {

				// dateText: DD.MM.YYYY
				// start/endText: HH:MM

				String dateStrings[] = appointmentView.getDateText().getText().split("\\.");

				int day = Integer.parseInt(dateStrings[0]);
				int month = Integer.parseInt(dateStrings[1]);
				int year = Integer.parseInt(dateStrings[2]);

				String startTime[] = appointmentView.getStartText().getText().split(":");

				int startHour = Integer.parseInt(startTime[0]);
				int startMinute = Integer.parseInt(startTime[1]);

				String endTime[] = appointmentView.getEndText().getText().split(":");

				int endHour = Integer.parseInt(endTime[0]);
				int endMinute = Integer.parseInt(endTime[1]);

				GregorianCalendar newStartGC = new GregorianCalendar(year, month - 1, day, startHour, startMinute);
				newStartGC.setTimeZone(new SimpleTimeZone(3600000, "Europe/Paris", Calendar.MARCH, -1, Calendar.SUNDAY, 3600000, SimpleTimeZone.UTC_TIME, Calendar.OCTOBER, -1, Calendar.SUNDAY, 3600000, SimpleTimeZone.UTC_TIME, 3600000));
				GregorianCalendar newEndGC = new GregorianCalendar(year, month - 1, day, endHour, endMinute);
				newEndGC.setTimeZone(new SimpleTimeZone(3600000, "Europe/Paris", Calendar.MARCH, -1, Calendar.SUNDAY, 3600000, SimpleTimeZone.UTC_TIME, Calendar.OCTOBER, -1, Calendar.SUNDAY, 3600000, SimpleTimeZone.UTC_TIME, 3600000));

				Date newStart = newStartGC.getTime();
				Date newEnd = newEndGC.getTime();

				appointmentView.getAppointmentModel().setStart(newStart, false);
				appointmentView.getAppointmentModel().setEnd(newEnd, false);

			} catch (Exception exception) {

			}

		} else if (source.equalsIgnoreCase("alarmTimeField")) {
			java.util.Date startTime = appointmentView.getAppointmentModel().getStart();
            java.util.Date alarmDate = new java.util.Date(startTime.getTime() + appointmentView.getAlarmInMinutes() * 60*1000);
			System.out.println(alarmDate);
			appointmentView.getAlarmModel().setDate(alarmDate);
		}
	}

	public void valueChanged(javax.swing.event.ListSelectionEvent listSelectionEvent) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//System.out.println(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//System.out.println(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		//System.out.println(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//System.out.println(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//System.out.println(e);
	}

	public void openNew() {
		Appointment newAppointment = new Appointment(client.getLoggedInUser());
		InternalAttendant newAttendant = new InternalAttendant(client.getLoggedInUser(), newAppointment);
		Alarm newAlarm = new Alarm(client.getLoggedInUser(), newAppointment, new java.util.Date(0));
		appointmentView.setComponentsToDefault();
		appointmentView.setModel(newAppointment, newAttendant, newAlarm);
		appointmentView.setInternalUsersAndGroups(client.getInternalUsers(), client.getGroups());
		appointmentView.setVisible(true);
	}

	public void open(Appointment appointment) {
		Attendant attendant = appointment.getAttendant(client.getLoggedInUser());
		Alarm alarm = Utilities.getAlarm(appointment, client.getLoggedInUser(), client.getAlarms());
		if(appointment.getOwner() == client.getLoggedInUser()) {
			appointmentView.setComponentsToDefault();
			appointmentView.setModel(appointment, attendant, alarm);
			appointmentView.setVisible(true);
		} else {
			viewAppointmentView.setModel(appointment, attendant, alarm);
			viewAppointmentView.setVisible(true);
		}
	}

	public void setVisible(boolean state) {
		appointmentView.setVisible(state);
		viewAppointmentView.setVisible(state);
	}

	public Appointment getModel() {
		return appointmentView.getAppointmentModel();
	}
}
