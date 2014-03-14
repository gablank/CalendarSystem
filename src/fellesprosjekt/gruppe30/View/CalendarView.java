package fellesprosjekt.gruppe30.View;


import fellesprosjekt.gruppe30.Model.Appointment;
import fellesprosjekt.gruppe30.Model.Calendar;
import fellesprosjekt.gruppe30.Model.InternalUser;
import fellesprosjekt.gruppe30.Model.PersonListModel;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventListener;

public class CalendarView extends JPanel implements PropertyChangeListener {
	private JButton             addButton, removeButton, newAppointmentButton, logOutButton, leftArrowButton, rightArrowButton;
	private JComboBox 	        users;
	private JLabel    	        weekLabel, showCalendarsFor;
	private JLabel[]            dayLabels = new JLabel[7];
	private JList<InternalUser> userCalendars;
	private JPanel[]            appointments = new JPanel[7];
	private JFrame      		frame;
	private Calendar      		model;
	private JScrollPane 		userCalendarsScroller;
	private PersonListModel     personListModel;
	JScrollPane[] 				scrollers = new JScrollPane[7];

	public CalendarView() {
		GridBagConstraints c = new GridBagConstraints();
		GridBagConstraints contents = new GridBagConstraints();
		setLayout(new GridBagLayout());

		//set appearance of all buttons
		addButton = new JButton("Add");
		addButton.setName("add_calendar");
		addButton.setPreferredSize(new Dimension(60, 30));

		removeButton = new JButton("Remove");
		removeButton.setName("remove_calendar");
		removeButton.setPreferredSize(new Dimension(80, 30));

		newAppointmentButton = new JButton("New Appointment");
		newAppointmentButton.setName("new_appointment");
		newAppointmentButton.setPreferredSize(new Dimension(145, 30));

		logOutButton = new JButton("Log out");
		logOutButton.setName("log_out");
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


		for(int i = 0; i < 7; i++) {
			dayLabels[i] = new JLabel();
			appointments[i] = new JPanel();

			scrollers[i] = new JScrollPane(appointments[i]);
			scrollers[i].setFocusable(true);
			scrollers[i].setPreferredSize(new Dimension(155, 500));
			scrollers[i].getVerticalScrollBar().setUnitIncrement(15);
			scrollers[i].getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
			appointments[i].setBackground(Color.WHITE);
			appointments[i].setBorder(BorderFactory.createLineBorder(Color.black));
			appointments[i].setLayout(new BoxLayout(appointments[i], BoxLayout.Y_AXIS));
		}

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

		// Add labels
		for(int i = 0; i < 7; i++) {
			add(dayLabels[i], c);
			c.gridx++;
		}

		//main calendar contents
		contents.gridx = 1;
		contents.gridy = 1;
		contents.gridheight = 7;

		for(int i = 0; i < 7; i++) {
			add(scrollers[i], contents);
			contents.gridx++;
		}
		
		personListModel = new PersonListModel();
		this.setPersonListModel(personListModel);
		//test code
		personListModel.addElement("Dude dudeson");
		//end test code

		frame = new JFrame("Calendar view");
		frame.add(this);
		frame.pack();
		frame.setVisible(false);
		frame.setResizable(true);
		
	}
	
	public void setPersonListModel(PersonListModel model) {
		this.personListModel = model;
		userCalendars.setModel(model);
	}

	public void addListener(EventListener controller) {
		this.addActionListener((ActionListener) controller);
		this.frame.addWindowListener((WindowListener) controller);
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

	public void setInternalUsers(java.util.List<InternalUser> internalUsers) {
		users.removeAllItems();
		for(InternalUser user : internalUsers) {
			this.addInternalUser(user);
		}
	}

	public void addInternalUser(InternalUser user) {
		users.addItem(user);
	}

	public void removeInternalUser(InternalUser user) {
		users.removeItem(user.getEmail());
	}
	
	public void updateView(){
		java.util.List<java.util.List<Appointment>> showAppointments = model.getAppointments();
		String[] labels = model.getDays();
		int weekNumber = model.getWeek();

		java.util.List<InternalUser> showCalendarsFor = model.getUsers();
		PersonListModel personListModel = new PersonListModel();
		for(InternalUser user : showCalendarsFor) {
			personListModel.addElement(user);
		}
		this.setPersonListModel(personListModel);

		java.util.List<InternalUser> allInternalUsers = model.getInternalUsers();
		this.setInternalUsers(allInternalUsers);

		weekLabel.setText("Week " + Integer.toString(weekNumber));
		for(int i = 0; i < 7; i++) {
			dayLabels[i].setText(labels[i]);
			this.appointments[i].removeAll();
			this.appointments[i].repaint();

			for(Appointment appointment : showAppointments.get(i)) {
				this.appointments[i].add(new AppointmentSummaryView(appointment, showCalendarsFor));
			}
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

	public InternalUser getSelectedUser() {
		return (InternalUser) users.getSelectedItem();
	}

	public InternalUser getSelectedDropDownUser() {
		return userCalendars.getSelectedValue();
	}
}