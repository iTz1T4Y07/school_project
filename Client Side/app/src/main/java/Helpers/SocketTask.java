package Helpers;

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

    public SocketTask(JSONObject jsonObject){
        this.JSONSendingData = jsonObject;
    }

    @Override
    protected JSONObject doInBackground(JSONObject... jsonObjects) {
        try {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(GlobalVars.SERVER_IP, GlobalVars.PORT);
            this.socket = new Socket();
            this.socket.connect(inetSocketAddress, 1000);
            sendData(this.JSONSendingData);
            JSONObject receivedJson = receiveData();
            //closeConnection();
            //socket.getInputStream().close();
            this.socket.close();
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
        String data = JSONData.toString();
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.socket.getOutputStream(), StandardCharsets.UTF_8);
            outputStreamWriter.write(data);
            outputStreamWriter.flush();
            outputStreamWriter.write(new String(new char[2*GlobalVars.PACKET_SIZE]).replace("\0", " ")); //Sends packet of spaces in length of Packet Size * 2
            outputStreamWriter.flush();
            //outputStreamWriter.close();
        }
        catch (Exception e){
            Log.e("Exception", e.toString());
        }
    }

    private JSONObject receiveData(){
        JSONObject receivedJSON = null;
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(this.socket.getInputStream(), StandardCharsets.UTF_8);
            char[] buffer = new char[GlobalVars.PACKET_SIZE];

            StringBuilder stringBuilder = new StringBuilder();

            inputStreamReader.read(buffer);
            while (!new String(buffer).equals(new String(new char[GlobalVars.PACKET_SIZE]).replace("\0", " "))) { // If last 1024 cells is " ", then we end receiving the data
                stringBuilder.append(buffer);
                inputStreamReader.read(buffer);
            }


            //inputStreamReader.close();
            receivedJSON = new JSONObject(stringBuilder.toString());

        }
        catch (JSONException | IOException e){
            Log.e("Exception", e.toString());
        }
        return receivedJSON;
    }

    private void closeConnection(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Status", "Close");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sendData(jsonObject);
    }
}
