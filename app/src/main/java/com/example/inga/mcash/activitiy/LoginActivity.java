package com.example.inga.mcash.activitiy;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.inga.mcash.Basket;
import com.example.inga.mcash.Cashier;
import com.example.inga.mcash.R;
import com.example.inga.mcash.database.CashierDataSource;
import com.example.inga.mcash.database.CommodityDataSource;
import com.example.inga.mcash.database.MySQLiteHelper;


public class LoginActivity extends Activity {

    public static Basket basket;
    public static Cashier cashier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(screenIsLarge()){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

//        updateDB();

        Button buttonLogin = (Button) findViewById(R.id.button1);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextId = (EditText) findViewById(R.id.editText_id);
                EditText editTextPw = (EditText) findViewById(R.id.editText_pw);

                String idStr = editTextId.getText().toString();
                String pw = editTextPw.getText().toString();

                if (loginCorrect(pw, idStr)) {
                    basket = new Basket();
                    Intent intent = new Intent(getApplicationContext(), ProductsActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    findViewById(R.id.textView_error).setVisibility(View.VISIBLE);
                    findViewById(R.id.imageView_error).setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void updateDB() {
        MySQLiteHelper mySQLiteHelper = new MySQLiteHelper(this);
        mySQLiteHelper.onUpgrade(mySQLiteHelper.getWritableDatabase(), 4, 5);
        CommodityDataSource commodityDataSource = new CommodityDataSource(this);
        commodityDataSource.open();
        commodityDataSource.createCommodity("Getränke", "", 0, 0, 1,null);
        commodityDataSource.createCommodity("Kaffeegetränke", "", 0, 1, 1,null);
        commodityDataSource.createCommodity("Cappuccino", "cappuccino", 280, 2, 0,"00457217");
        commodityDataSource.createCommodity("Fruchtshakes", "fruchtshakes", 0, 1, 1,null);
        commodityDataSource.createCommodity("Schokoladenkuchen", "schokokuchen", 325, 0, 0,"84690029");
        commodityDataSource.createCommodity("Grüner Smoothie", "smoothie_gruen", 250, 4, 0,"06300036");
        commodityDataSource.createCommodity("Smootie Rotebeete", "smoothie_rotebeete", 275, 4, 0,"51700041");
        commodityDataSource.createCommodity("Latte Macchiato", "lattemacchiato", 295, 2, 0,"62877053");
        commodityDataSource.createCommodity("Esspresso", "esspresso", 195, 2, 0,"01083064");
        commodityDataSource.createCommodity("Erdnüsse", "erdnuesse", 200, 0, 0,"21030079");
        commodityDataSource.createCommodity("Tee versch. Sorten", "tee", 180, 1, 0,"90650086");
        commodityDataSource.close();
        CashierDataSource cashierDataSource = new CashierDataSource(this);
        cashierDataSource.open();
        cashierDataSource.createCashier("Max", "Mustermann", "1234");
        cashierDataSource.close();

    }

    private boolean loginCorrect(String pw, String idStr) {
        if (!idStr.equals("") && !pw.equals("")) {
            int id = Integer.parseInt(idStr);
            CashierDataSource cashierDataSource = new CashierDataSource(getApplicationContext());
            cashierDataSource.open();
            cashier = cashierDataSource.getCashierById(id);
            if (cashier != null && cashier.getPw().equalsIgnoreCase(pw)) {
                return true;
            }
        }
        return false;
    }

    public boolean screenIsLarge() {
        if ((getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE) {
            return true;
        }
        return false;
    }


}
