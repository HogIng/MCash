package com.example.inga.mcash;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.math.BigDecimal;
import java.text.NumberFormat;


/**
 * Created by Inga on 08.03.2015.
 */
public class ProductsActivity extends Activity {

    private Button buttonBasket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

/*        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();*/

       /* ProductsFragment fragment = new ProductsFragment();
        fragmentTransaction.add(R.id.products_container, fragment);
        fragmentTransaction.commit();*/

        buttonBasket = (Button) findViewById(R.id.buttonBasket);
        buttonBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BasketActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume(){
      super.onResume();

        int price = LoginActivity.basket.getTotalAmount();
        BigDecimal price2 = new BigDecimal(price).movePointLeft(2);
        NumberFormat numberFormat =
                NumberFormat.getCurrencyInstance();
      buttonBasket.setText(numberFormat.format(price2));
    }
}
