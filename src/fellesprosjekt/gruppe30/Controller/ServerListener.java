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

	// for testing only
	public ClientHandler getFirstHandler() {
		if(clientHandlers.isEmpty()) {
			return null;
		} else {
			return clientHandlers.get(0);
		}
	}

	public void broadcast(JSONObject message){
		for (ClientHandler clientHandler : clientHandlers) {
			clientHandler.send(message);
		}
		if (message.has("title") && message.has("attendants") && message.has("description") && message.has("start") && message.has("end")) {
			JSONArray attendants = message.getJSONArray("Attendants");
			for (int i = 0; i < attendants.length(); i++) {
				JSONObject attendant = attendants.getJSONObject(i);
				if (attendant.has("email")) {
					String recipient = attendant.getString("email");
					String subject = message.getString("title");
					Long start = message.getLong("start");
					Long end = message.getLong("end");
					Date startDate = new Date(start);
					Date endDate = new Date(end);
					String body = "Date: " + Integer.toString(startDate.getDate()) + "\n" + message.getString("description") + "\nTime: " +
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
				System.out.println("Something bad happened when accepting a connetion.");
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

}