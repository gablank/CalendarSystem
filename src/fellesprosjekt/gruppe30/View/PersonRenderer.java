package fellesprosjekt.gruppe30.View;

import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

import fellesprosjekt.gruppe30.Model.User;

public class PersonRenderer implements ListCellRenderer{

	public PersonRenderer() {}

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		User model = (User) value;
		
		JLabel label;
		String firstName = model.getFirstname();
		String lastName = model.getLastname();
		label = new JLabel(firstName + " " + lastName.charAt(0)+ ".");
		
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
