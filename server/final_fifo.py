from sklearn import linear_model
from pymongo import MongoClient
import numpy as np

def predict_dispatch_sequence():
	clf=linear_model.LinearRegression()
	client=MongoClient()
	db=client.server_db
	cursor=db.corpusfifo.find()
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

	test=[]
	def0=[]
	def1=[]
	def2=[]
	def3=[]
	def4=[]
	def5=[]
	def6=[]

	for item in x:
		if(item[3]==0):
			def0.append(item)
		if(item[3]==1):
			def1.append(item)
		if(item[3]==2):
			def2.append(item)
		if(item[3]==3):
			def3.append(item)
		if(item[3]==4):
			def4.append(item)
		if(item[3]==5):
			def5.append(item)
		if(item[3]==6):
			def6.append(item)
		
	unordered_queue=[]

	unordered_queue.append((np.mean(clf.predict(def0)), 0))
	unordered_queue.append((np.mean(clf.predict(def1)), 1))
	unordered_queue.append((np.mean(clf.predict(def2)), 2))
	unordered_queue.append((np.mean(clf.predict(def3)), 3))
	unordered_queue.append((np.mean(clf.predict(def4)), 4))
	unordered_queue.append((np.mean(clf.predict(def5)), 5))
	unordered_queue.append((np.mean(clf.predict(def6)), 6))

	#print unordered_queue

	unordered_queue.sort(key=lambda x: x[0], reverse=True)


	print "Dispatch the stocks accordingly: "
	for i in unordered_queue:
		print str(i[1])
	return unordered_queue	
	#print test
	#print clf.predict(test)
