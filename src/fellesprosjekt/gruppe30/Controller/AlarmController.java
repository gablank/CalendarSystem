package fellesprosjekt.gruppe30.Controller;


import fellesprosjekt.gruppe30.Server;

public class AlarmController extends Thread {
    private Server server;

    public AlarmController(Server server) {
        this.server = server;
    }

    // Runs then sleeps 60 secs
    public void run() {


        // Wait 60 seconds
        try {
            Thread.sleep(60 * 1000);
        } catch(InterruptedException e) {
            System.out.println("Interrupt during Thread.sleep()!");
        }
    }
}
