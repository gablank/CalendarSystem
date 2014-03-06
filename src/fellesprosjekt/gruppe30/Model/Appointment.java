package fellesprosjekt.gruppe30.Model;

import java.util.Date;

public class Appointment {
	
	private String title;
	private String description;
	private Date start;
	private Date end;
	private String meeting_place;
	private MeetingRoom room;
	
	public Appointment(String title, String description, Date start, Date end, String meeting_place, MeetingRoom room) {
		this.title = title;
		this.description = description;
		this.start.setTime(start.getTime());
		this.end.setTime(end.getTime());
		
		if (room == null) {
			this.meeting_place = meeting_place;
		} else this.room = room;
	}

	public String get_itle() {
		return title;
	}

	public void set_title(String title) {
		this.title = title;
	}

	public String get_description() {
		return description;
	}

	public void set_description(String description) {
		this.description = description;
	}

	public Date get_start() {
		return start;
	}

	public void set_start(Date start) {
		this.start.setTime(start.getTime());
	}

	public Date get_end() {
		return end;
	}

	public void set_end(Date end) {
		this.end.setTime(end.getTime());
	}

	public String get_meeting_place() {
		return meeting_place;
	}

	public void set_meeting_place(String meeting_place) {
		this.meeting_place = meeting_place;
	}

	public MeetingRoom get_room() {
		return room;
	}

	public void setRoom(MeetingRoom room) {
		this.room = room;
	}
	
	

}
