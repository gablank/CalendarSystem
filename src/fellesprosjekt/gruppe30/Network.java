package fellesprosjekt.gruppe30;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;


public abstract class Network implements Runnable {
	Socket connection_socket;
	boolean running = true;
	
	BufferedReader incoming_stream = null;
	DataOutputStream outgoing_stream = null;
	
	@Override
	public void run() {
		try{
			if(connection_socket != null && connection_socket.isConnected()){
				set_up_streams();
				
			}else{
				System.out.println("connection_socket was not set up, terminating");
				running = false;
			}
			
			while(running){
				String message = read_line();

				//
				// do stuff based on the message
				//
				
				if(message != null && message.compareTo("closing...") == 0){
					connection_socket.close();
				}

				if(connection_socket.isClosed()){
					System.out.println("Clienthandler: connection is closed, terminating...");
					running = false;
				}
			}
		} catch (IOException e) {
			System.out.println("Network.run(): Caught exception, terminating...");
			e.printStackTrace();
		}
	}
	
	
	public void set_up_streams() throws IOException{
		incoming_stream = new BufferedReader( new InputStreamReader(connection_socket.getInputStream()));
		outgoing_stream = new DataOutputStream(connection_socket.getOutputStream());
		
		if(incoming_stream == null || outgoing_stream == null){
			System.out.println("Network: failed to set up socket stream(s), terminating...");
			running = false;
		}
	}
	
	public void send(String message){
		if(connection_socket == null || connection_socket.isClosed())
			return;
		
		try {
			System.out.println("Sending: " + message);
			outgoing_stream.writeUTF(message);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String read_line(){
		if(connection_socket == null || connection_socket.isClosed())
			return null;
		
		try {
			String message = null;
			message = incoming_stream.readLine();		
			if(message != null){
				System.out.println("got: " + message + " (len: " + message.length() + ")");
			}
			
			return message;
			
		} catch (IOException e) {
			System.out.println("readLine threw Exception, connection is closed");
			running = false;
		}

		return null;
	}
	
	
	public void close_connection(){
		running = false;
		send("closing...");
		
		try {
			//Thread.sleep(2000);
			connection_socket.close();
			
		} catch (IOException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}