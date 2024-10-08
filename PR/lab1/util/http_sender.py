import socket
import ssl

def get_html_content(url):
    def get_host_path_port(url):
        port = 443 if "https" in url else 80
        host_start = url.find("//") + len("//")
        path_start = url.find("/", host_start)
        host = url[host_start:path_start]
        path = url[path_start:]
        return host, path, port
    
    hostname, path, port = get_host_path_port(url)

    # Create a socket
    context = ssl.create_default_context()
    sock = socket.create_connection((hostname, port))

    # Wrap the socket in SSL
    ssl_sock = context.wrap_socket(sock, server_hostname=hostname)
    try:
        # Send an HTTP request
        request = f"GET {path} HTTP/1.1\r\nHost: {hostname}\r\nUser-Agent: PythonSocket\r\nConnection: close\r\n\r\n"
        ssl_sock.sendall(request.encode('utf-8'))

        # Receive the response
        response = b""
        while True:
            data = ssl_sock.recv(4096)
            if not data:
                break
            response += data
        
        return response.split(b"\r\n\r\n", 1)[1].decode('utf-8')
    finally:
        ssl_sock.close()