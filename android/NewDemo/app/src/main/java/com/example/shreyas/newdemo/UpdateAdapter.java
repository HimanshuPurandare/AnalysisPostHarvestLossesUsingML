package com.example.shreyas.newdemo;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

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
                public void onClick(View view) {

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
    }

}

