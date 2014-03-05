package fellesprosjekt.gruppe30;

import java.io.IOException;
import java.net.Socket;

public class ClientNetwork extends Network {
	Socket socket;

	int serverPort = 11223;
	String serverAddress = "192.168.2.4";
	
	@Override
	public void run() {
		try {
			socket = new Socket(serverAddress, serverPort);
			System.out.println("ClientNetwork: Connected to server: " + socket.getRemoteSocketAddress());
			
			sleep(2000);
			
			System.out.println("ClientNetwork: Closing the connection.");
			socket.close();
			
			if(!socket.isClosed())
				System.out.println("ClientNetwork: ...but it is not closed.");				
			
		} catch (IOException e) {
			System.out.println("ClientNetwork: Failed to set up connection.");
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("ClientNetwork: run() is returning.");
	}
}
