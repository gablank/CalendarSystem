package fellesprosjekt.gruppe30.Controller;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.SimpleTimeZone;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import fellesprosjekt.gruppe30.Application;
import fellesprosjekt.gruppe30.Client;
import fellesprosjekt.gruppe30.Utilities;
import fellesprosjekt.gruppe30.Model.Appointment;
import fellesprosjekt.gruppe30.Model.MeetingRoom;
import fellesprosjekt.gruppe30.View.BookMeetingRoomView;

public class BookMeetingRoomController implements ActionListener, ListSelectionListener, KeyListener {
	private final Client		client;
	private BookMeetingRoomView	bookMeetingRoomView;

	// private Appointment appointment;
	
	public BookMeetingRoomController(Client client) {
		this.client = client;

		this.bookMeetingRoomView = new BookMeetingRoomView();
		bookMeetingRoomView.addListener(this);

	}
	
	public void setModel(Appointment model) {
		bookMeetingRoomView.setModel(model);
		populateList();
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		String cmd = ((Component) actionEvent.getSource()).getName();
		
		if (cmd.equals("ok_button")) {
			MeetingRoom selectedRoom = bookMeetingRoomView.getSelectedRoom();
			if (selectedRoom != null)
				bookMeetingRoomView.getModel().setMeetingRoom(selectedRoom);
			client.close(Client.ViewEnum.BOOKMEETINGROOM);

		} else if (cmd.equals("cancel_button")) {
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
			System.out.println(validRooms.get(i).getId() + " - " + validRooms.get(i).getRoomSize());
			if (validRooms.get(i).getRoomSize() < wantCapacity) {
				validRooms.remove(i);
				--i;
			}
		}
		
		for (int i = 0; i < validRooms.size(); ++i) {
			if (!Utilities.isAvailable(validRooms.get(i), bookMeetingRoomView.getModel(), client.getAppointments())) {
				validRooms.remove(i);
				--i;
			}
		}

		bookMeetingRoomView.populateList(validRooms);
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
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		try{
			String source = ((Component) e.getSource()).getName().toLowerCase();
			if (source.equals("start_text") || source.equals("end_text") || source.equals("date_text") || source.equals("capacity")) {
				
				//dateText: 	 DD.MM.YYYY
				//start/endText: HH:MM
				
				String dateStrings[] = bookMeetingRoomView.getDateText().getText().split("\\.");
	
				int day = Integer.parseInt(dateStrings[0]);
				int month = Integer.parseInt(dateStrings[1]);
				int year = Integer.parseInt(dateStrings[2]);
	
				String startTime[] = bookMeetingRoomView.getStartText().getText().split(":");
	
				int startHour = Integer.parseInt(startTime[0]);
				int startMinute = Integer.parseInt(startTime[1]);
	
				String endTime[] = bookMeetingRoomView.getEndText().getText().split(":");
	
				int endHour = Integer.parseInt(endTime[0]);
				int endMinute = Integer.parseInt(endTime[1]);
	
				GregorianCalendar newStartGC = new GregorianCalendar(year, month - 1, day, startHour, startMinute);
				newStartGC.setTimeZone(new SimpleTimeZone(3600000, "Europe/Paris", Calendar.MARCH, -1, Calendar.SUNDAY, 3600000, SimpleTimeZone.UTC_TIME, Calendar.OCTOBER, -1, Calendar.SUNDAY, 3600000, SimpleTimeZone.UTC_TIME, 3600000));
				GregorianCalendar newEndGC = new GregorianCalendar(year, month - 1, day, endHour, endMinute);
				newEndGC.setTimeZone(new SimpleTimeZone(3600000, "Europe/Paris", Calendar.MARCH, -1, Calendar.SUNDAY, 3600000, SimpleTimeZone.UTC_TIME, Calendar.OCTOBER, -1, Calendar.SUNDAY, 3600000, SimpleTimeZone.UTC_TIME, 3600000));

				Date newStart = newStartGC.getTime();
				Date newEnd = newEndGC.getTime();
	
				bookMeetingRoomView.getModel().setStart(newStart);
				bookMeetingRoomView.getModel().setEnd(newEnd);
				
				populateList();
			}
		} catch (Exception exception) {
			
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub

	}

	public void setVisible(boolean state) {
		bookMeetingRoomView.setVisible(state);
	}

}
