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

	private JLabel titleLabel, timeLabel, yesLabel, noLabel, creatorLabel, unanswered ;
	private JFrame frame;
	private JComboBox<User> participants;
	
	public AppointmentSummaryView() {
		GridBagConstraints c = new GridBagConstraints();
		setLayout(new GridBagLayout());
		
		
		titleLabel = new JLabel ("Lunch");
		timeLabel = new JLabel("12:15 - 12:45");
		yesLabel = new JLabel ("Y");
		yesLabel.setPreferredSize(new Dimension(40,30));
		yesLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		noLabel = new JLabel("N");
		noLabel.setPreferredSize(new Dimension(40,30));
		noLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		unanswered = new JLabel("?");
		unanswered.setPreferredSize(new Dimension(40,30));
		unanswered.setBorder(BorderFactory.createLineBorder(Color.black));
		participants = new JComboBox<User>();
		participants.setPreferredSize(new Dimension(130, 30));
		
		
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0,0,0,0);
		
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
		headerPanel.add(titleLabel);
		headerPanel.add(timeLabel);
		headerPanel.setPreferredSize(new Dimension(150,40));
		headerPanel.setBackground(Color.BLUE);
		add(headerPanel, c);
		c.gridy = 1;
		
		JPanel statusPanel = new JPanel();
		statusPanel.add(yesLabel);
		yesLabel.setHorizontalAlignment(yesLabel.CENTER);
		statusPanel.add(noLabel);
		noLabel.setHorizontalAlignment(noLabel.CENTER);
		statusPanel.add(unanswered);
		unanswered.setHorizontalAlignment(unanswered.CENTER);
		statusPanel.setPreferredSize(new Dimension(150,40));
		statusPanel.setBackground(Color.GREEN);
		add(statusPanel, c);
		c.gridy = 2;
		
		JPanel participantPanel = new JPanel();
		participantPanel.add(participants);
		participantPanel.setPreferredSize(new Dimension(150,40));
		participantPanel.setBackground(Color.pink);
		add(participantPanel, c);
		
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
