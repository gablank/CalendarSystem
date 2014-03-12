package fellesprosjekt.gruppe30;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fellesprosjekt.gruppe30.Controller.AppointmentController;
import fellesprosjekt.gruppe30.Controller.CalendarController;
import fellesprosjekt.gruppe30.Controller.ClientNetwork;
import fellesprosjekt.gruppe30.Controller.LoginController;
import fellesprosjekt.gruppe30.Model.*;
import fellesprosjekt.gruppe30.View.AppointmentView;
import fellesprosjekt.gruppe30.View.BookMeetingRoomView;
import fellesprosjekt.gruppe30.View.CalendarView;
import fellesprosjekt.gruppe30.View.LoginView;
import fellesprosjekt.gruppe30.View.ViewAppointmentView;

import org.json.JSONObject;

import javax.swing.*;


public class Client {
	private final LoginController loginController;
    private final LoginView loginView;
    private final Calendar calendar;
    private final CalendarController calendarController;
    private final CalendarView calendarView;
	private final AppointmentView appointmentView;
	private final ViewAppointmentView viewAppointmentView;
    private final BookMeetingRoomView bookMeetingRoomView;
    private final AppointmentController appointmentController;
    public  final ClientNetwork network;

	private List<User> users;
	private List<Appointment> appointments;
	private List<MeetingRoom> meetingRooms;
	private List<Alarm> alarms;
	private User loggedInUser = null;

	public Client() {
		// test code
		appointments = new ArrayList<Appointment>();
		users = new ArrayList<User>();
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

	public AppointmentController getAppointmentController() {
		return appointmentController;
	}
	
	public LoginView getLoginView() {
		return this.loginView;
	}
	
	public Calendar getCalendar() {
		return this.calendar;
	}
	
	public CalendarView getCalendarView() {
		return calendarView;
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

	public void setLoggedin(String email) {
		close(ViewEnum.LOGIN);
		open(ViewEnum.CALENDAR);
		this.loggedInUser = Utilities.getUserByEmail(email, users);
	}

	public void logout() {
		this.loggedInUser = null;
		JSONObject obj = new JSONObject();
		obj.put("type", "logout");
		this.network.send(obj);
		close(ViewEnum.ALL);
		open(ViewEnum.LOGIN);
	}

	public void newAppointment() {
		open(Client.ViewEnum.APPOINTMENT);
		Appointment newAppointment = new Appointment((InternalUser) this.loggedInUser);
		this.appointments.add(newAppointment);
		getAppointmentView().setModel(newAppointment);
	}

	public static enum ViewEnum {
        ALL, LOGIN, CALENDAR, APPOINTMENT, BOOKMEETINGROOM, VIEWAPPOINTMENTVIEW
    }

    public void quit(int i) {
    	System.exit(i);
    }
    
	public void addUser(User user) {
		users.add(user);
	}

	public LoginController getLoginController() {
		return loginController;
	}

	public void addAppointment(Appointment appointment) {
		appointments.add(appointment);
	}

	public void removeAppointment(Appointment appointment) {
		appointments.remove(appointment);
	}

	public void addMeetingRoom(MeetingRoom meetingRoom) {
		meetingRooms.add(meetingRoom);
	}

	public void addAlarm(Alarm alarm) {
		alarms.add(alarm);
	}

	public void removeAlarm(Appointment appointment, User user) {
		Alarm alarm = Utilities.getAlarm(appointment, user, this.alarms);
		alarms.remove(alarm);
	}
}
