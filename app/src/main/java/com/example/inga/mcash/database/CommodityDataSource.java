package com.example.inga.mcash.database;

/**
 * Created by Inga on 12.03.2015.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.inga.mcash.Commodity;

import java.util.ArrayList;
import java.util.List;

public class CommodityDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_COMMO_ID,
            MySQLiteHelper.COLUMN_COMMO_NAME, MySQLiteHelper.COLUMN_COMMO_PRICE, MySQLiteHelper.COLUMN_COMMO_IMG, MySQLiteHelper.COLUMN_COMMO_GROUP_ID, MySQLiteHelper.COLUMN_COMMO_IS_GROUP};

    public CommodityDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Commodity createCommodity(String name, String img, int price, int groupId,int isGroup) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_COMMO_NAME, name);
        values.put(MySQLiteHelper.COLUMN_COMMO_PRICE, price);
        values.put(MySQLiteHelper.COLUMN_COMMO_IMG, img);
        values.put(MySQLiteHelper.COLUMN_COMMO_GROUP_ID, groupId);
        values.put(MySQLiteHelper.COLUMN_COMMO_IS_GROUP, isGroup);
        long insertId = database.insert(MySQLiteHelper.TABLE_COMMODITY, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMODITY,
                allColumns, MySQLiteHelper.COLUMN_COMMO_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Commodity newCommodity = cursorToCommodity(cursor);
        cursor.close();
        return newCommodity;
    }

    public void deleteCommodity(long id) {

        System.out.println("Commodity deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_COMMODITY, MySQLiteHelper.COLUMN_COMMO_ID
                + " = " + id, null);
    }

    public List<Commodity> getAllCommodities() {
        List<Commodity> commodities = new ArrayList<Commodity>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMODITY,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Commodity commodity = cursorToCommodity(cursor);
            commodities.add(commodity);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return commodities;
    }

    private Commodity cursorToCommodity(Cursor cursor) {
        Commodity commodity = new Commodity();
        commodity.setId(cursor.getInt(0));
        commodity.setName(cursor.getString(1));
        commodity.setPrice(cursor.getInt(2));
        commodity.setImage(cursor.getString(3));
        commodity.setGroupId(cursor.getInt(4));
        commodity.setIsGroup(cursor.getInt(5));
        return commodity;
    }

    public Commodity getCommodityById(int id) {
        String whereClause = MySQLiteHelper.COLUMN_COMMO_ID + " = ?";
        String[] values = new String[]{String.valueOf(id)};

        Commodity commodity = null;
        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMODITY,
                allColumns, whereClause, values, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            commodity = cursorToCommodity(cursor);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return commodity;
    }

    public ArrayList<Commodity> getCommoditiesOfGroup(int groupId){
        ArrayList<Commodity> commoditiesOfGroup = new ArrayList<>();
        String whereClause = MySQLiteHelper.COLUMN_COMMO_GROUP_ID + " = ?";
        String[] values = new String[]{String.valueOf(groupId)};

        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMODITY,
                allColumns, whereClause, values, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Commodity commodity = cursorToCommodity(cursor);
            commoditiesOfGroup.add(commodity);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        return commoditiesOfGroup;
    }

    public ArrayList<Commodity> getSearchedCommodities(String searchStr){
        ArrayList<Commodity> commoditiesSearched= new ArrayList<>();
        String whereClause = MySQLiteHelper.COLUMN_COMMO_NAME + " = ? " ;
        String[] values = new String[]{searchStr};

        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMODITY,
                allColumns, whereClause, values, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Commodity commodity = cursorToCommodity(cursor);
            commoditiesSearched.add(commodity);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        return commoditiesSearched;
    }

}
