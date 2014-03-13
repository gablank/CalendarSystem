package fellesprosjekt.gruppe30.Model;

import fellesprosjekt.gruppe30.Client;
import fellesprosjekt.gruppe30.Utilities;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;

public class Calendar {
	private GregorianCalendar  gregorianCalendar;
	private List<InternalUser> otherCalendars;
	private Client             client;

	PropertyChangeSupport pcs;

	public Calendar(Client client) {
		this.client = client;
		this.otherCalendars = new ArrayList<InternalUser>();

		gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(new java.util.Date());
		gregorianCalendar.setFirstDayOfWeek(java.util.Calendar.MONDAY);
		pcs = new PropertyChangeSupport(this);
		pcs.addPropertyChangeListener(client.getCalendarView());
	}

	public void nextWeek() {
		int week = getWeek() + 1;
		if(week > gregorianCalendar.getWeeksInWeekYear()) {
			gregorianCalendar.set(java.util.Calendar.YEAR, this.getYear() + 1);
			week = 1;
		}
		gregorianCalendar.set(java.util.Calendar.WEEK_OF_YEAR, week);
		pcs.firePropertyChange("change", 1, 2); // Hack
		// Not sure if needed
		//gregorianCalendar.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.MONDAY);
	}

	public void previousWeek() {
		int week = getWeek() - 1;
		if(week < 1) {
			gregorianCalendar.set(java.util.Calendar.YEAR, this.getYear() - 1);
			week = gregorianCalendar.getWeeksInWeekYear();
		}
		gregorianCalendar.set(java.util.Calendar.WEEK_OF_YEAR, week);
		pcs.firePropertyChange("change", 1, 2); // Hack
		// Not sure if needed
		//gregorianCalendar.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.MONDAY);
	}

	public void addPropertyChangeSupportListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	public void addUser(InternalUser user) {
		otherCalendars.add(user);
		pcs.firePropertyChange("change", 1, 2); // Hack
	}

	public void removeUser(InternalUser user) {
		otherCalendars.remove(user);
		pcs.firePropertyChange("change", 1, 2); // Hack
	}

	public List<List<Appointment>> getAppointments() {
		List<List<Appointment>> days = new ArrayList<List<Appointment>>();
		for(int i = 0; i < 7; i++) {
			days.add(new ArrayList<Appointment>());
		}

		for(Appointment appointment : client.getAppointments()) {
			if(appointment.getWeek() == this.getWeek() && appointment.getYear() == this.getYear()) {
				for(Attendant attendant : appointment.getAttendants()) {
					if(attendant instanceof ExternalAttendant) {
						continue;
					}
					InternalUser user = (InternalUser) attendant.getUser();
					if(this.otherCalendars.contains(user) && attendant instanceof InternalAttendant && ((InternalAttendant) attendant).getVisibleOnCalendar()) {
						days.get(appointment.getDayOfWeek()).add(appointment);
					}
				}
			}
		}

		// Remove duplicates
		for(int i = 0; i < 7; i++) {
			HashSet<Appointment> hashSet = new HashSet<Appointment>();
			hashSet.addAll(days.get(i));
			days.get(i).clear();
			days.get(i).addAll(hashSet);
		}

		return days;
	}

	public int getWeek() {
		return gregorianCalendar.get(java.util.Calendar.WEEK_OF_YEAR);
	}

	private int getYear() {
		return gregorianCalendar.get(java.util.Calendar.YEAR);
	}

	public String[] getDays() {
		String[] days = new String[7];
		days[0] = "Mon";
		days[1] = "Tue";
		days[2] = "Wed";
		days[3] = "Thu";
		days[4] = "Fri";
		days[5] = "Sat";
		days[6] = "Sun";
		String[] labels = new String[7];
		for(int i = 0; i < 7; i++) {
			labels[i] = days[i] + " " + Utilities.dateToFormattedString(new java.util.Date(gregorianCalendar.getTime().getTime() + (i - (8 - gregorianCalendar.get(java.util.Calendar.DAY_OF_WEEK))) * 24 * 60 * 60 * 1000), false);
		}
		return labels;
	}

	public List<InternalUser> getUsers() {
		return otherCalendars;
	}


	public static void main(String[] args) {


	}
}
