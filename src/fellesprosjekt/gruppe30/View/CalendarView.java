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
	private JButton add_button, remove_button, new_appointment_button, log_out_button, left_arrow_button, right_arrow_button;
	private JComboBox users;
	private JLabel week_label, show_calendars_for, mon_label, tue_label, wed_label, thu_label, fri_label, sat_label, sun_label;
	private JList<User> user_calendars;
	private JList<AppointmentSummaryView>  mon_appointments, tue_appointments, wed_appointments, thu_appointments, fri_appointments, sat_appointments, sun_appointments;
	private JScrollPane mon_scrollpane, tue_scrollpane, wed_scrollpane, thu_scrollpane, fri_scrollpane, sat_scrollpane, sun_scrollpane;
	private JFrame frame;
	
	public CalendarView() {
		GridBagConstraints c = new GridBagConstraints();
		GridBagConstraints contents = new GridBagConstraints();
		setLayout(new GridBagLayout());
		
		//bestem utseende på hver knapp
		add_button = new JButton("Add");
		add_button.setPreferredSize(new Dimension(60, 30));
		
		remove_button = new JButton("Remove");
		remove_button.setPreferredSize(new Dimension(80, 30));
	
		new_appointment_button = new JButton("New Appointment");
		new_appointment_button.setPreferredSize(new Dimension(145, 30));
		
		log_out_button = new JButton("Log out");
		log_out_button.setPreferredSize(new Dimension(80, 25));
		
		left_arrow_button = new JButton("<");
		left_arrow_button.setPreferredSize(new Dimension(45, 30));
		
		right_arrow_button = new JButton(">");
		right_arrow_button.setPreferredSize(new Dimension(45, 30));
		
		user_calendars = new JList<User>();
		user_calendars.setPreferredSize(new Dimension(140,150));
		
		users = new JComboBox();
		users.setPreferredSize(new Dimension(140, 25));
		
		week_label = new JLabel("week 10");
		show_calendars_for = new JLabel("Show calendars for:");
		mon_label = new JLabel("Mon 3/3");
		tue_label = new JLabel("Tue 4/3");
		wed_label = new JLabel("Wed 5/3");
		thu_label = new JLabel("Thu 6/3");
		fri_label = new JLabel("Fri 7/3");
		sat_label = new JLabel("Sat 8/3");
		sun_label = new JLabel("Sun 9/3");
		
		mon_appointments = new JList<AppointmentSummaryView>();
		tue_appointments = new JList<AppointmentSummaryView>();
		wed_appointments = new JList<AppointmentSummaryView>();
		thu_appointments = new JList<AppointmentSummaryView>();
		fri_appointments = new JList<AppointmentSummaryView>();
		sat_appointments = new JList<AppointmentSummaryView>();
		sun_appointments = new JList<AppointmentSummaryView>();
		
		mon_scrollpane = new JScrollPane(mon_appointments);
		mon_scrollpane.setPreferredSize(new Dimension(160, 450));
		
		tue_scrollpane = new JScrollPane(tue_appointments);
		tue_scrollpane.setPreferredSize(new Dimension(160, 450));
		
		wed_scrollpane = new JScrollPane(wed_appointments);
		wed_scrollpane.setPreferredSize(new Dimension(160, 450));
		
		thu_scrollpane = new JScrollPane(thu_appointments);
		thu_scrollpane.setPreferredSize(new Dimension(160, 450));
		
		fri_scrollpane = new JScrollPane(fri_appointments);
		fri_scrollpane.setPreferredSize(new Dimension(160, 450));
		
		sat_scrollpane = new JScrollPane(sat_appointments);
		sat_scrollpane.setPreferredSize(new Dimension(160, 450));
		
		sun_scrollpane = new JScrollPane(sun_appointments);
		sun_scrollpane.setPreferredSize(new Dimension(160, 450));
		
		
		
		// bygg opp en gridbag
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5,5,5,5);
		contents.insets = new Insets(0,0,0,0);
		
		JPanel arrow_panel = new JPanel();
		arrow_panel.add(left_arrow_button);
		arrow_panel.add(week_label);
		arrow_panel.add(right_arrow_button);
		add(arrow_panel, c);

		c.gridy = 1;
		add(show_calendars_for, c);
		c.gridy = 2;
		add(user_calendars, c);
		c.gridy = 3;
		add(users, c);
		c.gridy = 4;
		JPanel add_remove = new JPanel();
		add_remove.add(add_button);
		add_remove.add(remove_button);
		add(add_remove, c);
		c.gridy = 5;
		add(new_appointment_button, c);
		c.gridy = 7;
		c.anchor = GridBagConstraints.SOUTHWEST;
		add(log_out_button, c);
		c.anchor = GridBagConstraints.CENTER;
		c.gridy = 0;
		c.gridx = 1;
		add(mon_label, c);
		c.gridx = 2;
		add(tue_label, c);
		c.gridx = 3;
		add(wed_label, c);
		c.gridx = 4;
		add(thu_label, c);
		c.gridx = 5;
		add(fri_label, c);
		c.gridx = 6;
		add(sat_label, c);
		c.gridx = 7;
		add(sun_label, c);
		

		contents.gridx = 1;
		contents.gridy = 1;
		contents.gridheight = 7;
		add(mon_scrollpane, contents);
		contents.gridx = 2;
		add(tue_scrollpane, contents);
		contents.gridx = 3;
		add(wed_scrollpane, contents);
		contents.gridx = 4;
		add(thu_scrollpane, contents);
		contents.gridx = 5;
		add(fri_scrollpane, contents);
		contents.gridx = 6;
		add(sat_scrollpane, contents);
		contents.gridx = 7;
		add(sun_scrollpane, contents);
		
		
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
