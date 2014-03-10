package fellesprosjekt.gruppe30.View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fellesprosjekt.gruppe30.Model.User;

public class AppointmentSummaryView extends JPanel {

	private JLabel title_label, time_label, yes_label, no_label, creator_label, unanswered ;
	private JFrame frame;
	private JComboBox<User> participants;
	
	public AppointmentSummaryView() {
		GridBagConstraints c = new GridBagConstraints();
		setLayout(new GridBagLayout());
		
		
		title_label = new JLabel ("Lunch");
		time_label = new JLabel("12:15 - 12:45");
		yes_label = new JLabel ("Y");
		yes_label.setPreferredSize(new Dimension(40,30));
		yes_label.setBorder(BorderFactory.createLineBorder(Color.black));
		no_label = new JLabel("N");
		no_label.setPreferredSize(new Dimension(40,30));
		no_label.setBorder(BorderFactory.createLineBorder(Color.black));
		unanswered = new JLabel("?");
		unanswered.setPreferredSize(new Dimension(40,30));
		unanswered.setBorder(BorderFactory.createLineBorder(Color.black));
		participants = new JComboBox<User>();
		participants.setPreferredSize(new Dimension(130, 30));
		
		
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0,0,0,0);
		
		JPanel header_panel = new JPanel();
		header_panel.setLayout(new BoxLayout(header_panel, BoxLayout.Y_AXIS));
		header_panel.add(title_label);
		header_panel.add(time_label);
		header_panel.setPreferredSize(new Dimension(150,40));
		header_panel.setBackground(Color.BLUE);
		add(header_panel, c);
		c.gridy = 1;
		
		JPanel status_panel = new JPanel();
		status_panel.add(yes_label);
		yes_label.setHorizontalAlignment(yes_label.CENTER);
		status_panel.add(no_label);
		no_label.setHorizontalAlignment(no_label.CENTER);
		status_panel.add(unanswered);
		unanswered.setHorizontalAlignment(unanswered.CENTER);
		status_panel.setPreferredSize(new Dimension(150,40));
		status_panel.setBackground(Color.GREEN);
		add(status_panel, c);
		c.gridy = 2;
		
		JPanel participant_panel = new JPanel();
		participant_panel.add(participants);
		participant_panel.setPreferredSize(new Dimension(150,40));
		participant_panel.setBackground(Color.pink);
		add(participant_panel, c);
		
		frame = new JFrame();
		frame.add(this);
		frame.pack();
		frame.setVisible(false);
		frame.setResizable(false);
		//test code
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//end test code
		
	}
	
	public void setVisible(boolean visible){
		this.frame.setVisible(visible);
	}
	
	
	public static void main(String[] args) {
		AppointmentSummaryView view = new AppointmentSummaryView();
		view.setVisible(true);

	}

}
