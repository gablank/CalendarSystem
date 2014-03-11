package fellesprosjekt.gruppe30.Controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import fellesprosjekt.gruppe30.Server;


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