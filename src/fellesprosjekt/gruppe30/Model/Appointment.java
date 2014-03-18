package fellesprosjekt.gruppe30.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;
import java.util.Calendar;

public class Appointment {
	private int             id;
	private String          title;
	private String          description;
	private Date            start;
	private Date            end;
	private String          meetingPlace;
	private List<Attendant> attendants;
	private MeetingRoom     room;
	private Date            lastUpdated;
	private InternalUser    owner;
	private PropertyChangeSupport pcs;


	public Appointment(InternalUser owner) {
		this(owner, "Title", "Description", new Date(), new Date(), "", null);
	}

	public Appointment(InternalUser owner, String title, String description, Date start, Date end) {
		this(owner, title, description, start, end, "", null);
	}

	public Appointment(InternalUser owner, String title, String description, Date start, Date end, String meetingPlace) {
		this(owner, title, description, start, end, meetingPlace, null);
	}

	public Appointment(InternalUser owner, String title, String description, Date start, Date end, MeetingRoom meetingRoom) {
		this(owner, title, description, start, end, "", meetingRoom);
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
		this.attendants = new ArrayList<Attendant>();
		this.lastUpdated = new Date();
		pcs = new PropertyChangeSupport(this);
        //this.addUser(owner);
	}

	public void addListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	public void addAttendant(Attendant attendant) {
		this.attendants.add(attendant);
		pcs.firePropertyChange("change", 1, 2);
	}

	public void addUser(User user) {
		// Avoid adding duplicates
		for(Attendant attendant1 : attendants) {
			if(attendant1.getUser() == user) {
				return;
			}
		}

		Attendant attendant;
		if(user instanceof InternalUser) {
			attendant = new InternalAttendant((InternalUser) user, this);
		} else {
			attendant = new ExternalAttendant((ExternalUser) user, this);
		}
		addAttendant(attendant);
	}

	public void addGroup(Group group) {
		for(User user : group.getMembers()) {
			this.addUser(user);
		}
	}

	public void removeAttendant(Attendant attendant) {
		this.attendants.remove(attendant);
		pcs.firePropertyChange("change", 1, 2);
	}

	public void setAttendants(List<Attendant> attendants) {
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
		setStart(start, true);
	}

	public void setStart(Date start, boolean fireChange) {
		this.start.setTime(start.getTime());
		if (fireChange)
			pcs.firePropertyChange("start", 1, 2);
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date start) {
		setEnd(start, true);
	}

	public void setEnd(Date end, boolean fireChange) {
		this.end.setTime(end.getTime());
		if (fireChange)
			pcs.firePropertyChange("end", 1, 2);
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
		pcs.firePropertyChange("room", 1, 2);
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
		obj.put("attendants", attendants);
		if(this.room == null) {
			obj.put("meetingRoom", -1);
		} else {
			obj.put("meetingRoom", this.room.getId());
		}
		obj.put("lastUpdated", this.lastUpdated.getTime());
		obj.put("owner", this.owner.getEmail());
		return obj;
	}

	public void copyAllFrom(Appointment appointment) {
		this.id = appointment.getId();
		this.owner = appointment.getOwner();
		this.title = appointment.getTitle();
		this.description = appointment.getDescription();
		this.start = appointment.getStart();
		this.end = appointment.getEnd();
		this.meetingPlace = appointment.getMeetingPlace();
		this.room = appointment.getMeetingRoom();
		this.attendants = appointment.getAttendants();
		this.lastUpdated = appointment.getLastUpdated();
	}

	public int getYear() {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(start);
		return cal.get(java.util.Calendar.YEAR);
	}

	public int getWeek() {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(start);
		return cal.get(java.util.Calendar.WEEK_OF_YEAR);
	}

	public int getDayOfWeek() {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(start);
		int dayOfWeek = cal.get(java.util.Calendar.DAY_OF_WEEK);
		dayOfWeek -= 1;
		if(dayOfWeek == 0) {
			dayOfWeek = 6;
		}
		return dayOfWeek;
	}
}
