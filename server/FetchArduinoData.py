import serial
import time

try:
        ser = serial.Serial('/dev/ttyACM0',9600)
except:
        print "Check /dev/"

def fetch_sm():
#        print "inside fetch_sm"
        sm = (ser.readline())
#        sm = "23.34"
        d = {"T":0,"H":0,"S":0}
        try:
                if sm[0] == 'S':
                    
                        sm1 = sm.split('S')
                        f=open("sm.txt","w")

                        sm = float(sm)
                        f=open("sm.txt","w")
                        f.write(str(sm))
                        f.close()
                        d["S"] = int(sm1[1])
                        print "S",sm1
                        return d
                if sm[0] == 'T':
                        te = sm.split('T')
                        f=open("te.txt","w")
                        f.write(str(te))
                        f.close()
                        d['T'] = int(te[1])
                        print te
                        return d
                if sm[0] == 'H':
                        te = sm.split('H')
                        f=open("hu.txt","w")
                        f.write(str(te))
                        f.close()
                        d['H'] = int(te[1])
                        print te
                        return d

                        
        except:
                fetch_sm()


def fetch_hu():
        global hu
#        print "inside fetch_hu"
        hu = (ser.readline())
#        hu = "34.45"
        try:
                hu = float(hu)
                f=open("hu.txt","w")
                f.write(str(hu))
                f.close()
                return hu
        except:
#                print "exception in fetching hu"
                fetch_hu()


def fetch_te():
        global te
#        print "inside fetch_te"
        te = (ser.readline())
#        te = "56.67"
        try:
                te = float(te)
                f=open("te.txt","w")
                f.write(str(te))
                f.close()
                return te
        except:
                fetch_te()



while True:
        print fetch_sm()
