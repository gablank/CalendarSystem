package fellesprosjekt.gruppe30;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

// Unnecessary? 
// Might be removed later if no common functionality is needed
public abstract class Network implements Runnable {
	Socket connection_socket;
	boolean running = true;
	
	BufferedReader incoming_stream = null;
	DataOutputStream outgoing_stream = null;
	
	
	public void set_up_streams() throws IOException{
		incoming_stream = new BufferedReader( new InputStreamReader(connection_socket.getInputStream()));
		outgoing_stream = new DataOutputStream(connection_socket.getOutputStream());
		
		if(incoming_stream == null || outgoing_stream == null){
			System.out.println("ClientNetwork: failed to set up socket stream(s), terminating...");
			running = false;
		}
	}
	
	public void send(String message){
		try {
			outgoing_stream.writeUTF(message);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String read_line(){
		String message = null;
		
		try {
			message = incoming_stream.readLine();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(message != null){
			System.out.println("got: " + message);
		}
		
		return message;
	}
}