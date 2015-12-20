from pymongo import *
from datetime import datetime

"""
database name --> server_db
various
"""
client=MongoClient('localhost',27017)

db=client.server_db

def create_collections():
    collections_list = db.collection_names()
    if 'app_info' not in collections_list:
        app_info = db['app_info']
    if 'user_info' not in collections_list:
        user_info = db['user_info']
    if 'farm_data' not in collections_list:
        farm_data = db['farm_data']
    if 'warehouse_data' not in collections_list:
        farm_data = db['warehouse_data']
    if 'daily_data_farmers' not in collections_list:
        daily_data_farmers = db['daily_data_farmers']
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
    farm_data=db['farm_data']
    farm_data.insert_one(farmdata)


def add_warehouse(warehousedata):
    godown_data=db['warehouse_data']
    godown_data.insert_one(warehousedata)

    
def return_farms(received_data):
    farm_data = db['farm_data']
    print received_data['Email']
    list_of_farms = farm_data.find({"AddFarmUID":received_data['Email']})
    print "hty"
    print "list",list_of_farms
    return list_of_farms
    

def return_farm_info(received_data):
	farm_data=db['farm_data']
	print "collection created"
	farm_info=farm_data.find_one({"AddFarmUID":received_data['UserID'],"AddFarmName":received_data['FarmName']})
	print farm_info
	return farm_info    
    
def return_warehouses(received_data):
    warehouse_data = db['warehouse_data']
    print received_data['Email']
    list_of_warehouses = warehouse_data.find({"AddWarehouseUID":received_data['Email']})
    print "hty"
    print "list",list_of_warehouses
    return list_of_warehouses
    
    
def IsValidUser(email,password):
    handle = db['user_info']
    
    list_of_users = handle.find_one({"SignUpUid":email})
    print "list of users : " ,list_of_users
    info = []
    if(list_of_users) == None:
        return info
    else:
        if list_of_users['SignUpPwd']==password:
            info.append(list_of_users['SignUpRole'])
            info.append(list_of_users['SignUpName'])
        return info
