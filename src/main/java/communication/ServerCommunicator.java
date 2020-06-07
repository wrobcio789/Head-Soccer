package communication;

import game.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

import static java.lang.Thread.sleep;

public class ServerCommunicator implements Runnable {
    private static final String HOSTNAME = "0.0.0.0";
    private static final int PORT = 16300;

    private final MessageQueue messagesToSend;
    private final MessageQueue receivedMessages;
    private final Socket socket;


    public ServerCommunicator(MessageQueue messagesToSend, MessageQueue receivedMessages) throws IOException {
        this.messagesToSend = messagesToSend;
        this.receivedMessages = receivedMessages;
        this.socket = createSocket();
    }

    public ServerCommunicator(MessageQueue messagesToSend, MessageQueue receivedMessages, Socket socket) {
        this.messagesToSend = messagesToSend;
        this.receivedMessages = receivedMessages;
        this.socket = socket;
    }

    private Socket createSocket() throws IOException {
        try {
            return new Socket(HOSTNAME, PORT);
        } catch (IOException e) {
            System.out.println("Cannot create socket.");
            throw e;
        }
    }

    @Override
    public void run() {
        messagesToSend.clear();
        try(InputStream serverInput = socket.getInputStream();
            OutputStream serverOutput = socket.getOutputStream();) {
            communicate(serverInput, serverOutput);
        } catch (IOException e) {
            System.out.println("Cannot connect to server.");
        } catch (InterruptedException e) {
            System.out.println("Communicator interrupted.");
        }
    }

    private void communicate(InputStream input, OutputStream output) throws InterruptedException {
        while(true){
            tryToSendMessage(output);
            tryToReceiveMessage(input);
            Thread.sleep(2);
        }
    }

    private void tryToSendMessage(OutputStream output){
        try {
            while(messagesToSend.hasFront()) {
                sendMessage(output);
            }
        } catch (InterruptedException e) {
            System.out.println("Connection interrupted.");
        } catch (IOException e) {
            System.out.println("Could not send message.");
        }
    }

    private void sendMessage(OutputStream output) throws InterruptedException, IOException {
        byte[] buffer = new byte[Constants.MESSAGE_BUFFER_SIZE];
        byte[] message = messagesToSend.front();
        if(message.length > Constants.MESSAGE_BUFFER_SIZE)
            throw new UnsupportedOperationException("MESSAGE MUST BE SHORTER THEN BUFFER SIZE");
        System.arraycopy(message, 0, buffer, 0, message.length);
        output.write(buffer);
        output.flush();
    }

    private void tryToReceiveMessage(InputStream input){
        try{
            while(input.available() != 0){
                receiveMessage(input);
            }
        }catch (IOException e) {
            System.out.println("Could not send message.");
        } catch (InterruptedException e) {
            System.out.println("Interrupted.");
        }
    }

    private void receiveMessage(InputStream input) throws IOException, InterruptedException {
        byte[] buffer = new byte[Constants.MESSAGE_BUFFER_SIZE];
        input.read(buffer, 0, Constants.MESSAGE_BUFFER_SIZE);
        receivedMessages.add(buffer);
    }


}
