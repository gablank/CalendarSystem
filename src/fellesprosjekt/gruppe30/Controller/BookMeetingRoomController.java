package fellesprosjekt.gruppe30.Controller;

import java.awt.Component;
import java.awt.List;
import java.awt.event.ActionEvent;

import fellesprosjekt.gruppe30.Application;
import fellesprosjekt.gruppe30.Client;
import fellesprosjekt.gruppe30.Model.MeetingRoom;

public class BookMeetingRoomController {
	public final Client client;
	
	public BookMeetingRoomController(Client client) {
		this.client = client;
	}
	
	public void actionPerformed(ActionEvent actionEvent) {
		String cmd = ((Component) actionEvent.getSource()).getName();
		System.out.println(cmd);
		
		if (cmd.equals("ok_button")) {
			//send rom og tid til appointmentkontroller
			client.close(Client.ViewEnum.BOOKMEETINGROOM);
		}
		else if (cmd.equals("quit_button")) {
			client.close(Client.ViewEnum.BOOKMEETINGROOM);
		}
		
		else if (cmd.equals("start_text") || cmd.equals("end_text") || cmd.equals("date_text")) {
			List<MeetingRoom> allRooms =  Application.getMeetingRooms();
		}
	}
	
	

	public static void main(String[] args) {
	
	}

}
