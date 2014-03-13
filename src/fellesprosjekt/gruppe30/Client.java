package fellesprosjekt.gruppe30;


import fellesprosjekt.gruppe30.Controller.AppointmentController;
import fellesprosjekt.gruppe30.Controller.CalendarController;
import fellesprosjekt.gruppe30.Controller.ClientNetwork;
import fellesprosjekt.gruppe30.Controller.LoginController;
import fellesprosjekt.gruppe30.Model.*;
import fellesprosjekt.gruppe30.View.*;

import org.json.JSONObject;

import javax.swing.*;

import java.awt.Container;
import java.util.ArrayList;
import java.util.List;


public class Client extends Application {
	private final LoginController       loginController;
	private final LoginView             loginView;
	private final Calendar              calendar;
	private final CalendarController    calendarController;
	private final CalendarView          calendarView;
	private final AppointmentView       appointmentView;
	private final ViewAppointmentView   viewAppointmentView;
	private final BookMeetingRoomView   bookMeetingRoomView;
	private final AppointmentController appointmentController;
	public  final ClientNetwork         network;

	private User loggedInUser = null;

	public User getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(User loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public Client() {
		// test code
		appointments = new ArrayList<Appointment>();
		users = new ArrayList<User>();
		meetingRooms = new ArrayList<MeetingRoom>();
		alarms = new ArrayList<Alarm>();
		groups = new ArrayList<Group>();
		InternalUser test = new InternalUser("Anders", "Wenhaug", "anders@wenhaug.no");
		users.add(test);
		// end test code

		this.loginController = new LoginController(this);
		this.loginView = new LoginView();
		this.loginView.addListener(loginController);

		this.calendar = new Calendar();
		this.calendarController = new CalendarController(this);
		this.calendarView = new CalendarView();
		this.calendarView.addListener(calendarController);
		//this.calendar.addPropertyChangeSuppertListener(calendarView);

		this.appointmentView = new AppointmentView();
		this.bookMeetingRoomView = new BookMeetingRoomView();
		this.viewAppointmentView = new ViewAppointmentView();

		this.appointmentController = new AppointmentController(this);
		this.appointmentView.addListener(this.appointmentController);


		this.close(ViewEnum.ALL);
		this.open(ViewEnum.LOGIN);

		this.network = new ClientNetwork(this);
		Thread networkThread = new Thread(this.network);
		networkThread.start();
	}

	public void open(ViewEnum viewEnum) {
		this.setViewVisible(viewEnum, true);
	}

	public void close(ViewEnum viewEnum) {
		this.setViewVisible(viewEnum, false);
	}

	public void setViewVisible(ViewEnum viewEnum, boolean state) {
		if(viewEnum.equals(ViewEnum.ALL) || viewEnum.equals(ViewEnum.LOGIN)) {
			this.loginView.setVisible(state);
		}
		if(viewEnum.equals(ViewEnum.ALL) || viewEnum.equals(ViewEnum.APPOINTMENT)) {
			this.appointmentView.setVisible(state);
		}
		if(viewEnum.equals(ViewEnum.ALL) || viewEnum.equals(ViewEnum.BOOKMEETINGROOM)) {
			this.bookMeetingRoomView.setVisible(state);
		}
		if(viewEnum.equals(ViewEnum.ALL) || viewEnum.equals(ViewEnum.CALENDAR)) {
			this.calendarView.setVisible(state);
		}
		if(viewEnum.equals(ViewEnum.ALL) || viewEnum.equals(ViewEnum.VIEWAPPOINTMENTVIEW)) {
			this.viewAppointmentView.setVisible(state);
		}
	}

	public AppointmentView getAppointmentView() {
		return appointmentView;
	}

	public BookMeetingRoomView getBookMeetingRoomView() {
		return bookMeetingRoomView;
	}

	public LoginView getLoginView() {
		return this.loginView;
	}

	public CalendarView getCalendarView() {
		return calendarView;
	}

	public Calendar getCalendar() {
		return this.calendar;
	}

	public void setLoggedIn(String email) {
		close(ViewEnum.LOGIN);
		open(ViewEnum.CALENDAR);
		this.loggedInUser = Utilities.getUserByEmail(email, users);
	}

	public void newAppointment() {
		open(Client.ViewEnum.APPOINTMENT);
		Appointment newAppointment = new Appointment((InternalUser) this.loggedInUser);
		// this.appointments.add(newAppointment);
		getAppointmentView().setModel(newAppointment);
	}

	public LoginController getLoginController() {
		return loginController;
	}

	public void logout() {
		this.loggedInUser = null;
		JSONObject obj = new JSONObject();
		obj.put("type", "logout");
		this.network.send(obj);
		close(ViewEnum.ALL);
		open(ViewEnum.LOGIN);
	}

	public void quit(int i) {
		System.exit(i);
	}

	public static enum ViewEnum {
		ALL, LOGIN, CALENDAR, APPOINTMENT, BOOKMEETINGROOM, VIEWAPPOINTMENTVIEW
	}

	/**
	 * Application entry point
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
