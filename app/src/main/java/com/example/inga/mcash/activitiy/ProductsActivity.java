package com.example.inga.mcash.activitiy;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.inga.mcash.EuroFormat;
import com.example.inga.mcash.R;
import com.example.inga.mcash.activitiy.BasketActivity;
import com.example.inga.mcash.activitiy.LoginActivity;


/**
 * Created by Inga on 08.03.2015.
 */
public class ProductsActivity extends BaseActivity {

    private Button buttonBasket;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                finish();
            }
        });

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_products;
    }

    @Override
    protected int getTitleResourceId() {
        return R.string.title_products;
    }

    @Override
    protected void onResume() {
        super.onResume();
        buttonBasket.setText(new EuroFormat().formatPrice(LoginActivity.basket.getTotalAmount()));

    }



}
