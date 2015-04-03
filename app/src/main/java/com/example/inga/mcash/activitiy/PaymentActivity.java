package com.example.inga.mcash.activitiy;

import android.app.Activity;
import android.os.Bundle;

import com.example.inga.mcash.Payment;
import com.example.inga.mcash.PaymentDataSource;
import com.example.inga.mcash.R;
import com.example.inga.mcash.activitiy.PayActivity;

/**
 * Created by Inga on 20.03.2015.
 */
public class PaymentActivity extends Activity {

    public static Payment payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        int paymentId = getIntent().getIntExtra(PayActivity.PAYMENT_ID,0);
        PaymentDataSource pDS = new PaymentDataSource(getApplicationContext());
        pDS.open();
        payment = pDS.getPaymentById(paymentId);
        pDS.close();

    }
}
