package fellesprosjekt.gruppe30.View;

import fellesprosjekt.gruppe30.Model.Attendant;
import fellesprosjekt.gruppe30.Model.ExternalUser;
import fellesprosjekt.gruppe30.Model.InternalUser;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PersonRenderer implements ListCellRenderer {
	private static PersonRenderer instance;
	ImageIcon       accept, decline, unanswer, creator;
	Attendant       model;


	protected PersonRenderer() {
        //prepare Icon images
        File directory = new File(System.getProperty("user.dir"), "Icons");
        accept   = new ImageIcon(new File(directory, "yesIcon(small).png")    .getPath());
        decline  = new ImageIcon(new File(directory, "noIcon(small).png")     .getPath());
        unanswer = new ImageIcon(new File(directory, "unanswer(small).png")   .getPath());
        creator  = new ImageIcon(new File(directory, "creatorIcon(small).png").getPath());
    }

    public static PersonRenderer getInstance() {
        if(instance == null) {
            instance = new PersonRenderer();
        }
        return instance;
    }

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		model = (Attendant) value;

		JPanel jPanel = new JPanel();
		jPanel.setLayout(new GridBagLayout());
		jPanel.setMinimumSize(new Dimension(100, 1));
		jPanel.setMaximumSize(new Dimension(100, 50));

		JLabel nameLabel;
		//make the renderer display the name or email correctly
		if(model.getUser() instanceof InternalUser) {
			String firstName = ((InternalUser) model.getUser()).getFirstName();
			String lastName = ((InternalUser) model.getUser()).getLastName();
			String firstNameStrip = firstName.substring(0, Math.min(firstName.length(), 13));
			
			nameLabel = new JLabel(firstNameStrip + " " + lastName.charAt(0) + ".");
		} else {
			nameLabel = new JLabel(((ExternalUser) model.getUser()).getEmail());
		}
		nameLabel.setHorizontalTextPosition(SwingConstants.LEFT);

		JLabel iconLabel = new JLabel();
		//choose correct icon to display
		if (model.getStatus() == 0){
			iconLabel.setIcon(accept);
		}
		else if (model.getStatus() == 1){
			iconLabel.setIcon(decline);
		}
		else if (model.getStatus() == 2){
			iconLabel.setIcon(unanswer);
		}
        if(model.getUser() == model.getAppointment().getOwner()) {
	        iconLabel.setIcon(creator);
        }
		iconLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        if (cellHasFocus && isSelected) {
            nameLabel.setBackground(list.getSelectionBackground());
            nameLabel.setOpaque(true);
        }

		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 1;
		gc.anchor = GridBagConstraints.WEST;
		jPanel.add(nameLabel, gc);
		gc.weightx = 0;
		gc.gridx = 1;
		jPanel.add(iconLabel, gc);

		//nameLabel.addMouseListener(this);

		if(model.getCrash()) {
			jPanel.setBackground(Color.RED);
		}

		return jPanel;
	}
}
