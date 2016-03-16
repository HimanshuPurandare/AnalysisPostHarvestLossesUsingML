from sklearn.linear_model import BayesianRidge
from pymongo import MongoClient
import numpy as np

def get_opti_temp(crop_list=["Onions","Tomatoes"]):
	clft=BayesianRidge()
	clfo=BayesianRidge()
	client=MongoClient()             #//////////////////////////////////////////////////////////////////////
	db=client.server_db
	cursor_tomato=db.corpusoptitemp.find({"Datatype":"Tomatoes"})
	cursor_onion=db.corpusoptitemp.find({"Datatype":"Onions"})
	xt=[]
	yt=[]
	xo=[]
	yo=[]
	for docs in cursor_tomato:
		item=[]
		item.append(docs["Temperature"])
		item.append(docs["Humidity"])
		xt.append(item)
		yt.append(docs["Losses"])
		
		 
	for docs in cursor_onion:
		item=[]
		item.append(docs["Temperature"])
		item.append(docs["Humidity"])
		xo.append(item)
		yo.append(docs["Losses"])
	 
	clft.fit(xt,yt)
	clfo.fit(xo,yo)

	final = []

	for temp in range(9,40):
		for hum in range(60,100):
			a = clft.predict([[temp, hum]]) 
			b = clfo.predict([[temp, hum]])
			item = []
			item.append((a[0]+b[0])/2)
			item.append(temp)
			item.append(hum)
			final.append(item)

	#print final
	final.sort(key=lambda x: x[0])
	print "Store the produce at ", final[0][1], "C and at ",final[0][2],"% relative humidity."
	return final[0][1],final[0][2]
