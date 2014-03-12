package fellesprosjekt.gruppe30.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Appointment {
    private int id;
	private String title;
	private String description;
	private Date start;
	private Date end;
	private String meetingPlace;
    private ArrayList<InternalAttendant> internalAttendants;
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
        internalAttendants = new ArrayList<InternalAttendant>();
        this.lastUpdated = new Date();
    }

    public void addAttendant(InternalAttendant internalAttendant) {
        this.internalAttendants.add(internalAttendant);
    }

    public void removeAttendant(InternalAttendant internalAttendant) {
        this.internalAttendants.remove(internalAttendant);
    }
    
    public void setInternalAttendants(ArrayList<InternalAttendant> internalAttendants){
    	this.internalAttendants = internalAttendants;
    }

    public ArrayList<InternalAttendant> getInternalAttendants() {
        return this.internalAttendants;
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
		for(InternalAttendant internalAttendant : this.internalAttendants) {
			attendants.put(internalAttendant.getJSON());
		}
		obj.put("attendants", this.internalAttendants);
		obj.put("meetingRoom", this.room.getId());
		obj.put("lastUpdated", this.lastUpdated.getTime());
		obj.put("owner", this.owner.getId());
		return obj;
	}
}
