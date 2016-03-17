package com.example.shreyas.newdemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shreyas on 3/16/16.
 */
public class UpdateAdapter extends RecyclerView.Adapter<UpdateAdapter.PersonViewHolder>{

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        Log.d("onattach", "c");
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.updatecard, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        Log.d("oncrete","c");
        return pvh;
    }

    List<Update_info> gen_info;

    public void setupdates(Context context)
    {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        final long load_time_start=System.currentTimeMillis();
        progressDialog.isIndeterminate();
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setMessage(context.getString(R.string.farm_loading_progressdialog_message));
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
                    //showToast(context.getString(R.string.problem_in_loading_message));

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
            Toast.makeText(context,"No Internet Connection",Toast.LENGTH_LONG).show();
        }
    }



    @Override
    public void onBindViewHolder(final PersonViewHolder personViewHolder, int i) {
        personViewHolder.fieldname.setText(persons.get(i).field_name);
        personViewHolder.date.setText(persons.get(i).date);
        personViewHolder.notification.setText(persons.get(i).Notification);
        Log.d("onbind", "c");

        if(persons.get(i).flag.equals("activity"))
        {
            personViewHolder.byes.setVisibility(View.GONE);
            personViewHolder.bno.setVisibility(View.GONE);
        }
        else
        {
            personViewHolder.byes.setVisibility(View.VISIBLE);
            personViewHolder.bno.setVisibility(View.VISIBLE);



            final String notification = persons.get(i).Notification;
            final String name = persons.get(i).field_name;
            final int i1 = i;
            personViewHolder.byes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                        DiseaseDialog d = new DiseaseDialog(view.getContext(), name, i1, notification);
                        d.setCancelable(false);
                        d.show();


                }
            });


            personViewHolder.bno.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    final ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                    final long load_time_start=System.currentTimeMillis();
                    progressDialog.isIndeterminate();
                    progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    progressDialog.setCancelable(false);
                    progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    progressDialog.setMessage(view.getContext().getString(R.string.farm_loading_progressdialog_message));
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
                                //showToast(getString(R.string.problem_in_loading_message));

                            }
                        }
                    });

                    temp_timer_thread.start();


                    Log.d("Connected to network", MainActivity.ConnectedToNetwork + "");
                    if(MainActivity.ConnectedToNetwork==true)
                    {

                        JSONObject j;
                        j = new JSONObject();
                        try {
                            j.put("Email",MainActivity.Global_Email_Id);
                            j.put("Farm_name",name);
                            j.put("question",notification);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String url = MainActivity.ServerIP + "/noupdate/";


                        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, j, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // the response is already constructed as a JSONObject!
                                Log.d("Onresponse", "yes");
                                try {
                                    response = response.getJSONObject("Android");
                                    //String fname = response.getString("AddFarmName");


                                    try {
                                        String a = response.getString("Result");
                                        if(a.equals("Valid"))
                                        {
                                            setupdates(view.getContext());
                                        }
                                    }
                                    catch (Exception e)
                                    {

                                    }




                                    notifyDataSetChanged();
                                    progressDialog.dismiss();


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
                        Toast.makeText(view.getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
                    }

                }
            });




        }

    }

    @Override
    public int getItemCount() {
        Log.d("getitemc","c");
        return persons.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView fieldname;
        TextView date;
        TextView notification;
        Button byes,bno;
        static int set = 0;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            fieldname = (TextView)itemView.findViewById(R.id.field_name);
            date = (TextView)itemView.findViewById(R.id.date);
            notification = (TextView)itemView.findViewById(R.id.noti);
            byes = (Button)itemView.findViewById(R.id.yes);
            bno = (Button)itemView.findViewById(R.id.no);


        }
    }
    List<Update_info> persons;
    UpdateAdapter(List<Update_info> persons){
        this.persons = persons;
        Log.d("RVAD","c");
        this.gen_info = new ArrayList<Update_info>();

    }

}

