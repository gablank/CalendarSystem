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
		meetingPlaceField.setEditable(false);
		participants.setEnabled(false);

		useMeetingRoom.setVisible(false);
		addButton.setVisible(false);
		removeButton.setVisible(false);
		allUsersAndGroups.setVisible(false);
		deleteButton.setVisible(false);
		inviteByEmail.setVisible(false);
		selectRoom.setVisible(false);
	}

	public static void main(String[] args) {
		ViewAppointmentView view = new ViewAppointmentView();
		view.setVisible(true);
	}

}
