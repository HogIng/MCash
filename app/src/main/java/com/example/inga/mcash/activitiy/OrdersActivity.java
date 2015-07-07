package com.example.inga.mcash.activitiy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.inga.mcash.Payment;
import com.example.inga.mcash.R;
import com.example.inga.mcash.fragment.OrderDetailsFragment;
import com.example.inga.mcash.fragment.OrdersFragment;


public class OrdersActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (detailsFragment != null) {
            Button buttonDeleteOrder = (Button) findViewById(R.id.button_cancel);
            buttonDeleteOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((OrderDetailsFragment) detailsFragment).deleteOrder();
                    listFragment.updateDataSet();

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

    @Override
    protected void setFragments() {
        listFragment = (OrdersFragment) getFragmentManager().findFragmentById(R.id.fragmentOrders);
        detailsFragment = (OrderDetailsFragment) getFragmentManager().findFragmentById(R.id.fragmentOrder);
    }

    @Override
    protected void startDetailsActivity(Payment payment) {
        Intent intent = new Intent(getApplicationContext(), OrderDetailsActivity.class);
        intent.putExtra(PaymentDetailsActivity.PAYMENT_ID, payment.getId());
        startActivity(intent);
        finish();
    }

}
