package fellesprosjekt.gruppe30.Model;

import fellesprosjekt.gruppe30.Utilities;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class Calendar {
	private GregorianCalendar  gregorianCalendar;
	private List<InternalUser> otherCalendars;
	private List<Appointment>  appointments;

	PropertyChangeSupport pcs;

	public Calendar() {
		appointments = new ArrayList<Appointment>();
		/*
		 * TODO get appointments
		 */
		gregorianCalendar.setTime(new java.util.Date());
		gregorianCalendar.setFirstDayOfWeek(java.util.Calendar.MONDAY);
		pcs = new PropertyChangeSupport(this);
	}

	public void nextWeek() {
		int week = getWeek();
		if(week >= Utilities.getNumberOfWeeksInYear(this.getYear())) {
			gregorianCalendar.set(java.util.Calendar.YEAR, this.getYear() + 1);
			week = Utilities.getNumberOfWeeksInYear(this.getYear());
		} else {
			week++;
		}
		gregorianCalendar.set(java.util.Calendar.WEEK_OF_YEAR, week);
		// Not sure if needed
		//gregorianCalendar.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.MONDAY);
	}

	public void previousWeek() {
		int week = getWeek();
		if(week <= 1) {
			gregorianCalendar.set(java.util.Calendar.YEAR, this.getYear() - 1);
			week = Utilities.getNumberOfWeeksInYear(this.getYear());
		} else {
			week--;
		}
		gregorianCalendar.set(java.util.Calendar.WEEK_OF_YEAR, week);
		// Not sure if needed
		//gregorianCalendar.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.MONDAY);
	}

	public void addPropertyChangeSupportListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	public void addUser(InternalUser user) {
		otherCalendars.add(user);
	}

	public void removeUser(InternalUser user) {
		otherCalendars.remove(user);
	}

	public List<List<Appointment>> getAppointments() {
		List<List<Appointment>> days = new ArrayList<List<Appointment>>();
		List<Appointment> monday = new ArrayList<Appointment>();
		days.add(monday);
		return days;
	}

	public int getWeek() {
		return gregorianCalendar.get(java.util.Calendar.WEEK_OF_YEAR);
	}

	private int getYear() {
		return gregorianCalendar.get(java.util.Calendar.YEAR);
	}

	public static void main(String[] args) {


	}
}
