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
	private JList day_appointments, tue_appointments;
	private JScrollPane scrollpane, scrollpane2;
	private JFrame frame;
	
	public CalendarView() {
		GridBagConstraints c = new GridBagConstraints();
		setLayout(new GridBagLayout());
		
		//bestem utseende på hver knapp
		add_button = new JButton("Add");
		add_button.setPreferredSize(new Dimension(60, 30));
		
		remove_button = new JButton("Remove");
		remove_button.setPreferredSize(new Dimension(80, 30));
	
		new_appointment_button = new JButton("New Appt...");
		new_appointment_button.setPreferredSize(new Dimension(120, 30));
		
		log_out_button = new JButton("Log out");
		log_out_button.setPreferredSize(new Dimension(80, 25));
		
		left_arrow_button = new JButton("<---");
		left_arrow_button.setPreferredSize(new Dimension(60, 30));
		
		right_arrow_button = new JButton("--->");
		right_arrow_button.setPreferredSize(new Dimension(60, 30));
		
		users = new JComboBox();
		users.setPreferredSize(new Dimension(100, 25));
		
		show_calendars_for = new JLabel("Show calendars for:");
		
		day_appointments = new JList<AppointmentSummaryView>();
		tue_appointments = new JList<AppointmentSummaryView>();

		scrollpane = new JScrollPane(day_appointments);
		scrollpane.setFocusable(true);
		scrollpane.setPreferredSize(new Dimension(150, 450));
		
		scrollpane2 = new JScrollPane(tue_appointments);
		scrollpane2.setFocusable(true);
		scrollpane2.setPreferredSize(new Dimension(150, 450));
		
		// bygg opp en gridbag
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5,5,5,5);
		
		JPanel arrow_panel = new JPanel();
		arrow_panel.add(left_arrow_button);
		arrow_panel.add(right_arrow_button);
		add(arrow_panel, c);

		c.gridy = 1;
		add(show_calendars_for, c);
		c.gridy = 2;
		add(users, c);
		c.gridy = 3;
		JPanel add_remove = new JPanel();
		add_remove.add(add_button);
		add_remove.add(remove_button);
		add(add_remove, c);
		c.gridy = 4;
		add(new_appointment_button, c);
		c.gridy = 7;
		add(log_out_button, c);
		c.gridx = 1;
		c.gridy = 1;
		c.gridheight= 7;
		add(scrollpane, c);
		c.gridx = 2;
		add(scrollpane2, c);

		
		
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
