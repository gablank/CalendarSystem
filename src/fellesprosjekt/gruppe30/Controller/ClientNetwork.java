package fellesprosjekt.gruppe30.Controller;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

import fellesprosjekt.gruppe30.Model.*;
import fellesprosjekt.gruppe30.Utilities;

import org.json.JSONArray;
import org.json.JSONObject;

import fellesprosjekt.gruppe30.Client;

public class ClientNetwork extends Network {
	int serverPort = 11223;
	String	serverAddress	= "www.furic.pw";
	Client client;

	public ClientNetwork(Client client) {
		this.client = client;
	}

	@Override
	public void run() {
		try {
			connectionSocket = new Socket(serverAddress, serverPort);
			System.out.println("ClientNetwork: Connected to server: " + connectionSocket.getRemoteSocketAddress());

		} catch (IOException e) {
			System.out.println("ClientNetwork: Failed to set up connection, terminating...");
			e.printStackTrace();
			return;
		}
		super.run();
	}

	@Override
	protected void handleMessage(JSONObject message) {
		if (message.has("type")) {
			String type = message.getString("type");

			switch (type) {
				case "login":
					handleLoginMessage(message);
					break;

				case "internalUser":
					handleInternalUserMessage(message);
					break;

				case "externalUser":
					handleExternalUserMessage(message);
					break;

				case "appointment":
					handleAppointmentMessage(message);
					break;

				case "meetingRoom":
					handleMeetingRoomMessage(message);
					break;

				case "alarm":
					handleAlarmMessage(message);
					break;

				case "group":
					handleGroupMessage(message);
					break;

				default:
					System.out.println("got a message of invalid type: " + message.toString());
			}

		} else {
			System.out.println("a message had no type field: " + message.toString());
		}
	}

	private void handleLoginMessage(JSONObject message) {
		if (message.has("status") && message.has("statusMessage")) {

			String status = message.getString("status");
			String statusMessage = message.getString("statusMessage");

			String username = "";
			if (message.has("username"))
				username = message.getString("username");

			client.getLoginController().handleLoginResponse(status.equals("success"), username);

		} else {
			System.out.println("a login message did not have the required fields: " + message.toString());
		}
	}
	
	private void handleInternalUserMessage(JSONObject message) {
		if (message.has("firstName") && message.has("lastName") && message.has("email")) {

			String firstName = message.getString("firstName");
			String lastName = message.getString("lastName");
			String email = message.getString("email");

			InternalUser user = new InternalUser(email, firstName, lastName);

			client.addUser(user);
			System.out.println("successfully added internal user!");
		}
	}

	private void handleExternalUserMessage(JSONObject message) {
		if (message.has("email")) {

			String email = message.getString("email");

			ExternalUser user = new ExternalUser(email);

			client.addUser(user);
			System.out.println("successfully added external user!");
		}
	}

	private void handleAppointmentMessage(JSONObject message) {
		String action = "";
		if (message.has("action"))
			action = message.getString("action");

		if (("new".equals(action) || "change".equals(action)) && message.has("id") && message.has("title") && message.has("description") && message.has("start") && message.has("end") && message.has("meetingPlace") && message.has("attendants") && message.has("owner") && message.has("lastUpdated") && message.has("meetingRoom")) {

			String title = message.getString("title");
			String description = message.getString("description");
			long start = message.getLong("start");
			long end = message.getLong("end");
			String meetingPlace = message.getString("meetingPlace");
			JSONArray attendants = message.getJSONArray("attendants");
			String ownerEmail = message.getString("owner");
			int meetingRoomId = message.getInt("meetingRoom");
			long lastUpdated = message.getLong("lastUpdated");

			InternalUser owner = (InternalUser) Utilities.getUserByEmail(ownerEmail, client.getUsers());

			if (owner == null) {
				System.out.println("failed handling an appointment message, owner was not found. Message: " + message.toString());
				return;
			}

			Date startDate = new Date(start);
			Date endDate = new Date(end);

			MeetingRoom meetingRoom = Utilities.getMeetingRoomById(meetingRoomId, client.getMeetingRooms());

			Appointment appointment = null;

			if (meetingRoom == null && meetingPlace.isEmpty()) {
				System.out.println("failed handling an appointment message, no meetingPlace or valid meetingRoom was set. Message: " + message.toString());
				return;

			} else if (meetingRoom != null && !meetingPlace.isEmpty()) {
				System.out.println("failed handling an appointment message, both meetingPlace and meetingRoom were set. Message: " + message.toString());
				return;

			} else if (meetingRoom != null) {
				appointment = new Appointment(owner, title, description, startDate, endDate, meetingRoom);

			} else {
				appointment = new Appointment(owner, title, description, startDate, endDate, meetingPlace);

			}

			for (int i = 0; i < attendants.length(); i++) {
				JSONObject attendantObj = attendants.getJSONObject(i);

				if (attendantObj.has("type") 
						&& attendantObj.has("email") 
						&& attendantObj.has("status")) {

					String attendantType = attendantObj.getString("type");
					String attendantEmail = attendantObj.getString("email");
					int attendantStatus = attendantObj.getInt("status");

					User user = Utilities.getUserByEmail(attendantEmail, client.getUsers());

					if (attendantType.equals("externalAttendant")) {
						ExternalAttendant externalAttendant = new ExternalAttendant((ExternalUser) user, appointment);
						externalAttendant.setStatus(attendantStatus);
						appointment.addAttendant(externalAttendant);
						System.out.println("successfully added external attendant to appointment");

					} else if (attendantType.equals("internalAttendant")) {
						if (attendantObj.has("visibleOnCalendar") && attendantObj.has("lastChecked")) {
							boolean visibleOnCalendar = attendantObj.getBoolean("visibleOnCalendar");
							long lastChecked = attendantObj.getLong("lastChecked");

							Date lastCheckedDate = new Date(lastChecked);

							InternalAttendant internalAttendant = new InternalAttendant((InternalUser) user, appointment);
							internalAttendant.setStatus(attendantStatus);
							internalAttendant.setLastChecked(lastCheckedDate);
							internalAttendant.setVisibleOnCalendar(visibleOnCalendar);
							appointment.addAttendant(internalAttendant);
							System.out.println("successfully added internal attendant to appointment");

						} else {
							System.out.println("failed handling an appointment message, an internal attendant did not have the required fields: " + attendantObj.toString());
							return;
						}

					} else {
						System.out.println("failed handling an appointment message, an attendant had no type: " + message.toString());
						return;
					}

				} else {
					System.out.println("failed handling an appointment message, an attendant did not have the required fields: " + attendantObj.toString());
					return;
				}
			}

			int id = message.getInt("id");
			appointment.setId(id);

			if ("change".equals(action)) {
				Appointment oldAppointment = Utilities.getAppointmentById(id, client.getAppointments());
				if (Utilities.getAppointmentById(id, client.getAppointments()) == null) {
					System.out.println("failed handling an change appointment message, the appointment with specified id could not be found.");
					return;
				}

				oldAppointment.copyAllFrom(appointment);
				System.out.println("successfully changed an appointment!");

			} else {
				client.addAppointment(appointment);
				System.out.println("successfully added an appointment!");
			}

		} else if ("remove".equals(action) && message.has("id")) {
			int id = message.getInt("id");

			Appointment appointment = Utilities.getAppointmentById(id, client.getAppointments());
			client.removeAppointment(appointment);
			System.out.println("successfully removed appointment!");

		} else {
			System.out.println("an appointment message did not have the required fields: " + message.toString());
		}
	}

	private void handleMeetingRoomMessage(JSONObject message) {
		if (message.has("id") && message.has("roomSize")) {

			int id = message.getInt("id");
			int roomSize = message.getInt("roomSize");

			MeetingRoom meetingRoom = new MeetingRoom(roomSize);
			meetingRoom.setId(id);

			client.addMeetingRoom(meetingRoom);
			System.out.println("successfully added meetingroom!");

		} else {
			System.out.println("a meetingRoom message did not have the required fields: " + message.toString());
		}
	}

	private void handleAlarmMessage(JSONObject message){
		String action = "";
		
		if (message.has("action"))
			action = message.getString("action");

		if (message.has("userEmail") && message.has("appointmentId")) {

			String userEmail = message.getString("userEmail");
			int appointmentId = message.getInt("appointmentId");

			InternalUser user = (InternalUser) Utilities.getUserByEmail(userEmail, client.getUsers());
			Appointment appointment = Utilities.getAppointmentById(appointmentId, client.getAppointments());

			if ("new".equals(action)) {
				if (!message.has("time")) {
					System.out.println("an add alarm message did not have the required time field: " + message.toString());
					return;
				}

				long time = message.getLong("time");
				Date date = new Date(time);
				Alarm alarm = new Alarm(user, appointment, date);

				client.addAlarm(alarm);
				System.out.println("successfully added alarm!");

			} else if ("change".equals(action)) {
				if (!message.has("time")) {
					System.out.println("an add alarm message did not have the required time field: " + message.toString());
					return;
				}

				long time = message.getLong("time");
				Date date = new Date(time);

				Utilities.getAlarm(appointment, user, client.getAlarms()).setDate(date);
				System.out.println("successfully changed alarm!");

			} else if ("remove".equals(action)) {
				client.removeAlarm(appointment, user);
				System.out.println("successfully removed alarm!");

			} else {
				System.out.println("an alarm message had invalid (or no) action field: " + message.toString());
			}

		} else {
			System.out.println("an alarm message did not have the required fields: " + message.toString());
		}
	}

	private void handleGroupMessage(JSONObject message) {
		if (message.has("name") && message.has("members")) {

			String name = message.getString("name");
			JSONArray memberEmails = message.getJSONArray("members");

			Group group = new Group(name);

			for (int i = 0; i < memberEmails.length(); i++) {
				String email = memberEmails.getString(i);
				User member = Utilities.getUserByEmail(email, client.getUsers());
				group.addMember((InternalUser) member);
			}

			client.addGroup(group);
			System.out.println("successfully added group!");

		} else {
			System.out.println("a group message did not have the required fields: " + message.toString());
		}
	}

}
