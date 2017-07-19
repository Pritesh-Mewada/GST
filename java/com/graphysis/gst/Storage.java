package com.graphysis.gst;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



/**
 * Created by pritesh on 12/7/17.
 */

class Storage extends SQLiteOpenHelper {

    public static Cursor goodItem=null,serviceItem=null;
    public Storage(Context context) {
        super(context,"SavedGST" ,null, 8);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+Tables.goodStore+" ( Item TEXT,Price TEXT,Quantity INT,Id TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+Tables.serviceStore+" ( Item TEXT,Price TEXT,Quantity INT,Id TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+Tables.goodItems+ " ( Item TEXT , Rate TEXT , Description TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+Tables.serviceItem+ " ( Item TEXT , Rate TEXT , Description TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+Tables.goodItems);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+Tables.serviceItem);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+Tables.goodStore);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+Tables.serviceStore);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        onUpgrade(db,oldVersion,newVersion);
    }

    public void Insert(Calculator calculator,String table){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Item",String.valueOf(calculator.getItem()));
        values.put("Price",String.valueOf(calculator.getCostprice()));
        values.put("Quantity",Integer.parseInt(calculator.getQuantity()));
        values.put("Id", calculator.getId());
        db.insert(table,null,values);
    }

    //read store data
    public Cursor read(String table){
        Cursor cursor = getReadableDatabase().query(table,new String[]{"Item","Price","Quantity","Id"},null,null,null,null,null,null);
        cursor.moveToFirst();
        return cursor;
    }

    //read store item data retrieved from net
    public Cursor readItems(String table){
        if(table == Tables.goodItems){
            return Storage.goodItem;
        }else{
            return Storage.serviceItem;
        }
    }

//    public void initialiseStored(){
//        Cursor cursor = getReadableDatabase().query(Tables.goodStore,new String[]{"Item","Price","Quantity","Id"},null,null,null,null,null,null);
//        cursor.moveToFirst();
//        Storage.goodStore = cursor;
//        cursor = getReadableDatabase().query(Tables.serviceStore,new String[]{"Item","Price","Quantity","Id"},null,null,null,null,null,null);
//        cursor.moveToFirst();
//        Storage.ServiceStore = cursor;
//    }
    public void initialiseItems(String table){
        if(table ==Tables.goodItems){
            Cursor cursor = getReadableDatabase().query(table,new String[]{"Item","Rate","Description"},null,null,null,null,null,null);
            cursor.moveToFirst();
            Storage.goodItem = cursor;
        }else {
            Cursor cursor = getReadableDatabase().query(table,new String[]{"Item","Rate","Description"},null,null,null,null,null,null);
            cursor.moveToFirst();
            Storage.serviceItem = cursor;
        }
    }

    // update the data base
    public void reCreateItems(String table){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + table);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+table+" ( Item TEXT , Rate TEXT , Description TEXT)");
    }

    public void insertItem(String table, GstModel insert){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Item",String.valueOf(insert.getName()));
        values.put("Rate",Float.parseFloat(String.valueOf(insert.getRate())));
        values.put("Description", insert.getDescription());
        db.insert(table,null,values);

    }


    public void  delete(String id,String table){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(table,"Id=?",new String[]{id});
    }

}
