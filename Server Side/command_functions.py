import db_queries
import datetime
from global_vars import ErrorCodes, UserCheckMethod, JsonArgs, DayParts, EventUpdateMethods

"""
Date:28/03/2021
Written By: Itay Kahalani

The script manage to handle a request and return the information required.
"""


def dict_builder(error, args=None):
    """
    Builds a dictionary that will represent JSON Object
    :param error: Tuple of (numeric value - Error code, text - Error Description)
    :param args: Any arguments that have to be included
    :return:
    """
    return {JsonArgs.JSON_ERROR_ID_KEY: error[0], JsonArgs.JSON_ERROR_DESC_KEY: error[1], JsonArgs.JSON_ARGS_KEY: args}


def add_new_user(json_object):
    """
    Extracting the information about a new user that has to be added to database
    Then calling another function to add it to database
    :param json_object: Contains the args of the user
    :return: Dictionary that represents JSON Object,
    that contains the Error code, and the ID of the new user if it succeed
    """

    try:
        username = json_object[JsonArgs.JSON_USERNAME_KEY]
        password = json_object[JsonArgs.JSON_PASSWORD_KEY]
        private_name = json_object[JsonArgs.JSON_PRIVATE_NAME_KEY]
        last_name = json_object[JsonArgs.JSON_LAST_NAME_KEY]
        email = json_object[JsonArgs.JSON_EMAIL_KEY]
        birthday = datetime.datetime.strptime(json_object[JsonArgs.JSON_BIRTHDAY_KEY],
                                              "%d-%m-%Y")
    except KeyError as e:
        # Json Object is missing data
        print("Error: {}".format(e))
        return dict_builder(ErrorCodes.Args_Missing, {JsonArgs.JSON_MISSING_ARG: str(e)[1:-1]})

    exist_user_info = db_queries.get_user_id(username, email, UserCheckMethod.Register_Check);
    if exist_user_info is not None:
        return dict_builder(ErrorCodes.User_Exist, exist_user_info[1])

    error_code, user_id = db_queries.add_user_to_database(username, password, private_name, last_name, email, birthday)
    return dict_builder(error_code, {JsonArgs.JSON_ID_KEY: user_id})


def check_login_credentials(json_object):
    """
    Extracting the login credentials about a user
    Then calling another function to check if the user exist
    :param json_object: Contains the args of the user
    :return: Dictionary that represents JSON Object, that contains the Error code and the ID of the user if exist,
            Success ErrorCode if user exist, if it is not exist - Wrong_User_Credentials error code.
    """

    try:
        username = json_object[JsonArgs.JSON_USERNAME_KEY]
        password = json_object[JsonArgs.JSON_PASSWORD_KEY]
    except KeyError as e:
        # Json object is missing data
        print("Error: {}".format(e))
        return dict_builder(ErrorCodes.Args_Missing, {JsonArgs.JSON_MISSING_ARG: str(e)[1:-1]})

    user_id = db_queries.get_user_id(username, password, UserCheckMethod.Login_Check)
    if user_id is not None:
        # If user exist
        return dict_builder(ErrorCodes.Success, {JsonArgs.JSON_ID_KEY: user_id})

    return dict_builder(ErrorCodes.Wrong_User_Credentials)


def extract_user_information(json_object):
    """
    Extracting the ID of a user
    Then calling another function to get user information
    :param json_object: Contains the args of the user
    :return: Dictionary that represents JSON Object
    that contains the Error code and the information of the user if exist
    """

    try:
        user_id = json_object[JsonArgs.JSON_ID_KEY]
    except KeyError as e:
        # Json object is missing data
        print("Error: {}".format(e))
        return dict_builder(ErrorCodes.Args_Missing, {JsonArgs.JSON_MISSING_ARG: str(e)[1:-1]})

    user_info = db_queries.get_user_info(user_id)
    if user_info is not None:
        # If user exist
        return dict_builder(ErrorCodes.Success, user_info)

    return dict_builder(ErrorCodes.Wrong_User_Credentials)


def extract_beach_list(json_object):
    """
    Calling a function to get beach list
    :param json_object: Contains the request
    :return: Dictionary that represents JSON Object
    that contains the Error code and list of beaches
    """
    beach_list = db_queries.get_beach_list()
    if beach_list is not None:
        return dict_builder(ErrorCodes.Success, beach_list)

    return dict_builder(ErrorCodes.DB_Failure)


def extract_event_list(json_object):
    """
    Calling a function to get an event list
    :param json_object: Contains the request parameters
    :return: Dictionary that represents JSON Object
    that contains the Error code and list of events
    """

    try:
        user_id = json_object[JsonArgs.JSON_ID_KEY]
        beach_id = json_object[JsonArgs.JSON_BEACH_ID]
    except KeyError as e:
        # Json Object is missing data
        print("Error: {}".format(e))
        return dict_builder(ErrorCodes.Args_Missing, {JsonArgs.JSON_MISSING_ARG: str(e)[1:-1]})

    event_list = db_queries.get_event_list(beach_id, user_id)
    if event_list is not None:
        return dict_builder(ErrorCodes.Success, event_list)

    return dict_builder(ErrorCodes.DB_Failure)


def update_user_in_event(json_object):
    """
    Calling a function to add user or remove user from event, depends on request
    :param json_object: Contains the args of the request
    :return: Dictionary that represents JSON Object
    that contains the Error code and list of events
    """

    try:
        update_method = json_object[JsonArgs.JSON_EVENT_UPDATE_METHOD]
        user_id = json_object[JsonArgs.JSON_ID_KEY]
        event_id = json_object[JsonArgs.JSON_EVENT_ID]
        day_part = json_object[JsonArgs.JSON_DAY_PART]
    except KeyError as e:
        # Json Object is missing data
        print("Error: {}".format(e))
        return dict_builder(ErrorCodes.Args_Missing, {JsonArgs.JSON_MISSING_ARG: str(e)[1:-1]})

    if update_method == EventUpdateMethods.Add_User.value:
        error_code = db_queries.add_user_to_event(event_id, user_id, DayParts(day_part))
    elif update_method == EventUpdateMethods.Remove_User.value:
        error_code = db_queries.remove_user_from_event(event_id, user_id, DayParts(day_part))
    else:
        return dict_builder(ErrorCodes.Invalid_Data, {JsonArgs.JSON_INVALID_ARG: update_method})
    return dict_builder(error_code)



