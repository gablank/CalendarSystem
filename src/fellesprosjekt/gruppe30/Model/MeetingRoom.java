package fellesprosjekt.gruppe30.Model;

public class MeetingRoom {
    private int id;
	int roomSize;
	
	public MeetingRoom(int roomSize) {
		this.roomSize = roomSize;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

	public int getRoomSize() {
		return roomSize;
    }
}
