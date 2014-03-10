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
			int index, boolean isSelected, boolean cellHasFocus) {
		User model = (User) value;
		
		JLabel label;
		label = new JLabel(model.getFirstname() + model.getLastname());
		
		try {
			java.net.URL acceptIcon = new URL("http://www.nevadacpasend.com/files/images/yesIcon.jpg");
		    java.net.URL declineIcon = new URL("http://www.bankrollmob.com/gfx/reviews/no-icon.gif");
		    java.net.URL unansweredIcon = new URL ("http://en.lernu.net/grafikajhoj/aspekto/question-mark.gif");
		    ImageIcon accept = new ImageIcon(acceptIcon);
		    ImageIcon decline = new ImageIcon(declineIcon);
		    ImageIcon unanswered = new ImageIcon(unansweredIcon);
		    //TODO logikk for � velge riktig ikon
		    label.setIcon(accept);
		} catch (MalformedURLException e) {
			System.out.println("bad image URL");
			e.printStackTrace();
		}
		
		if (isSelected){
			label.setFont(label.getFont().deriveFont(14L));
		}
		label.setPreferredSize(new Dimension(250, 40));
		return label;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
