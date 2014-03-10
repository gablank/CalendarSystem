package fellesprosjekt.gruppe30;

import java.util.Queue;

public class QueueWorker implements Runnable {
	Queue<String> queue;
	
	@Override
	public void run() {
		while (true) {
            try {
                String work;
 
                synchronized ( queue ) {
                    while ( queue.isEmpty() )
                        queue.wait();
                     
                    // Get the next work item off of the queue
                    work = queue.remove();
                }
 
                // Process the work item
                

            }
            catch ( InterruptedException e ) {
                break;  // Terminate
            }
        }
	}
	
	public void addWork(String work) {
		synchronized (queue) {
			queue.add(work);
			queue.notify();
		}
	}
}
