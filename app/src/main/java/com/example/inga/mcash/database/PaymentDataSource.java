package com.example.inga.mcash.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.inga.mcash.MySQLiteHelper;
import com.example.inga.mcash.Payment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Inga on 20.03.2015.
 */
public class PaymentDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_PAYMENT_ID,
            MySQLiteHelper.COLUMN_PAYMENT_AMOUNT,MySQLiteHelper.COLUMN_PAYMENT_DATE,MySQLiteHelper.COLUMN_PAYMENT_CASHIER,MySQLiteHelper.COLUMN_PAYMENT_STATUS };

    public PaymentDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long createPayment(Payment payment) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_PAYMENT_AMOUNT,payment.getTotalAmount());
        values.put(MySQLiteHelper.COLUMN_PAYMENT_DATE,String.valueOf(payment.getCalendar().getTime().getTime()));
        values.put(MySQLiteHelper.COLUMN_PAYMENT_CASHIER,payment.getCashierID());
        values.put(MySQLiteHelper.COLUMN_PAYMENT_STATUS,payment.getStatus());
        long insertId = database.insert(MySQLiteHelper.TABLE_PAYMENT, null,
                values);
        return insertId;
    }

    public void deletePayment(int id) {

        System.out.println("Payment deleted with id: " + id);

        String whereClause = MySQLiteHelper.COLUMN_PAYMENT_ID + " = ?";
        String[] values = new String[]{String.valueOf(id)};

        database.delete(MySQLiteHelper.TABLE_PAYMENT, whereClause, values);
    }

    public ArrayList<Payment> getAllPayments() {
        ArrayList<Payment> payments = new ArrayList<Payment>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_PAYMENT,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Payment payment = cursorToPayment(cursor);
            payments.add(payment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return payments;
    }

    private Payment cursorToPayment(Cursor cursor) {
        Payment payment = new Payment();
        payment.setId(cursor.getInt(0));
        payment.setTotalAmount(cursor.getInt(1));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(cursor.getString(2)));
        payment.setCalendar(calendar);
        payment.setCashier(cursor.getInt(3));
        payment.setStatus(cursor.getInt(4));
        return payment;
    }

    public Payment getPaymentById(int id){
        String whereClause = MySQLiteHelper.COLUMN_PAYMENT_ID + " = ?";
        String[] values = new String[]{String.valueOf(id)};

        Cursor cursor = database.query(MySQLiteHelper.TABLE_PAYMENT,
                allColumns, whereClause, values, null, null, null);
        Payment payment = null;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            payment = cursorToPayment(cursor);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return payment;
    }

    public void deleteAllPayments(){
        database.delete(MySQLiteHelper.TABLE_PAYMENT, null, null);
        System.out.println("Payments deleted");
    }

    public void updatePayment(Payment payment){
        String whereClause = MySQLiteHelper.COLUMN_PAYMENT_ID + " = ?";
        String[] whereArgs = new String[]{String.valueOf(payment.getId())};

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_PAYMENT_AMOUNT,payment.getTotalAmount());
        values.put(MySQLiteHelper.COLUMN_PAYMENT_DATE,String.valueOf(payment.getCalendar().getTime().getTime()));
        values.put(MySQLiteHelper.COLUMN_PAYMENT_CASHIER,payment.getCashierID());
        values.put(MySQLiteHelper.COLUMN_PAYMENT_STATUS,Payment.STATUS_CANCELLED);

        database.update(MySQLiteHelper.TABLE_PAYMENT,values,whereClause,whereArgs);
    }

}
