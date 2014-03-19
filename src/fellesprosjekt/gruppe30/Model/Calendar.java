package fellesprosjekt.gruppe30.Model;

import fellesprosjekt.gruppe30.Client;
import fellesprosjekt.gruppe30.Utilities;
import fellesprosjekt.gruppe30.View.CalendarView;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

public class Calendar {
	private final GregorianCalendar  gregorianCalendar;
	private List<InternalUser>       otherCalendars;
	private final Client             client;

	PropertyChangeSupport pcs;

	public Calendar(Client client) {
		this.client = client;
		this.otherCalendars = new ArrayList<InternalUser>();

		gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(new java.util.Date());
		gregorianCalendar.set(java.util.Calendar.HOUR_OF_DAY, 0);
		gregorianCalendar.set(java.util.Calendar.MINUTE, 0);
		gregorianCalendar.set(java.util.Calendar.SECOND, 0);
		gregorianCalendar.setFirstDayOfWeek(java.util.Calendar.MONDAY);
		pcs = new PropertyChangeSupport(this);
	}

	public void addPcsListener(CalendarView calendarView) {
		this.addPropertyChangeSupportListener(calendarView);
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

	public void setUser(InternalUser user) {
		otherCalendars.clear();
		otherCalendars.add(user);
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
					// Abuse this loop to reset the crash field
					attendant.setCrash(false);
					if(attendant instanceof ExternalAttendant) {
						continue;
					}
					InternalUser user = (InternalUser) attendant.getUser();
					if(this.otherCalendars.contains(user) && ((InternalAttendant) attendant).getVisibleOnCalendar()) {
						days.get(appointment.getDayOfWeek()).add(appointment);
					}
				}
			}
		}

		// Remove duplicates and sort by start date asc
		for(int i = 0; i < 7; i++) {
			List<Appointment> day = days.get(i);

			// Remove duplicates
			HashSet<Appointment> hashSet = new HashSet<Appointment>();
			hashSet.addAll(day);
			day.clear();
			day.addAll(hashSet);

			// Sort it
			Collections.sort(day, new Comparator<Appointment>() {
				@Override
				public int compare(Appointment appointment1, Appointment appointment2) {
					// First sort by start time
					if(appointment1.getStart().before(appointment2.getStart())) {
						return -1;
					} else if(appointment1.getStart().after(appointment2.getStart())) {
						return 1;
					}

					// Then by end time if start time is equal
					if(appointment1.getStart().before(appointment2.getStart())) {
						return -1;
					} else if(appointment1.getStart().after(appointment2.getStart())) {
						return 1;
					}

					// Identical start and end times
					return 0;
				}
			});

			for(int j = 0; j < day.size() - 1; j++) {
				for(int k = j+1; k < day.size(); k++) {
					// If true there is a crash
					if(day.get(j).getEnd().after(day.get(k).getStart())) {
						// Mark all attendants that are ATTENDING both appointments as crashing
						for(Attendant attendant : day.get(j).getAttendants()) {
							if(attendant.getStatus() != Attendant.Status.ATTENDING) {
								break;
							}
							for(Attendant attendant1 : day.get(k).getAttendants()) {
								if(attendant1.getStatus() != Attendant.Status.ATTENDING) {
									break;
								}
								if(attendant.getUser() == attendant1.getUser()) {
									attendant.setCrash(true);
									attendant1.setCrash(true);
								}
							}
						}

					// If k doesn't crash with j, k+1 cant possibly crash with j because we have sorted them asc by start time
					} else {
						break;
					}
				}
			}
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
		long msPerDay = 24 * 60 * 60 * 1000;
		long time = gregorianCalendar.getTime().getTime() - (gregorianCalendar.get(java.util.Calendar.DAY_OF_WEEK) - 1) * msPerDay;
		for(int i = 0; i < 7; i++) {
			time += msPerDay;
			labels[i] = days[i] + " " + Utilities.dateToFormattedString(new java.util.Date(time), false);
		}
		return labels;
	}

	public List<InternalUser> getUsers() {
		return otherCalendars;
	}


	public static void main(String[] args) {

	}

	public List<InternalUser> getInternalUsers() {
		List<InternalUser> users = new ArrayList<>();
		for(User user : client.getUsers()) {
			if(user instanceof InternalUser && !otherCalendars.contains(user)) {
				users.add((InternalUser) user);
			}
		}
		return users;
	}
}
