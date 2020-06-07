package communication;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class MessageQueue {
    private static final int MAX_USERS = 1;

    private final Semaphore semaphore = new Semaphore(MAX_USERS);
    private final Queue<byte[]> queue = new LinkedList<>();

    public void add(byte[] message){
        try {
            semaphore.acquire();
            queue.add(message);
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public byte[] front() {
        try {
            semaphore.acquire();
            byte[] result = queue.poll();
            semaphore.release();
            return result;
        } catch(InterruptedException ignored){
            System.out.println("Message queue interrupted");
        }
        return null;
    }

    public void clear(){
        try {
            semaphore.acquire();
            queue.clear();
            semaphore.release();
        } catch(InterruptedException ignored){
            System.out.println("Message queue interrupted");
        }
    }

    public int size(){
        return queue.size();
    }


    public boolean hasFront(){
        return !queue.isEmpty();
    }
}
