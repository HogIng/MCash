package com.example.inga.mcash.activitiy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.inga.mcash.Payment;
import com.example.inga.mcash.database.PaymentDataSource;
import com.example.inga.mcash.R;
import com.example.inga.mcash.dialog.BaseDialog;
import com.example.inga.mcash.fragment.PaymentDetailsFragment;

/**
 * Created by Inga on 20.03.2015.
 */

public class PaymentDetailsActivity extends DetailsActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                        ((PaymentDetailsFragment)detailsFragment).cancelPayment(detailsFragment.getPaymentSelected());
                    }
                };

                baseDialog.show(getFragmentManager(), "cancel_dialog");
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_paymentdetails;
    }

    @Override
    protected int getTitleResourceId() {
        return R.string.title_paymentdetails;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), PaymentsActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected int getDetailsFragmentId() {
        return R.id.fragmentPayment;
    }

}