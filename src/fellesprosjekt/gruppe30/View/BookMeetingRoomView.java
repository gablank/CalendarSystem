package fellesprosjekt.gruppe30.View;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class BookMeetingRoomView extends JPanel {
	//Salvador Fali aka Kush Wagner
	private JList room_list; 
	private JFormattedTextField start_text, end_text, date_text, capacity_text;
	private JLabel start_label, end_label, date_label, room_label, capacity_label;
	private JScrollPane room_list_scroll;
	private JButton ok_button;
	
	public BookMeetingRoomView() {
		// gridbag og gridconstraints deklarasjon.
		GridBagLayout gridBag = new GridBagLayout();
		setLayout(gridBag);
		GridBagConstraints c_right = new GridBagConstraints();
		GridBagConstraints c_middle = new GridBagConstraints();
		GridBagConstraints c_left = new GridBagConstraints();
		// forflytningsrate og gridplassering.
		c_left.weightx = 0.5;
		c_left.gridx = 0;
		c_middle.weightx = 0.5;
		c_middle.gridx = 1;
		c_right.weightx = 0.5;
		c_right.gridx = 2;


		
		//Layout elements
		room_label = new JLabel("Room:");
		room_list = new JList();
		room_list.setPreferredSize(new Dimension(150,150));
		room_list_scroll = new JScrollPane(room_list);
		
		start_label = new JLabel("Start:");
		start_text = new JFormattedTextField(8);
		
		end_label = new JLabel("End:");
		end_text = new JFormattedTextField(8);
		
		date_label = new JLabel("Date:");
		date_text = new JFormattedTextField(8);
		
		capacity_label = new JLabel("Capacity:");
		capacity_text = new JFormattedTextField(1);
		
		ok_button = new JButton("Ok");
		
		c_right.gridy = 0;
		c_middle.gridy = 0;
		c_left.gridy = 0;
		
		c_right.gridheight = 5; // gjør at Scrollpanel får egen størrelse
		add(room_list, c_left);
		c_right.gridheight = 1;
		c_middle.gridheight = 1;
		c_left.gridheight = 1;
		add(start_label, c_middle);
		add(start_text, c_right);
		
		c_middle.gridy = 1;
		c_right.gridy = 1;
		
		add(end_label, c_middle);
		add(end_text, c_right);
		
		c_middle.gridy = 2;
		c_right.gridy = 2;
		
		add(date_label,c_middle);
		add(date_text,c_right);
		
		c_middle.gridy = 3;
		c_right.gridy = 3;
		
		add(capacity_label, c_middle);
		add(capacity_text, c_right);
		
		c_right.gridy = 5;
		add(ok_button, c_right);
		
		
	}
	
	
	
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Reserve Room");
		BookMeetingRoomView panel = new BookMeetingRoomView();
		frame.add(panel);
		frame.setVisible(true);

	}

}
