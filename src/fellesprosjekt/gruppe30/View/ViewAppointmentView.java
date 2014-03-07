package fellesprosjekt.gruppe30.View;

import java.awt.Color;

public class ViewAppointmentView extends AppointmentView{

	
	public ViewAppointmentView() {
		super();
		title_field.setEditable(false);
		description.setEditable(false);
		description.setBackground(title_field.getBackground());
		date_field.setEditable(false);
		start_time_field.setEditable(false);
		end_time_field.setEditable(false);
		meeting_room_field.setEditable(false);
		
		use_meeting_room.setVisible(false);
		add_button.setVisible(false);
		remove_button.setVisible(false);
		participant_list.setVisible(false);
		delete_button.setVisible(false);
	}

	public static void main(String[] args) {
		ViewAppointmentView view = new ViewAppointmentView();
	}

}
