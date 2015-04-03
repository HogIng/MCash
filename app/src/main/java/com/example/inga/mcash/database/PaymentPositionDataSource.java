package com.example.inga.mcash.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.inga.mcash.MySQLiteHelper;
import com.example.inga.mcash.PaymentPosition;

import java.util.ArrayList;

/**
 * Created by Inga on 20.03.2015.
 */
public class PaymentPositionDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_PAYMENTPOSITION_ID,
            MySQLiteHelper.COLUMN_PAYMENTPOSITION_COMMOID, MySQLiteHelper.COLUMN_PAYMENTPOSITION_NUMBER};

    public PaymentPositionDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void createPosition(long paymentId, long commodityId, int number) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_PAYMENTPOSITION_ID, paymentId);
        values.put(MySQLiteHelper.COLUMN_PAYMENTPOSITION_COMMOID, commodityId);
        values.put(MySQLiteHelper.COLUMN_PAYMENTPOSITION_NUMBER, number);
        database.insert(MySQLiteHelper.TABLE_PAYMENTPOSITION, null,
                values);
    }

    public void deletePaymentPosition(long paymentId, long commodityId) {

        System.out.println("PaymentPosition deleted with id: " + paymentId + "," + commodityId);

        String whereClause = MySQLiteHelper.COLUMN_PAYMENTPOSITION_ID+ " = ? and "+MySQLiteHelper.COLUMN_PAYMENTPOSITION_COMMOID +" = ?";
        String[] values = new String[]{String.valueOf(paymentId),String.valueOf(commodityId)};
        database.delete(MySQLiteHelper.TABLE_PAYMENTPOSITION
                , whereClause, values);
    }


    private PaymentPosition cursorToPaymentPosition(Cursor cursor) {
        PaymentPosition paymentPosition = new PaymentPosition();
        paymentPosition.setPaymentId(cursor.getInt(0));
        paymentPosition.setCommodityId(cursor.getInt(1));
        paymentPosition.setNumber(cursor.getInt(2));
        return paymentPosition;
    }

    public ArrayList<PaymentPosition> getPaymentPositionsByPaymentId(int id) {
        String whereClause = MySQLiteHelper.COLUMN_PAYMENTPOSITION_ID+" = ?";
        String[] values = new String[]{String.valueOf(id)};
        ArrayList<PaymentPosition> positions = new ArrayList<>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_PAYMENTPOSITION,
                allColumns, whereClause, values, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            PaymentPosition paymentPosition = cursorToPaymentPosition(cursor);
            positions.add(paymentPosition);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return positions;
    }

}
