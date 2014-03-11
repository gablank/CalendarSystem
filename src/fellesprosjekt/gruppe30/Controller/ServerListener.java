package fellesprosjekt.gruppe30.Controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import fellesprosjekt.gruppe30.Server;
import fellesprosjekt.gruppe30.Model.Appointment;
import fellesprosjekt.gruppe30.Model.Attendant;
import fellesprosjekt.gruppe30.Model.MeetingRoom;
import fellesprosjekt.gruppe30.Model.User;

public class ServerListener implements Runnable {
	int listenerPort = 11223;
	List<ClientHandler> clientHandlers = new ArrayList<ClientHandler>();
	Server server;

	public ServerListener(Server server) {
		this.server = server;
	}
	
	@Override
	public void run() {
		ServerSocket listenerSocket = null;
		
		try {
			listenerSocket = new ServerSocket(listenerPort);
			listenerSocket.setReuseAddress(true);
			System.out.println("Server is now listening at port " + listenerPort);
			
		} catch (IOException e) {
			System.out.println("ServerNetwork failed to set up listenerSocket");
			e.printStackTrace();
			//if(listenerSocket != null)
			//	listenerSocket.close();
			return;
		}
		
		
		boolean running = true;
		while(running){
			Socket connectionSocket = null;
			
			try {
				connectionSocket = listenerSocket.accept();
				if(connectionSocket != null){
					ClientHandler clientHandler = new ClientHandler(connectionSocket, server);
					clientHandlers.add(clientHandler);
					
					System.out.println("ServerNetwork: accepted new connection from " + connectionSocket.getRemoteSocketAddress());
					
					Thread thread = new Thread(clientHandler);
					thread.start();
				}
				
			} catch (IOException e) {
				System.out.println("Something bad happened when accepting a connetion.");
				e.printStackTrace();
				continue;
			}
		}
		
		try {
			listenerSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

class ClientHandler extends Network {
	Server server;

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
			
			if(message.has("action"))
				action = message.getString("action");
			

			switch (type) {
			case "login":
				if(message.has("username") && message.has("password")){
					String username = message.getString("username");
					String password = message.getString("password");
					
					//
					// send response
					//

				}else{
					System.out.println("a login message did not have the required fields: " + message.toString());
				}
				
				break;

			case "appointment":
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
					long   start         = message.getLong("start");
					long   end           = message.getLong("end");
					String meetingPlace  = message.getString("meetingPlace");
					String attendants    = message.getString("attendants");
					int    ownerId       = message.getInt("owner");
					int    meetingRoomId = message.getInt("meetingRoom");
					

					User owner = server.getUserById(ownerId);
					
					if(owner == null){
						System.out.println("failed handling an appointment message, owner was not found. Message: " + message.toString());
						return;
					}
					
					Date startDate = new Date(start);
					Date endDate = new Date(end);
					
					MeetingRoom meetingRoom = server.getMeetingRoomById(meetingRoomId);
					
					//
					// todo: parse, look up and add attendants
					//

					Appointment appointment = null;

					if(meetingRoom == null && meetingPlace.isEmpty()){
						System.out.println("failed handling an appointment message, no meetingPlace or valid meetingRoom was set. Message: " + message.toString());
						return;
						
					}else if(meetingRoom != null && !meetingPlace.isEmpty()){
						System.out.println("failed handling an appointment message, both meetingPlace and meetingRoom were set. Message: " + message.toString());
						return;
						
					}else if(meetingRoom != null){
						appointment = new Appointment(owner, title, description, startDate, endDate, meetingRoom);
						
					}else{
						appointment = new Appointment(owner, title, description, startDate, endDate, meetingPlace);
						
					}

					if(action == "change"){
						int id = message.getInt("id");
						appointment.setId(id);
						if(server.getAppointmentById(id) == null){
							System.out.println("failed handling an change appointment message, the appointment with specified id could not be found.");
						}
					}
					
					server.insertAppointment(appointment);
					

				} else if(action == "remove" && message.has("id")  ){
					int    id           = message.getInt("id");
					
					
					server.removeAppointment(id);
				
				}else{
					System.out.println("an appointment message did not have the required fields: " + message.toString());
				}
				
				break;

			case "meetingRoom":
				if ((action == "new" || action == "change") && message.has("id") && message.has("roomSize")) {
					
					int    id       = message.getInt("id");
					String roomSize = message.getString("title");
					
					//
					// modify serverside model
					//
					
				} else if(action == "remove" && message.has("id")) {
					
					//
					// modify serverside model
					//

				} else {
					System.out.println("a meetingRoom message did not have the required fields: " + message.toString());
				}

					
				break;

			case "logout":
				
				
				//
				// do stuff?
				//

				connectionSocket.close();
				running = false;

				break;
				
			default:
				System.out.println("got a message of invalid type: " + message.toString());
			}

		} else {
			System.out.println("a message had no type field: " + message.toString());
		}
	}
}
