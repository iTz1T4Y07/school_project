from enum import Enum, auto

"""
Date:28/03/2021
Written By: Itay Kahalani

The script holds global variables
"""


class ErrorCodes(Enum):
    """
    A class used to represent errors

    Attributes
    ----------
    value: tuple
        contains the id of the error and its description

    Methods
    --------

    __get__(instance, owner)
        return the value

    """

    def __get__(self, instance, owner):
        return self.value

    Success = (0, "Action Succeed")
    DB_Failure = (1, "Database failed")
    User_Exist = (2, "Username or email already exist")
    Invalid_Request = (3, "Request is missing or invalid")
    Args_Missing = (4, "Some of the arguments needed not provided")
    Invalid_Data = (5, "Server received invalid data")
    Wrong_User_Credentials = (6, "Could not find a user with the provided credentials")


class UserCheckMethod(Enum):
    """
    A class used to represent user check methods

    """

    Register_Check = auto()
    Login_Check = auto()


class DayParts(Enum):
    """
    A class used to represent parts of day

    Attributes
    ----------
    value: string
        contains the name of part of day

    """

    Morning = "Morning"
    Afternoon = "Afternoon"
    Evening = "Evening"


class EventUpdateMethods(Enum):
    """
    A class used to represent update methods of event

    Attributes
    ----------
    value: string
        contains the name of the update method

    """

    Add_User = "Add User"
    Remove_User = "Remove User"


class JsonArgs(Enum):
    """
    A class used to represent keys used in JSONObject

    Attributes
    ----------
    value: string
        contains the argument used in the JSONObject

    Methods
    --------

    __get__(instance, owner)
        return the value

    """

    def __get__(self, instance, owner):
        return self.value

    JSON_ARGS_KEY = "Args"
    JSON_ID_KEY = "ID"
    JSON_USERNAME_KEY = "Username"
    JSON_PASSWORD_KEY = "Password"
    JSON_PRIVATE_NAME_KEY = "Private Name"
    JSON_LAST_NAME_KEY = "Last Name"
    JSON_EMAIL_KEY = "Email"
    JSON_BIRTHDAY_KEY = "Birthday"
    JSON_BEACH_ID = "Beach ID"
    JSON_BEACH_NAME = "Beach Name"
    JSON_BEACH_LIST = "Beach List"
    JSON_EVENT_ID = "Event ID"
    JSON_EVENT_DATE = "Event Date"
    JSON_EVENT_MORNING_COUNT = "Event Morning Count"
    JSON_EVENT_AFTERNOON_COUNT = "Event Afternoon Count"
    JSON_EVENT_EVENING_COUNT = "Event Evening Count"
    JSON_EVENT_MORNING_PARTICIPATE = "User's Participation Morning Status"
    JSON_EVENT_AFTERNOON_PARTICIPATE = "User's Participation Afternoon Status"
    JSON_EVENT_EVENING_PARTICIPATE = "User's Participation Evening Status"
    JSON_EVENT_LIST = "Event List"
    JSON_EVENT_UPDATE_METHOD = "Event Update Method"
    JSON_DAY_PART = "Day Part"
    JSON_ERROR_ID_KEY = "Error ID"
    JSON_ERROR_DESC_KEY = "Error Description"
    JSON_MISSING_ARG = "Missing Argument"
    JSON_INVALID_ARG = "Invalid Argument"
