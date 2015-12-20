from flask import Flask, jsonify
from send_receive import *
from flask import request
import requests
from db_controller import *

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

@app.route('/getfarmstatus/',methods=['POST', 'GET'])
def getfarmstatus():
    #r = requests.get("http://192.168.0.116:5000/")
    #r = requests.get("http://192.168.1.133:5000/")
    #print r.text
    data = {"Temperature":"320","Humidity":"4","SoilMoisture":"10"}
    return jsonify(Android = data)

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

@app.route('/')
def hello_world():
    print hello
    return 'Hello World!'


if __name__ == '__main__':
    create_collections()
#    app.run(host="192.168.0.105")
#    app.run(host="192.168.1.131")
    app.run(host="10.42.0.249")
