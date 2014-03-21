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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

public class AppointmentSummaryView extends JPanel implements MouseListener, PropertyChangeListener {

	private JLabel 			titleLabel, timeLabel, yesLabel, noLabel, creatorLabel, unanswered;
	private JFrame          frame;
	private JList<Attendant>participants;
	private PersonRenderer  listrenderer;
	private PersonListModel personListModel;
	private int				userCount	= 3;
	private Appointment     model;
	private Client          client;

	public AppointmentSummaryView(Appointment model, java.util.List<InternalUser> toShow, Client client) {
		this.client = client;

		model.addListener(this);
		personListModel = new PersonListModel();

		setModel(model, toShow);

		GridBagConstraints c = new GridBagConstraints();
		setLayout(new GridBagLayout());

		//set appearance of all components
		File directory = new File(System.getProperty("user.dir"), "Icons");

		titleLabel = new JLabel();
		titleLabel.setOpaque(false);
		titleLabel.setBackground(Color.GREEN);
		timeLabel = new JLabel();
		timeLabel.setOpaque(false);

		yesLabel = new JLabel();
		yesLabel.setPreferredSize(new Dimension(30, 30));
		yesLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		ImageIcon accept = new ImageIcon(new File(directory, "yesIcon.png").getPath());
		yesLabel.setIcon(accept);
		yesLabel.setOpaque(false);
		yesLabel.setVisible(false);

		noLabel = new JLabel();
		noLabel.setPreferredSize(new Dimension(30, 30));
		noLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		ImageIcon decline = new ImageIcon(new File(directory, "noIcon.png").getPath());
		noLabel.setIcon(decline);
		noLabel.setOpaque(false);
		noLabel.setVisible(false);

		unanswered = new JLabel();
		unanswered.setPreferredSize(new Dimension(30, 30));
		unanswered.setBorder(BorderFactory.createLineBorder(Color.black));
		ImageIcon unanswer = new ImageIcon(new File(directory, "unanswer.png").getPath());
		unanswered.setIcon(unanswer);
		unanswered.setOpaque(false);
		unanswered.setVisible(false);

		participants = new JList<Attendant>();
		participants.setName("appointmentSummaryViewParticipants");
		participants.setMinimumSize(new Dimension(130, 20 * userCount));
		participants.setMaximumSize(new Dimension(130, 20 * userCount));
		participants.setLayout(new BoxLayout(participants, BoxLayout.Y_AXIS));
		participants.setVisibleRowCount(4);
		participants.setBackground(Color.WHITE);
		participants.setBorder(BorderFactory.createLineBorder(Color.black));


		//build a gridbag
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 0, 0);

		JPanel headerPanel = new JPanel();
		headerPanel.setOpaque(false);
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
		statusPanel.setOpaque(false);
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
		participants.addMouseListener(client.getAppointmentController());
        participants.addMouseListener(this);

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
		//System.out.println(participants.getAppointmentModel().getSize());
		//end test code
	}

	public void setModel(java.util.List<InternalUser> toShow) {
		setModel(this.model, toShow);
	}

	public void setModel(Appointment appointment, java.util.List<InternalUser> toShow) {
		this.model = appointment;
		setBackgroundColor();
		personListModel.removeAllElements();
		for (Attendant attendant : this.model.getAttendants()) {
			if (attendant instanceof ExternalAttendant) {
				continue;
			}

			if (toShow.contains(attendant.getUser())) {
				personListModel.addElement(attendant);
			}
		}

		userCount = (personListModel.size());
	}

	private void setBackgroundColor() {
		this.setBackground(Color.LIGHT_GRAY);
		// If the logged in user is invited to the appointment
		Attendant loggedInUser;
		loggedInUser = Utilities.getAttendantByUserAppointment(model, client.getLoggedInUser());
		if(loggedInUser != null) {
			if(((InternalAttendant) loggedInUser).getLastChecked().before(model.getLastUpdated())) {
				this.setBackground(Color.GRAY);
			}
		}
	}

	public void setVisible(boolean visible) {
		this.frame.setVisible(visible);
	}

	public void setPersonListModel(PersonListModel model) {
		this.personListModel = model;
		participants.setModel(model);
	}
	
	public void setMeetingStatus(){
		int declineCheck = 0;
		int unanswerCheck = 0;
		for(Attendant attendant : model.getAttendants()) {
			if(attendant.getStatus() == Attendant.Status.NOT_ANSWERED) {
				unanswered.setVisible(true);
				yesLabel.setVisible(false);
				unanswerCheck = 1;
			}
			if(attendant.getStatus() == Attendant.Status.NOT_ATTENDING) {
				noLabel.setVisible(true);
				yesLabel.setVisible(false);
				declineCheck = 1;
			}
			if (unanswerCheck == 0){
				unanswered.setVisible(false);
			}
			if (declineCheck == 0){
				noLabel.setVisible(false);
			}
			if (!noLabel.isVisible() && !unanswered.isVisible()){
				yesLabel.setVisible(true);
			}
		}
		participants.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
        if(arg0.getSource() instanceof JPanel) {
		    client.getAppointmentController().open(model);
	        Attendant loggedInAttendant = Utilities.getAttendantByUserAppointment(model, client.getLoggedInUser());
	        if(loggedInAttendant != null) {
		        ((InternalAttendant) loggedInAttendant).setLastChecked();
		        client.getAppointmentController().save(model, true, false);
		        SwingUtilities.invokeLater(new Runnable() {
			        @Override
			        public void run() {
				        setBackgroundColor();
			        }
		        });
	        }
        }
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

	@Override
	public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setMeetingStatus();
				setModel(client.getCalendarModel().getUsers());
			}
		});
	}


	// public static void main(String[] args) {
	// //AppointmentSummaryView view = new AppointmentSummaryView();
	// //view.setVisible(true);
	//
	// }

}
