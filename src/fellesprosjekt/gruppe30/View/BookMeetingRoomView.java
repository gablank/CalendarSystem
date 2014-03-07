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
	private JList room_list; 
	private JFormattedTextField start_text, end_text, date_text, capacity_text;
	private JLabel start_label, end_label, date_label, room_label, capacity_label, spaceLabel;
	private JScrollPane room_list_scroll;
	private JButton ok_button, quit_button;
	private JFrame frame;
	
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
		c_right.insets = new Insets(5,5,5,5);


		
		//Layout elements
		spaceLabel = new JLabel("    ");
		
		room_label = new JLabel("Rooms availible:");
		room_list = new JList();
		room_list.setPreferredSize(new Dimension(200,200));
		room_list_scroll = new JScrollPane(room_list);
		room_list.setVisible(true);
		
		start_label = new JLabel("Start:");
		MaskFormatter start_formatter;
		try {
			start_formatter = new MaskFormatter("##:##");
			start_formatter.setPlaceholder("00000");
			start_text = new JFormattedTextField(start_formatter);
			start_text.setHorizontalAlignment(start_text.CENTER);
		} catch(ParseException e) {
			start_text.setText("hei");
		}
		start_text.setColumns(5);
		
		end_label = new JLabel("End:");
		MaskFormatter end_formatter;
		try {
			end_formatter = new MaskFormatter("##:##");
			end_formatter.setPlaceholder("00000");
			end_text = new JFormattedTextField(end_formatter);
			end_text.setHorizontalAlignment(end_text.CENTER);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		end_text.setColumns(5);
		
		date_label = new JLabel("Date:");
		MaskFormatter date_formatter;
		try {
			date_formatter = new MaskFormatter("##.##.####");
			date_formatter.setPlaceholder("0000000000");
			date_text = new JFormattedTextField(date_formatter);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		capacity_label = new JLabel("Capacity:");
		MaskFormatter capacity_formatter;
		try {
			capacity_formatter = new MaskFormatter("##");
			capacity_formatter.setPlaceholder("0");
			capacity_text = new JFormattedTextField(capacity_formatter);
			capacity_text.setHorizontalAlignment(capacity_text.CENTER);
		} catch (ParseException e) {
			capacity_text.setText("hei");
		}
		capacity_text.setColumns(2);
		
		ok_button = new JButton("Ok");
		quit_button = new JButton("Quit");
		
		c_right.gridy = 0;
		c_middle.gridy = 0;
		c_left.gridy = 0;
		
		
		c_left.gridheight = 5;
		JPanel room_panel = new JPanel();
		room_panel.setLayout(new BoxLayout(room_panel, BoxLayout.Y_AXIS));
		room_panel.add(room_label, c_left);
		room_panel.add(room_list_scroll, c_left);
		add(room_panel, c_left);
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
		JPanel ok_quit_panel  = new JPanel();
		ok_quit_panel.setLayout(new BoxLayout(ok_quit_panel, BoxLayout.X_AXIS));
		ok_quit_panel.add(ok_button, c_left);
		ok_quit_panel.add(spaceLabel, c_left);
		ok_quit_panel.add(quit_button, c_right);
		add(ok_quit_panel, c_right);
		
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
		 ok_button.addActionListener(controller);
	 }
	 
	 public void addKeyListener(KeyListener controller) {
		 start_text.addKeyListener(controller);
		 end_text.addKeyListener(controller);
	 }
	 
	 public void addListSelectionListener(ListSelectionListener controller){
		 room_list.addListSelectionListener(controller);
	 }
	 
	
	
	public static void main(String[] args) {
		BookMeetingRoomView frame = new BookMeetingRoomView();
		frame.setVisible(true);
	}

}
