package com.example.shreyas.newdemo;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by niranjan on 19/12/15.
 */
public class SharedPreferencesClass
{
    public void PullSharedPreferences(Context context)
    {
        android.content.SharedPreferences sharedPreferences=context.getSharedPreferences("SignInData", Context.MODE_PRIVATE);
        String signedinstatus=sharedPreferences.getString("SignedInStatus", "No");
        if(signedinstatus.equals("Yes"))
        {
            MainActivity.signedin=1;
        }
        else
        {
            MainActivity.signedin=0;
        }
        MainActivity.Global_Email_Id=sharedPreferences.getString("SignedInEmailId","(Email)");
        MainActivity.Global_User_Name=sharedPreferences.getString("SignedInName","(UserName)");
        MainActivity.GlobalUser_Role=sharedPreferences.getString("SignedInRole","UserType");

        SharedPreferences sharedPreferences1=context.getSharedPreferences("AppRegistration", Context.MODE_PRIVATE);

        MainActivity.Global_Regi_Token=sharedPreferences1.getString("RegistrationToken", "-");

    }

}
