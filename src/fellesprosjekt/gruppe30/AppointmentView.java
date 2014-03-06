package fellesprosjekt.gruppe30;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class AppointmentView extends JPanel {

	private JTextField title_field, meeting_room_field;
	private JTextArea description;
	private JFormattedTextField date_field, start_time_field, end_time_field, alarm_time_field;
	private JCheckBox use_meeting_room, hide_from_calendar, set_alarm;
	private JComboBox participant_list;
	private JList participants;
	private JButton add_button, remove_button, save_button, delete_button, cancel_button;
	private JScrollPane scrollpane;
	
	public AppointmentView() {
		GridBagConstraints c = new GridBagConstraints() ;
		setLayout(new GridBagLayout());
		
		title_field = new JTextField("Title", 13);
		description = new JTextArea(5,13);
		description.setText("Description");
		meeting_room_field = new JTextField("Place", 13);
		//String date = new SimpleDateFormat("DD.MM.YY").format(new Date());
		date_field = new JFormattedTextField();
		date_field.setPreferredSize(new Dimension(100,20));
		date_field.setText("Date");
		start_time_field = new JFormattedTextField();
		start_time_field.setPreferredSize(new Dimension(100,20));
		start_time_field.setText("start time");
		end_time_field = new JFormattedTextField();
		end_time_field.setPreferredSize(new Dimension(100,20));
		end_time_field.setText("end time");
		alarm_time_field = new JFormattedTextField();
		alarm_time_field.setPreferredSize(new Dimension(100,20));
		alarm_time_field.setText("alarm time");
		use_meeting_room = new JCheckBox("Use meeting room");
		hide_from_calendar = new JCheckBox("Hide from calendar");
		set_alarm = new JCheckBox("Alarm");
		participant_list = new JComboBox();
		participant_list.setPreferredSize(new Dimension(100, 20));
		participants = new JList();
		scrollpane = new JScrollPane(participants);
		scrollpane.setFocusable(false);
		scrollpane.setPreferredSize(new Dimension(150, 150));
		add_button = new JButton("Add");
		add_button.setPreferredSize(new Dimension(80,25));
		remove_button = new JButton("Remove");
		remove_button.setPreferredSize(new Dimension(80,25));
		save_button = new JButton("Save");
		delete_button = new JButton("Delete");
		cancel_button = new JButton("Cancel");
		
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5,5,5,5);
		add(title_field, c);
		c.gridy++;
		add(description, c);
		c.gridy++;
		c.ipady = 0;
		add(date_field, c);
		c.gridy++;
		add(start_time_field, c);
		c.gridx++;
		add(end_time_field, c);
		c.gridy++;
		c.gridx--;
		c.gridwidth = 1;
		add(use_meeting_room, c);
		c.gridy++;
		add(meeting_room_field, c);
		c.gridx+=2;
		c.gridy = 0;
		add(hide_from_calendar, c);
		c.gridy++;
		add(set_alarm, c);
		c.gridy++;
		add(alarm_time_field, c);
		c.gridy++;
		add(scrollpane, c);
		c.gridy++;
		add(participant_list, c);
		c.gridy++;
		add(add_button, c);
		c.gridy++;
		add(remove_button, c);
		c.gridy++;
		c.gridx = 0;
		c.anchor=GridBagConstraints.WEST;
		add(save_button, c);
		c.gridx++;
		add(delete_button, c);
		c.gridx++;
		add(cancel_button, c);
		
	}
	
	
	public static void main(String[] args) {
		AppointmentView view = new AppointmentView();
		JFrame frame = new JFrame("Appointment view");
		frame.add(view);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
