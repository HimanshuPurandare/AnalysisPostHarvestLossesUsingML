from pymongo import *
from datetime import datetime
from FilesToDatabasenew import *
"""
database name --> server_db
various
"""
#client=MongoClient('mongodb://shreyas:shreyas@ds047365.mongolab.com:47365/server_db')

#db=client.server_db

SEED_DATA = [
    {
        'decade': '1970s',
        'artist': 'Debby Boone',
        'song': 'You Light Up My Life',
        'weeksAtOne': 10
    },
    {
        'decade': '1980s',
        'artist': 'Olivia Newton-John',
        'song': 'Physical',
        'weeksAtOne': 10
    },
    {
        'decade': '1990s',
        'artist': 'Mariah Carey',
        'song': 'One Sweet Day',
        'weeksAtOne': 16
    }
]


try:
#    client=MongoClient('mongodb://shreyas:shreyas@ds047365.mongolab.com:47365/server_db')
	client=MongoClient('localhost',27017)
	db=client.server_db

except:
    print "not done"


def create_collections():
	collections_list = db.collection_names()
	    
	if 'app_info' not in collections_list:
		app_info = db['app_info']
	if 'user_info' not in collections_list:
		user_info = db['user_info']
	if 'notifications' not in collections_list:
		noti = db['notifications'] 
	if 'farm_data' not in collections_list:
		farm_data = db['farm_data']
	if 'warehouse_data' not in collections_list:
		farm_data = db['warehouse_data']
	if 'daily_data_farmers' not in collections_list:
		daily_data_farmers = db['daily_data_farmers']
	if 'weather_data' not in collections_list:
		weather=db['weather_data']
	if 'corpusharvest' not in collections_list:
		corp=db['corpusharvest']
		CreateHarvestData(corp)
	
	if 'corpusoptitemp' not in collections_list:
		corp=db['corpusoptitemp']
		CreateOptiTempData(corp)
	
	if 'corpusfifo' not in collections_list:
		corp=db['corpusfifo']
		CreateFifoData(corp)
	if 'corpusdisease' not in collections_list:
		corp=db['corpusdisease']
		CreateDiseaseData(corp)
	if 'corpus_farm' not in collections_list:
		corpus_farm = db['corpus_farm']
	if 'daily_data_godown' not in collections_list:
		daily_data_godown = db['daily_data_godown']        
	if 'stock_info' not in collections_list:
		stock_info = db['stock_info']
	if 'corpus_godown' not in collections_list:
		corpus_godown = db['corpus_godown']        


def register_app(tokeninfo):
    app_info = db['app_info']
    app_info.insert_one(tokeninfo)


def add_user_to_db(uname):
    user_info = db['user_info']
    user_info.insert_one(uname)    
    #TODO : Error handling
    
    
def add_farm(farmdata):
#	unique name of farm???
    farm_data=db['farm_data']
    farm_data.insert_one(farmdata)


def add_warehouse(warehousedata):
    godown_data=db['warehouse_data']
    godown_data.insert_one(warehousedata)

def return_daily_data_farmer(harwareID):
    daily_data_farmers=db['daily_data_farmers']
    weatherdata = {}
    weatherdata=daily_data_farmers.find({"HWID":harwareID})
    w=sorted(weatherdata,key=lambda x:x['date'])
#    print "w",w
#    print "last w",w[-1]
#    print weatherdata
    return w[-1]

def add_daily_farm_data(indata):
    daily_data_farmers=db['daily_data_farmers']
    daily_data_farmers.insert_one(indata)

def return_user_info(email):
    user_info = db['user_info']
    userinfo=user_info.find_one({"SignUpUid":email})
#    print userinfo
    return userinfo   
    
def return_farm_info_for_hw(hw_id):
    farm_data=db['farm_data']
    farm=farm_data.find_one({"AddFarmHWID":hw_id})
#    print "got the farm",farm
    return farm  

    
def return_farms(received_data):
    farm_data = db['farm_data']
#    print received_data['Email']
    list_of_farms = farm_data.find({"AddFarmUID":received_data['Email']})
#    print "hty"
#    print "list",list_of_farms
    return list_of_farms
    

def return_farm_info(received_data):
	farm_data=db['farm_data']
#	print "collection created"
	farm_info=farm_data.find_one({"AddFarmUID":received_data['UserID'],"AddFarmName":received_data['FarmName']})
#	print farm_info
	return farm_info    
    
def return_warehouses(received_data):
    warehouse_data = db['warehouse_data']
#    print received_data['Email']
    list_of_warehouses = warehouse_data.find({"AddWarehouseUID":received_data['Email']})
#    print "hty"
#    print "list",list_of_warehouses
    return list_of_warehouses

def return_warehouse_info(received_data):
	warehouse_data=db['warehouse_data']
	warehouse_info=warehouse_data.find_one({"AddWarehouseName":received_data['WareHouseName'],"AddWarehouseUID":received_data['Email']})
	return warehouse_info
    
    
def IsValidUser(email,password):
    handle = db['user_info']
    
    list_of_users = handle.find_one({"SignUpUid":email})
#    print "list of users : " ,list_of_users
    info = []
    if(list_of_users) == None:
        return info 
    else:
        if list_of_users['SignUpPwd']==password:
            info.append(list_of_users['SignUpRole'])
            info.append(list_of_users['SignUpName'])
        return info
        

def add_stock(data):
#	print data
#	Check of Uniqueness
	stock_info=db['stock_info']
	stock_info.insert_one(data)	
	return True        

def return_stocks(data):
	stock_info = db['stock_info']
	list_of_stocks = stock_info.find({"StockUID":data['Email'],"StockWareHouseName":data['WareHouseName']})
	print "prepared list",list_of_stocks
	return list_of_stocks


def return_stocks_of_crop(data):
	stock_info=db['stock_info']
	list_of_stocks=stock_info.find(data)
	print "sending list",list_of_stocks
	return list_of_stocks

def return_stock_info(data):
	stock_info=db['stock_info']
	info=stock_info.find({"StockUID":data['Email'],"StockWareHouseName":data['WareHouseName'],"StockName":data['StockName']})
	print "printing info",info[0]
	print type(info)
	
	if info.count()!=1:
		print "Error in fetching stock info",info
		return False
	else:
		print "inside else"
		print "printing info[0]",info[0]
		return info[0]
def finalize_dispatch(data):
	final_dispatch_array=data['Final_Dispatch_Array']
	stock_info=db['stock_info']
	print "start of disptach",final_dispatch_array
	return_val=""
	for i in final_dispatch_array:
		current_stock=stock_info.find({"StockUID":data['Email'],"StockWareHouseName":data['WareHouseName'],"StockName":i['StockName']})
		print "printing current stock",current_stock[0]
		if(current_stock.count()!=1):
			print "Error in stock fetching"
			return_val="Error in stock fetching"
		else:		
			print current_stock[0]
			current_stock=current_stock[0]
			if int(i['DispatchedAmount'])>int(current_stock['StockAmount']):
				print "Invalid Dispatch"
				return_val="Invalid Dispatch"
			elif int(i['DispatchedAmount'])==(current_stock['StockAmount']):
				print "Valid"
				return_val="Valid"
			else:
				print "Valid"
				return_val="Valid"
	if return_val=="Valid":
		for i in final_dispatch_array:
			print "processing for",i['StockName']
			current_stock=stock_info.find({"StockUID":data['Email'],"StockWareHouseName":data['WareHouseName'],"StockName":i['StockName']})[0]
			print "current_stock",current_stock
			if int(current_stock['StockAmount'])>int(i['DispatchedAmount']):
				print "using function",stock_info.update_one
				stock_info.update_one({"StockUID":data['Email'],"StockWareHouseName":data['WareHouseName'],"StockName":i['StockName']},{'$set':{"StockAmount":(str(int(current_stock['StockAmount'])-int(i['DispatchedAmount'])))}})
				print "updated",current_stock['StockName']
			else:
				print "Inside delete"
				stock_info.delete_one({"StockUID":data['Email'],"StockWareHouseName":data['WareHouseName'],"StockName":i['StockName']})
				print "deleted ",current_stock['StockName']
	print "returning"	
	return return_val
        
def AddNotification(userID,farmname,token,message):
    notifications=db['notifications']
    notifications.insert_one({"UserID":userID,"Farmname":farmname,"Token":token,"Message":message,"date":str(datetime.now())})
    
def get_notifications(received_data):
    notifications=db['notifications']
#    print "collection created"
#    print received_data['UserID']
#    print received_data['Farmname']

    notification_data=notifications.find({"UserID":received_data['UserID'],"Farmname":received_data['Farmname']})
#    print "niranjan"
    a=[]
    notification_data1=sorted(notification_data,key=lambda x:x['date'])
    for i in notification_data1:
        a.append(i)
    return a

def get_fifo_data_cursor():
	corpusfifo=db['corpusfifo']
	print "Printing cursor",corpusfifo.find()
	return corpusfifo.find()
	


	

