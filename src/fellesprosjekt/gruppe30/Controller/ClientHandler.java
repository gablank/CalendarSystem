package fellesprosjekt.gruppe30.Controller;

import fellesprosjekt.gruppe30.Model.*;
import fellesprosjekt.gruppe30.Server;
import fellesprosjekt.gruppe30.Utilities;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;


public class ClientHandler extends Network {
	Server server;
	String username = null;

	public ClientHandler(Socket connectionSocket, Server server) {
		this.connectionSocket = connectionSocket;
		this.server = server;
		System.out.println("ClientHandler created.");
	}

	@Override
	protected void handleMessage(JSONObject message) throws IOException {
		if (message.has("type")) {
			String type = message.getString("type");
			String action = "";

			if (username == null && !type.equals("login")) {
				System.out.println("Recieved message to do something while not logged in, ignoring it. message: " + message.toString());
				return;
			}

			switch (type) {
				case "login":
					handleLoginMessage(message);
					break;

				case "appointment":
					handleAppointmentMessage(message);
					break;

				case "alarm":
					handleAlarmMessage(message);
					break;

				case "logout":
					username = null;
					break;

				default: {
					System.out.println("got a message of invalid type: " + message.toString());
				}
			}

		} else {
			System.out.println("a message had no type field: " + message.toString());
		}
	}

	private void handleLoginMessage(JSONObject message) {
		if (message.has("username") && message.has("password")) {
			String usernameReceived = message.getString("username");
			// password is a sha256 hash (64 chars)
			String password = message.getString("password");

			JSONObject response = new JSONObject();
			response.put("type", "login");

			if (this.server.verifyLogin(usernameReceived, password)) {
				response.put("status", "success");
				response.put("statusMessage", "OK");
				response.put("username", usernameReceived);
				username = usernameReceived;
				sendAllModels();
				System.out.println(username + " has logged in!");
			} else {
				response.put("status", "wrongCombination");
				response.put("statusMessage", "The username and password combination was wrong!");
				System.out.println("Someone tried logging in as " + username + ", but the password was wrong!");
			}

			send(response);

		} else {
			System.out.println("a login message did not have the required fields: " + message.toString());
		}
	}

	private void handleAppointmentMessage(JSONObject message) {
		String action = "";
		if (message.has("action")) {
			action = message.getString("action");
		}

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


			InternalUser owner = (InternalUser) Utilities.getUserByEmail(ownerEmail, server.getUsers());

			if (owner == null) {
				System.out.println("failed handling an appointment message, owner was not found. Message: " + message.toString());
				return;
			}

			Date startDate = new Date(start);
			Date endDate = new Date(end);

			MeetingRoom meetingRoom = Utilities.getMeetingRoomById(meetingRoomId, server.getMeetingRooms());

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

				if (attendantObj.has("type") && attendantObj.has("email") && attendantObj.has("appointmentId") && attendantObj.has("status")) {

					String attendantType = attendantObj.getString("type");
					String attendantEmail = attendantObj.getString("email");
					int appointmentId = attendantObj.getInt("appointmentId");
					int attendantStatus = attendantObj.getInt("status");

					Attendant attendant;
					User user = Utilities.getUserByEmail(attendantEmail, server.getUsers());

					if (appointmentId != appointment.getId()) {
						System.out.println("failed handling an appointment message, an attendant had no type: " + message.toString());
					}

					if (attendantType.equals("externalAttendant")) {
						if (user == null) {
							user = new ExternalUser(attendantEmail);
						}
						attendant = new ExternalAttendant((ExternalUser) user, appointment);
						attendant.setStatus(attendantStatus);
						appointment.addAttendant(attendant);
						System.out.println("successfully added external attendant to appointment");

					} else if (attendantType.equals("internalAttendant")) {
						if (attendantObj.has("visibleOnCalendar") && attendantObj.has("lastChecked")) {
							if (user == null) {
								System.out.println("InternalUser doesn't exist!");
							}

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


			if ("change".equals(action)) {
				int id = message.getInt("id");
				appointment.setId(id);

				Appointment oldAppointment = Utilities.getAppointmentById(id, server.getAppointments());
				if (Utilities.getAppointmentById(id, server.getAppointments()) == null) {
					System.out.println("failed handling an change appointment message, the appointment with specified id could not be found.");
					return;
				}

				oldAppointment.copyAllFrom(appointment);
				server.getDatabase().insertAppointment(oldAppointment);
				System.out.println("successfully changed an appointment!");

			} else {

				server.addAppointment(appointment);
				server.getDatabase().insertAppointment(appointment);
				System.out.println("successfully added an appointment!");
			}

		} else if ("remove".equals(action) && message.has("id")) {
			int id = message.getInt("id");

			Appointment appointment = Utilities.getAppointmentById(id, server.getAppointments());
			server.removeAppointment(appointment);
			System.out.println("successfully removed appointment!");

		} else {
			System.out.println("an appointment message did not have the required fields: " + message.toString());
		}
	}

	private void handleAlarmMessage(JSONObject message) {
		String action = "";
		if (message.has("action"))
			action = message.getString("action");

		if (message.has("userEmail") && message.has("appointmentId")) {

			String userEmail = message.getString("userEmail");
			int appointmentId = message.getInt("appointmentId");

			InternalUser user = (InternalUser) Utilities.getUserByEmail(userEmail, server.getUsers());
			Appointment appointment = Utilities.getAppointmentById(appointmentId, server.getAppointments());

			if ("new".equals(action)) {
				if (!message.has("time")) {
					System.out.println("an add alarm message did not have the required time field: " + message.toString());
					return;
				}

				long time = message.getLong("time");
				Date date = new Date(time);
				Alarm alarm = new Alarm(user, appointment, date);

				server.addAlarm(alarm);
				server.getDatabase().insertAlarm(alarm);
				System.out.println("successfully added alarm!");

			} else if ("change".equals(action)) {
				if (!message.has("time")) {
					System.out.println("a change alarm message did not have the required time field: " + message.toString());
					return;
				}

				long time = message.getLong("time");
				Date date = new Date(time);

				Alarm alarm = Utilities.getAlarm(appointment, user, server.getAlarms());
				alarm.setDate(date);
				server.getDatabase().insertAlarm(alarm);
				System.out.println("successfully changed alarm!");

			} else if ("remove".equals(action)) {
				server.removeAlarm(appointment, user);

				Alarm alarm = Utilities.getAlarm(appointment, user, server.getAlarms());

				server.getDatabase().deleteAlarm(alarm);
				System.out.println("successfully removed alarm!");

			} else {
				System.out.println("an alarm message had invalid (or no) action field: " + message.toString());
			}

		} else {
			System.out.println("an alarm message did not have the required fields: " + message.toString());
		}

	}

	private void sendAllModels() {
		for (User user : server.getUsers()) {
			send(user.getJSON());
		}

		for (MeetingRoom meetingRoom : server.getMeetingRooms()) {
			send(meetingRoom.getJSON());
		}

		for (Appointment appointment : server.getAppointments()) {
			JSONObject message = appointment.getJSON();
			message.put("action", "new");
			send(message);
		}

		for (Alarm alarm : server.getAlarms()) {
			JSONObject message = alarm.getJSON();
			message.put("action", "new");
			send(message);
		}

		for (Group group : server.getGroups()) {
			send(group.getJSON());
		}
	}
}
