package Helpers;

/**
 * Async Task used to connect to server, send data and retrieve data
 * @author Itay Kahalani
 * @date 28/03/2021
 * @version 1.0.0
 * @since 1.0
 */

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

public class SocketTask extends AsyncTask<JSONObject, Void, JSONObject> {

    private Socket socket;
    private JSONObject JSONSendingData;

    /**
     * Creates a SocketTask using specified jsonObject
     * @param jsonObject The jsonObject to send to the server
     */
    public SocketTask(JSONObject jsonObject){
        this.JSONSendingData = jsonObject;
    }

    @Override
    protected JSONObject doInBackground(JSONObject... jsonObjects) {
        try {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(GlobalVars.SERVER_IP, GlobalVars.PORT); // Creates inetSocketAddress using IP and port
            this.socket = new Socket(); // Creates new socket
            this.socket.connect(inetSocketAddress, 1000); // Connects the socket using inetSocketAddress
            sendData(this.JSONSendingData); // Sending Data
            JSONObject receivedJson = receiveData(); // Receiving data
            this.socket.close(); // Closing socket
            return receivedJson;
        }
        catch (SocketTimeoutException e){
            Log.e("Exception", e.toString());
        }
        catch (Exception e) {
            Log.e("Exception", e.toString());
        }
        return null;
    }

    private void sendData(JSONObject JSONData){
        /**
         * Sending data to the server
         * @param JSONData The JSONObject needs to be sent to the server
         */
        String data = JSONData.toString(); // Converts JSONObject to String
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.socket.getOutputStream(), StandardCharsets.UTF_8); // Creates a new outputStreamWriter
            outputStreamWriter.write(data); // Writes data into outputStreamWriter
            outputStreamWriter.flush(); // Sending the data to the server - "Flushes" the stream
            outputStreamWriter.write(new String(new char[2*GlobalVars.PACKET_SIZE]).replace("\0", " ")); // Writes packet of spaces in length of Packet Size * 2
            outputStreamWriter.flush(); // Sending the data to the server - "Flushes" the stream
        }
        catch (Exception e){
            Log.e("Exception", e.toString());
        }
    }

    private JSONObject receiveData(){
        /**
         * Receiving the data that has been sent from the server
         * @return JSONObject contains the data received from the server
         */
        JSONObject receivedJSON = null;
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(this.socket.getInputStream(), StandardCharsets.UTF_8); // Creates a new inputStreamReader
            char[] buffer = new char[GlobalVars.PACKET_SIZE]; // Creates a buffer in length of the packet size

            StringBuilder stringBuilder = new StringBuilder(); // Creates new stringBuilder

            inputStreamReader.read(buffer); // reads data in length of packet size to our buffer
            while (!new String(buffer).equals(new String(new char[GlobalVars.PACKET_SIZE]).replace("\0", " "))) { // If last 1024 cells is " ", then we end receiving the data
                stringBuilder.append(buffer); // Adding the data from the buffer to our stringBuilder
                inputStreamReader.read(buffer); // Reading another data
            }

            receivedJSON = new JSONObject(stringBuilder.toString()); // Creating a JSONObject from the data we received

        }
        catch (JSONException | IOException e){
            Log.e("Exception", e.toString());
        }
        return receivedJSON;
    }
}
