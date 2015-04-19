package com.example.inga.mcash.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.inga.mcash.Cashier;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Inga on 20.03.2015.
 */
public class CashierDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_CASHIER_ID,
            MySQLiteHelper.COLUMN_CASHIER_FIRSTNAME,MySQLiteHelper.COLUMN_CASHIER_LASTNAME,MySQLiteHelper.COLUMN_CASHIER_PW };

    public CashierDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long createCashier(String firstName, String lastName, String pw) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_CASHIER_FIRSTNAME,firstName);
        values.put(MySQLiteHelper.COLUMN_CASHIER_LASTNAME,lastName);
        values.put(MySQLiteHelper.COLUMN_CASHIER_PW,pw);
        long insertId = database.insert(MySQLiteHelper.TABLE_CASHIER, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_CASHIER,
                allColumns, MySQLiteHelper.COLUMN_CASHIER_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        cursor.close();
        return insertId;
    }

    public void deleteCashier(long id) {
        System.out.println("Cashier deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_CASHIER, MySQLiteHelper.COLUMN_CASHIER_ID
                + " = " + id, null);
    }

    public List<Cashier> getAllCashiers() {
        List<Cashier> cashiers = new ArrayList<Cashier>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_CASHIER,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Cashier cashier = cursorToCashier(cursor);
            cashiers.add(cashier);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return cashiers;
    }

    private Cashier cursorToCashier(Cursor cursor) {
        Cashier cashier = new Cashier();
        cashier.setId(cursor.getInt(0));
        cashier.setFirstName(cursor.getString(1));
        cashier.setLastName(cursor.getString(2));
        cashier.setPw(cursor.getString(3));
        return cashier;
    }

    public Cashier getCashierById(long id){
        String[] idValue = new String[1];
        idValue[0] = String.valueOf(id);
        Cursor cursor = database.query  (MySQLiteHelper.TABLE_CASHIER,
                allColumns, "id = ?", idValue, null, null, null);
        Cashier cashier = null;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            cashier = cursorToCashier(cursor);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return cashier;
    }

}