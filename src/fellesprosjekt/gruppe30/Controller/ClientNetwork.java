package fellesprosjekt.gruppe30.Controller;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

import fellesprosjekt.gruppe30.Model.*;

import org.json.JSONArray;
import org.json.JSONObject;

import fellesprosjekt.gruppe30.Client;


public class ClientNetwork extends Network {
	int serverPort = 11223;
	String serverAddress = "localhost";
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

			String action = "";


			switch (type) {
			case "login":
				if (message.has("status") && message.has("statusMessage")) {

					String status = message.getString("status");
					String statusMessage = message.getString("statusMessage");
					
					String username = "";
					if(message.has("username"))
						username = message.getString("username");
					
					
					client.getLoginController().handleLoginResponse(status.equals("success"), username);
					

				} else {
					System.out.println("a login message did not have the required fields: " + message.toString());
				}

				break;

			case "internalUser":
				if (message.has("firstName") && message.has("lastName")
						&& message.has("email")) {

					String firstName = message.getString("firstName");
					String lastName = message.getString("lastName");
					String email = message.getString("email");

					InternalUser user = new InternalUser(firstName, lastName, email);

					client.addUser(user);
				}
				
				break;
				
			case "externalUser":
				if (message.has("email")) {

					String email = message.getString("email");

					ExternalUser user = new ExternalUser(email);

					client.addUser(user);
				}
				
				break;

			case "appointment":
				if (message.has("action"))
					action = message.getString("action");
				
				if ((action == "new" || action == "change")
						&& message.has("id")
						&& message.has("title")
						&& message.has("description")
						&& message.has("start")
						&& message.has("end")
						&& message.has("meetingPlace")
						&& message.has("attendants")
						&& message.has("owner")
						&& message.has("meetingRoom")) {
					
					String title         = message.getString("title");
					String description   = message.getString("description");
					long start           = message.getLong("start");
					long end             = message.getLong("end");
					String meetingPlace  = message.getString("meetingPlace");
					String attendantsStr = message.getString("attendants");
					String ownerEmail    = message.getString("owner");
					int meetingRoomId    = message.getInt("meetingRoom");
				
				
					InternalUser owner = (InternalUser) client.getUserByEmail(ownerEmail);
					
					if (owner == null) {
						System.out.println("failed handling an appointment message, owner was not found or not internal. Message: " + message.toString());
						return;
					}

					Date startDate = new Date(start);
					Date endDate = new Date(end);
					
					MeetingRoom meetingRoom = client.getMeetingRoomById(meetingRoomId);
					
					// obj.put("type", "externalAttendant");
					// obj.put("email", this.user.getEmail());
					// obj.put("appointmentid", this.appointment.getId());
					// obj.put("status", this.status);
					//
					// obj.put("type", "internalAttendant");
					// obj.put("email", this.user.getEmail());
					// obj.put("appointmentid", this.appointment.getId());
					// obj.put("status", this.status);
					// obj.put("visibleOnCalendar", this.visibleOnCalendar);
					// obj.put("lastChecked", this.lastChecked);

					JSONArray attendants = new JSONArray(attendantsStr);

					for (int i = 0; i < attendants.length(); i++) {
						Attendant attendant = attendants.getJSONObject(i);
					}

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

					if (action == "change") {
						int id = message.getInt("id");
						appointment.setId(id);
						if (client.getAppointmentById(id) == null) {
							System.out.println("failed handling an change appointment message, the appointment with specified id could not be found.");
						}
					}

					client.addAppointment(appointment);

				} else if (action == "remove" && message.has("id")) {
					int id = message.getInt("id");
					
					Appointment appointment = client.getAppointmentById(id);
					client.removeAppointment(appointment);

				} else {
					System.out.println("an appointment message did not have the required fields: " + message.toString());
				}
				
				 break;

			case "meetingRoom":
				if (message.has("id")
						&& message.has("roomSize")) {

					int id       = message.getInt("id");
					int roomSize = message.getInt("roomSize");

					MeetingRoom meetingRoom = new MeetingRoom(roomSize);
					meetingRoom.setId(id);

					client.addMeetingRoom(meetingRoom);
				}
				
				break;
				
			case "alarm":
				if (message.has("action"))
					action = message.getString("action");
				
				if (message.has("userEmail")
						&& message.has("appointmentId")) {
					
					String userEmail  = message.getString("userEmail");
					int appointmentId = message.getInt("appointmentId");

					InternalUser user = (InternalUser) client.getUserByEmail(userEmail);
					Appointment appointment = client.getAppointmentById(appointmentId);

					
					if(action == "new"){
						if (!message.has("time")) {
							System.out.println("an add alarm message did not have the required time field: " + message.toString());
							return;
						}

						long time = message.getLong("time");
						Date date = new Date(time);
						Alarm alarm = new Alarm(user, appointment, date);
						client.addAlarm(alarm);

					} else if (action == "change") {
						if (!message.has("time")) {
							System.out.println("an add alarm message did not have the required time field: " + message.toString());
							return;
						}

						long time = message.getLong("time");
						Date date = new Date(time);
						

						client.getAlarm(appointment, user).setDate(date);
					
					
					} else if (action == "remove") {
						client.removeAlarm(appointment, user);
					

					} else {
						System.out.println("an alarm message had invalid action field: " + message.toString());
					}

				} else {
					System.out.println("an alarm message did not have the required fields: " + message.toString());
				}
				
				break;

			default:
				System.out.println("got a message of invalid type: " + message.toString());
			}

		} else {
			System.out.println("a message had no type field: " + message.toString());
		}
	}
	
	public void closeConnection(){
		running = false;
		JSONObject obj = new JSONObject();
		obj.put("type", "logout");
		send(obj);
		
		try {
			//Thread.sleep(2000);
			connectionSocket.close();
			
		} catch (IOException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
