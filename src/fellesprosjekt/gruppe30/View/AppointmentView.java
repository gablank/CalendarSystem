package fellesprosjekt.gruppe30.View;

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
import java.util.Calendar;
import java.util.List;

public class AppointmentView extends JPanel implements ActionListener, PropertyChangeListener, ListSelectionListener, FocusListener {
	protected PersonRenderer      listRenderer;
	protected PersonListModel     personListModel;
	protected JTextField          titleField, meetingPlaceField, emailField;
	protected JTextArea           description;
	protected JFormattedTextField dateField, startTimeField, endTimeField, alarmTimeField;
	protected JCheckBox           useMeetingRoom, hideFromCalendar, setAlarm, inviteByEmail;
	protected JComboBox<InternalUser>     participantList;
	protected JList<Attendant>         participants;
	protected JButton             addButton, removeButton, saveButton, deleteButton, cancelButton, selectRoom;
	protected JScrollPane         participantScroller, descriptionScroller;
	protected JLabel              participantLabel, dateLabel, startTimeLabel, endTimeLabel, alarmLabel;
	protected JFrame              frame;
	protected Appointment         appointmentModel;
    protected Attendant           attendantModel;
	protected Alarm               alarmModel;


	public AppointmentView() {
		GridBagConstraints cLeft = new GridBagConstraints();
		GridBagConstraints cRight = new GridBagConstraints();
		setLayout(new GridBagLayout());

		//set appearance of all buttons
		titleField = new JTextField("Title", 13);
		titleField.addFocusListener(this);


		description = new JTextArea("Description", 5, 13);
		description.setBorder(titleField.getBorder());
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		description.addFocusListener(this);
		descriptionScroller = new JScrollPane(description);


		meetingPlaceField = new JTextField("Place", 10);
		meetingPlaceField.addFocusListener(this);
		emailField = new JTextField("Email", 1);
		emailField.addFocusListener(this);


		selectRoom = new JButton("Select...");
		selectRoom.setPreferredSize(new Dimension(100, 25));


		MaskFormatter dateFormatter;
		try {
			dateFormatter = new MaskFormatter("##.##.####");
			dateField = new JFormattedTextField(dateFormatter);
			dateField.setPreferredSize(new Dimension(80, 20));
			dateField.setValue("03.07.2014");
			dateField.addFocusListener(this);
			dateField.setHorizontalAlignment(JFormattedTextField.CENTER);
		} catch(ParseException e) {
			e.printStackTrace();
		}

		MaskFormatter timeFormatter;
		try {
			timeFormatter = new MaskFormatter("##:##");
			startTimeField = new JFormattedTextField(timeFormatter);
			startTimeField.setPreferredSize(new Dimension(50, 20));
			startTimeField.setValue("08:40");
			startTimeField.setHorizontalAlignment(SwingConstants.CENTER);
			startTimeField.addFocusListener(this);
			endTimeField = new JFormattedTextField(timeFormatter);
			endTimeField.setPreferredSize(new Dimension(50, 20));
			endTimeField.setValue("10:40");
			endTimeField.setHorizontalAlignment(SwingConstants.CENTER);
			endTimeField.addFocusListener(this);
			alarmTimeField = new JFormattedTextField(timeFormatter);
			alarmTimeField.setPreferredSize(new Dimension(40, 20));
			alarmTimeField.setValue("00:30");
			alarmTimeField.setHorizontalAlignment(SwingConstants.CENTER);
			alarmTimeField.addFocusListener(this);

		} catch(ParseException e) {
			e.printStackTrace();
		}


		useMeetingRoom = new JCheckBox("Use meeting room");
		useMeetingRoom.setSelected(true);
		hideFromCalendar = new JCheckBox("Hide from calendar");
		setAlarm = new JCheckBox("Alarm");
		inviteByEmail = new JCheckBox("Invite by email");

		participantList = new JComboBox<InternalUser>();
		participantList.setPreferredSize(new Dimension(40, 25));
		participants = new JList<Attendant>();
		listRenderer = new PersonRenderer();
		participants.setCellRenderer(listRenderer);
		participants.addListSelectionListener(this);

		participantScroller = new JScrollPane(participants);
		participantScroller.setFocusable(true);
		participantScroller.setPreferredSize(new Dimension(150, 150));
		participantLabel = new JLabel("Participants");
		participantLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		addButton = new JButton("Add        ");
		addButton.setPreferredSize(new Dimension(80, 25));

		removeButton = new JButton("Remove");
		removeButton.setPreferredSize(new Dimension(80, 25));

		saveButton = new JButton("Save");
		deleteButton = new JButton("Delete");
		cancelButton = new JButton("Cancel");


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
		meetingPlaceField.setVisible(false);
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
		userOrEmail.add(inviteByEmail);
		userOrEmail.add(participantList, 1);
		userOrEmail.add(emailField, 1);
		emailField.setVisible(false);
		JPanel personAddRemove = new JPanel();
		personAddRemove.add(userOrEmail);
		personAddRemove.add(addRemove);
		add(personAddRemove, cRight);

		cRight.gridy = 8;

		JPanel saveDelete = new JPanel();
		saveDelete.add(saveButton);
		saveDelete.add(deleteButton);
		saveDelete.add(cancelButton);
		add(saveDelete, cRight);

		listRenderer = new PersonRenderer();
		participants.setCellRenderer(listRenderer);

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
	}

	public void addActionListener(ActionListener controller) {
		selectRoom.addActionListener(controller);
		addButton.addActionListener(controller);
		removeButton.addActionListener(controller);
		saveButton.addActionListener(controller);
		deleteButton.addActionListener(controller);
		cancelButton.addActionListener(controller);
		participantList.addActionListener(controller);
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
				participantList.setVisible(false);
				emailField.setVisible(true);
			} else {
				emailField.setVisible(false);
				participantList.setVisible(true);
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
		} else if(e.getSource() == dateField) {
			if(dateField.getText().equals("03.07.2014")) {
				dateField.setText("");
			}
		} else if(e.getSource() == startTimeField) {
			if(startTimeField.getText().equals("08:40")) {
				startTimeField.setText(":");
			}
		} else if(e.getSource() == endTimeField) {
			if(endTimeField.getText().equals("10:40")) {
				endTimeField.setText(":");
			}
		} else if(e.getSource() == alarmTimeField) {
			if(alarmTimeField.getText().equals("00:30")) {
				alarmTimeField.setText("");
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
		Attendant attendant = (Attendant) participants.getSelectedValue();
	}

	public void setVisible(boolean visible) {
		this.frame.setVisible(visible);
	}

	public void setModel(Appointment appointmentModel) {
		setModel(appointmentModel, null, null);
	}

	public void setModel(Appointment appointment, Attendant attendant, Alarm alarm) {
		this.appointmentModel = appointment;
		this.attendantModel = attendant;
		this.alarmModel = alarm;
		updateFields();
	}

	public void setInternalUsers(List<InternalUser> internalUsers) {
		for(InternalUser user : internalUsers) {
			participantList.addItem(user);
			System.out.println("Adding " + user + " to internal users combobox");
		}
	}

	private void updateFields() {
		titleField.setText(appointmentModel.getTitle());
		description.setText(appointmentModel.getDescription());
		dateField.setValue(Utilities.dateToFormattedString(appointmentModel.getStart()));
		startTimeField.setValue(Utilities.timeToFormattedString(appointmentModel.getStart()));
		endTimeField.setValue(Utilities.timeToFormattedString(appointmentModel.getEnd()));

		if (appointmentModel.getMeetingRoom() == null) {
			useMeetingRoom.setSelected(false);
            meetingPlaceField.setText(appointmentModel.getMeetingPlace());

		} else {
			useMeetingRoom.setSelected(true);
			selectRoom.setText("Room # " + Integer.toString(appointmentModel.getMeetingRoom().getId()));
		}

		if(attendantModel != null) {
			if(((InternalAttendant) attendantModel).getVisibleOnCalendar()) {
				hideFromCalendar.setSelected(false);
			} else {
				hideFromCalendar.setSelected(true);
			}
		}

		if(alarmModel == null) {
			setAlarm.setSelected(false);
		} else {
			setAlarm.setSelected(true);
			alarmTimeField.setValue(getAlarmTimeDiff());
		}
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
	
	public void inviteToAppointment() {
		if (inviteByEmail.isSelected()) {
			User user = new ExternalUser(emailField.getText());
			personListModel.addElement(user);
		} else {
			personListModel.addElement((User)participantList.getSelectedItem());
		}
	}
	
	public void removeFromAppointment() {
		personListModel.removeElement(participants.getSelectedValue());
	}
	
	public boolean setAlarmIsSelected() {
		return setAlarm.isSelected();
	}
	
	public int getAlarmInMinutes() {
		if (setAlarm.isSelected()) {
			String alarm = this.alarmTimeField.getText();
			int minutes = Integer.parseInt("" + alarm.charAt(3) + alarm.charAt(4));
			minutes += Integer.parseInt("" + alarm.charAt(0) + alarm.charAt(1)) * 60;
			return minutes;
		} return -1;
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

		int diff = startMinutes - alarmMinutes;
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

	public static void main(String[] args) {
		AppointmentView view = new AppointmentView();
		view.setVisible(true);
	}
}
