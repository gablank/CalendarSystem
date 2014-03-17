package fellesprosjekt.gruppe30;


import fellesprosjekt.gruppe30.Controller.*;
import fellesprosjekt.gruppe30.Model.*;
import fellesprosjekt.gruppe30.View.*;

import org.json.JSONObject;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Client extends Application {
	private final LoginController       loginController;
	private final CalendarController    calendarController;
	private final BookMeetingRoomController bookMeetingRoomController;
	private final AppointmentController appointmentController;
	private final AreYouSureView		areYouSureView;
	public  final ClientNetwork         network;

	private InternalUser loggedInUser = null;

	public InternalUser getLoggedInUser() {
		return loggedInUser;
	}

	public Client() {
		appointments = new ArrayList<Appointment>();
		users = new ArrayList<User>();
		meetingRooms = new ArrayList<MeetingRoom>();
		alarms = new ArrayList<Alarm>();
		groups = new ArrayList<Group>();

		this.loginController = new LoginController(this);

		this.calendarController = new CalendarController(this);

		this.bookMeetingRoomController = new BookMeetingRoomController(this);

		this.appointmentController = new AppointmentController(this);

		this.areYouSureView = new AreYouSureView();
		this.areYouSureView.addListener(appointmentController);
		
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
			this.loginController.setVisible(state);
		}
		if(viewEnum.equals(ViewEnum.ALL) || viewEnum.equals(ViewEnum.APPOINTMENT) || viewEnum.equals(ViewEnum.VIEWAPPOINTMENTVIEW)) {
			this.appointmentController.setVisible(state);
		}
		if(viewEnum.equals(ViewEnum.ALL) || viewEnum.equals(ViewEnum.BOOKMEETINGROOM)) {
			//this.bookMeetingRoomController.setVisible(state);
		}
		if(viewEnum.equals(ViewEnum.ALL) || viewEnum.equals(ViewEnum.CALENDAR)) {
			this.calendarController.setVisible(state);
		}
		if(viewEnum.equals(ViewEnum.ALL) || viewEnum.equals(ViewEnum.AREYOUSUREVIEW)) {
			this.areYouSureView.setVisible(state);
		}
	}

	public void setLoggedIn(String email) {
		close(ViewEnum.LOGIN);
		open(ViewEnum.CALENDAR);
		this.loggedInUser = (InternalUser) Utilities.getUserByEmail(email, users);
		this.calendarController.setUser((InternalUser) this.loggedInUser);
	}

	public void newAppointment() {
		appointmentController.openNew();
	}

	public LoginController getLoginController() {
		return loginController;
	}

	public void logout() {
		this.loggedInUser = null;
		this.users.clear();
		this.appointments.clear();
		this.meetingRooms.clear();
		this.alarms.clear();
		this.groups.clear();

		JSONObject obj = new JSONObject();
		obj.put("type", "logout");
		this.network.send(obj);

		close(ViewEnum.ALL);
		open(ViewEnum.LOGIN);
	}

	public void quit(int i) {
		System.exit(i);
	}

	public List<InternalUser> getInternalUsers() {
		List<InternalUser> users = new ArrayList<>();
		for(User user : this.users) {
			if(user instanceof InternalUser) {
				users.add((InternalUser) user);
			}
		}
		return users;
	}

	public static enum ViewEnum {
		ALL, LOGIN, CALENDAR, APPOINTMENT, BOOKMEETINGROOM, VIEWAPPOINTMENTVIEW, AREYOUSUREVIEW
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
