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
	private String meetingPlace;
    private List<Attendant> attendants;
	private MeetingRoom room;
    private Date lastUpdated;
    private User owner;

    public Appointment(User owner, String title, String description, Date start, Date end, String meetingPlace) {
        this(owner, title, description, start, end, meetingPlace, null);
    }

    public Appointment(User owner, String title, String description, Date start, Date end, MeetingRoom meetingRoom) {
        this(owner, title, description, start, end, null, meetingRoom);
    }

    public Appointment(User owner, String title, String description, Date start, Date end, String meetingPlace, MeetingRoom room) {
        this.id = -1;
        this.owner = owner;
		this.title = title;
		this.description = description;
		this.start = start;
		this.end = end;
        this.meetingPlace = meetingPlace;
        this.room = room;
        attendants = new ArrayList<Attendant>();
        this.lastUpdated = new Date();
    }

    public void addAttendant(Attendant attendant) {
        this.attendants.add(attendant);
    }

    public void removeAttendant(Attendant attendant) {
        this.attendants.remove(attendant);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start.setTime(start.getTime());
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end.setTime(end.getTime());
	}

	public String getMeetingPlace() {
		return meetingPlace;
	}

	public void setMeetingPlace(String meetingPlace) {
		this.meetingPlace = meetingPlace;
	}

	public MeetingRoom getRoom() {
		return room;
	}

	public void setRoom(MeetingRoom room) {
		this.room = room;
	}

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
