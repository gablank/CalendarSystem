package fellesprosjekt.gruppe30.View;

import fellesprosjekt.gruppe30.Model.InternalUser;
import fellesprosjekt.gruppe30.Model.PersonListModel;
import fellesprosjekt.gruppe30.Model.User;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class AppointmentSummaryView extends JPanel {

	private JLabel 			titleLabel, timeLabel, yesLabel, noLabel, creatorLabel, unanswered;
	private JFrame          frame;
	private JList<User>     participants;
	private PersonRenderer  listrenderer;
	private PersonListModel personListModel;
	private int             userCount = 3;

	public AppointmentSummaryView() {
		GridBagConstraints c = new GridBagConstraints();
		setLayout(new GridBagLayout());

		//set appearance of all components
		File directory = new File(System.getProperty("user.dir"), "Icons");
		new File(directory, "yesIcon.png").getPath();

		titleLabel = new JLabel();
		timeLabel = new JLabel();

		yesLabel = new JLabel();
		yesLabel.setPreferredSize(new Dimension(30, 30));
		yesLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		ImageIcon accept = new ImageIcon(new File(directory, "yesIcon.png").getPath());
		yesLabel.setIcon(accept);

		noLabel = new JLabel();
		noLabel.setPreferredSize(new Dimension(30, 30));
		noLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		ImageIcon decline = new ImageIcon(new File(directory, "noIcon.png").getPath());
		noLabel.setIcon(decline);

		unanswered = new JLabel();
		unanswered.setPreferredSize(new Dimension(30, 30));
		unanswered.setBorder(BorderFactory.createLineBorder(Color.black));
		ImageIcon unanswer = new ImageIcon(new File(directory, "unanswer.png").getPath());
		unanswered.setIcon(unanswer);

		participants = new JList<User>();
		participants.setMinimumSize(new Dimension(130, 20 * userCount));
		participants.setLayout(new BoxLayout(participants, BoxLayout.Y_AXIS));
		participants.setVisibleRowCount(4);
		participants.setBackground(Color.WHITE);
		participants.setBorder(BorderFactory.createLineBorder(Color.black));


		//build a gridbag
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 0, 0);

		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		headerPanel.add(titleLabel);
		timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		headerPanel.add(timeLabel);
		headerPanel.setPreferredSize(new Dimension(130, 40));
		add(headerPanel, c);
		c.gridy = 1;

		JPanel statusPanel = new JPanel();
		statusPanel.add(yesLabel);
		yesLabel.setHorizontalAlignment(JLabel.CENTER);
		statusPanel.add(noLabel);
		noLabel.setHorizontalAlignment(JLabel.CENTER);
		statusPanel.add(unanswered);
		unanswered.setHorizontalAlignment(JLabel.CENTER);
		statusPanel.setPreferredSize(new Dimension(130, 40));
		add(statusPanel, c);

		c.gridy = 2;
		add(participants, c);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		listrenderer = new PersonRenderer();
		participants.setCellRenderer(listrenderer);

		frame = new JFrame();
		frame.add(this);
		this.setMinimumSize(this.getPreferredSize());
		this.setMaximumSize(new Dimension(160, 80 + 20 * userCount));
		frame.pack();
		frame.setVisible(false);
		frame.setResizable(true);

		//test code
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		titleLabel.setText("Lunch");
		timeLabel.setText("12:15 - 12:45");

		personListModel = new PersonListModel();
		personListModel.addElement(new InternalUser("email", "Jonathan", "Cinderella"));
		for(int i = 0; i < userCount - 1; i++) {
			personListModel.addElement(new InternalUser("email", "Emil", "Heien"));
		}
		this.setPersonListModel(personListModel);
		frame.pack();
		//System.out.println(participants.getModel().getSize());
		//end test code
		

	}
	
	public void updateFrame(){
		userCount = (participants.getModel().getSize());
		this.frame.repaint();
	}

	public void setVisible(boolean visible) {
		this.frame.setVisible(visible);
	}

	public void setPersonListModel(PersonListModel model) {
		this.personListModel = model;
		participants.setModel(model);
	}


	public static void main(String[] args) {
		AppointmentSummaryView view = new AppointmentSummaryView();
		view.setVisible(true);

	}

}
