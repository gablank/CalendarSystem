package fellesprosjekt.gruppe30.View;

public class ViewAppointmentView extends AppointmentView {


	public ViewAppointmentView() {
		super();
		titleField.setEditable(false);
		description.setEditable(false);
		description.setBackground(titleField.getBackground());
		dateField.setEditable(false);
		startTimeField.setEditable(false);
		endTimeField.setEditable(false);
		participants.setEnabled(false);
		
		if (!meetingPlaceField.getText().equals("Place")){
			meetingPlaceField.setVisible(true);
		}
		meetingPlaceField.setEditable(false);
		
		if (selectRoom.getText().equals("Select...")){
			selectRoom.setVisible(false);
		}
		else {
			meetingPlaceField.setText(selectRoom.getText());
			meetingPlaceField.setVisible(true);
			selectRoom.setVisible(false);
		}

		useMeetingRoom.setVisible(false);
		addButton.setVisible(false);
		removeButton.setVisible(false);
		participantList.setVisible(false);
		deleteButton.setVisible(false);
		inviteByEmail.setVisible(false);
	}

	public static void main(String[] args) {
		ViewAppointmentView view = new ViewAppointmentView();
		view.setVisible(true);
	}

}
