

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
			    if '\n' in currentline[1]:
			    	currentline[1] = currentline[1].replace("\n", "");
			    text_file_doc = {"max_temp":int(currentline[0]),"min_temp":int(currentline[1])}
			    collection.insert(text_file_doc)


def CreateFifoData(collection):
	with open("FIFOdata.txt", "rt") as f:  # open a file
		for line in f:
			    currentline = line.split("\t")
			    if '\n' in currentline[1]:
			    	currentline[1] = currentline[1].replace("\n", "");
			    text_file_doc = {"days":int(currentline[0]),"temperature":float(currentline[1]),"humidity":float(currentline[2]),"losses":float(currentline[3]),"deflection":int(currentline[4])}
			    collection.insert(text_file_doc)


def CreateDiseaseData(collection):
	with open("diseasedatabase.csv", "rt") as f:  # open a file
		for line in f:
			    currentline = line.split("\t")
			    if '\n' in currentline[1]:
			    	currentline[1] = currentline[1].replace("\n", "");
			    text_file_doc = {"Temperature":int(currentline[0]),"Humidity":int(currentline[1]),'Diseaseno':int(currentline[2])}
			    collection.insert(text_file_doc)
        	



