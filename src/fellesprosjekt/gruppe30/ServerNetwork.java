package fellesprosjekt.gruppe30;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerNetwork extends Network {
	int listener_port = 11223;

	@Override
	public void run() {
		ServerSocket listener_socket = null;
		
		try {
			listener_socket = new ServerSocket(listener_port);
			listener_socket.setReuseAddress(true);
			System.out.println("Server is now listening at port " + listener_port);
			
		} catch (IOException e) {
			System.out.println("ServerNetwork failed to set up listener_socket");
			e.printStackTrace();
			//if(listener_socket != null)
			//	listener_socket.close();
			return;
		}
		
		
		boolean running = true;
		while(running){
			Socket connection_socket = null;
			
			try {
				connection_socket = listener_socket.accept();
				if(connection_socket != null){
					ClientHandler client_handler = new ClientHandler(connection_socket);
					
					System.out.println("ServerNetwork: accepted new connection from " + connection_socket.getRemoteSocketAddress());
					
					Thread thread = new Thread(client_handler);
					thread.start();
				}
				
			} catch (IOException e) {
				System.out.println("Something bad happened when acception a connetion.");
				e.printStackTrace();
				continue;
			}
		}
		
		try {
			listener_socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

class ClientHandler implements Runnable {
	Socket connectionSocket;
	boolean running = true;
	
	public ClientHandler(Socket connectionSocket) {
		this.connectionSocket = connectionSocket;
	}

	@Override
	public void run() {
		System.out.println("Hello from Clienthandler!");
		
		while(running){
			
			System.out.println("connected?: " + connectionSocket.isConnected());
			
			if(connectionSocket.isClosed()){
				System.out.println("Clienthandler: connection is closed, terminating...");
				running = false;
			}
		}
	}
	
}
