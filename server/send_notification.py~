from gcm import GCM
from db_controller import *


def sn():
    hw_id="yaugsbsvw"
    farm_info=return_farm_info_for_hw(hw_id)
    user_info=return_user_info(farm_info['AddFarmUID'])
    token=user_info['registrationtoken']
    sendnotification(farm_info['AddFarmUID'],farm_info['AddFarmName'],token,"Soil Moisture Too High")
    
#    sendnotification(user,farmname,token,message    



def sendnotification(user,farmname,token,message):
    """
    API key--> Niranjan
    """
    #project server key for gcm

    gcm = GCM('AIzaSyCyoIu2g7f0hyEiKs6P_14k57RuCt-1upo')
    data =   { "message":message}
    response = gcm.json_request(registration_ids=[token], data=data) 
#    print type(response['success'])
    a = response['success'].keys()[0]
#    print a    
    if (token in a):
        AddNotification(user,farmname,token,message)        
        return True
    else:
        return False
