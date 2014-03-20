package fellesprosjekt.gruppe30.View;

import fellesprosjekt.gruppe30.Controller.AppointmentController;
import fellesprosjekt.gruppe30.Model.*;
import fellesprosjekt.gruppe30.Utilities;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.MaskFormatter;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.util.*;
import java.util.List;

public class AppointmentView extends JPanel implements ActionListener, PropertyChangeListener, ListSelectionListener, FocusListener {
	protected PersonRenderer		listRenderer;
	public PersonListModel			personListModel;
	protected JTextField			titleField, meetingPlaceField, emailField;
	protected JTextArea				description;
	protected JFormattedTextField	dateField, startTimeField, endTimeField, alarmTimeField;
	protected JCheckBox				useMeetingRoom, hideFromCalendar, setAlarm, inviteByEmail;
	protected JComboBox<Object>		allUsersAndGroups;
	protected JList<Attendant>		participants;
	protected JButton				addButton, removeButton, saveButton, deleteButton, cancelButton, selectRoom;
	protected JScrollPane			participantScroller, descriptionScroller;
	protected JLabel				participantLabel, dateLabel, startTimeLabel, endTimeLabel, alarmLabel;
	protected JFrame				frame;
	protected Appointment			appointmentModel;
	protected Attendant				attendantModel;
	protected Alarm					alarmModel;


	public AppointmentView() {
		GridBagConstraints cLeft = new GridBagConstraints();
		GridBagConstraints cRight = new GridBagConstraints();
		setLayout(new GridBagLayout());

		//set appearance of all buttons
		titleField = new JTextField("Title", 13);
		titleField.addFocusListener(this);
		titleField.setName("title");


		description = new JTextArea("Description", 5, 13);
		description.setBorder(titleField.getBorder());
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		description.addFocusListener(this);
		description.setName("description");
		descriptionScroller = new JScrollPane(description);


		meetingPlaceField = new JTextField("Place", 10);
		meetingPlaceField.addFocusListener(this);
		meetingPlaceField.setName("meeting_place");
		emailField = new JTextField("Email", 10);
		emailField.addFocusListener(this);


		selectRoom = new JButton("Select...");
		selectRoom.setName("selectRoom");
		selectRoom.setPreferredSize(new Dimension(100, 20));
		selectRoom.setVisible(false);


		MaskFormatter dateFormatter;
		try {
			dateFormatter = new MaskFormatter("##.##.####");
			dateField = new JFormattedTextField(dateFormatter);
			dateField.setPreferredSize(new Dimension(80, 20));
			dateField.setHorizontalAlignment(JFormattedTextField.CENTER);
			dateField.setName("app_date_text");
		} catch(ParseException e) {
			e.printStackTrace();
		}

		MaskFormatter timeFormatter;
		try {
			timeFormatter = new MaskFormatter("##:##");
			startTimeField = new JFormattedTextField(timeFormatter);
			startTimeField.setPreferredSize(new Dimension(50, 20));
			startTimeField.setHorizontalAlignment(SwingConstants.CENTER);
			startTimeField.setName("app_start_text");
			endTimeField = new JFormattedTextField(timeFormatter);
			endTimeField.setPreferredSize(new Dimension(50, 20));
			endTimeField.setHorizontalAlignment(SwingConstants.CENTER);
			endTimeField.setName("app_end_text");
			alarmTimeField = new JFormattedTextField(timeFormatter);
			alarmTimeField.setName("alarmTimeField");
			alarmTimeField.setPreferredSize(new Dimension(40, 20));
			alarmTimeField.setHorizontalAlignment(SwingConstants.CENTER);

		} catch(ParseException e) {
			e.printStackTrace();
		}


		useMeetingRoom = new JCheckBox("Use meeting room");
		useMeetingRoom.setName("useMeetingRoom");
		hideFromCalendar = new JCheckBox("Hide from calendar");
		hideFromCalendar.setName("hideFromCalendar");
		setAlarm = new JCheckBox("Alarm");
		setAlarm.setName("setAlarm");
		inviteByEmail = new JCheckBox("Invite by email");
		inviteByEmail.setName("inviteByEmail");
		allUsersAndGroups = new JComboBox<>();
		allUsersAndGroups.setPreferredSize(new Dimension(30, 20));
		allUsersAndGroups.setName("allUsersAndGroups");
		participants = new JList<Attendant>();
		listRenderer = PersonRenderer.getInstance();
		participants.setCellRenderer(listRenderer);

		participantScroller = new JScrollPane(participants);
		participantScroller.setFocusable(true);
		participantScroller.setPreferredSize(new Dimension(150, 150));
		participantLabel = new JLabel("Participants");
		participantLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		addButton = new JButton("Add        ");
		addButton.setName("inviteUser");
		addButton.setPreferredSize(new Dimension(80, 25));

		removeButton = new JButton("Remove");
		removeButton.setName("removeUser");
		removeButton.setPreferredSize(new Dimension(80, 25));

		saveButton = new JButton("Save");
		saveButton.setName("save");
		deleteButton = new JButton("Delete");
		deleteButton.setName("delete");
		cancelButton = new JButton("Cancel");
		cancelButton.setName("cancel");


		//Build a gridbag
		cLeft.gridx = 0;
		cLeft.gridy = 0;
		cLeft.insets = new Insets(5, 5, 5, 5);

		add(titleField, cLeft);
		cLeft.gridy = 1;

		add(descriptionScroller, cLeft);
		cLeft.gridy = 2;

		JPanel datePanel = new JPanel();
		dateLabel = new JLabel("DD.MM.YYYY");
		datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.Y_AXIS));
		datePanel.add(dateLabel);
		datePanel.add(dateField);
		add(datePanel, cLeft);
		cLeft.gridy = 3;

		JPanel fromPanel = new JPanel();
		fromPanel.setLayout(new BoxLayout(fromPanel, BoxLayout.Y_AXIS));
		startTimeLabel = new JLabel("From");
		fromPanel.add(startTimeLabel);
		fromPanel.add(startTimeField);

		JPanel toPanel = new JPanel();
		toPanel.setLayout(new BoxLayout(toPanel, BoxLayout.Y_AXIS));
		endTimeLabel = new JLabel("To");
		toPanel.add(endTimeLabel);
		toPanel.add(endTimeField);

		JPanel timePanel = new JPanel();
		timePanel.add(fromPanel);
		timePanel.add(toPanel);
		add(timePanel, cLeft);
		cLeft.gridy = 4;


		add(useMeetingRoom, cLeft);
		cLeft.gridy = 5;
		add(meetingPlaceField, cLeft);
		meetingPlaceField.setVisible(true);
		add(selectRoom, cLeft);

		cRight.gridx = 2;
		cRight.gridy = 0;
		cRight.insets = new Insets(5, 50, 5, 5);

		JPanel alarmPanel = new JPanel();
		alarmPanel.add(setAlarm);
		alarmPanel.add(alarmTimeField);
		alarmLabel = new JLabel("before meeting");
		alarmPanel.add(alarmLabel);
		JPanel calendarAndAlarm = new JPanel();
		calendarAndAlarm.setLayout(new BoxLayout(calendarAndAlarm, BoxLayout.Y_AXIS));
		alarmPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		calendarAndAlarm.add(hideFromCalendar);
		calendarAndAlarm.add(alarmPanel);
		add(calendarAndAlarm, cRight);
		cRight.gridy = 1;

		cRight.gridheight = 3;
		JPanel participantPanel = new JPanel();
		participantPanel.setLayout(new BoxLayout(participantPanel, BoxLayout.Y_AXIS));
		participantPanel.add(participantLabel);
		participantPanel.add(participantScroller);
		add(participantPanel, cRight);
		cRight.gridy = 4;

		JPanel addRemove = new JPanel();
		addRemove.setLayout(new BoxLayout(addRemove, BoxLayout.Y_AXIS));
		addRemove.add(addButton);
		addRemove.add(removeButton);
		JPanel userOrEmail = new JPanel();
		userOrEmail.setLayout(new BoxLayout(userOrEmail, BoxLayout.Y_AXIS));
		allUsersAndGroups.setPreferredSize(new Dimension(120, 20));
		emailField.setPreferredSize(new Dimension(200, 20));
		userOrEmail.add(allUsersAndGroups, 0);
		userOrEmail.add(emailField, 0);
		emailField.setVisible(false);
		JPanel personAddRemove = new JPanel();
		personAddRemove.add(userOrEmail);
		personAddRemove.add(addRemove);
		
		add(inviteByEmail, cRight);
		cRight.gridy = 5;
		add(personAddRemove, cRight);
		cRight.gridy = 8;

		JPanel saveDelete = new JPanel();
		saveDelete.add(saveButton);
		saveDelete.add(deleteButton);
		saveDelete.add(cancelButton);
		add(saveDelete, cRight);

		frame = new JFrame("Appointment view");
		frame.add(this);
		frame.pack();
		frame.setVisible(false);
		frame.setResizable(false);

		useMeetingRoom.addActionListener(this);
		inviteByEmail.addActionListener(this);

		personListModel = new PersonListModel();
		this.setPersonListModel(personListModel);
		
		/*//test code
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		InternalUser user = new InternalUser("email", "Bjarne", "Fjarne");
		InternalUser user2 = new InternalUser("email", "Knut", "Grut");
		InternalUser user3 = new InternalUser ("email", "Stefan", "Trefan");
		InternalAttendant attendant = new InternalAttendant(user, new Appointment(user));
		InternalAttendant attendant2 = new InternalAttendant(user2, new Appointment(user2));
		InternalAttendant attendant3 = new InternalAttendant(user3, new Appointment(user3));
		personListModel.addElement(attendant);
		personListModel.addElement(attendant2);
		personListModel.addElement(attendant3);
		for(int i = 0; i < 10; i++) {
		}
		//end test code*/
	}

	//add listeners to all components
	public void addListener(EventListener controller) {
		this.addActionListener((ActionListener) controller);
		this.addKeyListener((KeyListener) controller);
		this.addListSelectionListener((ListSelectionListener) controller);
		participants.addMouseListener((MouseListener) controller);
	}

	public void addActionListener(ActionListener controller) {
		selectRoom.addActionListener(controller);
		addButton.addActionListener(controller);
		removeButton.addActionListener(controller);
		saveButton.addActionListener(controller);
		deleteButton.addActionListener(controller);
		cancelButton.addActionListener(controller);
		allUsersAndGroups.addActionListener(controller);
		hideFromCalendar.addActionListener(controller);
		setAlarm.addActionListener(controller);
		useMeetingRoom.addActionListener(controller);
		inviteByEmail.addActionListener(controller);
	}

	public void addKeyListener(KeyListener controller) {
		titleField.addKeyListener(controller);
		description.addKeyListener(controller);
		meetingPlaceField.addKeyListener(controller);
		dateField.addKeyListener(controller);
		startTimeField.addKeyListener(controller);
		endTimeField.addKeyListener(controller);
		alarmTimeField.addKeyListener(controller);
	}

	public void addListSelectionListener(ListSelectionListener controller) {
		participants.addListSelectionListener(controller);
	}


	@Override
	//change room select field into button and vice versa
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == useMeetingRoom) {
			if(useMeetingRoom.isSelected()) {
				meetingPlaceField.setVisible(false);
				selectRoom.setVisible(true);
				this.repaint();
			} else {
				selectRoom.setVisible(false);
				meetingPlaceField.setVisible(true);
				this.repaint();
			}
		} else if(e.getSource() == inviteByEmail) {
			if(inviteByEmail.isSelected()) {
				allUsersAndGroups.setVisible(false);
				emailField.setVisible(true);
			} else {
				emailField.setVisible(false);
				allUsersAndGroups.setVisible(true);
			}

		} else if (e.getSource() == setAlarm) {
			if (setAlarm.isSelected()) {
				alarmModel.setSet(true);
				alarmTimeField.setValue(getAlarmTimeDiff());

			} else {
				alarmModel.setSet(false);

			}
		}

	}
	
	@Override
	public void focusGained(FocusEvent e) {
		if(e.getSource() == titleField) {
			if(titleField.getText().equals("Title")) {
				titleField.setText("");
			}
		} else if(e.getSource() == description) {
			if(description.getText().equals("Description")) {
				description.setText("");
			}
		} else if(e.getSource() == meetingPlaceField) {
			if(meetingPlaceField.getText().equals("Place")) {
				meetingPlaceField.setText("");
			}
		} else if(e.getSource() == emailField) {
			if(emailField.getText().equals("Email")) {
				emailField.setText("");
			}
		}
	}
	@Override
	public void focusLost(FocusEvent e) {
		if(e.getSource() == titleField) {
			if(titleField.getText().equals("")) {
				titleField.setText("Title");
			}
		} else if(e.getSource() == description) {
			if(description.getText().equals("")) {
				description.setText("Description");
			}
		} else if(e.getSource() == meetingPlaceField) {
			if(meetingPlaceField.getText().equals("")) {
				meetingPlaceField.setText("Place");
			}
		} else if(e.getSource() == emailField) {
			if(emailField.getText().equals("")) {
				emailField.setText("Email");
			}
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {

	}

	public void setVisible(boolean visible) {
		this.frame.setVisible(visible);
	}


	public void setModel(Appointment appointment, InternalUser owner, Alarm alarm) {
		if(appointmentModel != null) {
			appointmentModel.removeListener(this);
		}
		
		if (appointment == null) {
			this.appointmentModel = new Appointment(owner);
			System.out.println("new!");
		} else {
			this.appointmentModel = appointment.myClone();
			System.out.println("clone!");
		}
		this.appointmentModel.addListener(this);

		this.attendantModel = new InternalAttendant(owner, appointmentModel);

		if (alarm == null) {
			Date alarmDate = new Date(this.appointmentModel.getStart().getTime() + 1000 * 60 * 30);
			this.alarmModel = new Alarm(owner, appointment, alarmDate);
			System.out.println("new!");
		} else {
			this.alarmModel = alarm.myClone();
			System.out.println("clone!");
		}

		updateFields();
	}

	public void setInternalUsersAndGroups(List<InternalUser> internalUsers, List<Group> groups) {
		allUsersAndGroups.removeAllItems();

		for(InternalUser user : internalUsers) {
			allUsersAndGroups.addItem(user);
		}

		for(Group group : groups) {
			allUsersAndGroups.addItem(group);
		}
	}

	protected void updateFields() {
		titleField.setText(appointmentModel.getTitle());
		description.setText(appointmentModel.getDescription());
		dateField.setValue(Utilities.dateToFormattedString(appointmentModel.getStart()));
		startTimeField.setValue(Utilities.timeToFormattedString(appointmentModel.getStart()));
		endTimeField.setValue(Utilities.timeToFormattedString(appointmentModel.getEnd()));

		if (appointmentModel.getMeetingRoom() != null) {
			useMeetingRoom.setSelected(true);
			selectRoom.setText("Room # " + Integer.toString(appointmentModel.getMeetingRoom().getId()));

		} else if (!meetingPlaceField.getText().isEmpty()) {
			useMeetingRoom.setSelected(false);
			meetingPlaceField.setText(appointmentModel.getMeetingPlace());
			meetingPlaceField.setVisible(true);
			selectRoom.setVisible(false);
		} else {
			// neither is set, do nothing.
		}


		if(attendantModel != null) {
			if(((InternalAttendant) attendantModel).getVisibleOnCalendar()) {
				hideFromCalendar.setSelected(false);
			} else {
				hideFromCalendar.setSelected(true);
			}
		}

		if(alarmModel != null) {
			setAlarm.setEnabled(true);
			alarmTimeField.setEnabled(true);

			if (alarmModel.isSet()) {
				setAlarm.setSelected(true);
				alarmTimeField.setValue(getAlarmTimeDiff());
			} else {
				setAlarm.setSelected(false);
				alarmTimeField.setValue("00:30");
			}

		}else{
			setAlarm.setEnabled(false);
			setAlarm.setSelected(false);
			alarmTimeField.setEnabled(false);
		}

		PersonListModel personListModel = new PersonListModel();
		List<Attendant> invited = appointmentModel.getAttendants();
		for(Attendant attendant : invited) {
			personListModel.addElement(attendant);
		}
		setPersonListModel(personListModel);
	}

	public void setPersonListModel(PersonListModel model) {
		this.personListModel = model;
		participants.setModel(model);
	}

	@Override
	public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
		this.updateFields();
	}

	public Appointment getAppointmentModel() {
		return appointmentModel;
	}
	
	public boolean getAlarmIsSelected() {
		return setAlarm.isSelected();
	}
	
	public int getAlarmInMinutes() {
		try {
			if (setAlarm.isSelected()) {
				String alarmTime[] = alarmTimeField.getText().split(":");

				int hour = Integer.parseInt(alarmTime[0]);
				int minutes = Integer.parseInt(alarmTime[1]);
				return hour * 60 + minutes;
			}
		} catch (Exception e) {
			// silently disregard exceptions from parseInt
		}
		return -1;
	}

	public String getAlarmTimeDiff() {
		if(appointmentModel.getStart() == null || alarmModel.getDate() == null) {
			return "00:30"; // Default
		}
		int startMinutes = 0;
		int alarmMinutes = 0;
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(appointmentModel.getStart());
		startMinutes += cal.get(java.util.Calendar.HOUR_OF_DAY) * 60;
		startMinutes += cal.get(java.util.Calendar.MINUTE);

		cal.setTime(alarmModel.getDate());
		alarmMinutes += cal.get(java.util.Calendar.HOUR_OF_DAY) * 60;
		alarmMinutes += cal.get(java.util.Calendar.MINUTE);

		int diff = alarmMinutes - startMinutes;
		if(diff < 0) {
			diff += 24 * 60;
		}
		int minutes = diff % 60;
		int hours = (diff - minutes) / 60;
		String hoursAsString = Integer.toString(hours);
		while(hoursAsString.length() < 2) {
			hoursAsString += "0";
		}
		String minutesAsString = Integer.toString(minutes);
		while(minutesAsString.length() < 2) {
			minutesAsString += "0";
		}

		return  hoursAsString + ":" + minutesAsString;
	}

	public List<User> getSelectedUsers() {
		List<User> users = new ArrayList<User>();
		if(inviteByEmail.isSelected()) {
			ExternalUser user = new ExternalUser(emailField.getText());
			users.add(user);
		} else {
			Object selected = allUsersAndGroups.getSelectedItem();
			if(selected instanceof Group) {
				for(User member : ((Group) selected).getMembers()) {
					users.add(member);
				}
			} else {
				users.add((User) selected);
			}
		}
		return users;
	}

	public Attendant getSelectedAttendant() {
		return participants.getSelectedValue();
	}

	// public static void main(String[] args) {
	// AppointmentView view = new AppointmentView();
	// view.setVisible(true);
	// //test code
	// view.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	// InternalUser user = new InternalUser("email", "Bjarne", "Fjarne");
	// InternalUser user2 = new InternalUser("email", "Knut", "Grut");
	// InternalUser user3 = new InternalUser ("email", "Stefan", "Trefan");
	// InternalAttendant attendant = new InternalAttendant(user, new
	// Appointment(user));
	// InternalAttendant attendant2 = new InternalAttendant(user2, new
	// Appointment(user2));
	// InternalAttendant attendant3 = new InternalAttendant(user3, new
	// Appointment(user3));
	// view.personListModel.addElement(attendant);
	// view.personListModel.addElement(attendant2);
	// view.personListModel.addElement(attendant3);
	// for(int i = 0; i < 10; i++) {
	// }
	// //end test code
	// }

	public JFormattedTextField getDateText() {
		return dateField;
	}

	public JFormattedTextField getStartText() {
		return startTimeField;
	}

	public JFormattedTextField getEndText() {
		return endTimeField;
	}

	public String getMeetingPlaceText() {
		return meetingPlaceField.getText();
	}
	
	public String getTitleText() {
		return titleField.getText();
	}
	
	public String getDescriptionText() {
		return description.getText();
	}

	public boolean useMeetingRoomIsChecked() {
		return useMeetingRoom.isSelected();
	}

	public void setComponentsToDefault() {
		titleField.setText("Title");
		description.setText("Description");

		dateField.setValue("03.07.2014");
		startTimeField.setValue("08:40");
		endTimeField.setValue("10:45");

		useMeetingRoom.setSelected(true);
		meetingPlaceField.setVisible(false);
		selectRoom.setVisible(true);
		selectRoom.setText("Select...");

		setAlarm.setSelected(false);
		alarmTimeField.setValue("00:30");
		hideFromCalendar.setSelected(false);

		inviteByEmail.setSelected(false);
		emailField.setText("Email");
		emailField.setVisible(false);
		allUsersAndGroups.setVisible(true);


	}

	public Alarm getAlarmModel() {
		return this.alarmModel;
	}
}
