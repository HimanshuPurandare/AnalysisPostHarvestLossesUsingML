from flask import Flask, jsonify
from send_receive import *
from flask import request
import requests
from db_controller import *
from send_notification import *

app = Flask(__name__)

@app.route('/signin/',methods=['POST', 'GET'])
def authenticate_user():
    #receive email and password
    received_data = receive_from_android(request)
    print "uid",received_data["SignInUid"]
    print "pwd",received_data["SignInPwd"] 
    
    response_to_android = {"Result":"Valid","Name":"Shreyas","Role":"Software Engineer"}
    #check in database
    rBool = IsValidUser(received_data["SignInUid"],received_data["SignInPwd"])        
    if len(rBool)==0 :
        print "Invalid"
        response_to_android = {"Result":"Invalid"}
    else:
        print "valid"
        response_to_android["Name"] = rBool[1]
        response_to_android["Role"] = rBool[0]
    return jsonify(Android = response_to_android)    
    #send appropriate results
    return 'Hello World!'

@app.route('/signup/',methods=['POST', 'GET'])
def add_user():
    #receive email and password
    received_data = receive_from_android(request)
    print "uid",received_data["SignUpUid"]
    print "pwd",received_data["SignUpPwd"]
    print "name",received_data["SignUpName"] 
    response_to_android = {"Result":"Valid"}
    #check in database
    add_user_to_db(received_data)
    
    print "valid"
    
    return jsonify(Android = response_to_android)    
    #send appropriate results
    return 'Hello World!'


@app.route('/getdailyweatherdata/',methods=['POST', 'GET'])
def getfarmstatus():
    print request
    print request.form
    print request.data
    response_from_server=receive_from_rpi(request)
    print response_from_server
    #data = {"Temperature":"320","Humidity":"4","SoilMoisture":"10"}
    return "ok"


@app.route('/getnotifications/',methods=['POST', 'GET'])
def getnotifications():
    received_data = receive_from_android(request)
    print received_data
    notification_list=get_notifications(received_data)
    for i in notification_list:
        print i['date']
    print "returned from functin"
    response_list=[]
    for i in notification_list:
        response_list.append({"Message":i['Message'],"date":i['date']})   
    print response_list


    
    return jsonify(Android = {"list":response_list} )   





@app.route('/registerapp/',methods=['POST', 'GET'])
def registerapp():
    received_data = receive_from_android(request)
    register_app(received_data)
    response_to_android = {"Result":"Valid"}
    return jsonify(Android = response_to_android)   


@app.route('/addfarm/',methods=['POST', 'GET'])
def addfarm():
    received_data = receive_from_android(request)
    print received_data
    add_farm(received_data)
    response_to_android = {"Result":"Valid"}
    print received_data
    return jsonify(Android = response_to_android)   


@app.route('/addwarehouse/',methods=['POST', 'GET'])
def addwarehouse():
    received_data = receive_from_android(request)
    print received_data
    add_warehouse(received_data)
    response_to_android = {"Result":"Valid"}
    print received_data
    return jsonify(Android = response_to_android)   


@app.route('/getfarms/',methods=['POST', 'GET'])
def getfarms():
    received_data = receive_from_android(request)
    print received_data
    farmlist = []
    response_to_android = {"AddFarmName":farmlist}
    returnval = return_farms(received_data) 
    print "hello"
    
    a = returnval[0]
    print a['AddFarmName']
    print "hi"
    
    for i in returnval:
        print "No"
        farmlist.append(i['AddFarmName'])
        print "yes"
    response_to_android['AddFarmName'] = farmlist
    print response_to_android
    
    
    
    return jsonify(Android = response_to_android)   

@app.route('/getfarminfo/',methods=['POST', 'GET'])
def fetchfarminfo():
	received_data = receive_from_android(request)
	print received_data
	returnval=return_farm_info(received_data)
	print returnval['AddFarmName']
	datalist={"Result":"Valid"}
	datalist['AddFarmName']=returnval['AddFarmName']
	datalist['AddFarmCropType']=returnval['AddFarmCropType']
	datalist['AddFarmHWID']=returnval['AddFarmHWID']
	datalist['AddFarmEndDate']=returnval['AddFarmEndDate']
	datalist['AddFarmStartDate']=returnval['AddFarmStartDate']
	datalist['AddFarmCrop']=returnval['AddFarmCrop']
	datalist['AddFarmArea']=returnval['AddFarmArea']
	
	return jsonify(Android = datalist)   


@app.route('/getwarehouses/',methods=['POST', 'GET'])
def getwarehouses():
    received_data = receive_from_android(request)
    print "warehouse"
    print received_data
    warehouselist = []
    response_to_android = {"AddWareHouseName":warehouselist}
    returnval = return_warehouses(received_data) 
    print "hello"
    print returnval[0]
    a = returnval[0]
    print a['AddWarehouseName']
    print "hi"
    
    for i in returnval:
        print "No"
        warehouselist.append(i['AddWarehouseName'])
        print "yes"
    response_to_android['AddWareHouseName'] = warehouselist
    print response_to_android
    return jsonify(Android = response_to_android)   

@app.route('/',methods=['POST', 'GET'])
def hello_world():
    in_data = request.form
    print in_data
    key = in_data.keys()
    print key
    #temperature.get_temp()
    length = len(key)
    print length
    print request.form
    for i in range(0,length):
        print key[i]
        print request.form[key[i]]
        
    return 'Hello World!'
    
    


if __name__ == '__main__':
	create_collections()
	app.run(host="192.168.0.105")
#	get_notifications({"UserID":"aa@aa","Farmname":"farm1"})
#	sendnotification('aa@aa','farm1','d6Sw4Ip5wkk:APA91bHwXk9vRWxgbaN5is8SLEzPBM8OSgATBOXATSggCW8w4envsEvaDHXitQo56PYFeOp6KNXrwhRoeqCqyefPr6RSHGr7fNaMVfAlk1H2igStZzoFPo7s-0wKWCrm6RKdIJ4gl6eE','Notification Sent*****!!!')
#	app.run(host="10.42.0.249")
    
#    app.run(host="192.168.1.131")
    

