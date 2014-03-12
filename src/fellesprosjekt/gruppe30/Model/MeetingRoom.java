package fellesprosjekt.gruppe30.Model;

import org.json.JSONObject;

public class MeetingRoom {
	private int id;
	private int roomSize;

	public MeetingRoom(int roomSize) {
		this.id = -1;
		this.roomSize = roomSize;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setRoomSize(int roomSize) {
		this.roomSize = roomSize;
	}

	public int getRoomSize() {
		return roomSize;
	}

	public JSONObject getJSON() {
		JSONObject obj = new JSONObject();
		obj.put("type", "meetingRoom");
		obj.put("id", this.id);
		obj.put("roomSize", this.roomSize);
		return obj;
	}
}
