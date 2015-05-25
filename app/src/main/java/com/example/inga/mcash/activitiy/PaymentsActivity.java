package com.example.inga.mcash.activitiy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.inga.mcash.Payment;
import com.example.inga.mcash.R;
import com.example.inga.mcash.adapter.PaymentListViewAdapter;
import com.example.inga.mcash.dialog.BaseDialog;
import com.example.inga.mcash.fragment.PaymentFragment;
import com.example.inga.mcash.fragment.PaymentsFragment;

/**
 * Created by Inga on 23.03.2015.
 */
public class PaymentsActivity extends BaseActivity {

    public static Payment payment;
    View selectedView;
    private PaymentsFragment paymentsFragment;
    private PaymentFragment paymentFragment;

    @Override
      public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        paymentFragment = (PaymentFragment) getFragmentManager().findFragmentById(R.id.fragmentPayment);
        paymentsFragment = (PaymentsFragment) getFragmentManager().findFragmentById(R.id.fragmentPayments);


        ListView listView = (ListView) findViewById(R.id.listView_payments);
        final PaymentListViewAdapter paymentListViewAdapter =(PaymentListViewAdapter) listView.getAdapter();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3) {
                Payment payment = (Payment) adapter.getItemAtPosition(position);
                if (paymentFragment != null) {
                    if(selectedView == null){
                        selectedView = paymentListViewAdapter.getFirstView();
                    }
                    selectedView.setBackgroundColor(getResources().getColor(R.color.grey_light_background));
                    v.setBackgroundColor(getResources().getColor(R.color.grey_background));
                    selectedView = v;
                    paymentFragment.setPayment(payment);
                } else {
                    Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                    intent.putExtra(PaymentActivity.PAYMENT_ID, payment.getId());
                    startActivity(intent);
                    finish();
                }
            }
        });

        Button buttonBack = (Button) findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentsFragment.selectDayBefor();
                if(paymentFragment!=null){
                    setPaymentToPaymentFragment();
                }
           }
        });

        Button buttonNext = (Button) findViewById(R.id.button_next);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentsFragment.selectNextDay();
                if(paymentFragment!=null) {
                    setPaymentToPaymentFragment();
                }
            }
        });

        if(paymentFragment!=null){

            if(paymentsFragment.getPaymentsSelected().size()==0){
                paymentFragment.showEmptyView();
            }
            else{
                paymentFragment.setPayment(paymentsFragment.getPaymentsSelected().get(0));
            }

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
                            paymentsFragment.selectToday();
                            paymentsFragment.updateDataSet();
                        }
                    };

                    baseDialog.show(getFragmentManager(),"cancel_dialog");
                }
            });

        }



    }

    private void showCancelDialog(){

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_payments;
    }

    @Override
    protected int getTitleResourceId() {
        return R.string.title_payments;
    }

    private void setPaymentToPaymentFragment(){
        if(paymentsFragment.getPaymentsSelected().size()>0){
            paymentFragment.setPayment(paymentsFragment.getPaymentsSelected().get(0));
            paymentFragment.hideEmptyView();
        }
        else{
            paymentFragment.showEmptyView();
        }
    }


}
