import sqlite3
import os.path as path
import datetime

DB_PATH = r".\database\surfing_database.db"

CREATE_USERS_TABLE_QUERY = """
CREATE TABLE IF NOT EXISTS USERS (
ID INTEGER NOT NULL PRIMARY KEY,
Username text NOT NULL UNIQUE,
Password text NOT NULL,
Private_Name text NOT NULL,
Last_Name text NOT NULL,
Email text NOT NULL UNIQUE,
Birthday DATE NOT NULL
);
"""

CREATE_BEACHES_TABLE_QUERY = """
CREATE TABLE IF NOT EXISTS BEACHES (
ID INTEGER NOT NULL PRIMARY KEY,
NAME text NOT NULL UNIQUE
);
"""

CREATE_EVENTS_TABLE_QUERY = """
CREATE TABLE IF NOT EXISTS EVENTS (
ID INTEGER NOT NULL PRIMARY KEY,
Beach_ID INTEGER NOT NULL,
Date DATE NOT NULL,
Morning_Count INTEGER DEFAULT 0,
Afternoon_Count INTEGER DEFAULT 0,
Evening_Count INTEGER DEFAULT 0,
Morning_Participants TEXT DEFAULT ",",
Afternoon_Participants TEXT DEFAULT ",",
Evening_Participants TEXT DEFAULT ",",
FOREIGN KEY (Beach_ID) REFERENCES BEACHES(ID)
);
"""


def create_sqlite_db(db_file):
    """
    Create a database connection to a SQLite database
    :param db_file: Path to database
    """

    conn = None
    try:
        conn = sqlite3.connect(db_file)
        print(sqlite3.version)
    except sqlite3.Error as e:
        print(e)
    finally:
        if conn:
            conn.close()


def get_db_connection(db_path):
    conn = None
    try:
        conn = sqlite3.connect(db_path)
    except sqlite3.Error as e:
        print(e)
    return conn


def create_new_table(conn, create_table_statement):
    """
    Create a new table in the database
    :param conn: Connection Object
    :param create_table_statement: create table query statement
    :return: true if success, false if failed
    """

    try:
        c = conn.cursor()
        c.execute(create_table_statement)
        return True
    except sqlite3.Error as e:
        print(e)
        return False


def create_database(db_path):
    if not path.exists(db_path):
        create_sqlite_db(db_path)

    if path.exists(db_path):
        # Create a connection to DB
        conn = get_db_connection(db_path)

        # Creating Users table
        print("Users Table created: {}".format(create_new_table(conn, CREATE_USERS_TABLE_QUERY)))
        # Creating Beaches table
        print("Beaches Table created: {}".format(create_new_table(conn, CREATE_BEACHES_TABLE_QUERY)))
        # Creating Events table
        print("Events Table created: {}".format(create_new_table(conn, CREATE_EVENTS_TABLE_QUERY)))

        """
        For testing:
        Creating user: 
        Creating 2 beaches: Rishon & Palmahim
        Creating for each one 2 events
        """
        try:
            c = conn.cursor()

            ADD_USER = """
            INSERT INTO USERS (ID, Username, Password, Private_Name, Last_Name, Email, Birthday)
            VALUES (?, ?, ?, ?, ?, ?, ?);
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

            birthday = datetime.datetime.strptime("07-04-2003", "%d-%m-%Y")
            c.execute(ADD_USER, (1, "Test", "abcd123", "Itay", "Kahalani", "abc@gmail.com", birthday))

            c.execute(ADD_BEACH, (1, "Rishon"))
            c.execute(ADD_BEACH, (2, "Palmahim"))

            sunday = datetime.datetime.strptime("14-02-2021", "%d-%m-%Y")
            monday = datetime.datetime.strptime("15-02-2021", "%d-%m-%Y")

            c.execute(ADD_EVENT, (1, sunday, 0, 0, 0, "", "", ""))
            c.execute(ADD_EVENT, (1, monday, 1, 0, 1, "1,", "", "1,"))

            c.execute(ADD_EVENT, (2, sunday, 0, 0, 0, "", "", ""))
            c.execute(ADD_EVENT, (2, monday, 1, 0, 1, "1,", "", "1,"))
            conn.commit()

        except sqlite3.Error as e:
            print(e)

        conn.close()


if __name__ == '__main__':
    create_database(DB_PATH)
