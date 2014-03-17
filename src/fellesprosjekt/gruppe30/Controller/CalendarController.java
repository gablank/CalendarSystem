package fellesprosjekt.gruppe30.Controller;

import fellesprosjekt.gruppe30.Client;
import fellesprosjekt.gruppe30.Model.Calendar;
import fellesprosjekt.gruppe30.Model.InternalUser;
import fellesprosjekt.gruppe30.View.CalendarView;

import javax.swing.*;
import java.awt.event.*;

public class CalendarController implements ActionListener, MouseListener, WindowListener {
	private final Client client;
	private final CalendarView calendarView;
	private final Calendar calendarModel;

	public CalendarController(Client client) {
		this.client = client;
		this.calendarView = new CalendarView(client);
		calendarView.addListener(this);
		this.calendarModel = new Calendar(client);
		calendarView.setModel(calendarModel);
        client.addPcsListener(calendarView);
	}

	public void actionPerformed(ActionEvent actionEvent) {
		String buttonName;
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
			calendarModel.nextWeek();
		} else if(buttonName.equalsIgnoreCase("prev_week")) {
			calendarModel.previousWeek();
		} else if(buttonName.equalsIgnoreCase("new_appointment")) {
			client.newAppointment();
		} else if(buttonName.equalsIgnoreCase("add_calendar")) {
			InternalUser added = calendarView.getSelectedUser();
			calendarModel.addUser(added);
		} else if(buttonName.equalsIgnoreCase("remove_calendar")) {
			InternalUser removed = calendarView.getSelectedDropDownUser();
			calendarModel.removeUser(removed);
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


	@Override
	public void windowOpened(WindowEvent windowEvent) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void windowClosing(WindowEvent windowEvent) {
		client.logout();
	}

	@Override
	public void windowClosed(WindowEvent windowEvent) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void windowIconified(WindowEvent windowEvent) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void windowDeiconified(WindowEvent windowEvent) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void windowActivated(WindowEvent windowEvent) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void windowDeactivated(WindowEvent windowEvent) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	public void setVisible(boolean state) {
		this.calendarView.setVisible(state);
	}

	public void setUser(InternalUser user) {
		this.calendarModel.setUser(user);
	}
}
