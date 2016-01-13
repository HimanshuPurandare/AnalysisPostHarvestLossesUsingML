import serial
import time

ser = serial.Serial('/dev/ttyACM0',9600)

sm = ""
hu = ""
te = ""
def fetch_sm():
        global sm
#        print "inside fetch_sm"
        sm = (ser.readline())
#        sm = "23.34"
        try:
                sm = float(sm)
                f=open("sm.txt","w")
                f.write(str(sm))
                f.close()
                return sm
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




