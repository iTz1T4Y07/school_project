package Helpers;

/**
 * This class holds all the global variables, and enums that used as global variables.
 * @author Itay Kahalani
 * @date 28/03/2021
 * @version 1.0.0
 * @since 1.0
 */

import static android.app.Activity.RESULT_FIRST_USER;

public class GlobalVars {

    //Intent Request Codes
    public static final int GUEST_PAGE_REQUEST_CODE = 1; // Changing activity from MainActivity to GuestPage
    public static final int LOGIN_PAGE_REQUEST_CODE = 2; // Changing activity from GuestPage to LoginPage
    public static final int REGISTER_PAGE_REQUEST_CODE = 3; // Changing activity from GuestPage to RegisterPage

    //Intent Result Codes
    public static final int RESULT_REGISTER_LOGIN = RESULT_FIRST_USER; // Result code used in changing from LoginPage to RegisterPage or opposite


    //Enums


    public enum Request {
        /**
         * Request codes that can be used in network communication
         * {@link #ADD_USER}
         * {@link #LOGIN_CHECK}
         * {@link #GET_USER_INFO}
         * {@link #GET_BEACH_LIST}
         * {@link #GET_EVENT_LIST}
         * {@link #UPDATE_USER_IN_EVENT}
         */
        /**
         * Request to add new user to database
         */
        ADD_USER("ADD_USER"),
        /**
         * Request to check login credentials
         */
        LOGIN_CHECK("LOGIN_CHECK"),
        /**
         * Request to get information about a user
         */
        GET_USER_INFO("GET_USER_INFO"),
        /**
         * Request to get list of the beaches.
         */
        GET_BEACH_LIST("GET_BEACH_LIST"),
        /**
         * Request to get list of events of a specified beach.
         */
        GET_EVENT_LIST("GET_EVENT_LIST"),
        /**
         * Request to add/remove user from event/
         */
        UPDATE_USER_IN_EVENT("UPDATE_USER_IN_EVENT");

        private String value;

        /**
         * Creates a request with the specified value.
         * @param value The value of the request.
         */
        Request(String value){
            this.value = value;
        }

        /**
         * Gets the value of the request.
         * @return A string representing the value of the request.
         */
        public String getValue(){
            return this.value;
        }
    }

    public enum DayPart {
        /**
         * Parts of day
         * {@link #MORNING}
         * {@link #AFTERNOON}
         * {@link #EVENING}
         */

        /**
         * Morning
         */
        MORNING("Morning"),
        /**
         * Afternoon
         */
        AFTERNOON("Afternoon"),
        /**
         * Evening
         */
        EVENING("Evening");

        private String value;

        /**
         * Creates a part of day with the specified value
         * @param value The name of the part of day.
         */
        DayPart(String value){
            this.value = value;
        }

        /**
         * Gets the value of the part of day.
         * @return A string representing the name of the part of day.
         */
        public String getValue(){
            return this.value;
        }
    }

    public enum JsonArg {
        /**
         * Keys that can be used in JSONObject used in network communication
         * {@link #REQUEST}
         * {@link #ARGS_KEY}
         * {@link #ID_KEY}
         * {@link #USERNAME_KEY}
         * {@link #PASSWORD_KEY}
         * {@link #PRIVATE_NAME_KEY}
         * {@link #LAST_NAME_KEY}
         * {@link #EMAIL_KEY}
         * {@link #BIRTHDAY_KEY}
         * {@link #BEACH_ID}
         * {@link #BEACH_NAME}
         * {@link #BEACH_LIST}
         * {@link #EVENT_ID}
         * {@link #EVENT_DATE}
         * {@link #EVENT_MORNING_COUNT}
         * {@link #EVENT_AFTERNOON_COUNT}
         * {@link #EVENT_EVENING_COUNT}
         * {@link #EVENT_MORNING_PARTICIPATE}
         * {@link #EVENT_AFTERNOON_PARTICIPATE}
         * {@link #EVENT_EVENING_PARTICIPATE}
         * {@link #EVENT_LIST}
         * {@link #EVENT_UPDATE_METHOD}
         * {@link #DAY_PART}
         * {@link #ERROR_ID_KEY}
         * {@link #ERROR_DESC_KEY}
         * {@link #MISSING_ARG}
         * {@link #INVALID_ARG}
         */


        /**
         *  Key used to represents a request
         */
        REQUEST("request"),

        /**
         *  Key used to represents arguments
         */
        ARGS_KEY("Args"),

        /**
         *  Key used to represents user's ID
         */
        ID_KEY("ID"),

        /**
         *  Key used to represents user's username
         */
        USERNAME_KEY("Username"),

        /**
         *  Key used to represents user's password
         */
        PASSWORD_KEY("Password"),

        /**
         *  Key used to represents user's private name
         */
        PRIVATE_NAME_KEY("Private Name"),

        /**
         *  Key used to represents user's last name
         */
        LAST_NAME_KEY("Last Name"),

        /**
         *  Key used to represents user's email
         */
        EMAIL_KEY("Email"),

        /**
         *  Key used to represents user's birthday
         */
        BIRTHDAY_KEY("Birthday"),

        /**
         * Key used to represents the ID of a beach
         */
        BEACH_ID("Beach ID"),

        /**
         * Key used to represents the  name of a beach
         */
        BEACH_NAME("Beach Name"),

        /**
         * Key used to represents a list of beaches
         */
        BEACH_LIST("Beach List"),

        /**
         * Key used to represents the ID of an event
         */
        EVENT_ID("Event ID"),

        /**
         * Key used to represents the date of an event
         */
        EVENT_DATE("Event Date"),

        /**
         * Key used to represents the number of people participate in the morning of an event
         */
        EVENT_MORNING_COUNT("Event Morning Count"),

        /**
         * Key used to represents the number of people participate in the afternoon of an event
         */
        EVENT_AFTERNOON_COUNT("Event Afternoon Count"),

        /**
         * Key used to represents the number of people participate in the evening of an event
         */
        EVENT_EVENING_COUNT("Event Evening Count"),

        /**
         * Key used to represents if the user participate in the morning of an event
         */
        EVENT_MORNING_PARTICIPATE("User's Participation Morning Status"),

        /**
         * Key used to represents if the user participate in the afternoon of an event
         */
        EVENT_AFTERNOON_PARTICIPATE("User's Participation Afternoon Status"),

        /**
         * Key used to represents if the user participate in the evening of an event
         */
        EVENT_EVENING_PARTICIPATE("User's Participation Evening Status"),

        /**
         * Key used to represents a list of events
         */
        EVENT_LIST("Event List"),

        /**
         * Key used to represents the update method of an event (Remove user/Add user)
         */
        EVENT_UPDATE_METHOD("Event Update Method"),

        /**
         * Key used to represents a part of day
         */
        DAY_PART("Day Part"),

        /**
         * Key used to represents an ID of error
         */
        ERROR_ID_KEY("Error ID"),

        /**
         * Key used to represents a description of error
         */
        ERROR_DESC_KEY("Error Description"),

        /**
         * Key used to represents which argument is missing
         */
        MISSING_ARG("Missing Argument"),

        /**
         * Key used to represents which argument is invalid.
         */
        INVALID_ARG("Invalid Argument");

        private String value;

        /**
         * Creates a JsonArg with the specified value.
         * @param value The value of the JsonArg.
         */
        JsonArg(String value){
            this.value = value;
        }

        /**
         * Gets the value of the JsonArg.
         * @return A string representing the value of the JsonArg.
         */
        public String getValue(){
            return this.value;
        }
    }

    public enum ErrorCode {
        /**
         * Error codes that can be used in network communication
         * {@link #Success}
         * {@link #DB_Failure}
         * {@link #User_Exist}
         * {@link #Invalid_Request}
         * {@link #Args_Missing}
         * {@link #Invalid_Data}
         * {@link #Wrong_User_Credentials}
         * {@link #No_Connection}
         * {@link #Action_Failed}
         * {@link #Username_Exist}
         * {@link #Email_Exist}
         */

        /**
         * Error code represents the action succeed
         */
        Success(0, "Action Succeed"),

        /**
         * Error code represents there was error in database
         */
        DB_Failure(1, "Database failed"),

        /**
         * Error code represents there is user with the credentials provided
         */
        User_Exist(2, "Username or email already exist"),

        /**
         * Error code represents invalid request has been sent
         */
        Invalid_Request(3, "Request is missing or invalid"),

        /**
         * Error code represents there is arguments missing
         */
        Args_Missing(4, "Some of the arguments needed not provided"),

        /**
         * Error code represents the server received invalid data
         */
        Invalid_Data(5, "Server received invalid data"),

        /**
         * Error code represents there is no user with the credentials provided
         */
        Wrong_User_Credentials(6, "Could not find a user with the provided credentials"),

        /**
         * Error code represents the connection is disabled
         */
        No_Connection(7,"Connection is disabled"),

        /**
         * Error code represents the action has been failed for any reason
         */
        Action_Failed(8, "Action failed"),

        /**
         * Error code represents the username is already exist
         */
        Username_Exist(9, "Username already exist"),

        /**
         * Error code represents the email is already exist
         */
        Email_Exist(10, "Email already exist");

        private int code;
        private String description;

        /**
         * Creates an error code with the specified code and description
         * @param code the code of the error
         * @param description the description of the error
         */
        ErrorCode(int code, String description){
            this.code = code;
            this.description = description;
        }

        /**
         * Gets the code of an error
         * @return An int represents the code of the error
         */
        public int getCode() { return this.code;};

        /**
         * Gets the description of an error
         * @return A String contains the description of the error
         */
        public String getDescription(){
            return this.description;
        }
    }

    public enum EventUpdateMethod{
        /**
         * Update methods that can be used when updating an event
         * {@link #ADD_USER}
         * {@link #REMOVE_USER}
         */

        /**
         * Add user to event
         */
        ADD_USER("Add User"),

        /**
         * Remove user from event
         */
        REMOVE_USER("Remove User");

        private String value;

        /**
         * Creates an eventUpdateMethod with the specified value
         * @param value The value of the eventUpdateMethod
         */
        EventUpdateMethod(String value){this.value = value;}

        /**
         * Gets the value of the eventUpdateMethod
         * @return A string representing the value of the eventUpdateMethod
         */
        public String getValue(){return this.value;}
    }




    //Network Data
    public static final String SERVER_IP = "192.168.1.175"; // Server IP address
    public static final int PORT = 21211; // Server Port
    public static final int PACKET_SIZE = 1024; // Default Packet Size


    private static boolean connectedState = false; // Determines if user is connected or a guest;
    private static int connectedId = -1; // The ID of the connected user - if not connected: -1

    /**
     * Sets the connected state
     * @param state A boolean represents if the user is connected or a guest
     */
    public static void setConnectedState(boolean state){ GlobalVars.connectedState = state;
    }

    /**
     * Gets the connected state
     * @return A boolean represents if the user is connected or a guest
     */
    public static boolean getConnectedState(){
        return connectedState;
    }

    /**
     * Gets the id of the user
     * @return An int represents the id of the user
     */
    public static int getConnectedId() {
        return connectedId;
    }

    /**
     * Sets the id of the user
     * @param connectedId An int represents the id of the user
     */
    public static void setConnectedId(int connectedId) {
        GlobalVars.connectedId = connectedId;
    }
}
