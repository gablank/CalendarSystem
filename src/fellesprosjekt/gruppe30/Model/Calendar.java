package fellesprosjekt.gruppe30.Model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import fellesprosjekt.gruppe30.View.CalendarView;

public class Calendar {

	private int week;
	private ArrayList<User> otherCalendars;
	private ArrayList<Appointment> appointments;

	PropertyChangeSupport pcs;
	
	public Calendar() {
		/*
		 * TODO get appointments
		 */
		week = java.util.Calendar.getInstance().get(java.util.Calendar.WEEK_OF_YEAR);
		pcs = new PropertyChangeSupport(this);
	}
	
	public void nextWeek() {
		week ++;
		pcs.firePropertyChange("next week", week-1, week);
	}
	
	public void previousWeek() {
		week --;
		pcs.firePropertyChange("previous week", week-1, week);
	}
	
	public void addPropertyChangeSuppertListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	
	public void addUser(User user) {
		otherCalendars.add(user);
	}
	
	public void removeUser(User user) {
		otherCalendars.remove(user);
	}
	
	public static void main(String[] args) {
	
		Calendar calendar = new Calendar();
	
		System.out.print(calendar.week);
	
	}
}
