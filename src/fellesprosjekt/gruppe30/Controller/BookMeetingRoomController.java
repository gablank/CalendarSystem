package fellesprosjekt.gruppe30.Controller;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import fellesprosjekt.gruppe30.Application;
import fellesprosjekt.gruppe30.Client;
import fellesprosjekt.gruppe30.Model.Appointment;
import fellesprosjekt.gruppe30.Model.MeetingRoom;
import fellesprosjekt.gruppe30.View.BookMeetingRoomView;

public class BookMeetingRoomController implements ActionListener, ListSelectionListener, KeyListener {
	private final Client		client;
	private BookMeetingRoomView	bookMeetingRoomView;
	private Appointment			model;
	
	public BookMeetingRoomController(Client client) {
		this.client = client;

		this.bookMeetingRoomView = new BookMeetingRoomView();
		bookMeetingRoomView.addListener(this);

	}
	
	void setModel(Appointment model) {
		this.model = model;
		populateList();
	}

	@Override
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

			// change appointment

			populateList();
		}
	}
	
	void populateList(){
		List<MeetingRoom> allRooms = client.getMeetingRooms();	

		List<MeetingRoom> validRooms = new ArrayList<MeetingRoom>(allRooms);
		
		int wantCapacity = bookMeetingRoomView.getCapacity();
		for (int i = 0; i < validRooms.size(); ++i) {
			if (validRooms.get(i).getRoomSize() < wantCapacity) {
				validRooms.remove(i);
				--i;
			}
		}
		
		Date startDate = model.getStart();
		Date endDate = model.getStart();
		for (int i = 0; i < validRooms.size(); ++i) {
			if (!isAvailable(validRooms.get(i), startDate, endDate, client.getAppointments())) {
				validRooms.remove(i);
				--i;
			}
		}

		bookMeetingRoomView.populateList(validRooms);
	}
	
	private boolean isAvailable(MeetingRoom room, Date startDate, Date endDate, List<Appointment> appointments) {
		
		int roomId = room.getId();
		
		for(Appointment appointment : appointments){
			if(appointment.getMeetingRoom() == null)
				continue;
			
			if(appointment.getMeetingRoom().getId() != roomId)
				continue;	
			
			// (StartDate1 <= EndDate2) 			   && (StartDate2 <= EndDate1)
			if (appointment.getStart().before(endDate) && startDate.before(appointment.getEnd())) {
				// there is overlap
				return false;
			}
		}

		return true;
	}

	public static void main(String[] args) {
		// arguments
		Date startDate1 = new Date(new Date().getTime() - 1000 * 60 * 1);
		Date endDate1 = new Date(new Date().getTime() + 1000 * 60 * 1);

		// other appointment
		Date startDate2 = new Date(new Date().getTime() - 1000 * 60 * 0);
		Date endDate2 = new Date(new Date().getTime() + 1000 * 60 * 0);

		if (startDate2.before(endDate1) && startDate1.before(endDate2)) {
			System.out.println("overlap!");
		} else {
			System.out.println("no overlap");
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub

	}

	public void setVisible(boolean state) {
		bookMeetingRoomView.setVisible(state);
	}

}
