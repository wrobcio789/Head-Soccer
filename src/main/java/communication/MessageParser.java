package communication;

import game.Constants;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Scanner;

import static game.Constants.MESSAGE_BUFFER_SIZE;

public class MessageParser {

    public interface MessageHandler{
        void handleMessage(MessageType type, int[] argsInt, float[] argsFloat);
    }

    public static void parseMessage(byte[] message, MessageHandler handler){
        ByteBuffer buffer = ByteBuffer.wrap(message);
        parseMessageWithBuffer(buffer, handler);
    }

    public static byte[] parseArgs(MessageType type, Object[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(MESSAGE_BUFFER_SIZE);
        parseArgsWithByteBuffer(buffer, type, args);
        return buffer.array();
    }

    private static void parseArgsWithByteBuffer(ByteBuffer buffer, MessageType type, Object[] args) {
       buffer.putInt(type.getValue());
       if(args != null) {
           for (Object arg : args) {
               if (arg instanceof Float) {
                   buffer.putFloat((float) arg);
               } else if (arg instanceof Integer) {
                   buffer.putInt((int) arg);
               }
           }
       }
    }

    private static void parseMessageWithBuffer(ByteBuffer buffer, MessageHandler handler){
        int typeInt = buffer.getInt();
        MessageType type = MessageType.getByInt(typeInt);

        float[] argsFloat = readArgsFloat(buffer.duplicate());
        int[] argsInt = readArgsInt(buffer);

        handler.handleMessage(type, argsInt, argsFloat);
    }

    private static float[] readArgsFloat(ByteBuffer buffer){
        float[] argsFloat = new float[Constants.MESSAGE_ARGS_SIZE];
        for(int i = 0; i<Constants.MESSAGE_ARGS_SIZE; i++)
            argsFloat[i] = buffer.getFloat();
        return argsFloat;
    }

    private static int[] readArgsInt(ByteBuffer buffer){
        int[] argsInt = new int[Constants.MESSAGE_ARGS_SIZE];
        for(int i = 0; i<Constants.MESSAGE_ARGS_SIZE; i++)
            argsInt[i] = buffer.getInt();
        return argsInt;
    }
}
