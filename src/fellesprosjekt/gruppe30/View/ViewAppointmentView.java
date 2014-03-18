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
		participants.setEnabled(true);
		
		if (meetingPlaceField.getText().equals("Place")){
			meetingPlaceField.setVisible(false);
		}
		else {
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
		allUsersAndGroups.setVisible(false);
		deleteButton.setVisible(false);
		inviteByEmail.setVisible(false);
		saveButton.setName("viewsave");
		cancelButton.setName("viewcancel");
	}

	/*public static void main(String[] args) {
		ViewAppointmentView view = new ViewAppointmentView();
		view.setVisible(true);
	}*/

}
