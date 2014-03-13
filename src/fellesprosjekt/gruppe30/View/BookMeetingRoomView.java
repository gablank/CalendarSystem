package fellesprosjekt.gruppe30.View;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.MaskFormatter;

import fellesprosjekt.gruppe30.Model.MeetingRoom;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.util.EventListener;


//TODO: skaler bedre(?), 
public class BookMeetingRoomView extends JPanel {
	//Salvador Fali aka Kush Wagner
	private JList<MeetingRoom>  roomList;
	private JFormattedTextField startText, endText, dateText;// capacityText;
	private JTextField          capacityText;
	private JLabel              startLabel, endLabel, dateLabel, roomLabel, capacityLabel, spaceLabel;
	private JScrollPane         roomListScroll;
	private JButton             okButton, quitButton;
	private JFrame              frame;

	public BookMeetingRoomView() {
		// gridbag og gridconstraints deklarasjon.
		GridBagLayout gridBag = new GridBagLayout();
		setLayout(gridBag);
		GridBagConstraints cRight = new GridBagConstraints();
		GridBagConstraints cMiddle = new GridBagConstraints();
		GridBagConstraints cLeft = new GridBagConstraints();
		// forflytningsrate og gridplassering.
		cLeft.weightx = 0.5;
		cLeft.gridx = 0;
		cMiddle.weightx = 0.5;
		cMiddle.gridx = 1;
		cRight.weightx = 0.5;
		cRight.gridx = 2;
		cRight.insets = new Insets(5, 5, 5, 5);


		//Layout elements
		spaceLabel = new JLabel("    ");

		roomLabel = new JLabel("Rooms available:");
		roomLabel.setAlignmentX(CENTER_ALIGNMENT);
		roomList = new JList<MeetingRoom>();
		//roomList.setPreferredSize(new Dimension(150, 200));
		roomListScroll = new JScrollPane(roomList);
		roomListScroll.setPreferredSize(new Dimension(180, 120));

		startLabel = new JLabel("Start:");
		MaskFormatter startFormatter;
		try {
			startFormatter = new MaskFormatter("##:##");
			startFormatter.setPlaceholder("00000");
			startText = new JFormattedTextField(startFormatter);
			startText.setHorizontalAlignment(JFormattedTextField.CENTER);
		} catch(ParseException e) {
			e.printStackTrace();
		}
		startText.setPreferredSize(new Dimension(70,20));

		endLabel = new JLabel("End:");
		MaskFormatter endFormatter;
		try {
			endFormatter = new MaskFormatter("##:##");
			endFormatter.setPlaceholder("00000");
			endText = new JFormattedTextField(endFormatter);
			endText.setHorizontalAlignment(JFormattedTextField.CENTER);
		} catch(ParseException e1) {
			e1.printStackTrace();
		}
		endText.setPreferredSize(new Dimension(70, 20));

		dateLabel = new JLabel("Date:");
		MaskFormatter dateFormatter;
		try {
			dateFormatter = new MaskFormatter("##.##.####");
			dateFormatter.setPlaceholder("0000000000");
			dateText = new JFormattedTextField(dateFormatter);
			dateText.setHorizontalAlignment(JFormattedTextField.CENTER);
		} catch(ParseException e1) {
			e1.printStackTrace();
		}
		dateText.setPreferredSize(new Dimension(70, 20));

		capacityLabel = new JLabel("Capacity:");
		capacityText = new JTextField("0");
		capacityText.setHorizontalAlignment(JFormattedTextField.CENTER);
		capacityText.setPreferredSize(new Dimension(70, 20));

		okButton = new JButton("Ok");
		quitButton = new JButton("Quit");

		
		cRight.gridy = 0;
		cMiddle.gridy = 0;
		cLeft.gridy = 0;


		cLeft.gridheight = 5;
		JPanel roomPanel = new JPanel();
		roomPanel.setLayout(new BoxLayout(roomPanel, BoxLayout.Y_AXIS));
		roomPanel.add(roomLabel, cLeft);
		roomPanel.add(roomListScroll, cLeft);
		add(roomPanel, cLeft);
		cLeft.gridheight = 1;

		add(startLabel, cMiddle);
		add(startText, cRight);

		cMiddle.gridy = 1;
		cRight.gridy = 1;

		add(endLabel, cMiddle);
		add(endText, cRight);

		cMiddle.gridy = 2;
		cRight.gridy = 2;

		add(dateLabel, cMiddle);
		add(dateText, cRight);

		cMiddle.gridy = 3;
		cRight.gridy = 3;

		add(capacityLabel, cMiddle);
		add(capacityText, cRight);

		cRight.gridy = 4;
		add(spaceLabel, cRight);
		cRight.gridy = 5;
		JPanel okQuitPanel = new JPanel();
		okQuitPanel.setLayout(new BoxLayout(okQuitPanel, BoxLayout.X_AXIS));
		okQuitPanel.add(okButton, cLeft);
		okQuitPanel.add(spaceLabel, cLeft);
		okQuitPanel.add(quitButton, cRight);
		cRight.gridheight = 2;
		add(okQuitPanel, cRight);

		frame = new JFrame("Reserve Room");
		frame.add(this);
		frame.setVisible(false);
		frame.setResizable(false);
		frame.setSize(500, 250);
		frame.setLocationRelativeTo(null);

	}

	public void setVisible(boolean visible) {
		this.frame.setVisible(visible);
	}

	//listener FEEEST
	public void addListener(EventListener controller) {
		this.addActionListener((ActionListener) controller);
		this.addListSelectionListener((ListSelectionListener) controller);
		this.addKeyListener((KeyListener) controller);
	}

	public void addActionListener(ActionListener controller) {
		okButton.addActionListener(controller);
	}

	public void addKeyListener(KeyListener controller) {
		startText.addKeyListener(controller);
		endText.addKeyListener(controller);
	}

	public void addListSelectionListener(ListSelectionListener controller) {
		roomList.addListSelectionListener(controller);
	}


	public static void main(String[] args) {
		BookMeetingRoomView frame = new BookMeetingRoomView();
		frame.setVisible(true);
	}

}