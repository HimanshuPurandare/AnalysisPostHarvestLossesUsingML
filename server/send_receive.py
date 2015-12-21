import json
import ast
from flask import request
import requests


def receive_from_android(request):
    print "niru"
    if request.method == 'GET' or request.method == 'POST':
        received_data = ast.literal_eval(request.data)
        print received_data
        return received_data
    else:
        print "sdfs"
                  
            
def receive_from_rpi(request):
    in_data = request.form
    key = in_data.keys()
    print key
    #temperature.get_temp()
    
    returnval = {}
    
    length = len(key)
    print length
    for i in range(0,length):
        print key[i]
        print request.form[key[i]]
        returnval[key[i]] = request.form[key[i]]

    return returnval
