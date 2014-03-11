package fellesprosjekt.gruppe30;


import fellesprosjekt.gruppe30.Controller.AppointmentController;
import fellesprosjekt.gruppe30.Controller.CalendarController;
import fellesprosjekt.gruppe30.Controller.LoginController;
import fellesprosjekt.gruppe30.Model.Calendar;
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

        if(view.equals("all") || view.equals("login")) {
            this.loginView.setVisible(state);
        } else if(view.equals("all") || view.equals("week")) {
            // TODO
        } else if(view.equals("all") || view.equals("appointment")) {
            this.appointmentView.setVisible(state);
        } else if(view.equals("all") || view.equals("bookmeetingroom")) {
            this.bookMeetingRoomView.setVisible(state);
        } else if(view.equals("all")|| view.equals("calendar")) {
        	this.calendarView.setVisible(state);
        } else if(view.equals("all")|| view.equals("view appointment")) {
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
    
    public void quit(int i) {
    	System.exit(i);
    }
}
