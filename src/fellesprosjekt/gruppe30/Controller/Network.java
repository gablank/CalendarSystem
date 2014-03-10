package fellesprosjekt.gruppe30.Controller;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

import org.json.JSONObject;

public abstract class Network implements Runnable {
	Socket connection_socket;
	public boolean running = true;
	
	DataInputStream incoming_stream = null;
	DataOutputStream outgoing_stream = null;
	
	String buffer; // for saving partial and double json-objects

	@Override
	public void run() {
		try{
			if(connection_socket != null && connection_socket.isConnected()){
				set_up_streams();
				System.out.println("streams set up!");
				
			} else {
				System.out.println("connection_socket was not set up, terminating");
				running = false;
			}
			
			while(running){
				
//				String message = read_line();
				String message = getJSONObject();

				//
				// do stuff based on the message
				//
				
				if (message != null	&& message.compareTo("{\"request\":\"close\"}") == 0) {
					System.out.println("closing clienthandler socket");
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

		System.out.println("run returning normally.");
	}
	
	
	public void set_up_streams() throws IOException{
		incoming_stream = new DataInputStream(connection_socket.getInputStream());
		outgoing_stream = new DataOutputStream(connection_socket.getOutputStream());
		
		if(incoming_stream == null || outgoing_stream == null){
			System.out.println("Network: failed to set up socket stream(s), terminating...");
			running = false;
		}
	}
	
//	public void send(String message){
//		if(connection_socket == null || connection_socket.isClosed())
//			return;
//		
//		try {
//			System.out.println("Sending: " + message);
//			outgoing_stream.writeUTF(message);
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	public void sendJSONObject(JSONObject obj) {
	// public void send_json_object(String json_string) {
		String json_string = obj.toString();
		System.out.println("sending: " + json_string);
		try {
			outgoing_stream.writeUTF(json_string);
			outgoing_stream.flush();
		} catch (IOException e) {
			System.out.println("send_json_object threw exception: ");
			e.printStackTrace();
		}
	}
	
	// public JSON_object get_json_object() {
	public String getJSONObject() {
		if (connection_socket == null || connection_socket.isClosed())
			return "";

		String result = "";

		try {
			result = incoming_stream.readUTF();
		} catch (IOException e) {
			// System.out.println("get_json_object threw exception: ");
			// e.printStackTrace();
			return "";
		}
		

		System.out.println("got: " + result);

		// JSONObject o = json.parse(result);
		return result;
		// return o;
	}

//	public String read_line(){

//		
//		try {
//			String message = null;
//			message = incoming_stream.readLine();		
//			if(message != null){
//				System.out.println("got: " + message + " (len: " + message.length() + ")");
//			}
//			
//			return message;
//			
//		} catch (IOException e) {
//			System.out.println("readLine threw Exception, connection is closed");
//			running = false;
//		}
//
//		return null;
//	}
	
	public void close_connection(){
		running = false;
		JSONObject obj = new JSONObject();
		obj.put("request", "close");
		sendJSONObject(obj);
		
		try {
			//Thread.sleep(2000);
			connection_socket.close();
			
		} catch (IOException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}