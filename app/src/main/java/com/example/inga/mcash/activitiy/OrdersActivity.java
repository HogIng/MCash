package com.example.inga.mcash.activitiy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.inga.mcash.Commodity;
import com.example.inga.mcash.Payment;
import com.example.inga.mcash.R;
import com.example.inga.mcash.database.PaymentPositionDataSource;
import com.example.inga.mcash.fragment.OrderFragment;
import com.example.inga.mcash.fragment.OrdersFragment;

import java.util.ArrayList;


public class OrdersActivity extends BaseActivity {

    View selectedView;
    private OrderFragment orderFragment;
    private OrdersFragment ordersFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ordersFragment = (OrdersFragment) getFragmentManager().findFragmentById(R.id.fragmentOrders);
        orderFragment = (OrderFragment) getFragmentManager().findFragmentById(R.id.fragmentOrder);
        ListView listView = (ListView) findViewById(R.id.listView_payments);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3) {
                Payment payment = (Payment) adapter.getItemAtPosition(position);
                if (orderFragment != null) {
                    if (selectedView != null) {
                        selectedView.setBackgroundColor(getResources().getColor(R.color.grey_lightest));
                    }
                    v.setBackgroundColor(getResources().getColor(R.color.grey_background));
                    selectedView = v;
                    orderFragment.setPayment(payment);
                } else {
                    Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
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
                ordersFragment.selectDayBefor();
                if(orderFragment!=null){
                    setPaymentToOrderFragment();
                }
            }
        });

        Button buttonNext = (Button) findViewById(R.id.button_next);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ordersFragment.selectNextDay();
                if(orderFragment!=null){
                    setPaymentToOrderFragment();
                }
            }
        });





        if(orderFragment!=null){
            if(ordersFragment.getPaymentsSelected().size()==0){
                orderFragment.showEmptyView();
            }
            else{
                orderFragment.setPayment(ordersFragment.getPaymentsSelected().get(0));
            }
            Button buttonDeleteOrder = (Button) findViewById(R.id.button_cancel);
            buttonDeleteOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    orderFragment.deleteOrder();
                    ordersFragment.updateDataSet();
                    setPaymentToOrderFragment();
                }
            });
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_orders;
    }

    @Override
    protected int getTitleResourceId() {
        return R.string.orders;
    }

    private void setPaymentToOrderFragment(){
        if(ordersFragment.getPaymentsSelected().size()>0){
            orderFragment.setPayment(ordersFragment.getPaymentsSelected().get(0));
            orderFragment.hideEmptyView();
        }
        else{
            orderFragment.showEmptyView();
        }
    }
}
