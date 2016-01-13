#import urllib
#import urllib2


from FetchArduinoData import *
from send_notification import *
from ProcessArduinoData import *


high_sm_cnt=0
cnt=0
mx_temp=0
min_temp=9999
mx_hu=0
min_hu=9999
mx_sm=0
min_sm=9999

while True:
#        print "Inside while"
        sm=fetch_sm()
        print sm
        if(sm>10):
                high_sm_cnt+=1
                if high_sm_cnt>3:
                        sn()                
                        high_sm_cnt=-100
                        print "\n\n\n\t\t\tmessage sent for soil moisture\n\n\n"

        else:
                high_sm_cnt=0
        hu=fetch_hu()
        print hu
        te=fetch_te()
        print te

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




        if cnt==60:
                cnt = 0
#                url = "http://192.168.0.3:5000/getdailyweatherdatafrompi/"
#               url = "http://192.168.0.105:5000/getdailyweatherdatafrompi/"
                #url = "http://192.168.1.131:5000/"
                d={}
                d["MaxTemperature"]=mx_temp
                d["MinTemperature"]=min_temp
                d["MaxHumidity"]=mx_hu
                d["MinHumidity"]=min_hu
                d["MaxSM"]=mx_sm
                d["MinSM"]=min_sm
                d["HWID"]="yaugsbsvw"
                ProcessDailyFarmData(d)
#                data = urllib.urlencode(d)
                #print "paddy"
#                req = urllib2.Request(url,data)
                #print req
#                r = urllib2.urlopen(req)


                mx_temp=0
                min_temp=9999
                mx_hu=0
                min_hu=9999
                mx_sm=0
                min_sm=9999

        cnt+=1


