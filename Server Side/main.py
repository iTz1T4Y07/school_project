import server_socket
import db_setup


def main():
    db_setup.main()  # Initializing DB
    server_socket.main()  # Starting server


if __name__ == '__main__':
    main()
