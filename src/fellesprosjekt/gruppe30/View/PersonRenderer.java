package fellesprosjekt.gruppe30.View;

import java.awt.Component;
import java.awt.Dimension;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import fellesprosjekt.gruppe30.Model.User;

public class PersonRenderer implements ListCellRenderer{

	public PersonRenderer() {}

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean is_selected, boolean cell_has_focus) {
		User model = (User) value;
		
		JLabel label;
		label = new JLabel(model.get_firstname() + model.get_lastname());
		
		try {
			java.net.URL accept_icon = new URL("http://www.nevadacpasend.com/files/images/yes_icon.jpg");
		    java.net.URL decline_icon = new URL("http://www.bankrollmob.com/gfx/reviews/no-icon.gif");
		    java.net.URL unanswered_icon = new URL ("http://en.lernu.net/grafikajhoj/aspekto/question-mark.gif");
		    ImageIcon accept = new ImageIcon(accept_icon);
		    ImageIcon decline = new ImageIcon(decline_icon);
		    ImageIcon unanswered = new ImageIcon(unanswered_icon);
		    //TODO logikk for å velge riktig ikon
		    label.setIcon(accept);
		} catch (MalformedURLException e) {
			System.out.println("bad image URL");
			e.printStackTrace();
		}
		
		if (is_selected){
			label.setFont(label.getFont().deriveFont(14L));
		}
		label.setPreferredSize(new Dimension(250, 40));
		return label;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
