import server_socket
import db_setup

"""
Date:28/03/2021
Written By: Itay Kahalani

The script starts the whole server side - Initializing database and starts server side
"""


def main():
    """
    Starting the server side:
    Creating the required resources and starting network connection
    """

    db_setup.main()  # Initializing DB
    server_socket.main()  # Starting server


if __name__ == '__main__':
    main()
