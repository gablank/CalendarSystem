package fellesprosjekt.gruppe30.Model;


import org.json.JSONObject;

public abstract class Attendant {
	protected User user;
	protected Appointment appointment;
	protected int status;


	public Attendant(User user, Appointment appointment) {
		this.user = user;
		this.appointment = appointment;
		status = InternalAttendant.Status.NOT_ANSWERED;
	}

	public User getUser() {
		return this.user;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public abstract JSONObject getJSON();


	public static class Status {
		public static final int ATTENDTING = 0;
		public static final int NOT_ATTENDING = 1;
		public static final int NOT_ANSWERED = 2;
	}
}
