package Helpers;

/**
 * This class is responsible for creating a data to send to the server,
 * and process the data received from the server
 * @author Itay Kahalani
 * @date 28/03/2021
 * @version 1.0.0
 * @since 1.0
 */

import android.app.usage.UsageEvents;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.util.Log;


import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import Objects.Beach;
import Objects.BeachEvent;
import Helpers.GlobalVars.JsonArg;
import Helpers.GlobalVars.Request;
import Helpers.GlobalVars.ErrorCode;
import Helpers.GlobalVars.DayPart;
import Helpers.GlobalVars.EventUpdateMethod;

public class DataProcess {


    private static JSONObject JSONObjectBuilder(Request request, JSONObject args){
        /**
         * Builds a JSON object in a specific format:
         * {request: (Request), Args: {(args)}}
         * @param request
         * @return JSONObject from the specific format
         */
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(JsonArg.REQUEST.getValue(), request.getValue());
            if (args != null){
                System.out.println(args.toString());
                jsonObject.put(JsonArg.ARGS_KEY.getValue(), args);}
            else
                jsonObject.put(JsonArg.ARGS_KEY.getValue(), new JSONObject());
        }
        catch (JSONException e){
            Log.e("Exception", e.toString());
            return null;
        }
        return jsonObject;
    }

    public static boolean checkConnectionAvailability(Context applicationContext){
        /**
         * Checks if the app can connect to the internet
         * @return true if it can connect, false otherwise
         */
        try{
            ConnectivityManager connectivityManager = (ConnectivityManager) applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE); // Creates connectivity manager
            if (Build.VERSION.SDK_INT < 29) // NetworkInfo Module was deprecated in API 29
                return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected(); // Checks if connectivity manager has active network and if it connected
            else {
                final boolean[] isConnected = {false};
                ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback(){ // Notifying network changes

                    @Override
                    public void onAvailable(@NonNull Network network) {
                        isConnected[0] = true;
                    }

                };

                connectivityManager.registerDefaultNetworkCallback(networkCallback); // Sets default networkCallBack to be notified on network changes
                TimeUnit.MILLISECONDS.sleep(10);
                connectivityManager.unregisterNetworkCallback(networkCallback); // Unregisters networkCallBack
                return isConnected[0];
            }
        }
        catch (Exception e){
            return false;
        }
    }

    public static ArrayList<Beach> getBeachList(){
        /**
         * Using SocketTask to connect to server and converts the JSON object sent to ArrayList
         * @return ArrayList of Beach type, contains the data from the JSON object
         */
        try {
            SocketTask dataTransportTask = new SocketTask(JSONObjectBuilder(Request.GET_BEACH_LIST, null));
            JSONObject receivedJSONObject = dataTransportTask.execute().get();
            if (receivedJSONObject == null)
                return null;
            if (receivedJSONObject.getInt(JsonArg.ERROR_ID_KEY.getValue()) != ErrorCode.Success.getCode())
                return new ArrayList<Beach>();
            JSONObject data = receivedJSONObject.getJSONObject(JsonArg.ARGS_KEY.getValue());
            String[] beachList = data.getString(JsonArg.BEACH_LIST.getValue()).split(" , ");
            ArrayList<Beach> beaches = new ArrayList<Beach>();
            for (int i=0; i < beachList.length; i++){
                JSONObject beach_data = new JSONObject(beachList[i]);
                String beachName = beach_data.getString(JsonArg.BEACH_NAME.getValue());
                int beachID = beach_data.getInt(JsonArg.BEACH_ID.getValue());
                beaches.add(new Beach(beachName, beachID));
            }
            return beaches;

        }
        catch (JSONException | ExecutionException | InterruptedException e){
            Log.e("Exception", e.toString());
        }
        return null;
    }

    public static ArrayList<BeachEvent> getEventList(int userID, int beachID){
        /**
         * Using SocketTask to connect to server and converts the JSON object sent to ArrayList
         * @return ArrayList of Beach type, contains the data from the JSON object
         */
        try {
            JSONObject args = new JSONObject();
            args.put(JsonArg.ID_KEY.getValue(), userID);
            args.put(JsonArg.BEACH_ID.getValue(), beachID);

            SocketTask dataTransportTask = new SocketTask(JSONObjectBuilder(Request.GET_EVENT_LIST, args));
            JSONObject receivedJSONObject = dataTransportTask.execute().get();
            if (receivedJSONObject == null)
                return null;
            if (receivedJSONObject.getInt(JsonArg.ERROR_ID_KEY.getValue()) != ErrorCode.Success.getCode())
                return new ArrayList<BeachEvent>();
            JSONObject data = receivedJSONObject.getJSONObject(JsonArg.ARGS_KEY.getValue());
            String[] eventList = data.getString(JsonArg.EVENT_LIST.getValue()).split(" , ");
            ArrayList<BeachEvent> events = new ArrayList<BeachEvent>();
            for (int i=0; i < eventList.length; i++){
                JSONObject event_data = new JSONObject(eventList[i]);
                int eventID = event_data.getInt(JsonArg.EVENT_ID.getValue());
                int morningCount = event_data.getInt(JsonArg.EVENT_MORNING_COUNT.getValue());
                int afternoonCount = event_data.getInt(JsonArg.EVENT_AFTERNOON_COUNT.getValue());
                int eveningCount = event_data.getInt(JsonArg.EVENT_EVENING_COUNT.getValue());
                boolean morningStatus = event_data.getBoolean(JsonArg.EVENT_MORNING_PARTICIPATE.getValue());
                boolean afternoonStatus = event_data.getBoolean(JsonArg.EVENT_AFTERNOON_PARTICIPATE.getValue());
                boolean eveningStatus = event_data.getBoolean(JsonArg.EVENT_EVENING_PARTICIPATE.getValue());
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = format.parse(event_data.getString(JsonArg.EVENT_DATE.getValue()));
                    events.add(new BeachEvent(eventID, date, morningCount, afternoonCount, eveningCount, morningStatus, afternoonStatus, eveningStatus));
                } catch (ParseException e){
                    Log.e("Exception", String.format("Parse Exception (%s): %s", JsonArg.EVENT_DATE.getValue().toString(), e.toString()));
                }
            }
            return events;

        }
        catch (JSONException | ExecutionException | InterruptedException e){
            Log.e("Exception", e.toString());
        }
        return null;
    }

    public static ErrorCode registerNewUser(String username, String password, String privateName,
                                            String lastName, String email, String birthdayDate ){
        /**
         * Using SocketTask to connect to server and register a new user
         * @return ErrorCode that represents the result.
         */
        try {
            JSONObject args = new JSONObject();
            args.put(JsonArg.USERNAME_KEY.getValue(), username);
            args.put(JsonArg.PASSWORD_KEY.getValue(), password);
            args.put(JsonArg.PRIVATE_NAME_KEY.getValue(), privateName);
            args.put(JsonArg.LAST_NAME_KEY.getValue(), lastName);
            args.put(JsonArg.EMAIL_KEY.getValue(), email);
            args.put(JsonArg.BIRTHDAY_KEY.getValue(), birthdayDate);

            SocketTask dataTransportTask = new SocketTask(JSONObjectBuilder(Request.ADD_USER, args));
            JSONObject receivedJSONObject = dataTransportTask.execute().get();


            if (receivedJSONObject == null)
                return ErrorCode.Action_Failed;
            int errorCode = receivedJSONObject.getInt(JsonArg.ERROR_ID_KEY.getValue());
            if (errorCode == ErrorCode.User_Exist.getCode()){ // If email/username exists
                String exist_param = receivedJSONObject.getString(JsonArg.ARGS_KEY.getValue());
                if (exist_param.equals(JsonArg.USERNAME_KEY.getValue()))
                    return ErrorCode.Username_Exist;
                else if (exist_param.equals(JsonArg.EMAIL_KEY.getValue()))
                    return ErrorCode.Email_Exist;
            }
            if (errorCode != ErrorCode.Success.getCode())
                return ErrorCode.Action_Failed;
            return ErrorCode.Success;

        } catch (JSONException | ExecutionException | InterruptedException e){
            Log.e("Exception", e.toString());
        }
        return ErrorCode.Action_Failed;

    }

    public static int loginCheck(String username, String password){
        /**
         * Using SocketTask to connect to server and register a new user
         * @return ErrorCode that represents the result.
         */
        try {
            JSONObject args = new JSONObject();
            args.put(JsonArg.USERNAME_KEY.getValue(), username);
            args.put(JsonArg.PASSWORD_KEY.getValue(), password);

            SocketTask dataTransportTask = new SocketTask(JSONObjectBuilder(Request.LOGIN_CHECK, args));
            JSONObject receivedJSONObject = dataTransportTask.execute().get();


            if (receivedJSONObject == null)
                return ErrorCode.Action_Failed.getCode() * (-1);
            int errorCode = receivedJSONObject.getInt(JsonArg.ERROR_ID_KEY.getValue());
            if (errorCode == ErrorCode.Args_Missing.getCode())
                return ErrorCode.Action_Failed.getCode() * (-1);

            if (errorCode == ErrorCode.Wrong_User_Credentials.getCode())
                return ErrorCode.Wrong_User_Credentials.getCode() * (-1);

            return receivedJSONObject.getJSONObject(JsonArg.ARGS_KEY.getValue()).getInt(JsonArg.ID_KEY.getValue());

        } catch (JSONException | ExecutionException | InterruptedException e){
            Log.e("Exception", e.toString());
        }
        return ErrorCode.Action_Failed.getCode() * (-1);

    }

    public static ErrorCode updateEvent(EventUpdateMethod updateMethod, int id, int eventID, GlobalVars.DayPart dayPart){
        /**
         * Using SocketTask to connect to server and register a new user
         * @return ErrorCode that represents the result.
         */
        try {
            JSONObject args = new JSONObject();
            args.put(JsonArg.EVENT_UPDATE_METHOD.getValue(), updateMethod.getValue());
            args.put(JsonArg.ID_KEY.getValue(), id);
            args.put(JsonArg.EVENT_ID.getValue(), eventID);
            args.put(JsonArg.DAY_PART.getValue(), dayPart.getValue());

            SocketTask dataTransportTask = new SocketTask(JSONObjectBuilder(Request.UPDATE_USER_IN_EVENT, args));
            JSONObject receivedJSONObject = dataTransportTask.execute().get();


            if (receivedJSONObject == null)
                return ErrorCode.Action_Failed;
            int errorCode = receivedJSONObject.getInt(JsonArg.ERROR_ID_KEY.getValue());
            if (errorCode != ErrorCode.Success.getCode())
                return ErrorCode.Action_Failed;
            return ErrorCode.Success;

        } catch (JSONException | ExecutionException | InterruptedException e){
            Log.e("Exception", e.toString());
        }
        return ErrorCode.Action_Failed;

    }

    public static JSONObject getUserInfo(int userID){
        /**
         * Using SocketTask to connect to server and converts the JSON object sent to ArrayList
         * @return ArrayList of Beach type, contains the data from the JSON object
         */
        try {
            JSONObject args = new JSONObject();
            args.put(JsonArg.ID_KEY.getValue(), userID);

            SocketTask dataTransportTask = new SocketTask(JSONObjectBuilder(Request.GET_USER_INFO, args));
            JSONObject receivedJSONObject = dataTransportTask.execute().get();
            if(receivedJSONObject == null)
                return null;
            if (receivedJSONObject.getInt(JsonArg.ERROR_ID_KEY.getValue()) != ErrorCode.Success.getCode())
                return null;
            JSONObject data = receivedJSONObject.getJSONObject(JsonArg.ARGS_KEY.getValue());
            return data;

        }
        catch (JSONException | ExecutionException | InterruptedException e){
            Log.e("Exception", e.toString());
        }
        return null;
    }

}
