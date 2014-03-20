package fellesprosjekt.gruppe30.Controller;

import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public abstract class Network implements Runnable {
	Socket connectionSocket;
	public boolean running = true;

	DataInputStream  incomingStream = null;
	DataOutputStream outgoingStream = null;


	@Override
	public void run() {
		try {
			if(connectionSocket != null && connectionSocket.isConnected()) {
				setUpStreams();

			} else {
				System.out.println("connectionSocket was not set up, terminating");
				running = false;
			}

			while(running) {

				JSONObject message = getNextMessage();

				if(message == null) {
					System.out.println("empty message, terminating ClientHandler/ClientNetwork");
					running = false;
					continue;
				}

				handleMessage(message);


				//				if (message.get("request").equals("close")) {
				//					System.out.println("closing clienthandler socket");
				//					connectionSocket.close();
				//				}

				if(connectionSocket.isClosed()) {
					System.out.println("Clienthandler: connection is closed, terminating...");
					running = false;
				}
			}
		} catch(IOException e) {
			System.out.println("Network.run(): Caught exception, terminating...");
			e.printStackTrace();
		}

		System.out.println("run returning normally.");
	}


	protected abstract void handleMessage(JSONObject message) throws IOException;


	private void setUpStreams() throws IOException {
		incomingStream = new DataInputStream(connectionSocket.getInputStream());
		outgoingStream = new DataOutputStream(connectionSocket.getOutputStream());

		if(incomingStream == null || outgoingStream == null) {
			System.out.println("Network: failed to set up socket stream(s), terminating...");
			running = false;
		}
	}

	public boolean send(JSONObject obj) {
		String jsonString = obj.toString();
		System.out.println("sending: " + jsonString);
		try {
			outgoingStream.writeUTF(jsonString);
			outgoingStream.flush();
			return true;
		} catch (IOException e) {
			System.out.println("send threw exception, other side disconnected?");
			return false;
		}
	}

	// returns null if there is an error
	public JSONObject getNextMessage() {
		if(connectionSocket == null || connectionSocket.isClosed())
			return null;

		JSONObject result = null;

		try {
			String stringObj = incomingStream.readUTF();
			result = new JSONObject(stringObj);
			System.out.println("received: " + result.toString());
		} catch(IOException e) {
			// System.out.println("getJsonObject threw exception: ");
		}

		return result;
	}

}
