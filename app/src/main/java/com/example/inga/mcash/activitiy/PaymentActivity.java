package com.example.inga.mcash.activitiy;

import android.content.Intent;
import android.os.Bundle;

import com.example.inga.mcash.Commodity;
import com.example.inga.mcash.Discount;
import com.example.inga.mcash.database.CommodityDataSource;
import com.example.inga.mcash.Payment;
import com.example.inga.mcash.database.PaymentDataSource;
import com.example.inga.mcash.PaymentPosition;
import com.example.inga.mcash.database.PaymentPositionDataSource;
import com.example.inga.mcash.R;

import java.util.ArrayList;

/**
 * Created by Inga on 20.03.2015.
 */

public class PaymentActivity extends BaseActivity {

    public static Payment payment;
    public static final String PAYMENT_ID = "paymentid";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get selected payment from intent
        int paymentId = getIntent().getIntExtra(PAYMENT_ID,0);
        PaymentDataSource pDS = new PaymentDataSource(getApplicationContext());
        pDS.open();
        payment = pDS.getPaymentById(paymentId);
        pDS.close();



        }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_payment;
    }

    @Override
    protected int getTitleResourceId() {
        return R.string.title_paymentdetails;
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), PaymentsActivity.class);
        startActivity(intent);
        finish();
    }
}
