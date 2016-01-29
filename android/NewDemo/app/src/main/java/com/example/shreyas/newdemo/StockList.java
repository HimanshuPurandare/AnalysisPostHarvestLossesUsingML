package com.example.shreyas.newdemo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niranjan on 20/1/16.
 */
public class StockList
{
    private int selected_amount;
    private String Stockname;
    private String Cropname;
    private int amount;
    private boolean isSelected;;


    StockList(String Stockname,String Cropname,int amount)
    {
        this.Stockname=Stockname;
        this.Cropname=Cropname;
        this.amount=amount;
        this.isSelected=false;
        this.selected_amount=0;
    }

    public int getSelected_amount() {
        return selected_amount;
    }

    public String getStockname() {
        return Stockname;
    }

    public String getCropname() {
        return Cropname;
    }

    public int getAmount() {
        return amount;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setSelected_amount(int selected_amount) {
        this.selected_amount = selected_amount;
    }

    public void setStockname(String stockname) {
        Stockname = stockname;
    }

    public void setCropname(String cropname) {
        Cropname = cropname;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
