package com.example.inga.mcash.activitiy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.inga.mcash.Commodity;
import com.example.inga.mcash.Discount;
import com.example.inga.mcash.database.CommodityDataSource;
import com.example.inga.mcash.Payment;
import com.example.inga.mcash.database.PaymentDataSource;
import com.example.inga.mcash.PaymentPosition;
import com.example.inga.mcash.database.PaymentPositionDataSource;
import com.example.inga.mcash.R;
import com.example.inga.mcash.dialog.BaseDialog;
import com.example.inga.mcash.fragment.PaymentFragment;

import java.util.ArrayList;

/**
 * Created by Inga on 20.03.2015.
 */

public class PaymentActivity extends BaseActivity {

    public static Payment payment;
    public static final String PAYMENT_ID = "paymentid";
    private PaymentFragment paymentFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        paymentFragment = (PaymentFragment) getFragmentManager().findFragmentById(R.id.fragmentPayment);

        //get selected payment from intent
        int paymentId = getIntent().getIntExtra(PAYMENT_ID,0);
        PaymentDataSource pDS = new PaymentDataSource(getApplicationContext());
        pDS.open();
        payment = pDS.getPaymentById(paymentId);
        pDS.close();

        Button buttonCancel = (Button) findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseDialog baseDialog = new BaseDialog() {
                    @Override
                    protected int getLayoutResourceId() {
                        return R.layout.dialog_cancellation;
                    }

                    @Override
                    protected int getTitleResourceId() {
                        return R.string.cancellation;
                    }

                    @Override
                    protected void doPositiveAction() {
                        paymentFragment.cancelPayment(paymentFragment.getPaymentSelected());
                    }
                };

                baseDialog.show(getFragmentManager(),"cancel_dialog");
            }
        });

        setPayment(payment);
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

    protected void setPayment(Payment payment1){
        paymentFragment.setPayment(payment1);
    }
}
