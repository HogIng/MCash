package com.example.inga.mcash;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Inga on 12.03.2015.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_COMMODITY = "commodity";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "date";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_IMG = "img";

    private static final String DATABASE_NAME = "commodity.db";
    private static final int DATABASE_VERSION = 1;

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_COMMODITY + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_NAME
            + " text not null, " + COLUMN_PRICE
            + " integer not null, " + COLUMN_IMG
            + " text);";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version \" + oldVersion + \" to \"\n" +
                        "                        + newVersion + \", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMODITY
        );
        onCreate(db);
    }
}
