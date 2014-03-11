package fellesprosjekt.gruppe30.Controller;

import java.io.IOException;
import java.net.Socket;

import org.json.JSONObject;


public class ClientNetwork extends Network {
	int serverPort = 11223;
	String serverAddress = "localhost";
	
	
	@Override
	public void run() {
		try {
			connectionSocket = new Socket(serverAddress, serverPort);
			System.out.println("ClientNetwork: Connected to server: " + connectionSocket.getRemoteSocketAddress());
			
		} catch (IOException e) {
			System.out.println("ClientNetwork: Failed to set up connection, terminating...");
			e.printStackTrace();
			return;
		}
		super.run();
	}


	@Override
	protected void handleMessage(JSONObject message) {
		
	}
	


	
	public void closeConnection(){
		running = false;
		JSONObject obj = new JSONObject();
		obj.put("type", "logout");
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
