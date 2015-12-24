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
    return request.form
