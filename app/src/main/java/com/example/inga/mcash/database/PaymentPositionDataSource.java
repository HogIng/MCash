package com.example.inga.mcash.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.inga.mcash.Payment;
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
            MySQLiteHelper.COLUMN_PAYMENTPOSITION_COMMOID, MySQLiteHelper.COLUMN_PAYMENTPOSITION_NUMBER, MySQLiteHelper.COLUMN_PAYMENTPOSITION_PRICE,MySQLiteHelper.COLUMN_PAYMENTPOSITION_PERCENTAGE};

    public PaymentPositionDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void createPosition(PaymentPosition pP) {
        System.out.println("PaymentPosition created with id: " + pP.getPaymentId() + "," + pP.getCommodityId());
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_PAYMENTPOSITION_ID,pP.getPaymentId());
        values.put(MySQLiteHelper.COLUMN_PAYMENTPOSITION_COMMOID, pP.getCommodityId());
        values.put(MySQLiteHelper.COLUMN_PAYMENTPOSITION_NUMBER, pP.getNumber());
        values.put(MySQLiteHelper.COLUMN_PAYMENTPOSITION_PRICE, pP.getPrice());
        values.put(MySQLiteHelper.COLUMN_PAYMENTPOSITION_PERCENTAGE, pP.getPercentage());

        database.insert(MySQLiteHelper.TABLE_PAYMENTPOSITION, null,
                values);
    }

    public void deletePaymentPosition(long paymentId, long commodityId) {
        System.out.println("PaymentPosition deleted with id: " + paymentId + "," + commodityId);
        String whereClause = MySQLiteHelper.COLUMN_PAYMENTPOSITION_ID+ " = ? and "+MySQLiteHelper.COLUMN_PAYMENTPOSITION_COMMOID +" = ?";
        String[] whereArgs = new String[]{String.valueOf(paymentId),String.valueOf(commodityId)};
        database.delete(MySQLiteHelper.TABLE_PAYMENTPOSITION
                , whereClause, whereArgs);
    }

    public void deletePaymentPositionsOfPayment(long paymentId) {
        System.out.println("PaymentPositions deleted with id: " + paymentId );
        String whereClause = MySQLiteHelper.COLUMN_PAYMENTPOSITION_ID+ " = ? ";
        String[] whereArgs = new String[]{String.valueOf(paymentId)};
        database.delete(MySQLiteHelper.TABLE_PAYMENTPOSITION
                , whereClause, whereArgs);
    }



    private PaymentPosition cursorToPaymentPosition(Cursor cursor) {
        PaymentPosition paymentPosition = new PaymentPosition();
        paymentPosition.setPaymentId(cursor.getInt(0));
        paymentPosition.setCommodityId(cursor.getInt(1));
        paymentPosition.setNumber(cursor.getInt(2));
        paymentPosition.setPrice(cursor.getInt(3));
        paymentPosition.setPercentage(cursor.getInt(4));
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
