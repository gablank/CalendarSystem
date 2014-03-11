package fellesprosjekt.gruppe30.View;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.EventListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;

import fellesprosjekt.gruppe30.Model.User;

public class CalendarView extends JPanel {
	private PersonRenderer listrenderer;
	private JButton addButton, removeButton, newAppointmentButton, logOutButton, leftArrowButton, rightArrowButton;
	private JComboBox users;
	private JLabel weekLabel, showCalendarsFor, monLabel, tueLabel, wedLabel, thuLabel, friLabel, satLabel, sunLabel;
	private JList<User> userCalendars;
	private JPanel monAppointment, tueAppointment, wedAppointment, thuAppointment, friAppointment, satAppointment, sunAppointment;
	private JFrame frame;
	
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
		leftArrowButton.setPreferredSize(new Dimension(45, 30));
		
		rightArrowButton = new JButton(">");
		rightArrowButton.setPreferredSize(new Dimension(45, 30));
		
		userCalendars = new JList<User>();
		userCalendars.setPreferredSize(new Dimension(140,150));
		
		users = new JComboBox();
		users.setPreferredSize(new Dimension(140, 25));
		
		weekLabel = new JLabel("week 10");
		showCalendarsFor = new JLabel("Show calendars for:");
		monLabel = new JLabel("Mon 3.3");
		tueLabel = new JLabel("Tue 4.3");
		wedLabel = new JLabel("Wed 5.3");
		thuLabel = new JLabel("Thu 6.3");
		friLabel = new JLabel("Fri 7.3");
		satLabel = new JLabel("Sat 8.3");
		sunLabel = new JLabel("Sun 9.3");
		
		monAppointment = new JPanel();
		JScrollPane monScroller = new JScrollPane(monAppointment);
		monScroller.setFocusable(true);
		monScroller.setPreferredSize(new Dimension(160, 450));
		monAppointment.setBackground(Color.WHITE);
		monAppointment.setBorder(BorderFactory.createLineBorder(Color.black));
		monAppointment.setLayout(new BoxLayout(monAppointment, BoxLayout.Y_AXIS));
		
		tueAppointment = new JPanel();
		JScrollPane tueScroller = new JScrollPane(tueAppointment);
		tueScroller.setFocusable(true);
		tueScroller.setPreferredSize(new Dimension(160, 450));
		tueAppointment.setBackground(Color.WHITE);
		tueAppointment.setBorder(BorderFactory.createLineBorder(Color.black));
		tueAppointment.setLayout(new BoxLayout(tueAppointment, BoxLayout.Y_AXIS));
		
		wedAppointment = new JPanel();
		JScrollPane wedScroller = new JScrollPane(wedAppointment);
		wedScroller.setFocusable(true);
		wedScroller.setPreferredSize(new Dimension(160, 450));
		wedAppointment.setBackground(Color.WHITE);
		wedAppointment.setBorder(BorderFactory.createLineBorder(Color.black));
		wedAppointment.setLayout(new BoxLayout(wedAppointment, BoxLayout.Y_AXIS));
		
		thuAppointment = new JPanel();
		JScrollPane thuScroller = new JScrollPane(thuAppointment);
		thuScroller.setFocusable(true);
		thuScroller.setPreferredSize(new Dimension(160, 450));
		thuAppointment.setBackground(Color.WHITE);
		thuAppointment.setBorder(BorderFactory.createLineBorder(Color.black));
		thuAppointment.setLayout(new BoxLayout(thuAppointment, BoxLayout.Y_AXIS));
		
		friAppointment = new JPanel();
		JScrollPane friScroller = new JScrollPane(friAppointment);
		friScroller.setFocusable(true);
		friScroller.setPreferredSize(new Dimension(160, 450));
		friAppointment.setBackground(Color.WHITE);
		friAppointment.setBorder(BorderFactory.createLineBorder(Color.black));
		friAppointment.setLayout(new BoxLayout(friAppointment, BoxLayout.Y_AXIS));
		
		satAppointment = new JPanel();
		JScrollPane satScroller = new JScrollPane(satAppointment);
		satScroller.setFocusable(true);
		satScroller.setPreferredSize(new Dimension(160, 450));
		satAppointment.setBackground(Color.WHITE);
		satAppointment.setBorder(BorderFactory.createLineBorder(Color.black));
		satAppointment.setLayout(new BoxLayout(satAppointment, BoxLayout.Y_AXIS));
		
		sunAppointment = new JPanel();
		JScrollPane sunScroller = new JScrollPane(sunAppointment);
		sunScroller.setFocusable(true);
		sunScroller.setPreferredSize(new Dimension(160, 450));
		sunAppointment.setBackground(Color.WHITE);
		sunAppointment.setBorder(BorderFactory.createLineBorder(Color.black));
		sunAppointment.setLayout(new BoxLayout(sunAppointment, BoxLayout.Y_AXIS));
		
		
		// build a gridbag
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
		for (int i=0; i<4; i++){monAppointment.add(new AppointmentSummaryView());}
		for (int i=0; i<5; i++){tueAppointment.add(new AppointmentSummaryView());}
		wedAppointment.add(new AppointmentSummaryView());
		wedAppointment.add(new AppointmentSummaryView());
		thuAppointment.add(new AppointmentSummaryView());
		//end test code
		
		
	}
	
	public void addListener(EventListener controller){
		this.addActionListener((ActionListener)controller);
	}
	
	public void addActionListener(ActionListener controller){
		addButton.addActionListener(controller);
		removeButton.addActionListener(controller);
		newAppointmentButton.addActionListener(controller);
		logOutButton.addActionListener(controller);
		leftArrowButton.addActionListener(controller);
		rightArrowButton.addActionListener(controller);
		users.addActionListener(controller);
	}
	
	public void addListSelectionListener(ListSelectionListener controller){
		userCalendars.addListSelectionListener(controller);
	}
	
	public void setVisible(boolean visible){
		this.frame.setVisible(visible);
	}
	
	public void propertyChange(PropertyChangeEvent pce) {
		if (pce.getPropertyName() == "next week") {
			weekLabel.setText((String)pce.getNewValue());
			/*
			 * TODO implement next week functions
			 */
		} else if (pce.getPropertyName() == "previous week") {
			weekLabel.setText((String)pce.getNewValue());
			/*
			 * TODO implement previous week functions
			 */
		}
	}

	public static void main(String[] args) {
		CalendarView view = new CalendarView();
		view.setVisible(true);
	}

}
