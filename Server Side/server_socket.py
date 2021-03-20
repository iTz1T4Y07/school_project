import socket
import threading
import json
import command_functions
from global_vars import ErrorCodes, JsonArgs
import select

IP_ADDRESS = '0.0.0.0'
PORT = 21211
PACKET_SIZE = 1024
TIMEOUT = 3

COMMANDS = {"ADD_USER": command_functions.add_new_user,
            "LOGIN_CHECK": command_functions.check_login_credentials,
            "GET_USER_INFO": command_functions.extract_user_information,
            "GET_BEACH_LIST": command_functions.extract_beach_list,
            "GET_EVENT_LIST": command_functions.extract_event_list,
            "UPDATE_USER_IN_EVENT": command_functions.update_user_in_event
            }


def new_connection_handle(client_socket, address):
    """
    Handles a new connection
    Finds the right function and call it
    :param client_socket: the socket that holding the client data
    :param address: IP address of the client
    :return:
    """
    print("New connection from {}".format(address))

    data_buffer = client_socket.recv(PACKET_SIZE)
    data = b""

    while data_buffer != b" " * PACKET_SIZE:  # If received data sent in multiple packets
        data += data_buffer
        data_buffer = client_socket.recv(PACKET_SIZE)
    client_socket.recv(PACKET_SIZE * 2)

    data = data.decode()
    print(data)

    try:
        json_object = json.loads(data)
        json_result_object = COMMANDS[json_object['request']](json_object[JsonArgs.JSON_ARGS_KEY])

    except ValueError as e:
        # Json failed decoding the received data
        print("Decoding JSON has failed")
        print("Error: {}".format(e))
        json_result_object = command_functions.dict_builder(ErrorCodes.Invalid_Data)
    except KeyError as e:
        # Json object is missing request field, or the value is invalid
        print("Invalid request")
        print("Error: {}".format(e))
        json_result_object = command_functions.dict_builder(ErrorCodes.Invalid_Request)
    finally:
        print("Result data that will be sent to client: {}".format(json_result_object))

        client_socket.send(json.dumps(json_result_object).encode())
        client_socket.send(b" " * 2 * PACKET_SIZE)  # Sends data in len of 2 packets, to make sure client received block
        # of empty spaces in the length of Packet Size

        try:
            time_passed = 0
            while time_passed < TIMEOUT:
                client_socket.send(" ".encode())
                threading.Event().wait(2)
                time_passed = time_passed + 2
            raise ConnectionAbortedError
        except ConnectionAbortedError as e:
            client_socket.close()


def main():
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.bind((IP_ADDRESS, PORT))
    server_socket.listen(10)
    print("Listening for connections on port %d" % PORT)

    while True:
        print("Server is waiting for a new connection")
        client_socket, address = server_socket.accept()
        connection_thread = threading.Thread(target=new_connection_handle, args=(client_socket, address))
        connection_thread.start()


if __name__ == '__main__':
    main()
