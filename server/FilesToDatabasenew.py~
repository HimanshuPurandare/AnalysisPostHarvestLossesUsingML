

def CreateOptiTempData(collection):
	with open("tomatoesdatabase.csv", "rt") as f:  # open a file
		for line in f:
			    currentline = line.split("\t")
			    if '\n' in currentline[1]:
			    	currentline[1] = currentline[1].replace("\n", "");
			    text_file_doc = {"Datatype":"Tomatoes","Temperature":float(currentline[0]),"Humidity":float(currentline[1]),"Losses":float(currentline[2])}
			    collection.insert(text_file_doc)
		    	
	#------------Onions-------------

	with open("onionsdatabase.csv", "rt") as f3:  # open a file
		for line in f3:
			    currentline = line.split("\t")
			    if '\n' in currentline[2]:
			    	currentline[2] = currentline[2].replace("\n", "");
			    text_file_doc = {"Datatype": "Onions", "Temperature": float(currentline[0]), "Humidity" : float(currentline[1]), "Losses": float(currentline[2]) }
			    collection.insert(text_file_doc)


def CreateHarvestData(collection):
	with open("temperature.csv", "rt") as f:  # open a file
		for line in f:
			currentline = line.split("\t")
			#print currentline
			if '\n' in currentline[2]:
				currentline[2] = currentline[2].replace("\n", "")
			text_file_doc = {"max_temp":int(currentline[0]),"min_temp":int(currentline[1]), "Datatype":str(currentline[2])}
			#print text_file_doc
			collection.insert(text_file_doc)


def CreateFifoData(collection):
#	print "Entered in function createfifoDATA"
	with open("FIFO_FINAL.csv", "rt") as f:  # open a file
		for line in f:
			currentline = line.split("\t")
			if '\n' in currentline[5]:
				currentline[5] = currentline[5].replace("\n", "");
			text_file_doc = {"days":int(currentline[0]),"temperature":float(currentline[1]), "humidity":float(currentline[2]), "losses":float(currentline[3]), "deflection":int(currentline[4]), "Datatype":str(currentline[5])}
			collection.insert(text_file_doc)

#	print "Endof createfifodata"

def CreateDiseaseData(collection):
	with open("DiseaseDatabase_final.csv", "rt") as f:  # open a file
		for line in f:
			currentline = line.split("\t")
			#print currentline
			if '\n' in currentline[3]:
				currentline[3] = currentline[3].replace("\n", "");
			text_file_doc = {"Temperature":int(currentline[0]),"Humidity":int(currentline[1]), "Datatype":str(currentline[3]), "Diseaseno":int(currentline[2])}
			#print text_file_doc
			collection.insert(text_file_doc)
			
'''
from pymongo import MongoClient
client=MongoClient()
db=client.server_db
col=db['corpusfifo']
CreateFifoData(col)
'''
