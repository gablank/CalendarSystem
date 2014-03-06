package fellesprosjekt.gruppe30;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

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
