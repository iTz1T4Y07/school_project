package Helpers;

import static android.app.Activity.RESULT_FIRST_USER;

public class GlobalVars {

    //Intent Request Codes
    public static final int GUEST_PAGE_REQUEST_CODE = 1;
    public static final int LOGIN_PAGE_REQUEST_CODE = 2;
    public static final int REGISTER_PAGE_REQUEST_CODE = 3;

    //Intent Result Codes
    public static final int RESULT_REGISTER_LOGIN = RESULT_FIRST_USER;


    //Enums


    public enum Request {
        ADD_USER("ADD_USER"),
        LOGIN_CHECK("LOGIN_CHECK"),
        GET_USER_INFO("GET_USER_INFO"),
        GET_BEACH_LIST("GET_BEACH_LIST"),
        GET_EVENT_LIST("GET_EVENT_LIST"),
        UPDATE_USER_IN_EVENT("UPDATE_USER_IN_EVENT");

        private String value;
        Request(String value){
            this.value = value;
        }

        public String getValue(){
            return this.value;
        }
    }

    public enum DayPart {
        MORNING("Morning"),
        AFTERNOON("Afternoon"),
        EVENING("Evening");

        private String value;
        DayPart(String value){
            this.value = value;
        }

        public String getValue(){
            return this.value;
        }
    }

    public enum JsonArg {
        REQUEST("request"),
        ARGS_KEY("Args"),
        ID_KEY("ID"),
        USERNAME_KEY("Username"),
        PASSWORD_KEY("Password"),
        PRIVATE_NAME_KEY("Private Name"),
        LAST_NAME_KEY("Last Name"),
        EMAIL_KEY("Email"),
        BIRTHDAY_KEY("Birthday"),
        BEACH_ID("Beach ID"),
        BEACH_NAME("Beach Name"),
        BEACH_LIST("Beach List"),
        EVENT_ID("Event ID"),
        EVENT_DATE("Event Date"),
        EVENT_MORNING_COUNT("Event Morning Count"),
        EVENT_AFTERNOON_COUNT("Event Afternoon Count"),
        EVENT_EVENING_COUNT("Event Evening Count"),
        EVENT_MORNING_PARTICIPATE("User's Participation Morning Status"),
        EVENT_AFTERNOON_PARTICIPATE("User's Participation Afternoon Status"),
        EVENT_EVENING_PARTICIPATE("User's Participation Evening Status"),
        EVENT_LIST("Event List"),
        EVENT_UPDATE_METHOD("Event Update Method"),
        DAY_PART("Day Part"),
        ERROR_ID_KEY("Error ID"),
        ERROR_DESC_KEY("Error Description"),
        MISSING_ARG("Missing Argument"),
        INVALID_ARG("Invalid Argument");

        private String value;

        JsonArg(String value){
            this.value = value;
        }

        public String getValue(){
            return this.value;
        }
    }

    public enum ErrorCode {
        Success(0, "Action Succeed"),
        DB_Failure(1, "Database failed"),
        User_Exist(2, "Username or email already exist"),
        Invalid_Request(3, "Request is missing or invalid"),
        Args_Missing(4, "Some of the arguments needed not provided"),
        Invalid_Data(5, "Server received invalid data"),
        Wrong_User_Credentials(6, "Could not find a user with the provided credentials"),
        No_Connection(7,"Connection is disabled"),
        Action_Failed(8, "Action failed"),
        Username_Exist(9, "Username already exist"),
        Email_Exist(10, "Email already exist");

        private int code;
        private String description;
        ErrorCode(int code, String description){
            this.code = code;
            this.description = description;
        }

        public int getCode() { return this.code;};
        public String getDescription(){
            return this.description;
        }
    }

    public enum EventUpdateMethod{
        ADD_USER("Add User"),
        REMOVE_USER("Remove User");

        private String value;
        EventUpdateMethod(String value){this.value = value;}

        public String getValue(){return this.value;}
    }




    //Network Data
    public static final String SERVER_IP = "192.168.1.175"; //Server IP address
    public static final int PORT = 21211; //Server Port
    public static final int PACKET_SIZE = 1024; // Default Packet Size


    private static boolean connectedState = false; //Determines if user is connected or a guest;
    private static int connectedId = -1;

    public static void setConnectedState(boolean state){ GlobalVars.connectedState = state;
    }

    public static boolean getConnectedState(){
        return connectedState;
    }

    public static int getConnectedId() {
        return connectedId;
    }

    public static void setConnectedId(int connectedId) {
        GlobalVars.connectedId = connectedId;
    }
}
