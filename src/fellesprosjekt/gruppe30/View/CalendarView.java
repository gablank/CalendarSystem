package fellesprosjekt.gruppe30.View;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import fellesprosjekt.gruppe30.Model.User;

public class CalendarView extends JPanel {
	private JButton addButton, removeButton, newAppointmentButton, logOutButton, leftArrowButton, rightArrowButton;
	private JComboBox users;
	private JLabel weekLabel, showCalendarsFor, monLabel, tueLabel, wedLabel, thuLabel, friLabel, satLabel, sunLabel;
	private JList<User> userCalendars;
	private JList<AppointmentSummaryView>  monAppointments, tueAppointments, wedAppointments, thuAppointments, friAppointments, satAppointments, sunAppointments;
	private JScrollPane monScrollpane, tueScrollpane, wedScrollpane, thuScrollpane, friScrollpane, satScrollpane, sunScrollpane;
	private JFrame frame;
	
	public CalendarView() {
		GridBagConstraints c = new GridBagConstraints();
		GridBagConstraints contents = new GridBagConstraints();
		setLayout(new GridBagLayout());
		
		//bestem utseende p� hver knapp
		addButton = new JButton("Add");
		addButton.setPreferredSize(new Dimension(60, 30));
		
		removeButton = new JButton("Remove");
		removeButton.setPreferredSize(new Dimension(80, 30));
	
		newAppointmentButton = new JButton("New Appointment");
		newAppointmentButton.setPreferredSize(new Dimension(145, 30));
		
		logOutButton = new JButton("Log out");
		logOutButton.setPreferredSize(new Dimension(80, 25));
		
		leftArrowButton = new JButton("<");
		leftArrowButton.setPreferredSize(new Dimension(45, 30));
		
		rightArrowButton = new JButton(">");
		rightArrowButton.setPreferredSize(new Dimension(45, 30));
		
		userCalendars = new JList<User>();
		userCalendars.setPreferredSize(new Dimension(140,150));
		
		users = new JComboBox();
		users.setPreferredSize(new Dimension(140, 25));
		
		weekLabel = new JLabel("week 10");
		showCalendarsFor = new JLabel("Show calendars for:");
		monLabel = new JLabel("Mon 3/3");
		tueLabel = new JLabel("Tue 4/3");
		wedLabel = new JLabel("Wed 5/3");
		thuLabel = new JLabel("Thu 6/3");
		friLabel = new JLabel("Fri 7/3");
		satLabel = new JLabel("Sat 8/3");
		sunLabel = new JLabel("Sun 9/3");
		
		monAppointments = new JList<AppointmentSummaryView>();
		tueAppointments = new JList<AppointmentSummaryView>();
		wedAppointments = new JList<AppointmentSummaryView>();
		thuAppointments = new JList<AppointmentSummaryView>();
		friAppointments = new JList<AppointmentSummaryView>();
		satAppointments = new JList<AppointmentSummaryView>();
		sunAppointments = new JList<AppointmentSummaryView>();
		
		monScrollpane = new JScrollPane(monAppointments);
		monScrollpane.setPreferredSize(new Dimension(160, 450));
		
		tueScrollpane = new JScrollPane(tueAppointments);
		tueScrollpane.setPreferredSize(new Dimension(160, 450));
		
		wedScrollpane = new JScrollPane(wedAppointments);
		wedScrollpane.setPreferredSize(new Dimension(160, 450));
		
		thuScrollpane = new JScrollPane(thuAppointments);
		thuScrollpane.setPreferredSize(new Dimension(160, 450));
		
		friScrollpane = new JScrollPane(friAppointments);
		friScrollpane.setPreferredSize(new Dimension(160, 450));
		
		satScrollpane = new JScrollPane(satAppointments);
		satScrollpane.setPreferredSize(new Dimension(160, 450));
		
		sunScrollpane = new JScrollPane(sunAppointments);
		sunScrollpane.setPreferredSize(new Dimension(160, 450));
		
		
		
		// bygg opp en gridbag
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5,5,5,5);
		contents.insets = new Insets(0,0,0,0);
		
		JPanel arrowPanel = new JPanel();
		arrowPanel.add(leftArrowButton);
		arrowPanel.add(weekLabel);
		arrowPanel.add(rightArrowButton);
		add(arrowPanel, c);

		c.gridy = 1;
		add(showCalendarsFor, c);
		c.gridy = 2;
		add(userCalendars, c);
		c.gridy = 3;
		add(users, c);
		c.gridy = 4;
		JPanel addRemove = new JPanel();
		addRemove.add(addButton);
		addRemove.add(removeButton);
		add(addRemove, c);
		c.gridy = 5;
		add(newAppointmentButton, c);
		c.gridy = 7;
		c.anchor = GridBagConstraints.SOUTHWEST;
		add(logOutButton, c);
		c.anchor = GridBagConstraints.CENTER;
		c.gridy = 0;
		c.gridx = 1;
		add(monLabel, c);
		c.gridx = 2;
		add(tueLabel, c);
		c.gridx = 3;
		add(wedLabel, c);
		c.gridx = 4;
		add(thuLabel, c);
		c.gridx = 5;
		add(friLabel, c);
		c.gridx = 6;
		add(satLabel, c);
		c.gridx = 7;
		add(sunLabel, c);
		

		contents.gridx = 1;
		contents.gridy = 1;
		contents.gridheight = 7;
		add(monScrollpane, contents);
		contents.gridx = 2;
		add(tueScrollpane, contents);
		contents.gridx = 3;
		add(wedScrollpane, contents);
		contents.gridx = 4;
		add(thuScrollpane, contents);
		contents.gridx = 5;
		add(friScrollpane, contents);
		contents.gridx = 6;
		add(satScrollpane, contents);
		contents.gridx = 7;
		add(sunScrollpane, contents);
		
		
		frame = new JFrame("Calendar view");	
		frame.add(this);
		frame.pack();
		frame.setVisible(false);
		frame.setResizable(true);
		//test code
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//end test code
		
	}
	
	public void setVisible(boolean visible){
		this.frame.setVisible(visible);
	}

	public static void main(String[] args) {
		CalendarView view = new CalendarView();
		view.setVisible(true);
	}

}
