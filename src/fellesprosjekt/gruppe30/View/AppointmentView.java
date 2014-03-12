package fellesprosjekt.gruppe30.View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;
import java.util.GregorianCalendar;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.MaskFormatter;

import fellesprosjekt.gruppe30.Model.Appointment;
import fellesprosjekt.gruppe30.Model.InternalUser;
import fellesprosjekt.gruppe30.Model.User;

public class AppointmentView extends JPanel implements ActionListener, PropertyChangeListener {
	protected PersonRenderer listrenderer;
	protected JTextField titleField, meetingRoomField, emailField;
	protected JTextArea description;
	protected JFormattedTextField dateField, startTimeField, endTimeField, alarmTimeField;
	protected JCheckBox useMeetingRoom, hideFromCalendar, setAlarm, inviteByEmail;
	protected JComboBox<User> participantList;
	protected JList<User> participants;
	protected JButton addButton, removeButton, saveButton, deleteButton, cancelButton, selectRoom;
	protected JScrollPane scrollpane;
	protected JLabel participantLabel, dateLabel, startTimeLabel, endTimeLabel, alarmLabel;
	protected JFrame frame;
	protected Appointment model;


	public AppointmentView() {
		GridBagConstraints cLeft = new GridBagConstraints() ;
		GridBagConstraints cRight = new GridBagConstraints() ;
		setLayout(new GridBagLayout());
		
		//set appearance of all buttons
		titleField = new JTextField("Title", 13);
		description = new JTextArea(5,13);
		description.setText("Description");
		description.setBorder(titleField.getBorder());
		
		meetingRoomField = new JTextField("Place", 11);
		emailField = new JTextField("Email", 1);
		
		
		selectRoom = new JButton("Select...");
		selectRoom.setPreferredSize(new Dimension(100,25));
		
		
		MaskFormatter dateformatter;
		try {
			dateformatter = new MaskFormatter("##.##.####");
			dateField = new JFormattedTextField(dateformatter);
			dateField.setPreferredSize(new Dimension(100,20));
			dateField.setValue("03.07.2014");
			dateField.setHorizontalAlignment(dateField.CENTER);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		MaskFormatter timeformatter;
		try {
			timeformatter = new MaskFormatter("##:##");
			startTimeField = new JFormattedTextField(timeformatter);
			startTimeField.setPreferredSize(new Dimension(50,20));
			startTimeField.setValue("08:40");
			startTimeField.setHorizontalAlignment(SwingConstants.CENTER);
			endTimeField = new JFormattedTextField(timeformatter);
			endTimeField.setPreferredSize(new Dimension(50,20));
			endTimeField.setValue("10:40");
			endTimeField.setHorizontalAlignment(SwingConstants.CENTER);
			alarmTimeField = new JFormattedTextField(timeformatter);
			alarmTimeField.setPreferredSize(new Dimension(40,20));
			alarmTimeField.setValue("00:30");
			alarmTimeField.setHorizontalAlignment(SwingConstants.CENTER);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}

		
		useMeetingRoom = new JCheckBox("Use meeting room");
		hideFromCalendar = new JCheckBox("Hide from calendar                                             ");
		setAlarm = new JCheckBox("Alarm");
		inviteByEmail = new JCheckBox("Invite by email");
		
		participantList = new JComboBox<User>();
		participantList.setPreferredSize(new Dimension(40, 25));
		participants = new JList<User>();
		
		scrollpane = new JScrollPane(participants);
		scrollpane.setFocusable(true);
		scrollpane.setPreferredSize(new Dimension(150, 150));
		participantLabel = new JLabel("              Participants");
		
		addButton = new JButton("Add        ");
		addButton.setPreferredSize(new Dimension(80,25));
		
		removeButton = new JButton("Remove");
		removeButton.setPreferredSize(new Dimension(80,25));
		
		saveButton = new JButton("Save");
		deleteButton = new JButton("Delete");
		cancelButton = new JButton("Cancel");
		

		//Build a gridbag
		cLeft.gridx = 0;
		cLeft.gridy = 0;
		cLeft.insets = new Insets(5,5,5,5);
		
		add(titleField, cLeft);
		cLeft.gridy = 1;
		
		add(description, cLeft);
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
		add(meetingRoomField,cLeft);
		add(selectRoom, cLeft);
		selectRoom.setVisible(false);
		
		cRight.gridx = 2;
		cRight.gridy = 0;
		cRight.insets = new Insets (5,5,5,5);
		
		JPanel alarmPanel = new JPanel();
		alarmPanel.add(setAlarm);
		alarmPanel.add(alarmTimeField);
		alarmLabel = new JLabel("before meeting");
		alarmPanel.add(alarmLabel);
		JPanel calendarAndAlarm = new JPanel();
		calendarAndAlarm.setLayout(new BoxLayout(calendarAndAlarm, BoxLayout.Y_AXIS));
		calendarAndAlarm.add(hideFromCalendar);
		calendarAndAlarm.add(alarmPanel);
		add(calendarAndAlarm, cRight);
		cRight.gridy = 1;
		
		cRight.gridheight = 3;
		JPanel participantPanel = new JPanel();
		participantPanel.setLayout(new BoxLayout(participantPanel, BoxLayout.Y_AXIS));
		participantPanel.add(participantLabel);
		participantPanel.add(scrollpane);
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
		cRight.gridx = 2;
		cRight.anchor=GridBagConstraints.CENTER;
		
		JPanel saveDelete = new JPanel();
		saveDelete.add(saveButton);
		saveDelete.add(deleteButton);
		saveDelete.add(cancelButton);
		add(saveDelete, cRight);

		listrenderer = new PersonRenderer();
		participants.setCellRenderer(listrenderer);
		
		//testing purposes, REMOVE this following code later:
		useMeetingRoom.addActionListener(this);
		inviteByEmail.addActionListener(this);
		//end of testing Code
		
		frame = new JFrame("Appointment view");
		frame.add(this);
		frame.pack();
		frame.setVisible(false);
		frame.setResizable(false);
		
		//test code:
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//why this shit dont work?
		DefaultListModel<User> model = new DefaultListModel<User>();
		participants = new JList<User>(model);
		model.add(0, new InternalUser("Emil", "Heien", "uberjew", "email"));
		//end test code
	}
	
	//add listeners to all components
	public void addListener(EventListener controller){
		this.addActionListener((ActionListener)controller);
		this.addKeyListener((KeyListener) controller);
		this.addListSelectionListener((ListSelectionListener) controller);
	}
	public void addActionListener(ActionListener controller){
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
	public void addKeyListener(KeyListener controller){
		titleField.addKeyListener(controller);
		description.addKeyListener(controller);
		meetingRoomField.addKeyListener(controller);
		dateField.addKeyListener(controller);
		startTimeField.addKeyListener(controller);
		endTimeField.addKeyListener(controller);
		alarmTimeField.addKeyListener(controller);		
	}
	public void addListSelectionListener(ListSelectionListener controller){
		participants.addListSelectionListener(controller);
	}
	


	@Override
	//change room select field into button and vice versa
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == useMeetingRoom){
			if (useMeetingRoom.isSelected()){
				meetingRoomField.setVisible(false);
				selectRoom.setVisible(true);
				this.repaint();
			}
			else {
				selectRoom.setVisible(false);
				meetingRoomField.setVisible(true);
				this.repaint();
			}
		}
		else if (e.getSource() == inviteByEmail){
			if (inviteByEmail.isSelected()){
				participantList.setVisible(false);
				emailField.setVisible(true);
			}
			else {
				emailField.setVisible(false);
				participantList.setVisible(true);
			}
		}
		
	}
	
	public void setVisible(boolean visible){
		this.frame.setVisible(visible);
	}

	public void setModel(Appointment model) {
		this.model = model;
		updateFields();
	}

	private void updateFields() {
		titleField.setText(model.getTitle());
		description.setText(model.getDescription());
		dateField.setValue(dateToFormattedString(model.getStart()));
		startTimeField.setValue(timeToFormattedString(model.getStart()));
		endTimeField.setValue(timeToFormattedString(model.getEnd()));
		if(model.getRoom() != null) {
			useMeetingRoom.setSelected(true);

		} else {
			useMeetingRoom.setSelected(false);
			selectRoom.setText("Room # " + Integer.toString(model.getRoom().getId()));
		}
	}

	private String dateToFormattedString(Date date) {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(date);
		String day = Integer.toString(gregorianCalendar.get(Calendar.DAY_OF_MONTH));
		if(day.length() == 1) {
			day = "0" + day;
		}
		String month = Integer.toString(gregorianCalendar.get(Calendar.MONTH));
		if(month.length() == 1) {
			month = "0" + month;
		}
		String year = Integer.toString(gregorianCalendar.get(Calendar.YEAR) - 2000);
		return day + "." + month + "." + year;
	}

	private String timeToFormattedString(Date date) {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(date);
		String hour = Integer.toString(gregorianCalendar.get(Calendar.HOUR_OF_DAY));
		if(hour.length() == 1) {
			hour = "0" + hour;
		}
		String minute = Integer.toString(gregorianCalendar.get(Calendar.MINUTE));
		if(minute.length() == 1) {
			minute = "0" + minute;
		}
		return hour + ":" + minute;
	}

	public static void main(String[] args) {
		AppointmentView view = new AppointmentView();
		view.setVisible(true);
	}

	@Override
	public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
		this.updateFields();
	}
}
