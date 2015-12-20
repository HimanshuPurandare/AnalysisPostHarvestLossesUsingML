package com.example.shreyas.newdemo;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by shreyas on 12/17/15.
 */
public class MyWareHouseAdapter extends RecyclerView.Adapter<MyWareHouseAdapter.PersonViewHolder>{

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mywarehouse_card, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        Log.d("oncrete", "c");

        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.WareHousename.setText(warehouses.get(i).warehousename);

        Log.d("onbind", "c");
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        Log.d("onattach", "c");
    }
    @Override
    public int getItemCount() {
        Log.d("getitemc","c");
        return warehouses.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView WareHousename;

        static int set = 0;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.mywarehousecv);
            WareHousename = (TextView)itemView.findViewById(R.id.name_of_warehouse);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //write here the code for wathever you want to do with a card
//                    //...
//                    Log.d("clicked", "yes");
//                    if (set == 0) {
//                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, 300, 1f);
//                        Farmname.setLayoutParams(lp);
//                        set = 1;
//                    } else {
//                        set = 0;
//                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, 200, 1f);
//                        Farmname.setLayoutParams(lp);
//                    }
//                }
//            });
        }
    }
    List<WareHouse_Info> warehouses;
    MyWareHouseAdapter(List<WareHouse_Info> persons){
        this.warehouses = persons;
    }

}
