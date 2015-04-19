package com.example.inga.mcash.activitiy;

import android.content.Intent;
import android.os.Bundle;

import com.example.inga.mcash.R;

public class OrderActivity extends PaymentActivity{

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected int getLayoutResourceId() {
        return R.layout.activity_order;
    }

    protected int getTitleResourceId() {
        return R.string.order;
    }

    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), OrdersActivity.class);
        startActivity(intent);
        finish();
    }
}
