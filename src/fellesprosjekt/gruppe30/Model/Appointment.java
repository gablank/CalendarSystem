package fellesprosjekt.gruppe30.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Appointment {
    private int id;
	private String title;
	private String description;
	private Date start;
	private Date end;
	private String meeting_place;
    private List<Attendant> attendants;
	private MeetingRoom room;
    private Date last_updated;
    private User owner;

    public Appointment(User owner, String title, String description, Date start, Date end, String meeting_place) {
        this(owner, title, description, start, end, meeting_place, null);
    }

    public Appointment(User owner, String title, String description, Date start, Date end, MeetingRoom meetingRoom) {
        this(owner, title, description, start, end, null, meetingRoom);
    }

    public Appointment(User owner, String title, String description, Date start, Date end, String meeting_place, MeetingRoom room) {
        this.owner = owner;
		this.title = title;
		this.description = description;
		this.start = start;
		this.end = end;
        this.meeting_place = meeting_place;
        this.room = room;
        attendants = new ArrayList<Attendant>();
        this.last_updated = new Date();
    }

    public void add_attendant(Attendant attendant) {
        this.attendants.add(attendant);
    }

    public void remove_attendant(Attendant attendant) {
        this.attendants.remove(attendant);
    }

    public int get_id() {
        return id;
    }

    public void set_id(int id) {
        this.id = id;
    }

	public String get_title() {
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

	public void set_room(MeetingRoom room) {
		this.room = room;
	}

    public User get_owner() {
        return owner;
    }

    public void set_owner(User owner) {
        this.owner = owner;
    }

    public Date get_last_updated() {
        return last_updated;
    }

    public void set_last_updated(Date last_updated) {
        this.last_updated = last_updated;
    }
}
