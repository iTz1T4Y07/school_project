import socket, os, sys, json


HOST_IP = '127.0.0.1'
DEST_PORT = 21211


def command_handle(connected_socket):
    try:
        """
        data = {"request": "ADD_USER"}
        data["Args"] = {"Username": "Tfest",
                        "Password": "abcd",
                        "Private Name": "Itay",
                        "Last Name" : "Kahalani",
                        "Email" : "abhc@gmail.com",
                        "Birthday": "07-04-2003"}
        
        data = {"request": "UPDATE_USER_IN_EVENT"}
        data["Args"] = {"ID": 2,
                        "Event ID": 3,
                        "Day Part": "Morning",
                        "Event Update Method": "Remove User"
                        }
                        
        data = {"request": "GET_EVENT_LIST", "Args": {"ID": "1",
                        "Beach ID": "1"}}
                        """

        data = {"request": "LOGIN_CHECK"}
        data["Args"] = {
            "Username": "test",
            "Password": "abcd123"
        }



        json_object = json.dumps(data)
        connected_socket.send(json_object.encode())
        connected_socket.send(b" "*1024*2)

        data_buffer = connected_socket.recv(1024)
        rec_data = b""

        while data_buffer != b" " * 1024:  # If received data sent in multiple packets
            rec_data += data_buffer
            data_buffer = connected_socket.recv(1024)

        print(len(rec_data))
        string = rec_data.decode()
        print(string)
        print(json.loads(string))
    except Exception as e:
        print(e)


def main():
    my_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    my_socket.connect((HOST_IP, DEST_PORT))

    command_handle(my_socket)


if __name__ == "__main__":
    main()
