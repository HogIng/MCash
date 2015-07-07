package com.example.inga.mcash.activitiy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.inga.mcash.Payment;
import com.example.inga.mcash.R;
import com.example.inga.mcash.dialog.BaseDialog;
import com.example.inga.mcash.fragment.PaymentDetailsFragment;
import com.example.inga.mcash.fragment.PaymentsFragment;

/**
 * Created by Inga on 23.03.2015.
 */
public class PaymentsActivity extends ListActivity {


    @Override
      public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(detailsFragment!=null){
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
                            PaymentDetailsFragment pDF = (PaymentDetailsFragment) detailsFragment;
                            pDF.cancelPayment(pDF.getPaymentSelected());
                            listFragment.selectToday();
                            listFragment.updateDataSet();
                        }
                    };
                    baseDialog.show(getFragmentManager(),"cancel_dialog");
                }
            });
        }
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_payments;
    }

    @Override
    protected int getTitleResourceId() {
        return R.string.title_payments;
    }

    @Override
    protected void setFragments() {
        listFragment = (PaymentsFragment) getFragmentManager().findFragmentById(R.id.fragmentPayments);
        detailsFragment = (PaymentDetailsFragment) getFragmentManager().findFragmentById(R.id.fragmentPayment);
    }

    @Override
    protected void startDetailsActivity(Payment payment) {
        Intent intent = new Intent(getApplicationContext(), PaymentDetailsActivity.class);
        intent.putExtra(PaymentDetailsActivity.PAYMENT_ID, payment.getId());
        startActivity(intent);
        finish();
    }

}
