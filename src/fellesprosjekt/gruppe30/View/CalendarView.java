package fellesprosjekt.gruppe30.View;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

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
	private JLabel weekday_label, week_label, show_calendars_for;
	private JList day_appointments;
	private JScrollPane scrollpane;
	private JFrame frame;
	
	public CalendarView() {
		GridBagConstraints c = new GridBagConstraints();
		setLayout(new GridBagLayout());
		
		//bestem utseende på hver knapp
		add_button = new JButton("Add");
		add_button.setPreferredSize(new Dimension(50, 25));
		
		remove_button = new JButton("Remove");
		remove_button.setPreferredSize(new Dimension(50,25));
		
		new_appointment_button = new JButton("New Appointment");
		new_appointment_button.setPreferredSize(new Dimension(80, 40));
		
		log_out_button = new JButton("Log out");
		log_out_button.setPreferredSize(new Dimension(80, 25));
		
		left_arrow_button = new JButton("<-");
		left_arrow_button.setPreferredSize(new Dimension(30, 20));
		
		right_arrow_button = new JButton("->");
		right_arrow_button.setPreferredSize(new Dimension(30, 20));
		
		users = new JComboBox();
		users.setPreferredSize(new Dimension(100, 25));
		
		show_calendars_for = new JLabel("Show calendars for:");
		
		day_appointments = new JList<AppointmentSummaryView>();

		scrollpane = new JScrollPane(day_appointments);
		scrollpane.setFocusable(true);
		scrollpane.setPreferredSize(new Dimension(150, 450));
		
		// bygg opp en gridbag
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5,5,5,5);
		
		JPanel arrow_panel = new JPanel();
		arrow_panel.add(left_arrow_button);
		arrow_panel.add(right_arrow_button);
		add(arrow_panel, c);
		//
		//stuff goes here later
		//
		c.gridy = 1;
		add(log_out_button, c);
		c.gridx = 1;
		add(day_appointments, c);

		
		
		frame = new JFrame("Calendar view");
		frame.add(this);
		frame.pack();
		frame.setVisible(false);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	public void setVisible(boolean visible){
		this.frame.setVisible(visible);
	}

	public static void main(String[] args) {
		CalendarView view = new CalendarView();
		view.setVisible(true);
	}

}
