package fellesprosjekt.gruppe30.View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
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
import javax.swing.event.ListSelectionListener;
import javax.swing.text.MaskFormatter;
import javax.xml.bind.Marshaller.Listener;

import fellesprosjekt.gruppe30.Model.User;

public class AppointmentView extends JPanel implements ActionListener {
	private PersonRenderer listrenderer;
	private JTextField title_field, meeting_room_field;
	private JTextArea description;
	private JFormattedTextField date_field, start_time_field, end_time_field, alarm_time_field;
	private JCheckBox use_meeting_room, hide_from_calendar, set_alarm;
	private JComboBox participant_list;
	private JList participants;
	private JButton add_button, remove_button, save_button, delete_button, cancel_button, select_room;
	private JScrollPane scrollpane;
	
	public AppointmentView() {
		GridBagConstraints c_left = new GridBagConstraints() ;
		GridBagConstraints c_right = new GridBagConstraints() ;
		setLayout(new GridBagLayout());
		
		//Bestem utseende på hver knapp
		title_field = new JTextField("Title", 13);
		
		description = new JTextArea(5,13);
		description.setText("Description");
		description.setBorder(title_field.getBorder());
		
		meeting_room_field = new JTextField("Place", 13);
		select_room = new JButton("Select...");
		select_room.setPreferredSize(new Dimension(100,25));
		
		MaskFormatter dateformatter;
		try {
			dateformatter = new MaskFormatter("##.##.##");
			date_field = new JFormattedTextField(dateformatter);
			date_field.setPreferredSize(new Dimension(100,20));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		MaskFormatter timeformatter;
		try {
			timeformatter = new MaskFormatter("##:##");
			start_time_field = new JFormattedTextField(timeformatter);
			start_time_field.setPreferredSize(new Dimension(50,20));
			end_time_field = new JFormattedTextField(timeformatter);
			end_time_field.setPreferredSize(new Dimension(50,20));
			alarm_time_field = new JFormattedTextField(timeformatter);
			alarm_time_field.setPreferredSize(new Dimension(100,20));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		
		use_meeting_room = new JCheckBox("Use meeting room");
		hide_from_calendar = new JCheckBox("Hide from calendar             ");
		set_alarm = new JCheckBox("Alarm");
		
		participant_list = new JComboBox();
		participant_list.setPreferredSize(new Dimension(100, 25));
		participants = new JList<User>();
		
		scrollpane = new JScrollPane(participants);
		scrollpane.setFocusable(false);
		scrollpane.setPreferredSize(new Dimension(150, 150));
		JLabel participant_label = new JLabel("              Participants");
		
		add_button = new JButton("Add        ");
		add_button.setPreferredSize(new Dimension(80,25));
		
		remove_button = new JButton("Remove");
		remove_button.setPreferredSize(new Dimension(80,25));
		
		save_button = new JButton("Save");
		delete_button = new JButton("Delete");
		cancel_button = new JButton("Cancel");
		

		//Bygg opp en gridbag
		c_left.gridx = 0;
		c_left.gridy = 0;
		c_left.insets = new Insets(5,5,5,5);
		
		add(title_field, c_left);
		c_left.gridy = 1;
		
		add(description, c_left);
		c_left.gridy = 2;
		
		JPanel date_panel = new JPanel();
		JLabel date_label = new JLabel("DD.MM.YY");
		date_panel.setLayout(new BoxLayout(date_panel, BoxLayout.Y_AXIS));
		date_panel.add(date_label, c_left);
		date_panel.add(date_field, c_left);
		add(date_panel, c_left);
		c_left.gridy = 3;

		JPanel from_panel = new JPanel();
		from_panel.setLayout(new BoxLayout(from_panel, BoxLayout.Y_AXIS));
		JLabel start_time_label = new JLabel("from");
		from_panel.add(start_time_label, c_left);
	    from_panel.add(start_time_field, c_left);
		
		JPanel to_panel = new JPanel();
		to_panel.setLayout(new BoxLayout(to_panel, BoxLayout.Y_AXIS));
		JLabel end_time_label = new JLabel("to");
		to_panel.add(end_time_label, c_left);
		to_panel.add(end_time_field, c_left);
		
		JPanel time_panel = new JPanel();
		time_panel.add(from_panel, c_left);
		time_panel.add(to_panel, c_left);
		add(time_panel, c_left);
		c_left.gridy = 4;
		
		
		add(use_meeting_room, c_left);
		c_left.gridy = 5;
		add(meeting_room_field,c_left);
		add(select_room, c_left);
		select_room.setVisible(false);
		
		c_right.gridx = 2;
		c_right.gridy = 0;
		c_right.insets = new Insets (5,5,5,5);
		
		JPanel alarm_panel = new JPanel();
		alarm_panel.add(set_alarm,c_right);
		alarm_panel.add(alarm_time_field, c_right);
		JPanel calendar_and_alarm = new JPanel();
		calendar_and_alarm.setLayout(new BoxLayout(calendar_and_alarm, BoxLayout.Y_AXIS));
		calendar_and_alarm.add(hide_from_calendar, c_right);
		calendar_and_alarm.add(alarm_panel, c_right);
		add(calendar_and_alarm, c_right);
		c_right.gridy = 1;
		
		c_right.gridheight = 3;
		JPanel participant_panel = new JPanel();
		participant_panel.setLayout(new BoxLayout(participant_panel, BoxLayout.Y_AXIS));
		participant_panel.add(participant_label, c_right);
		participant_panel.add(scrollpane, c_right);
		add(participant_panel, c_right);
		c_right.gridy = 4;
		
		JPanel add_remove = new JPanel();
		add_remove.setLayout(new BoxLayout(add_remove, BoxLayout.Y_AXIS));
		add_remove.add(add_button, c_right);
		add_remove.add(remove_button, c_right);
		JPanel person_add_remove = new JPanel();
		person_add_remove.add(participant_list, c_right);
		person_add_remove.add(add_remove, c_right);
		add(person_add_remove, c_right);

		c_right.gridy = 8;
		c_right.gridx = 2;
		c_right.anchor=GridBagConstraints.WEST;
		
		JPanel save_delete = new JPanel();
		save_delete.add(save_button, c_right);
		save_delete.add(delete_button, c_right);
		save_delete.add(cancel_button, c_right);
		add(save_delete, c_right);

		listrenderer = new PersonRenderer();
		participants.setCellRenderer(listrenderer);
		
		//testing purposes, REMOVE this following code later:
		use_meeting_room.addActionListener(this);
		//end of testing Code
	}
	
	public void addListener(Listener controller){
		this.addActionListener((ActionListener)controller);
		this.addKeyListener((KeyListener) controller);
		this.addListSelectionListener((ListSelectionListener) controller);
	}
	public void addActionListener(ActionListener controller){
		select_room.addActionListener(controller);
		add_button.addActionListener(controller);
		remove_button.addActionListener(controller);
		save_button.addActionListener(controller);
		delete_button.addActionListener(controller);
		cancel_button.addActionListener(controller);
		participant_list.addActionListener(controller);
		hide_from_calendar.addActionListener(controller);
		set_alarm.addActionListener(controller);	
	}
	public void addKeyListener(KeyListener controller){
		title_field.addKeyListener(controller);
		description.addKeyListener(controller);
		meeting_room_field.addKeyListener(controller);
		date_field.addKeyListener(controller);
		start_time_field.addKeyListener(controller);
		end_time_field.addKeyListener(controller);
		alarm_time_field.addKeyListener(controller);		
	}
	public void addListSelectionListener(ListSelectionListener controller){
		participants.addListSelectionListener(controller);
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {
		if (use_meeting_room.isSelected()){
			meeting_room_field.setVisible(false);
			select_room.setVisible(true);
			this.repaint();
		}
		else {
			select_room.setVisible(false);
			meeting_room_field.setVisible(true);
			this.repaint();
		}
		
	}

	public static void main(String[] args) {
		AppointmentView view = new AppointmentView();
		JFrame frame = new JFrame("Appointment view");
		frame.add(view);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		
	}
}
