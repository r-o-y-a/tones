from pythonosc import dispatcher, osc_server
from http.server import SimpleHTTPRequestHandler, HTTPServer
from socketserver import TCPServer
import threading
import json
import websockets
import asyncio

network_ip = '192.168.0.10'

data = None

def osc_handler(address, *args):
    global data
    if address == "/data":
        data = args

dispatcher = dispatcher.Dispatcher()
dispatcher.map("/data", osc_handler)
osc_server = osc_server.ThreadingOSCUDPServer(("127.0.0.1", 57121), dispatcher)

def serve_webpage():
    class MyHandler(SimpleHTTPRequestHandler):
        def end_headers(self):
            self.send_header('Access-Control-Allow-Origin', '*')
            #content_length = 10000
            #self.send_header('Content-Length', str(content_length))
            super().end_headers()

    #with TCPServer(('0.0.0.0', 8080), MyHandler) as httpd:
        #print("Web server serving at port 8080...")
     #  httpd.serve_forever()
    httpd = HTTPServer(('0.0.0.0', 8080), MyHandler)
    print("Web server serving at port 8080...")
    http_thread = threading.Thread(target=httpd.serve_forever)
    http_thread.start()

async def send_osc_data(websocket, path):
    global data
    while True:
        if data is not None:
            json_data = json.dumps({"data": data})
            await websocket.send(json_data)
            data = None
        await asyncio.sleep(1)
        

# start the OSC server in a separate thread
osc_thread = threading.Thread(target=osc_server.serve_forever)
osc_thread.start()

# start serving the webpage in another thread
web_thread = threading.Thread(target=serve_webpage)
web_thread.start()

# start the WebSocket server in the main thread (port is also in index.html)
start_server = websockets.serve(send_osc_data, network_ip, 8081)
asyncio.get_event_loop().run_until_complete(start_server)
asyncio.get_event_loop().run_forever()
