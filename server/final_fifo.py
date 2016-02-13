#Input will be a list of list, where inner lists will contain parameters in sequence of Storage_Days, temperature, humidity, deflection_from_harvesting time and a list of stock names arranged according to the sequence in listoflist.

from sklearn import linear_model
from pymongo import MongoClient
import numpy as np
import datetime
import requests
import json

#sample working input:
#listoflist = [[120, 26, 76, 3], [90, 27, 71, 6], [30, 18, 84, 0]]
#name = ["stock1", "stock2", "stock3"]



def get_dispatch_sequence(stock_list,warehouse_info,amount):
	listoflist=[]
	name=[]
	datatype=stock_list.__getitem__(0)['StockCropName']
	print datatype
	average_harvesting_time={"Wheat":130,"Onion":150,"Rice":133}


	lat_long=[map(float,y) for y in [x.split(',') for x in (((warehouse_info['AddWarehouseURL'].split('fillcolor:orange%7Ccolor:red%7C')[1]).split('%7C'))[1:])]]
	
	lattitude=str(sum([x[0] for x in lat_long])/len(lat_long))
	longitude=str(sum([x[1] for x in lat_long])/len(lat_long))
	
	
	
	url = "https://api.forecast.io/forecast/effdaeb7474c03015ad3f83872d83696/"+lattitude+","+longitude+","+datetime.datetime.now().isoformat().split('T')[0]+"T11:59:59"

	print "before fetching response data"
#	try:
#		response_data=json.loads(requests.get(url).content)['daily']['data'][0]
#	except:
	response_data={"temperatureMin":"19","temperatureMax":"28","humidity":"0.50"}
	print "Response Data is:",response_data
	name_amount={}
	date_format='%d-%m-%Y'
	
	print "\n\nstock list",stock_list
	for i in stock_list:
		print "\n\n\ninside the for loop"
		feature_list=[]

		feature_list.append((datetime.datetime.now()-datetime.datetime.strptime(i['StockHarvestEnd'],date_format)).days)
				
		feature_list.append(int(((float(response_data['temperatureMin'])+float(response_data['temperatureMax']))/2-32)*5/9))
		feature_list.append(int(float(response_data['humidity'])*100.0))
		
		print "feature list is",feature_list
		day,month,year=map(int,i['StockSowStart'].split('-'))
		print day,month,year
		ss=datetime.datetime(year,month,day)
		day,month,year=map(int,i['StockSowEnd'].split('-'))
		se=datetime.datetime(year,month,day)
		day,month,year=map(int,i['StockHarvestStart'].split('-'))
		hs=datetime.datetime(year,month,day)
		day,month,year=map(int,i['StockHarvestEnd'].split('-'))
		he=datetime.datetime(year,month,day)
		
		print ss,se,hs,he
		
		harvesting_time=0-((ss+datetime.timedelta((se-ss).days))-(hs+datetime.timedelta((he-hs).days))).days
		
		print harvesting_time

		feature_list.append(int(abs(harvesting_time-average_harvesting_time[i['StockCropName']])))

		print feature_list
		
		listoflist.append(feature_list)
		name.append(i['StockName'])
		
		name_amount[i['StockName']]=int(i['StockAmount'])
	
	print listoflist,name,datatype
	sequence=predict_dispatch_sequence(listoflist,name,datatype)
	
	print "\n\n\nThe sequence is:",sequence
	
	dispatch_list=[]
	print name_amount
	for i in sequence:
		temp_amount=name_amount[i]
		if amount>=temp_amount:
			dispatch_list.append({"StockName":i,"DispatchAmount":temp_amount})
			amount-=temp_amount
			if not amount:
				break

		else:
			dispatch_list.append({"StockName":i,"DispatchAmount":amount})	
			amount-=amount
			break
	
	
	
	print dispatch_list
	return dispatch_list
	
	
		
	

def predict_dispatch_sequence(listoflist, name, datatype):
	clf=linear_model.LinearRegression()
	client=MongoClient()
	db=client.server_db
	cursor=db.corpusfifo.find({"Datatype": datatype})
	x=[]
	y=[]


	for docs in cursor:
	     item=[]
	     item.append(docs["days"])
	     item.append(docs["temperature"])
	     item.append(docs["humidity"])
	     item.append(docs["deflection"])
	     x.append(item)
	     y.append(docs["losses"])
	
	clf.fit(x,y)

	unordered_queue=[]

	#there will be a for loop here.

	for l in range(0, len(name)):
		unordered_queue.append((clf.predict([listoflist[l]]), name[l]))

	unordered_queue.sort(key=lambda x: x[0], reverse=True)
	oqueue = []

	#print "Dispatch the stocks accordingly: "
	for i in unordered_queue:
		 oqueue.append(i[1])
	return oqueue
#print predict_dispatch_sequence(listoflist, name, "onions")
