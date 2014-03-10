package fellesprosjekt.gruppe30.Controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class ServerListener implements Runnable {
	int listenerPort = 11223;
	List<ClientHandler> clientHandlers = new ArrayList<ClientHandler>();

	
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
					ClientHandler clientHandler = new ClientHandler(connectionSocket);
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
	

	public ClientHandler(Socket connectionSocket) {
		this.connectionSocket = connectionSocket;
		System.out.println("ClientHandler created.");
	}

	@Override
	protected void handleMessage(JSONObject message) {
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
						&& message.has("attendants")) {

					int    id           = message.getInt("id");
					String title        = message.getString("title");
					String description  = message.getString("description");
					String start        = message.getString("start");
					String end          = message.getString("end");
					String meetingPlace = message.getString("meetingPlace");
					String attendants   = message.getString("attendants");

					
					
					//
					// modify serverside model
					//

				} else if(action == "remove" && message.has("id")  ){
					int    id           = message.getInt("id");
					
					//
					// modify serverside model
					//
				
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

				break;
				
			default:
				System.out.println("got a message of invalid type: " + message.toString());
			}

		} else {
			System.out.println("a message had no type field: " + message.toString());
		}
	}
}
