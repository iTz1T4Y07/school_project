import json
import sqlite3
from db_setup import get_db_connection, DB_PATH
from global_vars import ErrorCodes, UserCheckMethod, JsonArgs, DayParts
import datetime


EVENT_LIST_LIMIT = 7

ADD_USER = """
INSERT INTO USERS (Username, Password, Private_Name, Last_Name, Email, Birthday)
VALUES (?, ?, ?, ?, ?, ?);
"""

ADD_BEACH = """
INSERT INTO BEACHES (ID, Name)
VALUES (?, ?);
"""

ADD_EVENT = """
INSERT INTO EVENTS (Beach_ID, Date, Morning_Count, Afternoon_Count, Evening_Count,Morning_Participants,
Afternoon_Participants, Evening_Participants)
VALUES (?, ?, ?, ?, ?, ?, ?, ?);
"""

SEARCH_USER_BY_USERNAME = """
SELECT ID FROM USERS WHERE Username = (?);
"""

SEARCH_USER_BY_EMAIL = """
SELECT ID FROM USERS WHERE Email = (?);
"""

USER_LOGIN_CHECK = """
SELECT ID FROM USERS WHERE Username = (?) AND Password = (?);
"""

GET_USER_INFO = """
SELECT Username, Private_Name, Last_Name, Email, Birthday
FROM USERS WHERE ID = (?);
"""

GET_BEACH_LIST = """
SELECT ID, NAME FROM BEACHES;
"""

GET_EVENT_LIST = """
SELECT ID, Date, Morning_Count, Afternoon_Count, Evening_Count,
Morning_Participants, Afternoon_Participants, Evening_Participants 
FROM EVENTS WHERE Beach_ID = (?) AND
(Julianday(Date) - julianday('now')) >= 0 
LIMIT (?);
"""


ADD_USER_TO_EVENT = """
UPDATE EVENTS SET {day_part_count} = {day_part_count} + 1,
{day_part_users} = {day_part_users} || (?) || ','
WHERE ID = (?)
"""

REMOVE_USER_FROM_EVENT = """
UPDATE EVENTS SET {day_part_count} = {day_part_count} - 1,
{day_part_users} = replace({day_part_users}, ',' || :user_id || ',', ',')
WHERE ID = :event_id AND GLOB('*,' || :user_id || ',*', {day_part_users})
"""


def execute_statement(statement, args=()):
    """
    Executes a statement in db
    :param statement: statement to execute
    :param args: tuple of all the arguments needed
    :return: result of the execution, and connection
    """

    result = None
    try:
        conn = get_db_connection(DB_PATH)  # Connecting to database
        c = conn.cursor()
        result = c.execute(statement, args)
        print(statement)
        print(result)
        conn.commit()
    except sqlite3.Error as e:
        print(e)
        if conn:
            conn.close()
        return None, None

    # Connection remains open

    return result, conn


def get_user_id(username, arg2, check_type):
    """
    Search for a user with specific data values in database
    :param username: User's username
    :param arg2: User's Second parameter value
    :param check_type: The method used for searching the user
    :return: id of user and the exist param, if the user exist, else return None
    """

    if check_type == UserCheckMethod.Register_Check:
        data = None
        result, conn = execute_statement(SEARCH_USER_BY_EMAIL, (arg2.lower(), ))
        if result is not None:
            result = result.fetchall()
            if len(result) > 0:  # = If there are rows
                data = result[0][0]
            if conn:
                conn.close()
            if data:
                return data, JsonArgs.JSON_EMAIL_KEY

        result, conn = execute_statement(SEARCH_USER_BY_USERNAME, (username.lower(),))
        if result is not None:
            result = result.fetchall()
            if len(result) > 0:  # = If there are rows
                data = result[0][0]
            if conn:
                conn.close()
            if data:
                return data, JsonArgs.JSON_USERNAME_KEY
        return None

    elif check_type == UserCheckMethod.Login_Check:
        result, conn = execute_statement(USER_LOGIN_CHECK, (username.lower(), arg2))
    else:
        return None

    data = None

    if result is not None:
        result = result.fetchall()
        if len(result) > 0:  # = If there are rows
            data = result[0][0]
    if conn:
        conn.close()
    return data


def add_user_to_database(username, password, private_name, last_name, email, birthday):
    """
    Adding a new user to database
    :param username: User's username
    :param password: User's Password
    :param private_name: User's Private_name
    :param last_name: User's Last_name
    :param email: User's Email
    :param birthday: User's Birthday
    :return: Error code, and the ID of the new user if it succeed
    """
    result, conn = execute_statement(ADD_USER, (username.lower(), password, private_name, last_name,
                                                email.lower(), birthday))
    if conn:
        conn.close()
    if result is None:
        return ErrorCodes.DB_Failure, None
    else:
        user_id, exist_param = get_user_id(username.lower(), email.lower(), UserCheckMethod.Register_Check)
        return ErrorCodes.Success, user_id


def get_user_info(user_id):
    """
    Search for a user with specific id in database
    :param user_id: User's ID
    :return: Dictionary of the user's information
    """

    result, conn = execute_statement(GET_USER_INFO, (user_id,))

    data = None

    if result is not None:
        result = result.fetchall()
        if len(result) > 0:
            data = {
                JsonArgs.JSON_USERNAME_KEY: result[0][0],
                JsonArgs.JSON_PRIVATE_NAME_KEY: result[0][1],
                JsonArgs.JSON_LAST_NAME_KEY: result[0][2],
                JsonArgs.JSON_EMAIL_KEY: result[0][3],
                JsonArgs.JSON_BIRTHDAY_KEY: result[0][4]
            }
    if conn:
        conn.close()
    return data


def get_beach_list():
    """
    Search for all beaches exist in database
    :return: Dictionary contains list of all the beaches found
    """

    result, conn = execute_statement(GET_BEACH_LIST)

    data = None

    if result is not None:
        result = result.fetchall()
        information = []
        if len(result) > 0:
            for row in result:
                information.append({
                    JsonArgs.JSON_BEACH_ID: row[0],
                    JsonArgs.JSON_BEACH_NAME: row[1]
                })
            beach_list = ""
            for beach in information:
                beach_list += json.dumps(beach) + " , "
        data = {JsonArgs.JSON_BEACH_LIST: beach_list[0:-3]}
        print(data)
    if conn:
        conn.close()
    return data


def get_event_list(beach_id, user_id):
    """
    Search for all events with the specific beach id in dataase
    :param beach_id: Id of the beach the events are for
    :param user_id: Id of the user asked for the list
    :return: Dictionary that contains list of all events found, and information if user takes part of it or no
    """

    result, conn = execute_statement(GET_EVENT_LIST, (beach_id, EVENT_LIST_LIMIT))

    data = None

    if result is not None:
        result = result.fetchall()
        information = []
        if len(result) > 0:
            for row in result:
                information.append({
                    JsonArgs.JSON_EVENT_ID: row[0],
                    JsonArgs.JSON_EVENT_DATE: row[1].split()[0],
                    JsonArgs.JSON_EVENT_MORNING_COUNT: row[2],
                    JsonArgs.JSON_EVENT_AFTERNOON_COUNT: row[3],
                    JsonArgs.JSON_EVENT_EVENING_COUNT: row[4],
                    JsonArgs.JSON_EVENT_MORNING_PARTICIPATE: str(user_id) in row[5],
                    JsonArgs.JSON_EVENT_AFTERNOON_PARTICIPATE: str(user_id) in row[6],
                    JsonArgs.JSON_EVENT_EVENING_PARTICIPATE: str(user_id) in row[7]
                })
            event_list = ""
            for event in information:
                event_list += json.dumps(event) + " , "
            data = {JsonArgs.JSON_EVENT_LIST: event_list[0: -3]}
        if conn:
            conn.close()
        return data


def add_user_to_event(event_id, user_id, day_part):
    """
    Update an event, raise the count of specific part in the event by 1 and add's the user id to the list
    :param event_id: Event ID
    :param user_id: User ID
    :param day_part: Part of day
    :return: Error code
    """

    if day_part == DayParts.Morning:
        statement = ADD_USER_TO_EVENT.format(day_part_count="Morning_Count", day_part_users="Morning_Participants")

    elif day_part == DayParts.Afternoon:
        statement = ADD_USER_TO_EVENT.format(day_part_count="Afternoon_Count", day_part_users="Afternoon_Participants")

    elif day_part == DayParts.Evening:
        statement = ADD_USER_TO_EVENT.format(day_part_count="Evening_Count", day_part_users="Evening_Participants")
    else:
        return ErrorCodes.Invalid_Data

    result, conn = execute_statement(statement, (user_id, event_id))

    if conn:
        conn.close()
    if result is None:
        return ErrorCodes.DB_Failure, None
    else:
        return ErrorCodes.Success


def remove_user_from_event(event_id, user_id, day_part):
    """
    Update an event, reduce the count of specific part in the event by 1 and remove's the user id from the list
    :param event_id: Event ID
    :param user_id: User ID
    :param day_part: Part of day
    :return: Error code
    """

    if day_part == DayParts.Morning:
        statement = REMOVE_USER_FROM_EVENT.format(day_part_count="Morning_Count",
                                                  day_part_users="Morning_Participants")

    elif day_part == DayParts.Afternoon:
        statement = REMOVE_USER_FROM_EVENT.format(day_part_count="Afternoon_Count",
                                                  day_part_users="Afternoon_Participants")

    elif day_part == DayParts.Evening:
        statement = REMOVE_USER_FROM_EVENT.format(day_part_count="Evening_Count",
                                                  day_part_users="Evening_Participants")
    else:
        return ErrorCodes.Invalid_Data

    result, conn = execute_statement(statement, {"user_id": user_id, "event_id": event_id})

    if conn:
        conn.close()
    if result is None:
        return ErrorCodes.DB_Failure, None
    else:
        return ErrorCodes.Success
