package com.example.inga.mcash;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/**
 * Created by Inga on 08.03.2015.
 */
public class ProductsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

/*        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();*/

       /* ProductsFragment fragment = new ProductsFragment();
        fragmentTransaction.add(R.id.products_container, fragment);
        fragmentTransaction.commit();*/

        Button buttonBasket = (Button) findViewById(R.id.buttonBasket);
        buttonBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BasketActivity.class);
                startActivity(intent);
            }
        });

    }
}
