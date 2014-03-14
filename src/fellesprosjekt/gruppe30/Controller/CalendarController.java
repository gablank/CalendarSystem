package fellesprosjekt.gruppe30.Controller;

import fellesprosjekt.gruppe30.Client;
import fellesprosjekt.gruppe30.Model.InternalUser;
import fellesprosjekt.gruppe30.Utilities;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static fellesprosjekt.gruppe30.Utilities.*;

public class CalendarController implements ActionListener, MouseListener {
	private final Client client;

	public CalendarController(Client client) {
		this.client = client;
	}

	public void actionPerformed(ActionEvent actionEvent) {
		String cmd = actionEvent.getActionCommand();
		String buttonName = "";
		if(actionEvent.getSource() instanceof JButton) {
			buttonName = ((JButton) actionEvent.getSource()).getName();
		} else {
			System.out.println("actionEvent source is not a JButton!");
			return;
		}

		if(buttonName == null) {
			System.out.println("JButton has no name!");
			return;
		}

		if(buttonName.equalsIgnoreCase("log_out")) {
			client.logout();
		} else if(buttonName.equalsIgnoreCase("next_week")) {
			client.getCalendar().nextWeek();
		} else if(buttonName.equalsIgnoreCase("prev_week")) {
			client.getCalendar().previousWeek();
		} else if(buttonName.equalsIgnoreCase("new_appointment")) {
			client.newAppointment();
		} else if(buttonName.equalsIgnoreCase("add_calendar")) {
			InternalUser added = (InternalUser) Utilities.getUserByEmail(client.getCalendarView().getSelectedUserEmail(), client.getUsers());
			client.getCalendar().addUser(added);
		} else if(buttonName.equalsIgnoreCase("remove_calendar")) {
			InternalUser removed = (InternalUser) Utilities.getUserByEmail(client.getCalendarView().getSelectedUserEmail(), client.getUsers());
			client.getCalendar().removeUser(removed);
		}
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
