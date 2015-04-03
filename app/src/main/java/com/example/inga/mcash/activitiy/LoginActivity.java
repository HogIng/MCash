package com.example.inga.mcash.activitiy;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.inga.mcash.Basket;
import com.example.inga.mcash.Cashier;
import com.example.inga.mcash.CashierDataSource;
import com.example.inga.mcash.PaymentDataSource;
import com.example.inga.mcash.ProductsActivity;
import com.example.inga.mcash.R;


public class LoginActivity extends Activity {

    public static Basket basket;
    public static Cashier cashier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        PaymentDataSource paymentDataSource = new PaymentDataSource(getApplicationContext());
        paymentDataSource.open();
        paymentDataSource.deleteAllPayments();
        paymentDataSource.close();

        Button buttonLogin = (Button) findViewById(R.id.button1);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextId = (EditText) findViewById(R.id.editText_id);
                EditText editTextPw = (EditText) findViewById(R.id.editText_pw);
                String idStr = editTextId.getText().toString();
                if(!idStr.equals("")) {
                    int id = Integer.parseInt(idStr);
                    String pw = editTextPw.getText().toString();
                    if (pw != null && (!pw.equals(""))) {
                        CashierDataSource cashierDataSource = new CashierDataSource(getApplicationContext());
                        cashierDataSource.open();
                        cashier = cashierDataSource.getCashierById(id);
                        if (cashier != null && cashier.getPw().equalsIgnoreCase(pw)) {
                            basket = new Basket();
                            Intent intent = new Intent(getApplicationContext(), ProductsActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            }
        });
    }



}
