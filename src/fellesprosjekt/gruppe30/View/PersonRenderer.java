package fellesprosjekt.gruppe30.View;

import fellesprosjekt.gruppe30.Client;
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
	private static PersonRenderer instance;
	JLabel          label;
	ImageIcon       accept, decline, unanswer, creator;
	Attendant       model;
    private static Client client;


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
        if(model.getUser() == model.getAppointment().getOwner()) {
            label.setIcon(creator);
        }

        if (cellHasFocus && isSelected) {
            label.setBackground(list.getSelectionBackground());
            label.setOpaque(true);
        }
		return label;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
        Attendant attendant = ((JList<Attendant>) e.getSource()).getSelectedValue();
        if(attendant.getUser() == client.getLoggedInUser() || client.getLoggedInUser() == attendant.getAppointment().getOwner()) {
            int newStatus = (attendant.getStatus() + 1) % 3;
            attendant.setStatus(newStatus);
            ((JList)e.getSource()).repaint();
        }
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

    public static void setClient(Client client) {
        PersonRenderer.client = client;
    }
}
