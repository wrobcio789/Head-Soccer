package game.server;

import communication.MessageQueue;
import communication.ServerCommunicator;
import game.Constants;
import utility.Callback;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientsManager implements Runnable{

    private final MessageQueue receivedMessages;
    private final MessageQueue[] sentMessages;
    private final Callback resetCallback;

    public ClientsManager(MessageQueue receivedMessages, MessageQueue[] sentMessages, Callback resetCallback){
        this.receivedMessages = receivedMessages;
        this.sentMessages = sentMessages;
        this.resetCallback = resetCallback;
    }

    @Override
    public void run() {
        try (ServerSocket server = new ServerSocket(Constants.PORT, 2, InetAddress.getByName(Constants.HOST))) {
            System.out.println("Server listening on " + server.getInetAddress() + ":" + Constants.PORT);
            for(int id = 0; id < Constants.MAX_PLAYERS; id++)
                handleClient(server, id);
        } catch (IOException e) {
            System.out.println("Error occurred while creating socket or waiting for a connection.");
        }
    }

    private void handleClient(ServerSocket server, int id){
        try {
            Socket client = server.accept();
            new Thread(new ServerCommunicator(sentMessages[id], receivedMessages, client)).start();
            resetCallback.call();
        } catch (IOException e) {
            System.out.println("Could not connect to socket");
        }
    }
}
