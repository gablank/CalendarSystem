package fellesprosjekt.gruppe30.View;


import fellesprosjekt.gruppe30.Client;
import fellesprosjekt.gruppe30.Model.Appointment;
import fellesprosjekt.gruppe30.Model.Calendar;
import fellesprosjekt.gruppe30.Model.InternalUser;
import fellesprosjekt.gruppe30.Model.User;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.EventListener;
import java.util.GregorianCalendar;

public class CalendarView extends JPanel implements PropertyChangeListener {
	private PersonRenderer      listrenderer;
	private JButton             addButton, removeButton, newAppointmentButton, logOutButton, leftArrowButton, rightArrowButton;
	private JComboBox 	        users;
	private JLabel    	        weekLabel, showCalendarsFor, monLabel, tueLabel, wedLabel, thuLabel, friLabel, satLabel, sunLabel;
	private JList<InternalUser> userCalendars;
	private JPanel              monAppointment, tueAppointment, wedAppointment, thuAppointment, friAppointment, satAppointment, sunAppointment;
	private JFrame      		frame;
	private Calendar      		model;
	private JScrollPane 		userCalendarsScroller;

	public CalendarView() {
		GridBagConstraints c = new GridBagConstraints();
		GridBagConstraints contents = new GridBagConstraints();
		setLayout(new GridBagLayout());

		//set appearance of all buttons
		addButton = new JButton("Add");
		addButton.setPreferredSize(new Dimension(60, 30));

		removeButton = new JButton("Remove");
		removeButton.setPreferredSize(new Dimension(80, 30));

		newAppointmentButton = new JButton("New Appointment");
		newAppointmentButton.setPreferredSize(new Dimension(145, 30));

		logOutButton = new JButton("Log out");
		logOutButton.setPreferredSize(new Dimension(80, 25));

		leftArrowButton = new JButton("<");
		leftArrowButton.setName("prev_week");
		leftArrowButton.setPreferredSize(new Dimension(41, 20));

		rightArrowButton = new JButton(">");
		rightArrowButton.setName("next_week");
		rightArrowButton.setPreferredSize(new Dimension(41, 20));

		userCalendars = new JList<InternalUser>();
		userCalendarsScroller = new JScrollPane(userCalendars);
		userCalendarsScroller.setPreferredSize(new Dimension(140, 150));

		users = new JComboBox();
		users.setPreferredSize(new Dimension(140, 25));

		weekLabel = new JLabel();
		showCalendarsFor = new JLabel("Show calendars for:");
		monLabel = new JLabel();
		tueLabel = new JLabel();
		wedLabel = new JLabel();
		thuLabel = new JLabel();
		friLabel = new JLabel();
		satLabel = new JLabel();
		sunLabel = new JLabel();

		monAppointment = new JPanel();
		JScrollPane monScroller = new JScrollPane(monAppointment);
		monScroller.setFocusable(true);
		monScroller.setPreferredSize(new Dimension(155, 500));
		monScroller.getVerticalScrollBar().setUnitIncrement(15);
		monAppointment.setBackground(Color.WHITE);
		monAppointment.setBorder(BorderFactory.createLineBorder(Color.black));
		monAppointment.setLayout(new BoxLayout(monAppointment, BoxLayout.Y_AXIS));

		tueAppointment = new JPanel();
		JScrollPane tueScroller = new JScrollPane(tueAppointment);
		tueScroller.setFocusable(true);
		tueScroller.setPreferredSize(new Dimension(155, 500));
		tueScroller.getVerticalScrollBar().setUnitIncrement(15);
		tueAppointment.setBackground(Color.WHITE);
		tueAppointment.setBorder(BorderFactory.createLineBorder(Color.black));
		tueAppointment.setLayout(new BoxLayout(tueAppointment, BoxLayout.Y_AXIS));

		wedAppointment = new JPanel();
		JScrollPane wedScroller = new JScrollPane(wedAppointment);
		wedScroller.setFocusable(true);
		wedScroller.setPreferredSize(new Dimension(155, 500));
		wedScroller.getVerticalScrollBar().setUnitIncrement(15);
		wedAppointment.setBackground(Color.WHITE);
		wedAppointment.setBorder(BorderFactory.createLineBorder(Color.black));
		wedAppointment.setLayout(new BoxLayout(wedAppointment, BoxLayout.Y_AXIS));

		thuAppointment = new JPanel();
		JScrollPane thuScroller = new JScrollPane(thuAppointment);
		thuScroller.setFocusable(true);
		thuScroller.setPreferredSize(new Dimension(155, 500));
		thuScroller.getVerticalScrollBar().setUnitIncrement(15);
		thuAppointment.setBackground(Color.WHITE);
		thuAppointment.setBorder(BorderFactory.createLineBorder(Color.black));
		thuAppointment.setLayout(new BoxLayout(thuAppointment, BoxLayout.Y_AXIS));

		friAppointment = new JPanel();
		JScrollPane friScroller = new JScrollPane(friAppointment);
		friScroller.setFocusable(true);
		friScroller.setPreferredSize(new Dimension(155, 500));
		friScroller.getVerticalScrollBar().setUnitIncrement(15);
		friAppointment.setBackground(Color.WHITE);
		friAppointment.setBorder(BorderFactory.createLineBorder(Color.black));
		friAppointment.setLayout(new BoxLayout(friAppointment, BoxLayout.Y_AXIS));

		satAppointment = new JPanel();
		JScrollPane satScroller = new JScrollPane(satAppointment);
		satScroller.setFocusable(true);
		satScroller.setPreferredSize(new Dimension(155, 500));
		satScroller.getVerticalScrollBar().setUnitIncrement(15);
		satAppointment.setBackground(Color.WHITE);
		satAppointment.setBorder(BorderFactory.createLineBorder(Color.black));
		satAppointment.setLayout(new BoxLayout(satAppointment, BoxLayout.Y_AXIS));

		sunAppointment = new JPanel();
		JScrollPane sunScroller = new JScrollPane(sunAppointment);
		sunScroller.setFocusable(true);
		sunScroller.setPreferredSize(new Dimension(155, 500));
		sunScroller.getVerticalScrollBar().setUnitIncrement(15);
		sunAppointment.setBackground(Color.WHITE);
		sunAppointment.setBorder(BorderFactory.createLineBorder(Color.black));
		sunAppointment.setLayout(new BoxLayout(sunAppointment, BoxLayout.Y_AXIS));

		// build a gridbag
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5, 5, 5, 5);
		contents.insets = new Insets(0, 0, 0, 0);

		JPanel arrowPanel = new JPanel();
		arrowPanel.add(leftArrowButton);
		arrowPanel.add(weekLabel);
		arrowPanel.add(rightArrowButton);
		add(arrowPanel, c);
		

		c.gridy = 1;
		add(showCalendarsFor, c);
		c.gridy = 2;
		add(userCalendarsScroller, c);
		c.gridy = 3;
		add(users, c);
		c.gridy = 4;
		JPanel addRemove = new JPanel();
		addRemove.add(addButton);
		addRemove.add(removeButton);
		add(addRemove, c);
		c.gridy = 5;
		JLabel spaceLabel = new JLabel();
		add(spaceLabel, c);
		c.gridy = 6;
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

		//main calendar contents
		contents.gridx = 1;
		contents.gridy = 1;
		contents.gridheight = 7;
		add(monScroller, contents);
		contents.gridx = 2;
		add(tueScroller, contents);
		contents.gridx = 3;
		add(wedScroller, contents);
		contents.gridx = 4;
		add(thuScroller, contents);
		contents.gridx = 5;
		add(friScroller, contents);
		contents.gridx = 6;
		add(satScroller, contents);
		contents.gridx = 7;
		add(sunScroller, contents);


		frame = new JFrame("Calendar view");
		frame.add(this);
		frame.pack();
		frame.setVisible(false);
		frame.setResizable(true);

		//test code
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		for(int i = 0; i < 4; i++) {
//			AppointmentSummaryView asv = new AppointmentSummaryView();
//			/*
//			 * TODO add MouseListener : asv.addListener(Calendarcontroller cc) || asv.addListener(client.getCalendarController)
//			 */
//			monAppointment.add(asv);
//		}
//		for(int i = 0; i < 5; i++) {
//			AppointmentSummaryView asv = new AppointmentSummaryView();
//			/*
//			 * TODO add MouseListener : asv.addListener(Calendarcontroller cc) || asv.addListener(client.getCalendarController)
//			 */
//			tueAppointment.add(asv);
//		}
//		wedAppointment.add(new AppointmentSummaryView());
//		wedAppointment.add(new AppointmentSummaryView());
//		thuAppointment.add(new AppointmentSummaryView());
		
		weekLabel.setText("Week 10");
		monLabel.setText("Mon 3.3");
		tueLabel.setText("Tue 4.3");
		wedLabel.setText("Wed 5.3");
		thuLabel.setText("Thu 6.3");
		friLabel.setText("Fri 7.3");
		satLabel.setText("Sat 8.3");
		sunLabel.setText("Sun 9.3");
		//end test code


		
	}

	public void addListener(EventListener controller) {
		this.addActionListener((ActionListener) controller);
	}

	public void addActionListener(ActionListener controller) {
		addButton.addActionListener(controller);
		removeButton.addActionListener(controller);
		newAppointmentButton.addActionListener(controller);
		logOutButton.addActionListener(controller);
		leftArrowButton.addActionListener(controller);
		rightArrowButton.addActionListener(controller);
		users.addActionListener(controller);
	}

	public void addListSelectionListener(ListSelectionListener controller) {
		userCalendars.addListSelectionListener(controller);
	}

	public void setVisible(boolean visible) {
		this.frame.setVisible(visible);
	}

	public void propertyChange(PropertyChangeEvent pce) {
		updateView();
	}
	
	public void updateView(){
		java.util.List<java.util.List<Appointment>> appointments = model.getAppointments();
		String[] labels = model.getDays();
		int weekNumber = model.getWeek();

		weekLabel.setText("Week " + Integer.toString(weekNumber));
		monLabel.setText(labels[0]);
		tueLabel.setText(labels[1]);
		wedLabel.setText(labels[2]);
		thuLabel.setText(labels[3]);
		friLabel.setText(labels[4]);
		satLabel.setText(labels[5]);
		sunLabel.setText(labels[6]);

		monAppointment.removeAll();
		tueAppointment.removeAll();
		wedAppointment.removeAll();
		thuAppointment.removeAll();
		friAppointment.removeAll();
		satAppointment.removeAll();
		sunAppointment.removeAll();

		for(Appointment appointment : appointments.get(0)) {
			monAppointment.add(new AppointmentSummaryView(appointment));
		}

		for(Appointment appointment : appointments.get(1)) {
			tueAppointment.add(new AppointmentSummaryView(appointment));
		}

		for(Appointment appointment : appointments.get(2)) {
			wedAppointment.add(new AppointmentSummaryView(appointment));
		}

		for(Appointment appointment : appointments.get(3)) {
			thuAppointment.add(new AppointmentSummaryView(appointment));
		}

		for(Appointment appointment : appointments.get(4)) {
			friAppointment.add(new AppointmentSummaryView(appointment));
		}

		for(Appointment appointment : appointments.get(5)) {
			satAppointment.add(new AppointmentSummaryView(appointment));
		}

		for(Appointment appointment : appointments.get(6)) {
			sunAppointment.add(new AppointmentSummaryView(appointment));
		}
	}
	
	public void setModel (Calendar calendar){
		this.model = calendar;
		updateView();
	}

	public static void main(String[] args) {
		CalendarView view = new CalendarView();
		view.setVisible(true);
	}

}
