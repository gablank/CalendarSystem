package fellesprosjekt.gruppe30;

import java.io.BufferedReader;
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
	Socket socket;
	
	int serverPort = 11223;
	String serverAddress = "localhost";
	
	boolean running = true;
	
	BufferedReader incoming_stream;

	@Override
	public void run() {
		try {
			socket = new Socket(serverAddress, serverPort);
			System.out.println("ClientNetwork: Connected to server: " + socket.getRemoteSocketAddress());
			
			incoming_stream = new BufferedReader( new InputStreamReader(socket.getInputStream()));
			
			while(running){

				
				String message = incoming_stream.readLine();		//note: only reads to next newline
				
				if(message != null){
					System.out.println("got: " + message);
				}
				
				if(message.compareTo("closing...") == 0){ 
					socket.close();
				}
				
				if(socket.isClosed()){
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
}
