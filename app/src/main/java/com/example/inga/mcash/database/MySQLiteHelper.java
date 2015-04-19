package com.example.inga.mcash.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Inga on 12.03.2015.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mcash.db";
    private static final int DATABASE_VERSION = 1;

    //tablenames
    public static final String TABLE_PAYMENT = "payment";
    public static final String TABLE_PAYMENTPOSITION = "paymentpostion";
    public static final String TABLE_CASHIER = "cashier";
    public static final String TABLE_COMMODITY = "commodity";

    //commodity columnnames
    public static final String COLUMN_COMMO_ID = "id";
    public static final String COLUMN_COMMO_NAME = "name";
    public static final String COLUMN_COMMO_PRICE = "price";
    public static final String COLUMN_COMMO_IMG = "img";
    public static final String COLUMN_COMMO_GROUP_ID = "groupid";
    public static final String COLUMN_COMMO_IS_GROUP = "isgroup";

    //payment columnnames
    public static final String COLUMN_PAYMENT_ID = "id";
    public static final String COLUMN_PAYMENT_DATE="date";
    public static final String COLUMN_PAYMENT_AMOUNT = "amount";
    public static final String COLUMN_PAYMENT_CASHIER = "cashier";
    public static final String COLUMN_PAYMENT_STATUS = "status";

    //paymentposition columnnames
    public static final String COLUMN_PAYMENTPOSITION_ID = "id";
    public static final String COLUMN_PAYMENTPOSITION_COMMOID = "commoid";
    public static final String COLUMN_PAYMENTPOSITION_NUMBER = "number";
    public static final String COLUMN_PAYMENTPOSITION_PRICE = "price";
    public static final String COLUMN_PAYMENTPOSITION_PERCENTAGE = "percentage";

    //cashier columnnames
    public static final String COLUMN_CASHIER_ID = "id";
    public static final String COLUMN_CASHIER_FIRSTNAME = "firstname";
    public static final String COLUMN_CASHIER_LASTNAME = "lastname";
    public static final String COLUMN_CASHIER_PW = "pw";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Database creation sql statement
    private static final String TABLE_COMMO_CREATE = "create table "
            + TABLE_COMMODITY + "(" + COLUMN_COMMO_ID
            + " integer primary key autoincrement, " + COLUMN_COMMO_NAME
            + " text, " + COLUMN_COMMO_PRICE
            + " integer, " + COLUMN_COMMO_IMG
            + " text, " + COLUMN_COMMO_GROUP_ID
            + " integer," + COLUMN_COMMO_IS_GROUP
            + " integer);";

    private static final String TABLE_PAYMENT_CREATE = "create table "
            + TABLE_PAYMENT + "(" + COLUMN_PAYMENT_ID
            + " integer primary key autoincrement, " + COLUMN_PAYMENT_AMOUNT
            + " integer, " + COLUMN_PAYMENT_DATE
            + " text, " + COLUMN_PAYMENT_CASHIER
            + " integer, " + COLUMN_PAYMENT_STATUS
            + " integer);";

    private static final String TABLE_PAYMENTPOSITION_CREATE = "create table "
            + TABLE_PAYMENTPOSITION + "(" + COLUMN_PAYMENTPOSITION_ID
            + " integer, " + COLUMN_PAYMENTPOSITION_COMMOID
            + " integer, " + COLUMN_PAYMENTPOSITION_NUMBER
            + " integer, " + COLUMN_PAYMENTPOSITION_PRICE
            + " integer, " + COLUMN_PAYMENTPOSITION_PERCENTAGE
            + " integer);";

    private static final String TABLE_CASHIER_CREATE = "create table "
            + TABLE_CASHIER + "(" + COLUMN_CASHIER_ID
            + " integer primary key autoincrement, " + COLUMN_CASHIER_FIRSTNAME
            + " text, " + COLUMN_CASHIER_LASTNAME
            + " text, " + COLUMN_CASHIER_PW
            + " text);";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_COMMO_CREATE);
        db.execSQL(TABLE_PAYMENT_CREATE);
        db.execSQL(TABLE_PAYMENTPOSITION_CREATE);
        db.execSQL(TABLE_CASHIER_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version \" + oldVersion + \" to \"\n" +
                        "                        + newVersion + \", which will destroy all old data");
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAYMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAYMENTPOSITION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CASHIER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMODITY);

        // create new tables
        onCreate(db);
    }
}
