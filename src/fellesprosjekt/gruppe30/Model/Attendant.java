package fellesprosjekt.gruppe30.Model;


import org.json.JSONObject;

public abstract class Attendant {
	protected User        user;
	protected Appointment appointment;
	protected int         status;


	public Attendant(User user, Appointment appointment) {
		this.user = user;
		this.appointment = appointment;
		setStatus(Status.NOT_ANSWERED);
	}

	public User getUser() {
		return this.user;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setStatus(int status) {
		this.status = status;
		if(appointment.getOwner() == getUser()) {
			this.status = Status.ATTENDING;
		}
		appointment.firePcs();
	}

	public int getStatus() {
		return status;
	}

	public abstract JSONObject getJSON();


	public static class Status {
		public static final int ATTENDING    = 0;
		public static final int NOT_ATTENDING = 1;
		public static final int NOT_ANSWERED  = 2;
	}
}
