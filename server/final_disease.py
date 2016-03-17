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
		return "(Onion)Probable Disease: " + "Onion Smut\nSuggestion: 1. Shallow sowing depth, moderate temperatures and adequate soil moisture are key factors that favour rapid seedling emergence and hence reduce smut incidence by minimising exposure at the susceptible cotyledon and first leaf stages.\n2. rotation with non-Allium crops is advisable."
	if a[0] == 2:
		return "(Onion)Probable Disease is " + "Seedling Disorder\nSuggestions: Remove any weeds around young plants by hoeing shallowly so as not to damage the roots of the onions."
	if a[0] == 3:
		return "(Onion)Probable Disease is " + "Botrytis Leaf Blight\nSuggestions: 1. cull piles should be destroyed\n2. seed fields should be located well apart from commercial onion production fields\n3. volunteer onions should be rogued\n4. At harvest, severed onion tops should be removed from the field and destroyed."
	if a[0] == 4:
		return "(Onion)Probable Disease is " + "Downy mildew\nSuggestions: 1. Destroy volunteer Allium plants in and around the field and buildings\n2. fungicides may be applied on a 7-day schedule"
	if a[0] == 5:
		return "(Onion)Probable Disease is " + "Purple blotch\nSuggestions: 1. Destroy volunteer Allium plants in and around the field and buildings\n2. fungicides may be applied on a 7-day schedule"
	if a[0] == 6:
		return "(Onion)Probable Disease is " + "Fusarium Basal Rot\nSuggestions: 1. Control soil insects and foliage diseases\n2. Cure onions properly before storage\n3. Store at cool temperatures since infection is favored by warm conditions."
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
		return "(wheat)Probable Disease: " + "Yellow Rust\nSuggestions: Use proper fungicide."
	if a[0] == 2:
		return "(wheat)Probable Disease: " + "Foliar Blightss\nSuggestions: 1. combine optimum seeding date, seed treatment and foliar spray of fungicides \n2. Use disease resistant wheat genotypes"
	if a[0] == 3:
		return "(wheat)Probable Disease: " + "Powdery Mildew\nSuggestions: 1. do not over-fertilize with nitrogen\n2. Fungicides should not be applied until flag leaf emergence, unless a variety is susceptible"
	if a[0] == 4:
		return "(wheat)Probable Disease: " + "Black Point\nSuggestions: 1. reduce irrigation frequency after heading\n2. reduce nitrogen rates, without sacrificing either yield or quality."
	if a[0] == 5:
		return "(wheat)Probable Disease: " + "Loose Smut\nSuggestions: 1. Use fungicide. Fungicide used to treat the seed be systemic and not just external"
	if a[0] == 0:
		return "(wheat)No disease"
