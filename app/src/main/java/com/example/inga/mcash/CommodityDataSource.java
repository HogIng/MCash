package com.example.inga.mcash;

/**
 * Created by Inga on 12.03.2015.
 */




    import java.util.ArrayList;
    import java.util.List;

    import android.content.ContentValues;
    import android.content.Context;
    import android.database.Cursor;
    import android.database.SQLException;
    import android.database.sqlite.SQLiteDatabase;

    public class CommodityDataSource {

        // Database fields
        private SQLiteDatabase database;
        private MySQLiteHelper dbHelper;
        private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
                MySQLiteHelper.COLUMN_NAME,MySQLiteHelper.COLUMN_PRICE,MySQLiteHelper.COLUMN_IMG };

        public CommodityDataSource(Context context) {
            dbHelper = new MySQLiteHelper(context);
        }

        public void open() throws SQLException {
            database = dbHelper.getWritableDatabase();
        }

        public void close() {
            dbHelper.close();
        }

        public Commodity createCommodity(String name, String img, int price) {
            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.COLUMN_NAME, name);
            values.put(MySQLiteHelper.COLUMN_PRICE,price);
            values.put(MySQLiteHelper.COLUMN_IMG,img);
            long insertId = database.insert(MySQLiteHelper.TABLE_COMMODITY, null,
                    values);
            Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMODITY,
                    allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                    null, null, null);
            cursor.moveToFirst();
            Commodity newCommodity = cursorToCommodity(cursor);
            cursor.close();
            return newCommodity;
        }

        public void deleteCommodity(long id) {

            System.out.println("Commodity deleted with id: " + id);
            database.delete(MySQLiteHelper.TABLE_COMMODITY, MySQLiteHelper.COLUMN_ID
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
            commodity.setId(cursor.getLong(0));
            commodity.setName(cursor.getString(1));
            commodity.setPrice(cursor.getInt(2));
            commodity.setImage(cursor.getString(3));
            return commodity;
        }

}
