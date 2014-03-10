package fellesprosjekt.gruppe30.Controller;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

import org.json.JSONObject;

// ka som helst

public abstract class Network implements Runnable {
	Socket connectionSocket;
	public boolean running = true;
	
	DataInputStream incomingStream = null;
	DataOutputStream outgoingStream = null;
	
	String buffer; // for saving partial and double json-objects
	
	

	@Override
	public void run() {
		try{
			if(connectionSocket != null && connectionSocket.isConnected()){
				setUpStreams();
				System.out.println("streams set up!");
				
			} else {
				System.out.println("connectionSocket was not set up, terminating");
				running = false;
			}
			
			while(running){
				
				JSONObject message = getJSONObject();

				if (message == null) {
					System.out.println("tom message");
					continue;
				}

				//
				// do stuff based on the message
				//
				
				if (message.get("request").equals("close")) {
					System.out.println("closing clienthandler socket");
					connectionSocket.close();
				}

				if(connectionSocket.isClosed()){
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
	
	
	public void setUpStreams() throws IOException{
		incomingStream = new DataInputStream(connectionSocket.getInputStream());
		outgoingStream = new DataOutputStream(connectionSocket.getOutputStream());
		
		if(incomingStream == null || outgoingStream == null){
			System.out.println("Network: failed to set up socket stream(s), terminating...");
			running = false;
		}
	}
	
//	public void send(String message){
//		if(connectionSocket == null || connectionSocket.isClosed())
//			return;
//		
//		try {
//			System.out.println("Sending: " + message);
//			outgoingStream.writeUTF(message);
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	public void sendJSONObject(JSONObject obj) {
	// public void sendJsonObject(String jsonString) {
		String jsonString = obj.toString();
		System.out.println("sending: " + jsonString);
		try {
			outgoingStream.writeUTF(jsonString);
			outgoingStream.flush();
		} catch (IOException e) {
			System.out.println("sendJsonObject threw exception: ");
			e.printStackTrace();
		}
	}

// public String getJSONObject() {	

	// returns null if there is an error
	public JSONObject getJSONObject() {

		if (connectionSocket == null || connectionSocket.isClosed())
			return null;

		JSONObject result = null;

		try {
			String stringObj = incomingStream.readUTF();
			result = new JSONObject(stringObj);
			System.out.println("got: " + result.toString());
		} catch (IOException e) {
			// System.out.println("getJsonObject threw exception: ");
		}

		return result;
	}

//	public String readLine(){

//		
//		try {
//			String message = null;
//			message = incomingStream.readLine();		
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
	
	public void closeConnection(){
		running = false;
		JSONObject obj = new JSONObject();
		obj.put("request", "close");
		sendJSONObject(obj);
		
		try {
			//Thread.sleep(2000);
			connectionSocket.close();
			
		} catch (IOException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
