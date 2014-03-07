package fellesprosjekt.gruppe30;


import fellesprosjekt.gruppe30.Controller.Database;

public class Server {
    private final Database database = Database.getInstance();

    public Server() {

    }

    public static void main(String args[]) {
        Server server = new Server();
    }
}
