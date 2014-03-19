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

	public void setCrash(boolean crash) {
		if(crash) {
			status |= 1 << 2;
		} else {
			status &= 0b011;
		}
	}

	public boolean getCrash() {
		if((status & 0b100) > 0) {
			return true;
		} else {
			return false;
		}
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setStatus(int status) {
		this.status &= 0b100;
		this.status |= status;
		if(appointment.getOwner() == getUser()) {
			this.status &= 0b100;
			this.status |= Status.ATTENDING;
		}
		appointment.firePcs();
	}

	public int getStatus() {
		return status & 0b11;
	}

	public abstract JSONObject getJSON();


	public static class Status {
		public static final int ATTENDING    = 0;
		public static final int NOT_ATTENDING = 1;
		public static final int NOT_ANSWERED  = 2;
	}
}
