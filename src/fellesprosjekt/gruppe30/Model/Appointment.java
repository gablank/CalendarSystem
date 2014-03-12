package fellesprosjekt.gruppe30.Model;

import org.json.JSONArray;
import org.json.JSONObject;

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
    private InternalUser owner;


	public Appointment(InternalUser owner) {
		this(owner, "Title", "Description", new Date(), new Date(), null, null);
	}

    public Appointment(InternalUser owner, String title, String description, Date start, Date end, String meetingPlace) {
        this(owner, title, description, start, end, meetingPlace, null);
    }

    public Appointment(InternalUser owner, String title, String description, Date start, Date end, MeetingRoom meetingRoom) {
        this(owner, title, description, start, end, null, meetingRoom);
    }

    public Appointment(InternalUser owner, String title, String description, Date start, Date end, String meetingPlace, MeetingRoom room) {
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

    public void removeAttendant(InternalAttendant internalAttendant) {
        this.attendants.remove(internalAttendant);
    }
    
    public void setAttendants(List<Attendant> attendants){
    	this.attendants = attendants;
    }

    public List<Attendant> getAttendants() {
        return this.attendants;
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

	public MeetingRoom getMeetingRoom() {
		return room;
	}

	public void setMeetingRoom(MeetingRoom room) {
		this.room = room;
	}

    public InternalUser getOwner() {
        return owner;
    }

    public void setOwner(InternalUser owner) {
        this.owner = owner;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }


	public JSONObject getJSON() {
		JSONObject obj = new JSONObject();
		obj.put("type", "appointment");
		obj.put("id", this.id);
		obj.put("title", this.title);
		obj.put("description", this.description);
		obj.put("start", this.start.getTime());
		obj.put("end", this.end.getTime());
		obj.put("meetingPlace", this.meetingPlace);
		JSONArray attendants = new JSONArray();
		for(Attendant attendant : this.attendants) {
			attendants.put(attendant.getJSON());
		}
		obj.put("attendants", this.attendants);
		if (this.room == null) {
			obj.put("meetingRoom", -1);
		} else {
			obj.put("meetingRoom", this.room.getId());
		}
		obj.put("lastUpdated", this.lastUpdated.getTime());
		obj.put("owner", this.owner.getEmail());
		return obj;
	}
}
