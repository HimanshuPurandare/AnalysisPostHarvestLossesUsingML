import os
import time
import requests
import json
import socket
import sys
import os

server_address = '/home/pi/uds_socket'

prev = ''
curr = '12'

high_sm_cnt=0
cnt=0
mx_temp=0
min_temp=9999
mx_hu=0
min_hu=9999
mx_sm=0
min_sm=9999
sm = 0
hu = 0
te = 0

# Make sure the socket does not already exist
try:
    os.unlink(server_address)
except OSError:
    if os.path.exists(server_address):
        raise

# Create a UDS socket
sock = socket.socket(socket.AF_UNIX, socket.SOCK_STREAM)

# Bind the socket to the port
print >>sys.stderr, 'starting up on %s' % server_address
sock.bind(server_address)

# Listen for incoming connections
sock.listen(10)

while True:
    # Wait for a connection
    print >>sys.stderr, 'waiting for a connection'
    connection, client_address = sock.accept()
    try:
        print >>sys.stderr, 'connection from', client_address
	
	i = 3

	while i>0:
            requests.get('https://peaceful-shelf-30868.herokuapp.com/')
	    i-=1

        # Receive the data in small chunks and retransmit it
        while True:
            curr = connection.recv(16)
            if curr != "":
                print >>sys.stderr, 'received "%s"' % curr
                if prev == '9876' and curr !='9876' and curr != '6543' and curr != '3210':
                    asm = int(curr)
		    if asm < 1000:
			sm = asm
                    if sm > 10:
                        high_sm_cnt+=1
                        if high_sm_cnt > 3:
                            high_sm_cnt=0
                            print "notification sent"
                            re = requests.get('https://peaceful-shelf-30868.herokuapp.com/sendnotification/')
                            print re
                elif prev == '6543' and curr != '6543' and curr != '9876' and curr!='3210':
                    ahu = int(curr)
		    if ahu < 110:
			hu = ahu
                elif prev=='3210' and curr !='3210' and curr != '6543' and curr != '9876':
                    ate = int(curr)
		    if ate < 100:
			te = ate

                else:

                    print 'else'
                prev = curr

                if sm>=mx_sm and sm!=None:
                    print "mx_sm",mx_sm
                    mx_sm=sm
                if sm<=min_sm  and sm!=None:
                    print "min_sm",min_sm
                    min_sm=sm
                if hu>=mx_hu  and hu!=None:
                    print "mx_hu",mx_hu
                    mx_hu=hu
                if hu<=min_hu  and hu!=None:
                    print "min_hu",min_hu
                    min_hu=hu
                if te>=mx_temp  and te!=None :
                    print "mx_te",mx_temp
                    mx_temp=te
                if te<=min_temp  and te!=None:
                    print "min te",min_temp
                    min_temp=te
		if cnt == 6:
		    cnt = 0
		    d={}
                    d["MaxTemperature"]=mx_temp
                    d["MinTemperature"]=min_temp
                    d["MaxHumidity"]=mx_hu
                    d["MinHumidity"]=min_hu
                    d["MaxSM"]=mx_sm
                    d["MinSM"]=min_sm
                    d["HWID"]="yaugsbsvw"
		    print d
                    r = requests.get('https://peaceful-shelf-30868.herokuapp.com/sensordata/',data=d)
                    print r
		    mx_temp=0
                    min_temp=9999
                    mx_hu=0
                    min_hu=9999
                    mx_sm=0
                    min_sm=9999
                cnt+=1

            else:
                break

    finally:
        # Clean up the connection
        connection.close()
