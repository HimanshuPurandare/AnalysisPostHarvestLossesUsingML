package com.example.shreyas.newdemo;

/**
 * Created by niranjan on 22/1/16.
 */
public class StockInfo
{

    private String StockWareHouseName,StockName,StockCropName,StockCropType,StockSowStart,StockSowEnd,StockHarvestStart,StockHarvestEnd,StockFarmerName,StockAmount,StockInTime;
    StockInfo(String StockName,String StockWareHouseName,String StockCropName,String StockCropType,String StockSowStart,String StockSowEnd,String StockHarvestStart,String StockHarvestEnd,String StockAmount,String StockInTime,String StockFarmerName)
    {
        this.StockCropName=StockCropName;
        this.StockCropType=StockCropType;
        this.StockSowStart=StockSowStart;
        this.StockSowEnd=StockSowEnd;
        this.StockHarvestStart=StockHarvestStart;
        this.StockHarvestEnd=StockHarvestEnd;
        this.StockAmount=StockAmount;
        this.StockInTime=StockInTime;
        this.StockFarmerName=StockFarmerName;
        this.StockWareHouseName=StockWareHouseName;
        this.StockName=StockName;

    }


    public String getStockWareHouseName() {
        return StockWareHouseName;
    }

    public String getStockName() {
        return StockName;
    }

    public String getStockCropName() {
        return StockCropName;
    }

    public String getStockCropType() {
        return StockCropType;
    }

    public String getStockSowStart() {
        return StockSowStart;
    }

    public String getStockSowEnd() {
        return StockSowEnd;
    }

    public String getStockHarvestStart() {
        return StockHarvestStart;
    }

    public String getStockHarvestEnd() {
        return StockHarvestEnd;
    }

    public String getStockFarmerName() {
        return StockFarmerName;
    }

    public String getStockAmount() {
        return StockAmount;
    }

    public String getStockInTime() {
        return StockInTime;
    }


}
