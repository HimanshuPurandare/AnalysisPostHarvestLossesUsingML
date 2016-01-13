from db_controller import *


def ProcessDailyFarmData(d):
#    in_data = receive_from_rpi(request)
	in_data=d
	print in_data
	#adding this data to 
	print "date=",datetime.now()
	weather = { "MaxTemperature" : in_data['MaxTemperature'],"MaxHumidity":in_data['MaxHumidity'],"MaxSM":in_data['MaxSM'],"MinTemperature":in_data['MinTemperature'],"MinHumidity":in_data['MinHumidity'],"MinSM":in_data['MinSM'],"HWID": in_data['HWID'],"date":str(datetime.now())}
	    
	add_daily_farm_data(weather)
	print "Executed add daily farm data"
	return 'Hello World!'




def get_current_weather_data():

        f=open("sm.txt","r")
        a=f.read()
        f.close()
        f=open("hu.txt","r")
        b=f.read()
        f.close()
        f=open("te.txt","r")
        c=f.read()
        f.close()

        print "after func",a,b,c
        data = {'Humidity':b,'Soil Moisture':a,'Temperature':c}
        return data
        #return jsonify(Current_Weather = data)    



