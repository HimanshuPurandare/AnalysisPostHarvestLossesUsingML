package com.example.shreyas.newdemo;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shreyas on 3/16/16.
 */
public class DiseaseDialog extends Dialog
{

    Button b1,b2;
    EditText t1,t2;
    TextView tv1,tv2;
    String name;
    String noti;
    int pos;
    List<Update_info> gen_info;
    Activity activity;
    public DiseaseDialog(Context context,String name,int i,String a)
    {
        super(context);
        this.name = name;
        this.pos = i;
        this.noti = a;
        Log.d("Noti",noti);
        gen_info=new ArrayList<Update_info>();
        activity = getOwnerActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.disease_dialog);
        b1 = (Button)findViewById(R.id.cancle);
        b2 = (Button) findViewById(R.id.done);

        t1 = (EditText) findViewById(R.id.disease_name);
        t2 = (EditText) findViewById(R.id.disease_action);

        tv1 = (TextView) findViewById(R.id.disease);
        tv2 = (TextView) findViewById(R.id.action_taken);

        if(noti.contains("disease"))
        {
            tv1.setText(R.string.disease_as_string);
            tv2.setText(R.string.action_as_string);
        }
        else if(noti.contains("harvest"))
        {
            tv1.setVisibility(View.GONE);
            t1.setVisibility(View.GONE);
            tv2.setText(R.string.question_harvest_time);
        }
        else if(noti.contains("fertilizer"))
        {
            tv1.setVisibility(View.GONE);
            t1.setVisibility(View.GONE);
            tv2.setText(R.string.question_fertilizer);
        }
        else if(noti.contains("prediction-suggestion"))
        {
            tv1.setVisibility(View.GONE);
            t1.setVisibility(View.GONE);
            tv2.setText(R.string.question_suggestion);
        }
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = new ProgressDialog(getContext());
                final long load_time_start = System.currentTimeMillis();
                progressDialog.isIndeterminate();
                progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                progressDialog.setCancelable(false);
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                progressDialog.setMessage(getContext().getString(R.string.farm_loading_progressdialog_message));
                progressDialog.show();
                Thread temp_timer_thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (progressDialog.isShowing() && System.currentTimeMillis() - load_time_start < 10000) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if (progressDialog.isShowing() && System.currentTimeMillis() - load_time_start > 10000) {
                            progressDialog.dismiss();
                            showToast(getContext().getString(R.string.problem_in_loading_message));
                        }
                    }
                });

                temp_timer_thread.start();
                Log.d("Connected to network", MainActivity.ConnectedToNetwork + "");
                if (MainActivity.ConnectedToNetwork == true) {

                    JSONObject j;
                    j = new JSONObject();
                    try {
                        j.put("Email", MainActivity.Global_Email_Id);
                        j.put("Farm_name",name);
                        j.put("Action",t2.getText());
                        j.put("Disease",t1.getText());
                        j.put("question",noti);
//                        j.put("date",SuperAwesomeCardFragment.gen_info.get(pos).date);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String url = MainActivity.ServerIP + "/addactivity/";


                    JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, j, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // the response is already constructed as a JSONObject!
                            Log.d("Onresponse", "yes");
                            try {
                                response = response.getJSONObject("Android");
                                //String fname = response.getString("AddFarmName");
                                if(response.getString("Result").equals("Valid") && progressDialog.isShowing())
                                {

                                    progressDialog.dismiss();
                                    dismiss();
                                    setupdates();
                                }



                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });


                    MainActivity.getInstance().addToRequestQueue(jsonRequest);
                } else {
                    Toast.makeText(getContext(), R.string.toast_no_internet_connection_as_string, Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    public void setupdates()
    {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        final long load_time_start=System.currentTimeMillis();
        progressDialog.isIndeterminate();
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setMessage(getContext().getString(R.string.farm_loading_progressdialog_message));
        progressDialog.show();
        Thread temp_timer_thread=new Thread(new Runnable() {
            @Override
            public void run()
            {
                while(progressDialog.isShowing() && System.currentTimeMillis()-load_time_start<10000)
                {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (progressDialog.isShowing() && System.currentTimeMillis()-load_time_start>10000)
                {
                    progressDialog.dismiss();
                    showToast(getContext().getString(R.string.problem_in_loading_message));

                }
            }
        });

        temp_timer_thread.start();

        Log.d("geninfo before:", gen_info.toString());

        gen_info.clear();
        Log.d("Connected to network", MainActivity.ConnectedToNetwork + "");
        if(MainActivity.ConnectedToNetwork==true)
        {

            JSONObject j;
            j = new JSONObject();
            try {
                j.put("Email",MainActivity.Global_Email_Id);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            String url = MainActivity.ServerIP + "/getupdates/";


            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, j, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    // the response is already constructed as a JSONObject!
                    Log.d("Onresponse", "yes");
                    try {
                        response = response.getJSONObject("Android");
                        //String fname = response.getString("AddFarmName");

                        JSONArray a = new JSONArray();
                        int l;
                        try {
                            a = response.getJSONArray("farmlist");
                            Log.d("response array ", a.toString());
                            l = a.length();
                            Log.d("response array ", l + "");
                        }
                        catch (Exception e)
                        {
                            l = 0;
                        }
                        for(int i=0;i<l;i++)
                        {
                            JSONObject temp = a.getJSONObject(i);
                            Log.d("update_tag_line", temp.toString());
                            gen_info.add(new Update_info(temp.getString("farmname"), temp.getString("date"), temp.getString("question"), temp.getString("flag")));
                            //farmlist.add(new Farm_info(temp.getString("FarmName"),"18"+"/"+"29","39"+"/"+"52","0"+"/"+"495",temp.getString("URL")));
                        }


                        Log.d("geninfo after:", gen_info.toString());

                        SuperAwesomeCardFragment.update_genInfo(gen_info);
                        progressDialog.dismiss();
                        //dismiss();




                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });


            MainActivity.getInstance().addToRequestQueue(jsonRequest);
        }
        else
        {
            Toast.makeText(getContext(),R.string.toast_no_internet_connection_as_string,Toast.LENGTH_LONG).show();
        }
    }

    public void showToast(final String toast)
    {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getContext(), toast, Toast.LENGTH_LONG).show();
            }
        });
    }
}
