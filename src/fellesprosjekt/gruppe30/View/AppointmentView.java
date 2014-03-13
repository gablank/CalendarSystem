package fellesprosjekt.gruppe30.View;

import fellesprosjekt.gruppe30.Model.Appointment;
import fellesprosjekt.gruppe30.Model.InternalUser;
import fellesprosjekt.gruppe30.Model.PersonListModel;
import fellesprosjekt.gruppe30.Model.User;
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
import java.util.EventListener;

public class AppointmentView extends JPanel implements ActionListener, PropertyChangeListener, MouseListener, ListSelectionListener {
	protected PersonRenderer      listRenderer;
	protected PersonListModel     personListModel;
	protected JTextField          titleField, meetingRoomField, emailField;
	protected JTextArea           description;
	protected JFormattedTextField dateField, startTimeField, endTimeField, alarmTimeField;
	protected JCheckBox           useMeetingRoom, hideFromCalendar, setAlarm, inviteByEmail;
	protected JComboBox<User>     participantList;
	protected JList<User>         participants;
	protected JButton             addButton, removeButton, saveButton, deleteButton, cancelButton, selectRoom;
	protected JScrollPane         participantScroller, descriptionScroller;
	protected JLabel              participantLabel, dateLabel, startTimeLabel, endTimeLabel, alarmLabel;
	protected JFrame              frame;
	protected Appointment         model;


	public AppointmentView() {
		GridBagConstraints cLeft = new GridBagConstraints();
		GridBagConstraints cRight = new GridBagConstraints();
		setLayout(new GridBagLayout());

		//set appearance of all buttons
		titleField = new JTextField("Title", 13);
		titleField.addMouseListener(this);


		description = new JTextArea("Description", 5, 13);
		description.setBorder(titleField.getBorder());
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		description.addMouseListener(this);
		descriptionScroller = new JScrollPane(description);


		meetingRoomField = new JTextField("Place", 10);
		meetingRoomField.addMouseListener(this);
		emailField = new JTextField("Email", 1);
		emailField.addMouseListener(this);


		selectRoom = new JButton("Select...");
		selectRoom.setPreferredSize(new Dimension(100, 25));


		MaskFormatter dateFormatter;
		try {
			dateFormatter = new MaskFormatter("##.##.####");
			dateField = new JFormattedTextField(dateFormatter);
			dateField.setPreferredSize(new Dimension(80, 20));
			dateField.setValue("03.07.2014");
			dateField.addMouseListener(this);
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
			startTimeField.addMouseListener(this);
			endTimeField = new JFormattedTextField(timeFormatter);
			endTimeField.setPreferredSize(new Dimension(50, 20));
			endTimeField.setValue("10:40");
			endTimeField.setHorizontalAlignment(SwingConstants.CENTER);
			endTimeField.addMouseListener(this);
			alarmTimeField = new JFormattedTextField(timeFormatter);
			alarmTimeField.setPreferredSize(new Dimension(40, 20));
			alarmTimeField.setValue("00:30");
			alarmTimeField.setHorizontalAlignment(SwingConstants.CENTER);
			alarmTimeField.addMouseListener(this);

		} catch(ParseException e) {
			e.printStackTrace();
		}


		useMeetingRoom = new JCheckBox("Use meeting room");
		useMeetingRoom.setSelected(true);
		hideFromCalendar = new JCheckBox("Hide from calendar");
		setAlarm = new JCheckBox("Alarm");
		inviteByEmail = new JCheckBox("Invite by email");

		participantList = new JComboBox<User>();
		participantList.setPreferredSize(new Dimension(40, 25));
		participants = new JList<User>();
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
		add(meetingRoomField, cLeft);
		meetingRoomField.setVisible(false);
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

		//test code, REMOVE this following code later:
		useMeetingRoom.addActionListener(this);
		inviteByEmail.addActionListener(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		personListModel = new PersonListModel();
		personListModel.addElement(new InternalUser("email", "Jonathan", "Cinderella"));
		for(int i = 0; i < 10; i++) {
			personListModel.addElement(new InternalUser("email", "Emil", "Heien"));
		}
		this.setPersonListModel(personListModel);
		//end test code
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
		meetingRoomField.addKeyListener(controller);
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
				meetingRoomField.setVisible(false);
				selectRoom.setVisible(true);
				this.repaint();
			} else {
				selectRoom.setVisible(false);
				meetingRoomField.setVisible(true);
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
	public void mousePressed(MouseEvent e) {
		if(e.getSource() == titleField) {
			if(titleField.getText().equals("Title")) {
				titleField.setText("");
			}
		} else if(e.getSource() == description) {
			if(description.getText().equals("Description")) {
				description.setText("");
			}
		} else if(e.getSource() == meetingRoomField) {
			if(meetingRoomField.getText().equals("Place")) {
				meetingRoomField.setText("");
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
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		User user = (User) participants.getSelectedValue();
	}

	public void setVisible(boolean visible) {
		this.frame.setVisible(visible);
	}

	public void setModel(Appointment model) {
		this.model = model;
		updateFields();
	}

	private void updateFields() {
		titleField.setText(model.getTitle());
		description.setText(model.getDescription());
		dateField.setValue(Utilities.dateToFormattedString(model.getStart()));
		startTimeField.setValue(Utilities.timeToFormattedString(model.getStart()));
		endTimeField.setValue(Utilities.timeToFormattedString(model.getEnd()));
		if(model.getMeetingRoom() != null) {
			useMeetingRoom.setSelected(true);

		} else {
			useMeetingRoom.setSelected(false);
			selectRoom.setText("Room # " + Integer.toString(model.getMeetingRoom().getId()));
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

	public static void main(String[] args) {
		AppointmentView view = new AppointmentView();
		view.setVisible(true);
	}

	public Appointment getModel() {
		return model;
	}

}
