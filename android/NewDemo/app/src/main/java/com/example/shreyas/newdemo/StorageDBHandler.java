package com.example.shreyas.newdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niranjan on 8/3/16.
 */
public class StorageDBHandler extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "StorageManager";

    //table names
    private static final String TABLE_WAREHOUSES= "warehouses";
    private static final String TABLE_STOCKS= "stocks";

    // warehouses Table Columns names
    private static final String WAREHOUSE_NAME = "wh_name";

    //stocks table column names
    private static final String STOCK_NAME="stock_name";
    private static final String STOCK_WAREHOUSE="stock_warehouse";
    private static final String STOCK_CROP_NAME="stock_crop_name";
    private static final String STOCK_AMOUNT="stock_amount";
    private static final String STOCK_FARMER_NAME="stock_farmer_name";
    private static final String STOCK_CROP_TYPE="stock_crop_type";
    private static final String STOCK_SOW_START="stock_sow_start";
    private static final String STOCK_SOW_END="stock_sow_end";
    private static final String STOCK_HARVEST_START="stock_harvest_start";
    private static final String STOCK_HARVEST_END="stock_harvest_end";
    private static final String STOCK_IN_TIME="stock_in_time";



    public StorageDBHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_WAREHOUSES_TABLE = "CREATE TABLE " + TABLE_WAREHOUSES + "("
                + WAREHOUSE_NAME+ " TEXT " + ")";

        String CREATE_STOCKS_TABLE = "CREATE TABLE " + TABLE_STOCKS+ "("
                + STOCK_NAME + " TEXT,"
                + STOCK_WAREHOUSE + " TEXT,"
                + STOCK_CROP_NAME+ " TEXT,"
                + STOCK_CROP_TYPE+ " TEXT,"
                + STOCK_SOW_START+ " TEXT,"
                + STOCK_SOW_END+ " TEXT,"
                + STOCK_HARVEST_START+ " TEXT,"
                + STOCK_HARVEST_END+ " TEXT,"
                + STOCK_AMOUNT + " TEXT,"
                + STOCK_IN_TIME+ " TEXT,"
                + STOCK_FARMER_NAME+ " TEXT" + ")";

        db.execSQL(CREATE_WAREHOUSES_TABLE);

        db.execSQL(CREATE_STOCKS_TABLE);

        Log.d("Databases ", "created successfully");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WAREHOUSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STOCKS);

        // Create tables again
        onCreate(db);


    }


    void addWarehouse(String whname)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(WAREHOUSE_NAME, whname); // warehouse name


        // Inserting Row
        db.insert(TABLE_WAREHOUSES, null, value);

        db.close(); // Closing database connection

    }



    void addStock(StockInfo stockInfo)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value= new ContentValues();
        value.put(STOCK_NAME,stockInfo.getStockName());
        value.put(STOCK_AMOUNT,stockInfo.getStockAmount());
        value.put(STOCK_CROP_NAME,stockInfo.getStockCropName());
        value.put(STOCK_CROP_TYPE,stockInfo.getStockCropType());
        value.put(STOCK_SOW_START,stockInfo.getStockSowStart());
        value.put(STOCK_SOW_END,stockInfo.getStockSowEnd());
        value.put(STOCK_HARVEST_START,stockInfo.getStockHarvestStart());
        value.put(STOCK_HARVEST_END,stockInfo.getStockHarvestEnd());
        value.put(STOCK_FARMER_NAME,stockInfo.getStockFarmerName());
        value.put(STOCK_IN_TIME,stockInfo.getStockInTime());
        value.put(STOCK_WAREHOUSE,stockInfo.getStockWareHouseName());

        db.insert(TABLE_STOCKS, null, value);

        db.close(); // Closing database connection


    }


    void addIncompleteStock(String stockname,String stockwarehouse,String stockamount,String stockcropname)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value= new ContentValues();
        value.put(STOCK_NAME,stockname);
        value.put(STOCK_AMOUNT,stockamount);
        value.put(STOCK_CROP_NAME,stockcropname);
        value.put(STOCK_CROP_TYPE,"-");
        value.put(STOCK_SOW_START,"-");
        value.put(STOCK_SOW_END,"-");
        value.put(STOCK_HARVEST_START,"-");
        value.put(STOCK_HARVEST_END,"-");
        value.put(STOCK_FARMER_NAME,"-");
        value.put(STOCK_IN_TIME,"-");
        value.put(STOCK_WAREHOUSE,stockwarehouse);

        db.insert(TABLE_STOCKS, null, value);

        db.close(); // Closing database connection


    }


    public List<StockInfo> getstocks(String warehousename)
    {

        List<StockInfo> stocklist= new ArrayList<StockInfo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_STOCKS + " WHERE "+ STOCK_WAREHOUSE + " = "+ '"'+warehousename +'"' ;
//        String selectQuery = "SELECT  * FROM " + TABLE_STOCKS ;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do
            {
                stocklist.add(new StockInfo(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10)));
            } while (cursor.moveToNext());
        }


        Log.d("size of stocklist is", stocklist.size() + "");

        return stocklist;
    }



    public List<String> getwarehouses()
    {
        List<String> warehouseList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_WAREHOUSES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                warehouseList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        return warehouseList;
    }



    public void updateStockAmount(String whname,String stockname,String amount)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        String strSQL = "UPDATE " + TABLE_STOCKS +" SET " + STOCK_AMOUNT +" = " + amount +" WHERE "+ STOCK_WAREHOUSE +" = " +'"'+ whname  +'"'+ " AND " + STOCK_NAME +" = " +'"'+ stockname  +'"';

        db.execSQL(strSQL);

    }


    public void updateStock(StockInfo stockInfo)
    {

    }




    public int updateWarehouse(String oldname,String newname)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(WAREHOUSE_NAME, newname);

        // updating row
        return db.update(TABLE_WAREHOUSES, values, WAREHOUSE_NAME + " = ?",
                new String[] {oldname });
    }





    public void deletestock(String whname,String stockname)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_STOCKS,STOCK_WAREHOUSE + " = ? AND " + STOCK_NAME + " = ? ", new String[]{whname,stockname});
        db.close();
    }







    public void deleteWareHouse(String whname)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_WAREHOUSES, WAREHOUSE_NAME + " = ?",new String[]{whname});
        db.close();
    }


    // Getting contacts Count
    public int getWarehousesCount()
    {
        String countQuery = "SELECT  * FROM " + TABLE_WAREHOUSES
                ;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);
        Log.d("The count is ", cursor.getCount() + "");
        return cursor.getCount();

    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from "+ TABLE_WAREHOUSES);
        db.execSQL("delete from "+ TABLE_STOCKS);

    }







}
