package fellesprosjekt.gruppe30.Controller;

import fellesprosjekt.gruppe30.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


public class ServerListener implements Runnable {
	int                 listenerPort   = 11223;
	List<ClientHandler> clientHandlers = new ArrayList<ClientHandler>();
	Server server;
	public boolean running = true;

	public ServerListener(Server server) {
		this.server = server;
	}

	public void broadcast(JSONObject message){
		System.out.println("broadcasting to " + clientHandlers.size());
		for(int i = 0; i < clientHandlers.size(); ++i){
			// only broadcast to connections that have a logged in client
			if (clientHandlers.get(i).getUsername() != null) {

				if (clientHandlers.get(i).send(message)) {
					;// all is fine

				} else {
					// client has disconnected
					clientHandlers.get(i).running = false;
					clientHandlers.remove(i);
					--i;
				}
			}
		}

		if (message.has("action") && message.has("title") && message.has("attendants") && message.has("description") && message.has("start") && message.has("end")) {
			JSONArray attendants = message.getJSONArray("attendants");
			for (int i = 0; i < attendants.length(); i++) {
				JSONObject attendant = attendants.getJSONObject(i);
				if (attendant.has("email")) {
					String recipient = attendant.getString("email");
					String subject = message.getString("title");
					Date startDate = new Date(message.getLong("start"));
					Date endDate = new Date(message.getLong("end"));

					String action;
					if (message.getString("action").equals("new"))
						action = "New appointment!";
					else
						action = "Appointment has been edited.";

					String body = action + "\nDate: " + Integer.toString(startDate.getDate()) + "." + Integer.toString(startDate.getMonth() + 1) + "." + Integer.toString(startDate.getYear() + 1900) + "\n" + message.getString("description") + "\nTime: " +
					Integer.toString(startDate.getHours()) + ":" + Integer.toString(startDate.getMinutes()) + " - " + Integer.toString(endDate.getHours())
					+ ":" + Integer.toString(endDate.getMinutes()) + "\nNumber of participants: " + attendants.length();
					
					server.sendMail(recipient, subject, body);
				}
			}
		}
	}

	@Override
	public void run() {
		ServerSocket listenerSocket = null;

		try {
			listenerSocket = new ServerSocket(listenerPort);
			listenerSocket.setReuseAddress(true);
			System.out.println("Server is now listening at port " + listenerPort);

		} catch(IOException e) {
			System.out.println("ServerNetwork failed to set up listenerSocket");
			e.printStackTrace();
			//if(listenerSocket != null)
			//	listenerSocket.close();
			return;
		}


		while(running) {
			Socket connectionSocket = null;

			try {
				connectionSocket = listenerSocket.accept();
				if(connectionSocket != null) {
					ClientHandler clientHandler = new ClientHandler(connectionSocket, server);
					clientHandlers.add(clientHandler);

					System.out.println("ServerNetwork: accepted new connection from " + connectionSocket.getRemoteSocketAddress());

					Thread thread = new Thread(clientHandler);
					thread.start();
				}

			} catch(IOException e) {
				System.out.println("Something bad happened when accepting a connection.");
				e.printStackTrace();
				continue;
			}
		}

		try {
			listenerSocket.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	// public static void main(String[] args) {
	// Date startDate = new Date();
	// Date endDate = new Date(startDate.getTime() + 1000 * 60 * 15);
	//
	//
	// String body = "new" + "\nDate: " + Integer.toString(startDate.getDate())
	// + "." + Integer.toString(startDate.getMonth()) + 1 + "." +
	// Integer.toString(startDate.getYear() + 1900) + "\n" + "description..." +
	// "\nTime: " +
	// Integer.toString(startDate.getHours()) + ":" +
	// Integer.toString(startDate.getMinutes()) + " - " +
	// Integer.toString(endDate.getHours())
	// + ":" + Integer.toString(endDate.getMinutes()) +
	// "\nNumber of participants: " + 3;
	//
	// System.out.println(body);
	// }

}