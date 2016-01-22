package com.example.shreyas.newdemo;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niranjan on 20/1/16.
 */
public class WHAdapter extends RecyclerView.Adapter<WHAdapter.StockViewHolder>
{

    List<StockList> stocks;
    WHAdapter(List<StockList> stocks)
    {
//        this.stocks=new ArrayList<StockList>();
        this.stocks = stocks;
//        this.stocks.add(new StockList("abc","abc",14));
//        this.stocks.add(new StockList("abc","abc",14));
//        this.stocks.add(new StockList("abc","abc",14));

        Log.d("stocks",stocks.toString());
    }


    @Override
    public StockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.warehouse_card, parent, false);
        StockViewHolder svh = new StockViewHolder(v);
        Log.d("oncreateviewholder","called");
        return svh;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        Log.d("onattached called","---");
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(StockViewHolder holder, int pos)
    {
        holder.stockname.setText(stocks.get(pos).Stockname);
        holder.cropname.setText(stocks.get(pos).Cropname);
        holder.amount.setText(stocks.get(pos).amount+"");
        Log.d("Onbindvwhldr compl",stocks.get(pos).Cropname);

    }

    @Override
    public int getItemCount()
    {
        Log.d("getitemcount called",stocks.size()+"");
        return stocks.size();
    }


    public static class StockViewHolder extends RecyclerView.ViewHolder
    {
        CardView cv;
        TextView stockname,cropname,amount;

        StockViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.stocklistcv);
            stockname = (TextView)itemView.findViewById(R.id.warehouse_name);
            cropname = (TextView)itemView.findViewById(R.id.crop_name);
            amount = (TextView)itemView.findViewById(R.id.amount);

            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    StockInfoDialog stockInfoDialog=new StockInfoDialog((Activity)v.getContext());
                    stockInfoDialog.show();


                }
            });
            
            cv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    RelativeLayout relativeLayout = (RelativeLayout) v.getParent();
                    relativeLayout.setBackgroundColor(Color.LTGRAY);
                    return false;
                }
            });


        }

    }
}
