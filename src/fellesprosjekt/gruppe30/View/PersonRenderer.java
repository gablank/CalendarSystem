package fellesprosjekt.gruppe30.View;

import fellesprosjekt.gruppe30.Model.Attendant;
import fellesprosjekt.gruppe30.Model.ExternalUser;
import fellesprosjekt.gruppe30.Model.InternalUser;
import fellesprosjekt.gruppe30.Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class PersonRenderer implements ListCellRenderer, MouseListener {
	
	public boolean  canSelect = true;
	JLabel          label;
	ImageIcon       accept, decline, unanswer, creator;
	Attendant       model;

	public PersonRenderer() {
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		model = (Attendant) value;
		
		//make the renderer display the name or email correctly
		if(model.getUser() instanceof InternalUser) {
			String firstName = ((InternalUser) model.getUser()).getFirstName();
			String lastName = ((InternalUser) model.getUser()).getLastName();
			String firstNameStrip = firstName.substring(0, Math.min(firstName.length(), 13));
			
			label = new JLabel(firstNameStrip + " " + lastName.charAt(0) + ".");
		} else {
			label = new JLabel(((ExternalUser) model.getUser()).getEmail());
		}
		label.setHorizontalTextPosition(SwingConstants.LEFT);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		list.addMouseListener(this);

		//prepare Icon images
		File directory = new File(System.getProperty("user.dir"), "Icons");
		new File(directory, "yesIcon.png").getPath();
		accept = new ImageIcon(new File(directory, "yesIcon(small).png").getPath());
		decline = new ImageIcon(new File(directory, "noIcon(small).png").getPath());
		unanswer = new ImageIcon(new File(directory, "unanswer(small).png").getPath());
		creator = new ImageIcon(new File(directory, "creatorIcon(small).png").getPath());
	
		
		//choose correct icon to display
		if (model.getStatus() == 0){
			label.setIcon(accept);	
		}
		else if (model.getStatus() == 1){
			label.setIcon(decline);
		}
		else if (model.getStatus() == 2){
			label.setIcon(unanswer);
		}

		if (canSelect){
			if (isSelected){
				label.setForeground(Color.BLUE);
				if (model.getStatus() < 2){
					model.setStatus(model.getStatus()+1);
					list.repaint();
				}
				else if (model.getStatus() == 2) {
					model.setStatus(0);
					list.repaint();
				}
			}
			
		}
		return label;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
