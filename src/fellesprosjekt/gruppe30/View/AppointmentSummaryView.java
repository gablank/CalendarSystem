package fellesprosjekt.gruppe30.View;

import fellesprosjekt.gruppe30.Controller.AppointmentController;
import fellesprosjekt.gruppe30.Controller.CalendarController;
import fellesprosjekt.gruppe30.Controller.LoginController;
import fellesprosjekt.gruppe30.Model.*;
import fellesprosjekt.gruppe30.Client;
import fellesprosjekt.gruppe30.Utilities;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class AppointmentSummaryView extends JPanel implements MouseListener {

	private JLabel 			titleLabel, timeLabel, yesLabel, noLabel, creatorLabel, unanswered;
	private JFrame          frame;
	private JList<Attendant>participants;
	private PersonRenderer  listrenderer;
	private PersonListModel personListModel;
	private int				userCount	= 3;
	private Appointment     model;
	private Client          client;

	public AppointmentSummaryView(Appointment model, java.util.List<InternalUser> toShow, Client client) {
		this.model = model;
		this.client = client;

		personListModel = new PersonListModel();
		for (Attendant attendant : this.model.getAttendants()) {
			if (attendant instanceof ExternalAttendant) {
				continue;
			}

			if (toShow.contains(attendant.getUser())) {
				personListModel.addElement(attendant);
			}
		}


		userCount = (personListModel.size());
		System.out.println(userCount);

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
		yesLabel.setVisible(false);

		noLabel = new JLabel();
		noLabel.setPreferredSize(new Dimension(30, 30));
		noLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		ImageIcon decline = new ImageIcon(new File(directory, "noIcon.png").getPath());
		noLabel.setIcon(decline);
		noLabel.setVisible(false);

		unanswered = new JLabel();
		unanswered.setPreferredSize(new Dimension(30, 30));
		unanswered.setBorder(BorderFactory.createLineBorder(Color.black));
		ImageIcon unanswer = new ImageIcon(new File(directory, "unanswer.png").getPath());
		unanswered.setIcon(unanswer);
		unanswered.setVisible(false);

		participants = new JList<Attendant>();
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
		headerPanel.addMouseListener(this);
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
		listrenderer = PersonRenderer.getInstance();
		participants.setCellRenderer(listrenderer);
        participants.addMouseListener(listrenderer);

		frame = new JFrame();
		frame.add(this);
		this.setMinimumSize(this.getPreferredSize());
		this.setMaximumSize(new Dimension(160, 80 + 20 * userCount));
		frame.pack();
		frame.setVisible(false);
		frame.setResizable(true);

		//test code
		setMeetingStatus();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		titleLabel.setText(model.getTitle());
		timeLabel.setText(Utilities.timeToFormattedString(model.getStart()) + " - " + Utilities.timeToFormattedString(model.getEnd()));


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
	
	public void setMeetingStatus(){
		for (int i=0; i<participants.getModel().getSize(); i++){
			System.out.println(participants.getModel().getElementAt(i).getStatus());
			if (participants.getModel().getElementAt(i).getStatus() == 1){
				noLabel.setVisible(true);
			}
			if (participants.getModel().getElementAt(i).getStatus() == 2){
				unanswered.setVisible(true);
			}
		}
		if (!noLabel.isVisible() && !unanswered.isVisible()){
			yesLabel.setVisible(true);
		}
	}
	
	public void addListener(CalendarController controller) {
		this.addMouseListener((MouseListener) controller);
	}

	public void addMouseListener(MouseListener controller) {
		titleLabel.addMouseListener(controller);
		timeLabel.addMouseListener(controller);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		client.getAppointmentController().open(model);
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	// public static void main(String[] args) {
	// //AppointmentSummaryView view = new AppointmentSummaryView();
	// //view.setVisible(true);
	//
	// }

}
