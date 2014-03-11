package fellesprosjekt.gruppe30;


import java.util.List;

import fellesprosjekt.gruppe30.Controller.AppointmentController;
import fellesprosjekt.gruppe30.Controller.CalendarController;
import fellesprosjekt.gruppe30.Controller.ClientNetwork;
import fellesprosjekt.gruppe30.Controller.LoginController;
import fellesprosjekt.gruppe30.Model.Alarm;
import fellesprosjekt.gruppe30.Model.Appointment;
import fellesprosjekt.gruppe30.Model.Calendar;
import fellesprosjekt.gruppe30.Model.MeetingRoom;
import fellesprosjekt.gruppe30.Model.User;
import fellesprosjekt.gruppe30.View.AppointmentView;
import fellesprosjekt.gruppe30.View.BookMeetingRoomView;
import fellesprosjekt.gruppe30.View.CalendarView;
import fellesprosjekt.gruppe30.View.LoginView;
import fellesprosjekt.gruppe30.View.ViewAppointmentView;

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
    private final ClientNetwork network;

	private List<User> users;
	private List<Appointment> appointments;
	private List<MeetingRoom> meetingRooms;
	private List<Alarm> alarms;

    public Client() {
    	this.loginController = new LoginController(this);
        this.loginView = new LoginView();
		this.loginView.addListener(loginController);
		
		this.calendar = new Calendar();
		this.calendarController = new CalendarController(this);
		this.calendarView = new CalendarView();
		this.calendarView.addListener(calendarController);
		this.calendar.addPropertyChangeSuppertListener(calendarView);
		
        this.appointmentView = new AppointmentView();
        this.bookMeetingRoomView = new BookMeetingRoomView();
        this.viewAppointmentView = new ViewAppointmentView();

        this.appointmentController = new AppointmentController(this);
        this.appointmentView.addListener(this.appointmentController);


        this.close(ViewEnum.ALL);
        this.open(ViewEnum.LOGIN);

        this.network = new ClientNetwork();
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
        } else if(viewEnum.equals(ViewEnum.ALL) || viewEnum.equals(ViewEnum.APPOINTMENT)) {
            this.appointmentView.setVisible(state);
        } else if(viewEnum.equals(ViewEnum.ALL) || viewEnum.equals(ViewEnum.BOOKMEETINGROOM)) {
            this.bookMeetingRoomView.setVisible(state);
        } else if(viewEnum.equals(ViewEnum.ALL) || viewEnum.equals(ViewEnum.CALENDAR)) {
        	this.calendarView.setVisible(state);
        } else if(viewEnum.equals(ViewEnum.ALL)|| viewEnum.equals(ViewEnum.VIEWAPPOINTMENTVIEW)) {
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

    public enum ViewEnum {
        ALL, LOGIN, CALENDAR, APPOINTMENT, BOOKMEETINGROOM, VIEWAPPOINTMENTVIEW
    }

    public void quit(int i) {
    	System.exit(i);
    }
    
	public void addUser(User user) {
		users.add(user);
	}

	public void addMeetingRoom(MeetingRoom meetingRoom) {
		meetingRooms.add(meetingRoom);
	}

	public void removeAppointment(int id) {
//		appointments.remove(o)
	}
}
