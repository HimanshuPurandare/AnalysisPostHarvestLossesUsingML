from pymongo import MongoClient
client=MongoClient()
db=client.server_db
#datatype=str(raw_input("Crop : ")) 

def wheat_harvesting_time():
	cursor=db.corpusharvest.find({"Datatype":"wheat"})
	gdd_array=[]
	i=1
	for docs in cursor:
		gdd_array.append([(((docs["max_temp"]+docs["min_temp"])/2)-5),i])
		i+=1

	#print gdd_array

	cumu_gdd=0
	i=1

	for items in gdd_array:
		cumu_gdd+=items[0]
		if cumu_gdd >= 2200:
			print 'harvesting time =',items[1]," days"
			return 'harvesting time ='+str(items[1])+" days"
			break
	return "Harvesting time will be predicted shortly"

	#print cumu_gdd
	gdd_array=[]
	i=1
			
def rice_harvesting_time():
	cursor=db.corpusharvest.find({"Datatype":"rice"})
	gdd_array=[]
	i=1
	for docs in cursor:
		gdd_array.append([(((docs["max_temp"]+docs["min_temp"])/2)-10),i])
		i+=1

	#print gdd_array

	cumu_gdd=0
	i=1

	for items in gdd_array:
		cumu_gdd+=items[0]
		if cumu_gdd >= 1860:
			print 'harvesting time =',items[1]," days"
			return 'harvesting time ='+str(items[1])+" days"
			break
	return "Harvesting time will be predicted shortly"
	#print cumu_gdd
	gdd_array=[]
	i=1
	
#rice_harvesting_time()
#wheat_harvesting_time()
