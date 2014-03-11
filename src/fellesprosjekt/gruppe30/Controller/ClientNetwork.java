package fellesprosjekt.gruppe30.Controller;

import java.io.IOException;
import java.net.Socket;

import org.json.JSONObject;

import fellesprosjekt.gruppe30.Client;
import fellesprosjekt.gruppe30.Model.MeetingRoom;
import fellesprosjekt.gruppe30.Model.User;


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



			switch (type) {
			case "login":
				if (message.has("status") && message.has("statusMessage")) {

					String status = message.getString("status");
					String statusMessage = message.getString("statusMessage");

					//
					// notify() something loginController is wait()-ing on?
					//

				} else {
					System.out
							.println("a login message did not have the required fields: "
									+ message.toString());
				}

				break;

			case "user":
				if (message.has("id") && message.has("username")
						&& message.has("firstName") && message.has("lastName")
						&& message.has("email")) {

					int id = message.getInt("id");
					String username = message.getString("username");
					String firstName = message.getString("firstName");
					String lastName = message.getString("lastName");
					String email = message.getString("email");

					User user = new User(firstName, lastName, username, email);
					user.setId(id);

					client.addUser(user);
				}
				
				break;

			case "appointment":
				String action = "";
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
					
				// String title = message.getString("title");
				// String description = message.getString("description");
				// long start = message.getLong("start");
				// long end = message.getLong("end");
				// String meetingPlace = message.getString("meetingPlace");
				// String attendants = message.getString("attendants");
				// int ownerId = message.getInt("owner");
				// int meetingRoomId = message.getInt("meetingRoom");
				//
				//
				// User owner = server.getUserById(ownerId);
				//
				// if(owner == null){
				// System.out.println("failed handling an appointment message, owner was not found. Message: "
				// + message.toString());
				// return;
				// }
				//
				// Date startDate = new Date(start);
				// Date endDate = new Date(end);
				//
				// MeetingRoom meetingRoom =
				// server.getMeetingRoomById(meetingRoomId);
				//
				// //
				// // todo: parse, look up and add attendants
				// //
				//
				// Appointment appointment = null;
				//
				// if(meetingRoom == null && meetingPlace.isEmpty()){
				// System.out.println("failed handling an appointment message, no meetingPlace or valid meetingRoom was set. Message: "
				// + message.toString());
				// return;
				//
				// }else if(meetingRoom != null && !meetingPlace.isEmpty()){
				// System.out.println("failed handling an appointment message, both meetingPlace and meetingRoom were set. Message: "
				// + message.toString());
				// return;
				//
				// }else if(meetingRoom != null){
				// appointment = new Appointment(owner, title, description,
				// startDate, endDate, meetingRoom);
				//
				// }else{
				// appointment = new Appointment(owner, title, description,
				// startDate, endDate, meetingPlace);
				//
				// }
				//
				// if(action == "change"){
				// int id = message.getInt("id");
				// appointment.setId(id);
				// if(server.getAppointmentById(id) == null){
				// System.out.println("failed handling an change appointment message, the appointment with specified id could not be found.");
				// }
				// }
				//
				// server.insertAppointment(appointment);
					
				} else if (action == "remove" && message.has("id")) {
					int id = message.getInt("id");

					client.removeAppointment(id);

				} else {
					System.out.println("an appointment message did not have the required fields: " + message.toString());
				}
				
				 break;

			case "meetingRoom":
				if (message.has("id") 
						&& message.has("id")
						&& message.has("roomSize")) {

					int id       = message.getInt("id");
					int roomSize = message.getInt("roomSize");

					MeetingRoom meetingRoom = new MeetingRoom(roomSize);
					meetingRoom.setId(id);

					client.addMeetingRoom(meetingRoom);
				}
				
				break;
				
			case "alarm":
//				if (message.has("id") 
//						&& message.has("id")
//						&& message.has("roomSize")) {
//
//					int id       = message.getInt("id");
//					int roomSize = message.getInt("roomSize");
//
//					MeetingRoom meetingRoom = new MeetingRoom(roomSize);
//					meetingRoom.setId(id);
//
//					client.addMeetingRoom(meetingRoom);
//				}
				
				break;

			default:
				System.out.println("got a message of invalid type: "
						+ message.toString());
			}

		} else {
			System.out.println("a message had no type field: "
					+ message.toString());
		}
	}
	
	public void closeConnection(){
		running = false;
		JSONObject obj = new JSONObject();
		obj.put("type", "logout");
		sendJSONObject(obj);
		
		try {
			//Thread.sleep(2000);
			connectionSocket.close();
			
		} catch (IOException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
