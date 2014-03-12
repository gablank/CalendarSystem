package fellesprosjekt.gruppe30.View;

import java.awt.Component;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

import fellesprosjekt.gruppe30.Model.ExternalUser;
import fellesprosjekt.gruppe30.Model.InternalUser;
import fellesprosjekt.gruppe30.Model.User;

public class PersonRenderer implements ListCellRenderer{

	public PersonRenderer() {}

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		User model = (User) value;
		
		JLabel label;
		if(model instanceof InternalUser) {
			String firstName = ((InternalUser) model).getFirstName();
			String lastName = ((InternalUser) model).getLastName();
			label = new JLabel(firstName + " " + lastName.charAt(0) + ".");
		} else {
			label = new JLabel(((ExternalUser)model).getEmail());
		}
		
		File directory = new File(System.getProperty("user.dir"), "Icons");
		new File(directory,"yesIcon.png").getPath();
		
		ImageIcon accept = new ImageIcon(new File(directory,"yesIcon(small).png").getPath());
		ImageIcon decline = new ImageIcon(new File(directory,"noIcon(small).png").getPath());
		ImageIcon unanswer = new ImageIcon(new File(directory,"unanswer(small).png").getPath());
		ImageIcon creator = new ImageIcon (new File(directory,"creatorIcon(small).png").getPath());
			/*
		     *TODO logic to choose correct icon
		     */
		label.setIcon(accept);
		label.setHorizontalTextPosition(SwingConstants.LEFT);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setIconTextGap(40 - label.getText().length());
		
		return label;
	}

}
