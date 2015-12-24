from pymongo import MongoClient
client=MongoClient()
db=client.server_db

def predict_harvesting_time():
	cursor=db.corpusharvest.find()
	gdd_array=[]
	i=1
	for docs in cursor:
		gdd_array.append([(((docs["max_temp"]+docs["min_temp"])/2)-5),i])
		i+=1

	#print gdd_array

	cumu_gdd=0

	for items in gdd_array:
		cumu_gdd+=items[0]
		if cumu_gdd >= 2200:
			print 'harvesting time =',items[1]," days"
			return 'harvesting time ='+str(items[1])+" days"
		elif gdd_array.index(items)==len(gdd_array)-1:			
			return 'Harvesting time will be predicted shortly'
		
		
