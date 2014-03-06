package fellesprosjekt.gruppe30.Controller;

import java.io.IOException;
import java.net.Socket;

//intended use:
//
//ClientNetwork cn = new ClientNetwork();
//Thread thread = new Thread(cn);
//thread.start();
//...
//cn.functions_not_yet_implemented();

public class ClientNetwork extends Network {
	int serverPort = 11223;
	String serverAddress = "localhost";
	
	
	@Override
	public void run() {
		try {
			connection_socket = new Socket(serverAddress, serverPort);
			System.out.println("ClientNetwork: Connected to server: " + connection_socket.getRemoteSocketAddress());
			
		} catch (IOException e) {
			System.out.println("ClientNetwork: Failed to set up connection, terminating...");
			e.printStackTrace();
			return;
		}
		super.run();
	}
}
