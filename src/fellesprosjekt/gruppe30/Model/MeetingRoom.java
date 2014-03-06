package fellesprosjekt.gruppe30.Model;

public class MeetingRoom {
    private int id;
	int room_size;
	
	public MeetingRoom(int room_size) {
		this.room_size = room_size;
    }

    public int get_id() {
        return id;
    }

    public void set_id(int id) {
        this.id = id;
    }

	public int get_room_size() {
		return room_size;
    }
}
