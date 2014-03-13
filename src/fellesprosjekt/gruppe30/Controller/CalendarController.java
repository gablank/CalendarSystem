package fellesprosjekt.gruppe30.Controller;

import fellesprosjekt.gruppe30.Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CalendarController implements ActionListener, MouseListener {
	private final Client client;

	public CalendarController(Client client) {
		this.client = client;
	}

	public void actionPerformed(ActionEvent actionEvent) {
		String cmd = actionEvent.getActionCommand();

		if(cmd.equalsIgnoreCase("log out")) {
			client.logout();
		} else if(cmd.equalsIgnoreCase(">")) {
			client.getCalendar().nextWeek();
		} else if(cmd.equalsIgnoreCase("<")) {
			client.getCalendar().previousWeek();
		} else if(cmd.equalsIgnoreCase("new appointment")) {
			client.newAppointment();
		} else if(cmd.equalsIgnoreCase("add")) {
			//client.getCalendar().addUser(client.getCalendarView().getUser());
		} else if(cmd.equalsIgnoreCase("remove")) {
			//client.getCalendar().removeUser(client.getCalendarView().getUser());
		} 
		
		/*else if(cmd.equalsIgnoreCase("AppointmentSummaryView")) {
			client.open(Client.ViewEnum.VIEWAPPOINTMENTVIEW);
		} */
	}

	public void mouseClicked(MouseEvent e) {
		client.open(Client.ViewEnum.VIEWAPPOINTMENTVIEW);		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


}
