package com.example.inga.mcash.activitiy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.inga.mcash.Payment;
import com.example.inga.mcash.R;
import com.example.inga.mcash.database.PaymentDataSource;
import com.example.inga.mcash.dialog.BaseDialog;
import com.example.inga.mcash.fragment.DetailsFragment;
import com.example.inga.mcash.fragment.PaymentDetailsFragment;

/**
 * Created by Inga on 03.07.2015.
 */
public abstract class DetailsActivity extends BaseActivity {

    public static final String PAYMENT_ID = "paymentid";
    protected DetailsFragment detailsFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailsFragment = (DetailsFragment) getFragmentManager().findFragmentById(getDetailsFragmentId());
        //get selected payment from intent
        int paymentId = getIntent().getIntExtra(PAYMENT_ID,0);
        PaymentDataSource pDS = new PaymentDataSource(getApplicationContext());
        pDS.open();
        Payment payment = pDS.getPaymentById(paymentId);
        pDS.close();
        detailsFragment.setPayment(payment);
    }

    @Override
    protected int getLayoutResourceId() {
        return 0;
    }

    @Override
    protected int getTitleResourceId() {
        return 0;
    }


    protected abstract int getDetailsFragmentId();
}
