package fellesprosjekt.gruppe30.Controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.json.JSONObject;


public abstract class Network implements Runnable {
	Socket connectionSocket;
	public boolean running = true;
	
	DataInputStream incomingStream = null;
	DataOutputStream outgoingStream = null;
	

	@Override
	public void run() {
		try{
			if(connectionSocket != null && connectionSocket.isConnected()){
				setUpStreams();
				
			} else {
				System.out.println("connectionSocket was not set up, terminating");
				running = false;
			}
			
			while(running){
				
				JSONObject message = getJSONObject();

				if (message == null) {
					System.out.println("empty message");
					continue;
				}

				handleMessage(message);


				
//				if (message.get("request").equals("close")) {
//					System.out.println("closing clienthandler socket");
//					connectionSocket.close();
//				}

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
	
	
	protected abstract void handleMessage(JSONObject message) throws IOException;


	private void setUpStreams() throws IOException {
		incomingStream = new DataInputStream(connectionSocket.getInputStream());
		outgoingStream = new DataOutputStream(connectionSocket.getOutputStream());
		
		if(incomingStream == null || outgoingStream == null){
			System.out.println("Network: failed to set up socket stream(s), terminating...");
			running = false;
		}
	}

	public void sendJSONObject(JSONObject obj) {
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

	// returns null if there is an error
	public JSONObject getJSONObject() {
		if (connectionSocket == null || connectionSocket.isClosed())
			return null;

		JSONObject result = null;

		try {
			String stringObj = incomingStream.readUTF();
			result = new JSONObject(stringObj);
			System.out.println("recieved: " + result.toString());
		} catch (IOException e) {
			// System.out.println("getJsonObject threw exception: ");
		}

		return result;
	}

}
