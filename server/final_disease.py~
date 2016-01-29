from sklearn import tree
from pymongo import MongoClient

clf=tree.DecisionTreeClassifier()

client=MongoClient()
db=client.server_db

def onion_disease(current_temp, current_hum):
	cursor=db.corpusdisease.find({"Datatype":"onion"})

	x=[]
	y=[]
	for docs in cursor:
	     item=[]
	     item.append(docs["Temperature"])
	     item.append(docs["Humidity"])
	     x.append(item)
	     y.append(docs["Diseaseno"])
	 
	clf.fit(x,y)

	z = []
	w = []
	#z.append(int(raw_input("Enter temp: ")))
	#z.append(int(raw_input("Enter Humidity: ")))
	z=[int(current_temp), int(current_hum)]
	w.append(z)
	
	a = clf.predict(w)
#	print "The value of a is",a
	if a[0] == 1:
		return "(Onion)Predicted Disease is " + "Onion Smut"
	if a[0] == 2:
		return "(Onion)Predicted Disease is " + "Seedling Disorder"
	if a[0] == 3:
		return "(Onion)Predicted Disease is " + "Botrytis Leaf Blight"
	if a[0] == 4:
		return "(Onion)Predicted Disease is " + "Downy mildew"
	if a[0] == 5:
		return "(Onion)Predicted Disease is " + "Purple blotch"
	if a[0] == 6:
		return "(Onion)Predicted Disease is " + "Fusarium Basal Rot"
	if a[0] == 0:
		return "(Onion)No disease"

def wheat_disease(current_temp, current_hum):
	cursor=db.corpusdisease.find({"Datatype":"wheat"})

	x=[]
	y=[]
	for docs in cursor:
	     item=[]
	     item.append(docs["Temperature"])
	     item.append(docs["Humidity"])
	     x.append(item)
	     y.append(docs["Diseaseno"])
	 
	clf.fit(x,y)

	z = []
	w = []
	#z.append(int(raw_input("Enter temp: ")))
	#z.append(int(raw_input("Enter Humidity: ")))
	z=[int(current_temp), int(current_hum)]
	w.append(z)

	a = clf.predict(w)
#	print "The value of a is",a
	if a[0] == 1:
		return "(wheat)Predicted Disease is " + "Yellow Rust"
	if a[0] == 2:
		return "(wheat)Predicted Disease is " + "Foliar Blightss"
	if a[0] == 3:
		return "(wheat)Predicted Disease is " + "Powdery Mildew"
	if a[0] == 4:
		return "(wheat)Predicted Disease is " + "Black Point"
	if a[0] == 5:
		return "(wheat)Predicted Disease is " + "Loose Smut"
	if a[0] == 0:
		return "(wheat)No disease"
