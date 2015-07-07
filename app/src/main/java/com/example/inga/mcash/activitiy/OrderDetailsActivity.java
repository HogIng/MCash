package com.example.inga.mcash.activitiy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.inga.mcash.Payment;
import com.example.inga.mcash.R;
import com.example.inga.mcash.fragment.OrderDetailsFragment;

public class OrderDetailsActivity extends DetailsActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button buttonDeleteOrder = (Button) findViewById(R.id.button_cancel);
        buttonDeleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OrderDetailsFragment)detailsFragment).deleteOrder();
                Intent intent = new Intent(getApplicationContext(), OrdersActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    protected int getLayoutResourceId() {
        return R.layout.activity_orderdetails;
    }

    protected int getTitleResourceId() {
        return R.string.order;
    }

    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), OrdersActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected int getDetailsFragmentId() {
        return R.id.fragmentOrder;
    }
}
