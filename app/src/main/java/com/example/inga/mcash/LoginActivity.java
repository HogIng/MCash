package com.example.inga.mcash;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class LoginActivity extends Activity {

    public static Basket basket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        Button buttonLogin = (Button) findViewById(R.id.button1);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basket = new Basket();
                CommodityDataSource cDS = new CommodityDataSource(getApplicationContext());
                cDS.open();

                cDS.deleteCommodity(27);
                cDS.deleteCommodity(28);

                cDS.close();
                Intent intent = new Intent(getApplicationContext(), ProductsActivity.class);
                startActivity(intent);
            }
        });
    }



}
