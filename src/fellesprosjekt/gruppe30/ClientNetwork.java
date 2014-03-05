package fellesprosjekt.gruppe30;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

//intended use:
//
//ClientNetwork cn = new ClientNetwork();
//cn.start();
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
			
			if(connection_socket == null){
				System.out.println("connection_socket was null, terminating");
				running = false;
				
			}else{
				set_up_streams();
			}
			
			while(running){
				
				System.out.println("ClientNetwork: closing connection");
				close_connection();

				
				if(connection_socket.isClosed()){
					System.out.println("ClientNetwork: connection is closed, terminating...");
					running = false;
				}
			}
			
		} catch (IOException e) {
			System.out.println("ClientNetwork: Failed to set up connection.");
			e.printStackTrace();
		}
		
		System.out.println("ClientNetwork: run() is returning.");
	}
	
	public void close_connection(){
		send("closing...");
		
		try {
			connection_socket.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	
}
