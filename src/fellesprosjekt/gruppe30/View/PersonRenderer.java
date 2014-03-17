package fellesprosjekt.gruppe30.View;

import fellesprosjekt.gruppe30.Model.Attendant;
import fellesprosjekt.gruppe30.Model.ExternalUser;
import fellesprosjekt.gruppe30.Model.InternalUser;
import fellesprosjekt.gruppe30.Model.User;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PersonRenderer implements ListCellRenderer {
	
	public boolean canSelect = true;

	public PersonRenderer() {
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		Attendant model = (Attendant) value;

		JLabel label;
		if(model.getUser() instanceof InternalUser) {
			String firstName = ((InternalUser) model.getUser()).getFirstName();
			String lastName = ((InternalUser) model.getUser()).getLastName();
			String firstNameStrip = firstName.substring(0, Math.min(firstName.length(), 13));
			
			label = new JLabel(firstNameStrip + " " + lastName.charAt(0) + ".");
		} else {
			label = new JLabel(((ExternalUser) model.getUser()).getEmail());
		}

		File directory = new File(System.getProperty("user.dir"), "Icons");
		new File(directory, "yesIcon.png").getPath();

		ImageIcon accept = new ImageIcon(new File(directory, "yesIcon(small).png").getPath());
		ImageIcon decline = new ImageIcon(new File(directory, "noIcon(small).png").getPath());
		ImageIcon unanswer = new ImageIcon(new File(directory, "unanswer(small).png").getPath());
		ImageIcon creator = new ImageIcon(new File(directory, "creatorIcon(small).png").getPath());
	

		if (model.getStatus() == 0){
			label.setIcon(accept);	
		}
		else if (model.getStatus() == 1){
			label.setIcon(decline);
		}
		else if (model.getStatus() == 2){
			label.setIcon(unanswer);
		}
		
		label.setHorizontalTextPosition(SwingConstants.LEFT);
		label.setHorizontalAlignment(SwingConstants.RIGHT);

		if (canSelect){
			if (isSelected){
				label.setForeground(Color.BLUE);
			}
			
		}
		
		return label;
	}

}
