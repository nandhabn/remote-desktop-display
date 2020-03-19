import PIL.ImageGrab as scrn
import socket


s = socket.socket()          
port = 8000          

s.bind(('', port))         
s.listen(5)      

while True: 
   c, addr = s.accept()   
   print(addr)
   a=scrn.grab()    
   c.sendall(a)
   c.close() 
