package com.example.inga.mcash.activitiy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.inga.mcash.R;
import com.example.inga.mcash.fragment.BasketFragment;

/**
 * Created by Inga on 11.03.2015.
 */
public class BasketActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final BasketFragment basketFragment = (BasketFragment) getFragmentManager().findFragmentById(R.id.fragment_basket);
        if(basketFragment!=null) {
            Button buttonOrder = (Button) findViewById(R.id.buttonOrder);
            buttonOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long newId = basketFragment.savePayment();
                    LoginActivity.basket.removeCommodities();
                    Intent intent = new Intent(getApplicationContext(), OrdersActivity.class);
                    intent.putExtra(PaymentActivity.PAYMENT_ID, (int) newId);
                    startActivity(intent);
                    finish();
                }
            });
        }

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_basket;
    }

    @Override
    protected int getTitleResourceId() {
        return R.string.title_basket;
    }


}
