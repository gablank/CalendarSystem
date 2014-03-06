package fellesprosjekt.gruppe30.Model;

public class MeetingRoom {
	int room_size;
	int room_nmr;
	
	public MeetingRoom(int room_size, int room_nmr) {
		this.room_size = room_size;
		this.room_nmr = room_nmr;	}
	
	public int get_room_size() {
		return room_size;	}
	
	public int get_room_nmr() {
		return room_nmr;	}
}
