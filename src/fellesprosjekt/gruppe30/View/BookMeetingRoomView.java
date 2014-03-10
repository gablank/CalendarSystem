package fellesprosjekt.gruppe30.View;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.EventListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import javax.xml.bind.Marshaller.Listener;


//TODO: skaler bedre(?), 
public class BookMeetingRoomView extends JPanel {
	//Salvador Fali aka Kush Wagner
	private JList roomList; 
	private JFormattedTextField startText, endText, dateText, capacityText;
	private JLabel startLabel, endLabel, dateLabel, roomLabel, capacityLabel, spaceLabel;
	private JScrollPane roomListScroll;
	private JButton okButton, quitButton;
	private JFrame frame;
	
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
		cRight.insets = new Insets(5,5,5,5);


		
		//Layout elements
		spaceLabel = new JLabel("    ");
		
		roomLabel = new JLabel("Rooms availible:");
		roomList = new JList();
		roomList.setPreferredSize(new Dimension(200,200));
		roomListScroll = new JScrollPane(roomList);
		roomList.setVisible(true);
		
		startLabel = new JLabel("Start:");
		MaskFormatter startFormatter;
		try {
			startFormatter = new MaskFormatter("##:##");
			startFormatter.setPlaceholder("00000");
			startText = new JFormattedTextField(startFormatter);
			startText.setHorizontalAlignment(startText.CENTER);
		} catch(ParseException e) {
			startText.setText("hei");
		}
		startText.setColumns(5);
		
		endLabel = new JLabel("End:");
		MaskFormatter endFormatter;
		try {
			endFormatter = new MaskFormatter("##:##");
			endFormatter.setPlaceholder("00000");
			endText = new JFormattedTextField(endFormatter);
			endText.setHorizontalAlignment(endText.CENTER);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		endText.setColumns(5);
		
		dateLabel = new JLabel("Date:");
		MaskFormatter dateFormatter;
		try {
			dateFormatter = new MaskFormatter("##.##.####");
			dateFormatter.setPlaceholder("0000000000");
			dateText = new JFormattedTextField(dateFormatter);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		capacityLabel = new JLabel("Capacity:");
		MaskFormatter capacityFormatter;
		try {
			capacityFormatter = new MaskFormatter("##");
			capacityFormatter.setPlaceholder("0");
			capacityText = new JFormattedTextField(capacityFormatter);
			capacityText.setHorizontalAlignment(capacityText.CENTER);
		} catch (ParseException e) {
			capacityText.setText("hei");
		}
		capacityText.setColumns(2);
		
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
		
		add(dateLabel,cMiddle);
		add(dateText,cRight);
		
		cMiddle.gridy = 3;
		cRight.gridy = 3;
		
		add(capacityLabel, cMiddle);
		add(capacityText, cRight);
		
		cRight.gridy = 5;
		JPanel okQuitPanel  = new JPanel();
		okQuitPanel.setLayout(new BoxLayout(okQuitPanel, BoxLayout.X_AXIS));
		okQuitPanel.add(okButton, cLeft);
		okQuitPanel.add(spaceLabel, cLeft);
		okQuitPanel.add(quitButton, cRight);
		add(okQuitPanel, cRight);
		
		frame = new JFrame("Reserve Room");
		frame.add(this);
		frame.setVisible(false);
		frame.setSize(500, 250);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
	}
	
	public void setVisible(boolean visible) {
		this.frame.setVisible(visible);
	}
	
	//listener FEEEST
	 public void addListener (EventListener controller) {
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
	 
	 public void addListSelectionListener(ListSelectionListener controller){
		 roomList.addListSelectionListener(controller);
	 }
	 
	
	
	public static void main(String[] args) {
		BookMeetingRoomView frame = new BookMeetingRoomView();
		frame.setVisible(true);
	}

}